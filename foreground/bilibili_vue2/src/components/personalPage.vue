<!--
组员：孙然
功能：个人中心页面
-->
<template>
  <div style="width:100%;height:100%;margin-bottom:400px;">
    <NavBar :activeIndex2="getNavIndex()" :myStyle="'width:75%;margin:auto;'"></NavBar>
    <div style="width:72%;margin:auto;">
      <div class="addbg">
        
        <div style="padding:10px 30px;margin-top: 100px; display: inline-flex; justify-content: center; align-items: center;">
          <img :src="getCurrentIcon()" style="float:left;height:50px;width:50px;border: 1px solid gray;border-radius:50%;object-fit: cover;"/>
          <div style="float: left; margin-left: 20px;">
            <span style="display:block;width:90px;color:white;">{{user.name}}</span>
            <span style="display:block;width:90px;color:white;font-size:10px;margin-top:6px;">性别&nbsp;{{user.sex==1?"男":"女"}}</span>
          </div>
          <div v-show="!isSelf" style="float: right; margin-left: 20px;">
            <!-- <span style="display:block;width:90px;color:white;"><el-button>关注</el-button></span> -->
            <el-tooltip class="item" effect="dark" :content="isConcerned?'点击取消关注':'点击关注'" placement="bottom">
              <el-button @click.native="concern()">{{ isConcerned?'已':'' }}关注</el-button>
            </el-tooltip>
            
          </div>

        </div>
      
        <div style="width:100%;box-shadow: rgba(0, 0, 0, 0.7) 0px -20px 70px 20px;"></div>
      </div>
      <div class="card" style="width:100%;background-color: white;display: inline-block;">
        <ul style="list-style: none;margin:10px;padding:5px;">
          <li :class="tabPosition==index?'tabs current':'tabs'" v-for="(tab,index) in tabs" :key="index">
            <div class="tab">
              <span>{{ tab.title }}</span>
              </div>
          </li>
          <!-- <li class="tabs">
            <div class="tab">
              <span >主页</span>
            </div>
          </li> -->
          
          <!-- 统计信息 -->
          <li class="statistics" v-for="(item,index2) in statistics" :key="'statistics'+index2">
            <div>
              <span>{{ item.title +" "+item.num}}</span>
            </div>
          </li>

        </ul>
      </div>
      <div class="card" style="width:100%;background-color: white;display: inline-block;margin-top:10px;">
        <div style="padding:10px;">
          <el-row class="tac">
          <el-col :span="12">
          <el-menu default-active="0-1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose"  >
            <!-- <el-menu-item index="0" @click="switchComponent(myComponents.PersonInfo)">
              <i class="el-icon-menu"></i>
              <span slot="title">资料</span>
              <el-menu-item index="0-1">个人资料</el-menu-item>
              <el-menu-item index="0-2">修改密码</el-menu-item>
            </el-menu-item> -->

            <el-submenu index="0">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span slot="title">资料</span>
              </template>
              <el-menu-item-group>
                <!-- <span slot="title">个人信息</span> -->
                <el-menu-item index="0-1" @click="personInfo()">个人信息</el-menu-item>
                <el-menu-item index="0-2" @click="personEditPassword()" v-if="isSelf" >修改密码</el-menu-item>
                <el-menu-item index="0-3" @click="uploadIcon()" v-if="isSelf" >上传头像</el-menu-item>
              </el-menu-item-group>
            </el-submenu>

            <el-submenu index="1">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span slot="title">合集</span>
              </template>
              <el-menu-item-group>
                <span slot="title">发布</span>
                <el-menu-item index="1-1" @click="showGroupList('all')">全部</el-menu-item>
                <el-menu-item index="1-2" v-if="isSelf" @click="showGroupList('wait')">待审核</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group title="收藏" v-if="isSelf">
                <el-menu-item index="1-3" @click.native="showGroupList('collect')">收藏</el-menu-item>
              </el-menu-item-group>

              <el-menu-item-group title="我赞过的" v-if="isSelf">
                <el-menu-item index="1-4" @click.native="showGroupList('like')">我赞过的</el-menu-item>
              </el-menu-item-group>

              <!-- <el-submenu index="1-4">
                <span slot="title">赞过的视频</span>
                <el-menu-item index="1-4-1">选项1</el-menu-item>
              </el-submenu> -->
            </el-submenu>
            <!-- <el-menu-item index="2">
              <i class="el-icon-menu"></i>
              <span slot="title">导航二</span>
            </el-menu-item>
            <el-menu-item index="3" disabled>
              <i class="el-icon-document"></i>
              <span slot="title">导航三</span>
            </el-menu-item>
            <el-menu-item index="4">
              <i class="el-icon-setting"></i>
              <span slot="title">导航四</span>
            </el-menu-item> -->
          </el-menu>
          </el-col>
          <el-col :span="15">
            <div style="width:100%;padding:20px;border:1px solid gray;border:none;">
              <component :is="currentComponent" :props_="currentProps"></component>
            </div>
          </el-col>
          </el-row>
        </div>
      </div>
    </div>

    
  </div>
</template>

<script>
 import NavBar from './NavBar.vue';
 import PersonInfo from './personal/personInfo.vue';
 import GroupList from './personal/groupList.vue'; 
 import UploadIcon from './personal/uploadIcon.vue' ;
 const myComponents = {
    PersonInfo,GroupList,UploadIcon
  }
export default {
    name:"PersonalPage",
    components:{
        NavBar
    },
    data(){
      return {
        isConcerned:false,
        interaction:{},
        user:{},
        isSelf:false,
        tabPosition:0,
        tabs:[
          {
            title:"主页"
          },
          // {
          //   title:"其它"
          // }
        ],
        statistics:[
          {
            title:"关注",
            num:1
          },
          {
            title:"粉丝",
            num:2
          },
          {
            title:"获赞",
            num:3
          }

        ],
        statisticsDate:{},
        currentComponent:null,
        myComponents : myComponents,
        currentProps:{
          name:"hello",
          age:23,
          hobbys:["1",2,'3']
        },
        videoGroupData:{}
      }
    },
    watch:{
      user:{
        deep:true,
        immediate:true,
        handler(){
          
        }
      }
    },
    async created(){
      const { userId,isSelf } = this.$route.query;//获取传递过来的参数
      console.log(userId,isSelf);
      let me=this.$userUtils.getUser();
      if(isSelf=="true" || me.id==userId){
        this.user={...me};
        this.isSelf=true;
        //获取本人相关的合集，本人的合集（包括待审核的，收藏的）
        this.requestGroup();
      }else{
        console.log("获取用户信息");
        await this.getAuthor(userId);
        this.isSelf=false;
        //获取其他用户相关的合集（他的合集、他的收藏）
      }
      console.log("绑定事件",this.$myEvents.REQUEST_GROUP)
      console.log(this.user)
      //初始显示的是用户页面
      // 设置用户信息
      this.currentComponent=this.myComponents.PersonInfo;
      this.currentProps={
        user:this.user,
        isSelf:this.isSelf
      };


      this.$bus.$on(this.$myEvents.REQUEST_GROUP, this.requestGroup);
      //处理 userUtils.requestGroup 获取到数据后的通知
      this.$bus.$on(this.$myEvents.FRESH_GROUP,this.freshGroup);

      // 获取点赞，粉丝信息
      this.getStatistics({
        authorId:this.user.id
      });
      console.log("userId = " + userId + (isSelf=="true" ? "是本人" : "不是本人"));
      
    },
    beforeDestroy(){
      this.$bus.$off(this.$myEvents.REQUEST_GROUP);
      this.$bus.$off(this.$myEvents.FRESH_GROUP);
    },
    methods: {
      getNavIndex(){
        if(this.isSelf){
          return '6-1'
        }
        return '';
      },
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      switchComponent(component) {
        // 动态添加子组件
        this.currentComponent = component;
        console.log("currentcomponent=",component.name);
      },
      personInfo(){
        this.switchComponent(myComponents.PersonInfo);
        this.currentProps={
          user:this.user,
          isSelf:this.isSelf,
          editPassword:false
        };
      },
      personEditPassword(){
        this.switchComponent(myComponents.PersonInfo);
        this.currentProps={
          user:this.user,
          isSelf:this.isSelf,
          editPassword:true
        };
      },
      showGroupList(range){
        this.currentProps={};
        this.switchComponent(myComponents.GroupList);
        if(range == "all"){
          console.log("all")
          console.log(this.videoGroupData.myVideoGroup)
          this.currentProps.videos= this.videoGroupData.myVideoGroup;
          this.currentProps.fresh={
            range:"all"
          }
          this.currentProps.showDel=this.isSelf;
        }else if(range == "collect"){
          this.currentProps.videos=this.videoGroupData.myCollect;
          this.currentProps.fresh={
            range:"collect"
          }
        } else if(range == "like"){
          this.currentProps.videos=this.videoGroupData.myLike;
          this.currentProps.fresh={
            range:"like"
          }
        } else if(range == "wait"){
          this.currentProps.videos=this.videoGroupData.myWait;
          this.currentProps.fresh={
            range:"wait"
          }
        } else {
          this.currentProps={};
        }
      },
      requestGroup(fresh){
        console.log("personalPage requestGroup")
        let data = {
          byPerson:true,
          userId:this.user.id,
          isSelf:this.isSelf
        };
        this.$userUtils.requestGroup(this,data,fresh);
      },
      freshGroup(fresh){
        this.showGroupList(fresh.range);
        
      },
      uploadIcon(){
        this.currentProps={};
        this.switchComponent(myComponents.UploadIcon);
        // this.$userUtils.uploadIcon(this);
      },
      getCurrentIcon(){
        let data={
          userid:this.user.id,
          type:"icon"
        }
        return this.$userUtils.getUserIcon(this,data);
      },
      async getAuthor(authroId){
        let res = await this.$userUtils.getAuthor(this,{authorId:parseInt(authroId)});
        console.log(res);
        if(res.data.code==1){
          this.user = res.data.data;
          //拿到用户信息，获取合集
          this.requestGroup();//初始状态不需要更新,获取数据即可
        }else{
          this.$confirm("获取用户信息失败！");
        }
      },
      async concern(){
        let data = {
          authorId:this.user.id
        }
        let res = await this.$userUtils.concern(this,data);
        if(res.data.code==1){
          // this.isConcerned=res.data.data;
          this.getStatistics({ authorId:this.user.id });
        }else{
          this.$message.warning(res.data.msg);
        }
      },
      async getStatistics(data){
        let res = await this.$userUtils.getStatistics(this,data);
        if(res.data.code==1){
          this.statistics=res.data.data.statistics;
          this.isConcerned=res.data.data.concernState;
        }
      }
      

    },
    mounted(){
      // this.currentComponent=this.myComponents.PersonInfo;
      // this.currentProps={
      //   user:this.user,
      //   isSelf:this.isSelf
      // };
    }
}
</script>

<style scoped>
/* @import url('../assets/personalbg.png'); */
  .tabs {
    border-bottom: 3px solid rgb(255, 255, 255);
    float: left;
    padding-bottom: 10px;
    margin: 10px;
    cursor: pointer;
    color:black;
    margin-bottom:0;
    user-select: none;
  }
  .statistics {
    border-bottom: 3px solid rgb(255, 255, 255);
    float: right;
    padding-bottom: 10px;
    margin: 10px;
    cursor: pointer;
    color:black;
    margin-bottom:0;
    user-select: none;
  }

  .statistics div {
    display:flex;
  }
  .tabs.current {
    border-bottom: 3px solid #00a1d6;;
    color: skyblue;
  }
  .tabs:hover {
    border-bottom: 3px solid #00a1d6;;
    color: skyblue;
  }
  .addbg {
    width:100%;
    /* background-image: url(https://i0.hdslb.com/bfs/space/cb1c3ef50e22b6096fde67febe863494caefebad.png@2560w_400h_100q_1o.webp); */
    background-image: url(../assets/personalbg.png);
    background-repeat: no-repeat;
    background-size: cover;
    width:100%;
    overflow: hidden;
  }
  /* 折叠菜单 */
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
  .el-menu-item.is-active {
    color: #409EFF;
    background-color: #c7e2ff;
}
.el-col-12 {
    width: unset;
}
</style>