<template>
  <div style="background: #f4f5f7;
    padding: 30px;">
    <el-radio-group v-model="active" size="medium">
      <el-radio-button label="进行中"></el-radio-button>
      <el-radio-button label="已结束"></el-radio-button>
    </el-radio-group>
    <el-card class="box-card" v-for="(item, index) in currentWorks" :key="index + item.id">
      <div slot="header" class="clearfix">
        <div class="user-info">
          <div class="user-avatar">
            <img :src="icon(item.userId)" class="avatar-image" alt="">
          </div>
          <div class="user-details">
            <p class="user-name">{{ item.userName }}</p>
          </div>
          <div class="vote-info">
            <p>得票数：{{ item.sum }}</p>
          </div>
          <div style="width: 100%;">
            <!-- <span style="float:right;color:rgb(191, 102, 7);">最佳作品</span> -->
            <div style="float:right;">
              <div v-show="index==0" style="display: inline-flex;justify-content: center;align-items: center;">
                <i style="margin-right: 30px;color:orange;font-size:30px;" class="el-icon-medal-1" ></i>
              </div>
              <el-tag v-if="active=='进行中'" type="success">进行中</el-tag>
              <el-tag v-else type="info">已结束</el-tag>
            </div>
          </div>
        </div>
      </div>

      <div>
        <div class="work-info">
          <div class="work-title">
            <p>任务：</p>
            <p class="work-info-text">{{ item.taskName }}</p>
          </div>
          <div class="work-description">
            <p>作品介绍：</p>
            <p class="work-info-text">{{ item.videoDesc }}</p>
          </div>
        </div>

        <div class="video-container">
          <video :src="origin + item.url" class="video-player" controls @play="onPlay"></video>
        </div>

        <div class="rating-container" v-show="active=='进行中'" >
          <p>投票：</p>
          <el-rate v-model="item.tempVoteNum" show-text :colors="colors" @change="saveRate(item,index)"></el-rate>
        </div>
      </div>
    </el-card>
    </div>
</template>





  
<script>
import { mapActions,mapState } from 'vuex'
export default {
    name:"WorksView",
    data(){
      return {
        myWorks:{active:[],finish:[]},
        active:"进行中",
        rateValue:3,
        colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
        currentPlayVideo:null //正在播放的播放器
      }
    },
    watch:{
      works:{
        deep:true,
        immediate:true,
        handler(){
          this.initMyWorks();
        }
      },
      tasks:{
        deep:true,
        immediate:true,
        handler(){}
      }
    },
    computed:{
      ...mapState("works",["works"]),
      ...mapState("taskStore",["tasks"]),
      origin(){
        return this.$myInterface.origin;
      },
      currentWorks(){
        if(this.active=="进行中"){
          return this.myWorks.active;
        }else{
          return this.myWorks.finish;
        }
      }
      
    },
    methods:{
      ...mapActions("works",["touristGetWorks","serverSaveVote"]),
      icon(userId){
        return this.$userUtils.getUserIcon(this,{
          userid:userId,
          type:"icon"
        });
      },
      saveRate(works,index){
        let callback=(done,value)=>{
          //修改失败 回调
          // if(!done){
          //   console.log("重置原来的值",value);
          //   works.tempVoteNum=value;
          // }
            //成功了也得更新
            done;
          works.tempVoteNum=value;

        }
        this.serverSaveVote({
          index,
          worksId:works.id,
          num:works.tempVoteNum,
          callback
        });

      },
      onPlay(playEvent){
        if(this.currentPlayVideo==null){
          this.currentPlayVideo=playEvent.target;
        }else{
          // 判断是否为当前的播放器
          if(this.currentPlayVideo==playEvent.target){
            return ;
          }
          //暂停之前的播放器
          this.currentPlayVideo.pause();
          //记录当前的播放器
          this.currentPlayVideo=playEvent.target;
        }
        console.log("onPlay:=======",arguments);
      },
      setCurrentPlayVideo(event){
        if(this.currentPlayVideo==null){
          this.currentPlayVideo=event.target;
        }else{
          // 判断是否为当前的播放器
          if(this.currentPlayVideo==event.target){
            return ;
          }
          //暂停之前的播放器
          this.currentPlayVideo.pause();
          //记录当前的播放器
          this.currentPlayVideo=event.target;
        }
      },
      // checkActive(works){
      //   console.log("检查活动是否正在进行",works);
      //   let taskid = works.taskid;
      //   // 找到此任务，判断是否已结束
      //   for(let i=0;i<this.tasks.length;i++){
      //     if(taskid == this.tasks[i].id){
      //       if(new Date(this.tasks[i].dateStop).getTime() >= Date.now() && new Date(this.tasks[i].dateStart).getTime() <= Date.now()){
      //         return true;
      //       }
      //     }
      //   }
      //   return false;
      // },
      initMyWorks(){
        let result={active:[],finish:[]};
        console.log("this.works",this.works);
        if(!Array.isArray(this.works)){
          return console.log("不是数组");
        }
        this.works.forEach(element => {
          console.log("tasks",this.tasks)
          let taskid = element.taskid;
          for(let i=0;i<this.tasks.length;i++){
            console.log("循环了")
            if(taskid == this.tasks[i].id){
              //在活动期间
              if(new Date(this.tasks[i].dateStop).getTime() >= Date.now() && new Date(this.tasks[i].dateStart).getTime() <= Date.now()){
                result.active.push(element);
              }else{
                result.finish.push(element);
              }
            }
          }
          
        });
        this.myWorks=result;
      }
      
      
    },
    mounted(){
      this.touristGetWorks();
      this.initMyWorks();
    },
    


    // 17
    // 20-1
}
</script>

<style scoped>



.clearfix:before,
.clearfix:after {
  content: "";
  display: table;
}

.clearfix:after {
  clear: both;
}

.user-info {
  display: flex;
  align-items: center;
}

.user-avatar {
  display: inline-flex;
  justify-content: center;
  align-items: center;
}

.avatar-image {
  width: 40px;
  height: 40px;
  object-fit: cover;
}

.user-details {
  margin-left: 10px;
}

.user-name {
  font-weight: bold;
}

.vote-info {
  margin-left: 100px;
}

.work-info {
  /* margin-top: 20px; */
}

.work-title,
.work-description {
  display: flex;
  align-items: center;
}

.work-info-text {
  font-size: 10px;
  color: #a1a1a1;
  margin-left: 10px;
}

.video-container {
  margin: 20px 20px;
}

.video-player {
  border: 1px solid gray;
  height: 255px;
  background: black;
  width: 50%;
}

.rating-container {
    margin: 10px;
    display: flex;
    align-items: center;
}

.el-card__header {
    padding: 8px 20px;
}
.el-card {
  margin:10px 0;
}

p {
  white-space: nowrap;
  padding: 0;
  margin: 5px;
}
</style>