<!--
组员：杨万胜
功能：首页、展示合集
-->
<template>
    <div>
      <NavBar activeIndex2="1" :myStyle="'width:75%;margin:auto;'"/>
      <div class="myBody" style="width:80%;margin:auto;margin-top:20px;">
        <!-- <VideoList></VideoList> -->
        <div style="margin: auto; width: 90%;">
          <div class="card" style=" width: 100%; height: 100%; display: inline-block; border-radius: 5px; margin-bottom: 10px;" >
            <div style="padding:20px;">
              <div class="block">
                <span class="demonstration" style="margin-right:10px;color: #00a5e8;font-weight: bold;">分类</span>
                <el-cascader
                  placeholder="点击搜索"
                  :options="options"
                  @change="handleChange"
                  filterable
                  clearable
                  ></el-cascader>
              </div>
             
            </div>
          </div>
          <div class="card" style=" width: 100%; height: 100%; display: inline-block; border-radius: 5px; margin-bottom: 0px;" >
            <div style="padding: 0 30px;padding-top: 10px;">
              
              <!-- <BinnerPicture :topVideos="topVideos"></BinnerPicture> -->
              <BinnerPicture2 :topVideos="topVideos"></BinnerPicture2>
            </div>
          </div>
          
          <div class="card" style=" width: 100%; height: 100%; display: inline-block; border-radius: 5px; margin-bottom: 150px;" >
            <div style="padding:20px;">
              <GroupList :props_="videoGroupProp"/>
            </div>
          </div>
        </div>
        <!-- <router-view name="componentName"></router-view> -->
        <div style="height:100%;width:200px;">
          <div style="background-color:green;height:50%;width:100px;margin:auto;"></div>
          <div style="background-color:green;height:50%;width:100px;margin:auto;"></div>
          <div style="background-color:green;height:50%;width:100px;margin:auto;"></div>
        </div>
      </div>
    </div>
  </template>
  <script>
    import NavBar from './NavBar.vue';
    // import BinnerPicture from './index/bannerPicture_old.vue' ;
    import BinnerPicture2 from './index/BinnerPicture2.vue' ;
    import GroupList from './personal/groupList.vue'; 
    
    import { mapActions, mapState } from 'vuex';
    export default {
      name: 'IndexPage',
      components:{
        NavBar,GroupList,
        // BinnerPicture,
        BinnerPicture2
        // VideoList
      },
      data(){
        return {
          cascader:{},
          currentCatagoryIndex:0,
          videoGroupData:{},//接收返回的合集数据
          videoGroupProp:{}
        }
      },
      watch:{
        videoGroupData:{
          deep:true,
          immediate:true,
          handler(){
            console.log("这是index，videoGroupdata变了");
            this.freshGroup({
              range:"freshGroup"//对合集数据进行刷新
            });
          }
        }
      },
      computed:{
        options(){
          return this.$userUtils.groupOption();
        },
        ...mapState("indexPage",["topVideos"])
      },
      created(){
        //处理groupList的事件，获取合集数据
        this.$bus.$on(this.$myEvents.REQUEST_GROUP, (fresh)=>{
          console.log("收到通知：获取合集",this.$myEvents.REQUEST_GROUP,fresh);
          if(fresh.range==="freshGroup"){
            console.log("加载freshGroup");
            this.requestGroup(fresh);
          }
        });
        //处理 userUtils.requestGroup 获取到数据后的通知
        this.$bus.$on(this.$myEvents.FRESH_GROUP,this.freshGroup);

        //获取top10
        this.initIndexData();
      },
      beforeDestroy(){
        this.$bus.$off(this.$myEvents.REQUEST_GROUP);
        this.$bus.$off(this.$myEvents.FRESH_GROUP);
      },
      mounted(){
        console.log("mounted ,获取合集")
        this.requestGroup({
          range:"freshGroup"//对合集数据进行刷新
        });
        console.log("indexPage 查看当前页的router",this.$router.app._router)
      },
      methods:{ 
        freshGroup(fresh){
          console.log("fresh=",fresh);
          if(fresh.range === "freshGroup"){
            this.videoGroupProp={};//这样组件里的监视属性就能捕获变化
            //浅复制数组
            this.videoGroupProp.videos= Object.assign([],this.videoGroupData.indexVideoGroup);
            this.videoGroupProp.coverHeight = "150px";
            console.log("更新合集",this.videoGroupData);
            this.videoGroupProp.fresh={
              range:"freshGroup"
            }
          }
          
        },
        getData(type,index){
          console.log(type);
          this.currentCatagoryIndex = index;
        },
        getCatagoryBlockBg(index){
          if(index == this.currentCatagoryIndex){
            return "background-color:#87acdd;color:white;";
          }
        },
        requestGroup(fresh){
          let data = {
            isIndex:true
          };
          this.$userUtils.requestGroup(this,data,fresh);
        },
        handleChange(value) {
          // console.log(value);
          // this.cascader.catagory=value;
          // console.log("选择的内容：",value);
          //筛选视频
          if(value.length>0){
            this.videoGroupProp.videos.splice(0);
            let a = this.videoGroupData.indexVideoGroup.filter((item)=>{
              let arr = JSON.parse(item.catagory);
              for(let i = 0 ;i<arr.length;i++){
                
                if(arr[i]==value.join("/")){
                  console.log("arr[i],value.join('/')",arr[i],value.join('/'));
                  this.videoGroupProp.videos.push(item);
                  return true;
                }
              }
              return false;
            });
            console.log("筛选完成了：",a)
          }else{
            //清空选择时，显示所有
            //浅复制数组
            let temp = Object.assign([],this.videoGroupData.indexVideoGroup);
            // 清空数组
            this.videoGroupProp.videos.splice(0);
            temp.forEach((item)=>{
              this.videoGroupProp.videos.push(item);
            })
          }
        },
        ...mapActions("indexPage",["initIndexData"])
      }
    }
  </script>
  
<style>
  
</style>