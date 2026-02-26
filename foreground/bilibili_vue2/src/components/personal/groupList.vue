<!--
组员：yws
功能：合集组件
-->
<template>
  <div>
      <!-- <el-video src="https://v1.kwaicdn.com/upic/2022/12/03/19/BMjAyMjEyMDMxOTMwMjRfMTQ4MDIxMDkzMl85MDI0NzkxODc4MV8yXzM=_b_B0b1a2849fd1298a171288bbe7266509b.mp4?pkey=AAWWSIo55gzJxk7m4AiN0ybDsNRIFvJtdmQ7V6D0zeLu9YaLg9LxVuvVlFssSKID4vgCOaMrx7bdwW34xxGZOx93tdVuzrqSHHnLSaQtuahdZvSg4raP4SZAIHNnCrj3pPM" 
      controls>
    </el-video> -->
    <!-- <div>
      <VideoPlayer :options="playerOptions"></VideoPlayer>
    </div> -->
    <el-empty :image-size="200" v-if="empty.isEmpty" 
    :description="empty.description" 
    v-loading="isLoading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.2)"
    >
      <el-button icon="el-icon-refresh" circle title="刷新" @click.native="showLoading"  ></el-button>
    </el-empty>
    <el-row type="flex" justify="center"  v-for="(row) in rows" :key="'row'+row">
      
      <el-col :span="span" v-for="(col) in colums" :key="'col'+col" >
        <!-- {{ arr[(row-1)*colums+(col-1)].name}} -->
        <transition name="hello">
        <div :ref="'group'+((row-1)*colums+(col-1))">
      
        <div v-if="arr2[(row-1)*colums+(col-1)]!=null" class="grid-content bg-purple" style="margin: 10px;" >
            <div style="width:100%;position:relative;border:1px solid #f2f2f2">
              <a  style="display:flex;justify-content: center;align-items: center;" @click="goToVideoPage( arr2[(row-1)*colums+(col-1)] )">
                <!-- <img src="https://img-home.csdnimg.cn/images/20230608093134.jpg" style="object-fit: cover;" :style="coverimgStyle" alt=""> -->
                <!-- <img :src="origin+arr2[(row-1)*colums+(col-1)].coverUrl" style="object-fit: cover;width:100%;" :style="coverimgStyle" alt=""> -->
                <img :src="origin+arr2[(row-1)*colums+(col-1)].coverUrl" style="object-fit: cover;width:100%;height:150px;overflow: hidden;" alt="">
                <div class="img_bottom_line" style="position:absolute;bottom:0;width: 100%;">
                  
                  <div style="position:relative;">
                    <div style="position:absolute;left:0;width:100%;
                      box-shadow: rgba(0, 0, 0, 0.5) 0px 15px 25px 10px;">
                    </div>
                  </div>

                  <div style="background-color:#0000005e;">
                    <ul style="display:flex;justify-content:space-between;margin:0;padding:0 5px;list-style:none;position:relative;">
                      <li style="float:left;">
                        <!-- <span style="margin-right:5px;">播放量</span>
                        <span>评论量</span> -->
                        <!-- 改成图标 -->
                        <span style="
                          margin-right:5px;
                          display: flex;
                          justify-content: center;
                          align-items: center;">
                          <svg class="icon" style="width: 1.0908203125em;height: 1em;vertical-align: middle;fill: currentColor;overflow: hidden;" viewBox="0 0 1117 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2245"><path d="M186.181818 93.090909a93.090909 93.090909 0 0 0-93.090909 93.090909v651.636364a93.090909 93.090909 0 0 0 93.090909 93.090909h744.727273a93.090909 93.090909 0 0 0 93.090909-93.090909V186.181818a93.090909 93.090909 0 0 0-93.090909-93.090909H186.181818z m0-93.090909h744.727273a186.181818 186.181818 0 0 1 186.181818 186.181818v651.636364a186.181818 186.181818 0 0 1-186.181818 186.181818H186.181818a186.181818 186.181818 0 0 1-186.181818-186.181818V186.181818a186.181818 186.181818 0 0 1 186.181818-186.181818z m566.923637 565.620364a69.818182 69.818182 0 0 0 0-107.240728l-219.694546-183.063272A69.818182 69.818182 0 0 0 418.909091 328.983273v366.033454a69.818182 69.818182 0 0 0 114.501818 53.666909l219.694546-183.063272zM512 378.647273L672.023273 512 512 645.352727v-266.705454z" fill="#CCCCCC" p-id="2246"></path></svg>
                        &nbsp;{{arr2[(row-1)*colums+(col-1)].playCount ==null?0:arr2[(row-1)*colums+(col-1)].playCount}}&nbsp;&nbsp;
                        <svg t="1687168548364" class="icon" style="width: 1.130859375em;height: 1em;vertical-align: middle;fill: currentColor;overflow: hidden;" viewBox="0 0 1158 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5601"><path d="M1045.76318 113.055479v678.332873H174.953354l-26.285399 15.26249-35.612476 20.632625V113.055479h932.707701m113.055479-113.055479H0v1024L205.478333 904.443831H1158.818659V0z" fill="#cdcdcd" p-id="5602"></path><path d="M392.302512 384.388628a72.072868 72.072868 0 1 0 72.072868 72.072868 72.072868 72.072868 0 0 0-72.072868-72.072868zM766.516147 384.388628a72.072868 72.072868 0 1 0 72.072868 72.072868 72.072868 72.072868 0 0 0-72.072868-72.072868z" fill="#cdcdcd" p-id="5603"></path></svg>
                        &nbsp;{{arr2[(row-1)*colums+(col-1)].commentCount == null?0:arr2[(row-1)*colums+(col-1)].commentCount}}
                      </span>

                        <span style="
                          display: flex;
                          justify-content: center;
                          align-items: center;">
                          </span>
                      </li>
                      <!-- <li style="float:left;">评论量</li> -->
                      <li style="float:right;">{{countTime(arr2[(row-1)*colums+(col-1)].videos)}}</li>
                    </ul>
                  </div>
                  
                  

                </div>
              </a>
            </div>
        </div>
        <div v-if="arr2[(row-1)*colums+(col-1)]!=null">
          <div style="margin: 5px; padding-left: 10px;">
            <div style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">{{ arr2[(row-1)*colums+(col-1)].title }}</div>
            <div style="height:18px;display: inline-flex;justify-content: center;align-items: center;">
              <img src="@/assets/UP_gray.png" style="height:100%;" alt="">
              <span style="margin-left:5px;color:gray;position:relative;bottom:1px;font-size: 10px;">{{ arr2[(row-1)*colums+(col-1)].name }}</span>
              <!-- <div class="">
                <el-button type="danger" icon="el-icon-delete" circle></el-button>
              </div> -->
              <i v-if="showDel" class="el-icon-delete del_btn" @click="deleteGroup((row-1)*colums+(col-1))" title="删除"></i>
            </div>
          </div>
        </div>
       
        <!-- <div class="grid-content bg-purple">
          {{ "col="+col+"row"+row}}
        </div> -->
        
      </div>
    </transition>
      </el-col>
    </el-row>
  </div>
</template>

<script>

import axios from 'axios'
export default {
    components:{},
    name:"GroupList",
    props:["props_"],
    data(){
      
      return {
        arr2:this.props_.videos || [],
        colums:4,//列数
        isLoading:false,
        coverimgStyle:{height:"100px"}
        // playerOptions: {
        //   autoplay: false,
        //   controls: true,
        //   muted: false,
        //   sources: [{
        //     src: 'https://v1.kwaicdn.com/upic/2022/12/03/19/BMjAyMjEyMDMxOTMwMjRfMTQ4MDIxMDkzMl85MDI0NzkxODc4MV8yXzM=_b_B0b1a2849fd1298a171288bbe7266509b.mp4?pkey=AAWWSIo55gzJxk7m4AiN0ybDsNRIFvJtdmQ7V6D0zeLu9YaLg9LxVuvVlFssSKID4vgCOaMrx7bdwW34xxGZOx93tdVuzrqSHHnLSaQtuahdZvSg4raP4SZAIHNnCrj3pPM',
        //     type: 'video/mp4'
        //   }]
        // }

      }
    },
    computed:{
      origin(){
        return this.$myInterface.origin;
      },
      // arr(){
      //   let arr=this.props_.videos;
      //   arr = arr || [];
      //   return arr;
      // },
      showDel(){
        if(this.props_.showDel == true) {
          return true;
        }
        return false;
      },
      empty(){
        return{
          isEmpty:this.arr2.length==0,
          description:"没有查询到合集！",
          isLoading:false
        }
      },
      rows(){
        console.log("计算rows")
        console.log(this.arr2)
        return Math.ceil(this.arr2.length/this.colums);
      },
      span(){
        return Math.floor(24/(this.colums));// span:一行长是24,每个占的长度是 24/列数
      }
    },
    watch:{
      props_:{
        immediate:true,
        deep:true,
        handler(newValue,oldValue){
          let temp = newValue.videos || [] ;
          temp.forEach(element => {
            console.log(element);
          });
          this.arr2 = temp;
          console.log("newValue",newValue);
          console.log("oldValue",oldValue);
          console.log("检测到props_变了 arr2=",this.arr2);
          if(newValue.coverHeight!=null){
            this.coverimgStyle={height:newValue.coverHeight};
          }
          
        }
      }
    },
    methods:{
      showLoading() {
        
        this.isLoading=true;
        setTimeout(()=>{
          //通知获取合集数据
          console.log("通知获取合集",this.$myEvents.REQUEST_GROUP,this.props_.fresh);
          this.$bus.$emit(this.$myEvents.REQUEST_GROUP,this.props_.fresh);
        },500);
        setTimeout(() => {
          this.isLoading=false;
        }, 2000);
      },
      goToVideoPage(item){
        console.log("合集信息",item);
        
        this.$StorageUtils.setVideoGroup(item);
        // this.$router.push({ path: '/playPage', query: { videoId: "1234" } });
        this.$userUtils.addPlayCount(this,{groupId:item.id});
        window.open(window.location.origin+"/playPage", '_blank')
        item.playCount=item.playCount==null?0:item.playCount+1;
      },
      async deleteGroup(index){
        let group=this.arr2[index];
        const element = this.$refs["group"+index][0];
        console.log("要移除的元素",element);
        console.log("element.parentNode=",element.parentNode)
        //动画效果
        if (element && element.parentNode) {
            // element.parentNode.removeChild(element);
            this.arr2.splice(index,1);
        }else{
          console.log("没有动画效果")
        }
        

        let data = {
          groupid:group.id
        }
        console.log("delete:",data);
        // this.$userUtils.deleteGroup(this,data);
        // const response = await axios.delete(this.$myInterface.deleteGroup, {data});
        //后台根据header中的token获取用户id,用户id和groupid一定是一一对应的，篡改userid或groupid都不会成功删除
        const response = await axios.delete(`${this.$myInterface.deleteGroup}?groupid=${group.id}`);
        console.log(response)
        if(response.data.code==1){
          this.$message.success("删除成功");
          this.$bus.$emit(this.$myEvents.REQUEST_GROUP,this.props_.fresh);
        }else{
          this.$message.error("删除失败");
        }
      },
    countTime(videosJson){
      let videos=[];
      if(videosJson!=null){
        videos = JSON.parse(videosJson);
      }
      let countDuration=0;
      for(let i=0;i<videos.length;i++){
        countDuration+=parseInt(videos[i].duration);
      }
      function formatTime(duration) {
        let hours = Math.floor(duration / 3600);
        let minutes = Math.floor((duration - (hours * 3600)) / 60);
        let seconds = Math.floor(duration - (hours * 3600) - (minutes * 60));
        hours = hours.toString().padStart(2, '0');
        minutes = minutes.toString().padStart(2, '0');
        seconds = seconds.toString().padStart(2, '0');

        let h=hours=="00"?'':hours+":";
        return `${h}${minutes}:${seconds}`;
      }
      return formatTime(countDuration);
    }
    },
    destroyed(){
      console.log("destoryed")
    }
}
</script>

<style scoped>
.el-row {
    /* margin-bottom: 20px; */
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
    background: unset;
  }
  .bg-purple {
    background: #d3dce6;
    background: unset;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }

  .grid-content {
    padding-left:0px !important;
  }
  .grid-content:hover .img_bottom_line {
    display: none;
  }
  a {
    color: #cdcdcd;
  }

  /* 删除按钮 */
  .del_btn {
    color: red;
    margin-left: 10px;
    cursor: pointer;
  }
  
  .hello-enter-active {
  animation: atguigu 0.5s;
}
.hello-leave-active {
  animation: atguigu 0.5s reverse;
}
@keyframes atguigu {
  from {
    transform: translateX(-100%);
  }
  to {
    transform: translateX(0px);
  }
}

</style>