<!--
二次开发
组员：yws
功能：展示任务及奖励信息
-->
<template>
  <div>
    <el-empty :image-size="200" v-if="tasksActive.length==0" 
    :description="'空无一物'" ></el-empty>
    <el-card class="box-card" v-for="(item,index) in tasksActive" :key="index+item.id">
      <div slot="header" class="clearfix">
        <div>
          <div>
            <span>{{ (index+1) +" "+ item.name }}</span>
            <el-button style="float: right; " round size="mini" @click="gotoWork(item)">去创作</el-button>
          </div>
          <div>
            <span style="font-size: 10px;color: #a1a1a1;margin-left:10px;;">{{"活动时间 "+ new Date(item.dateStart).toLocaleDateString() +" -- " + new Date(item.dateStop).toLocaleDateString() }}</span>
          </div>
        </div>
      </div>
      <div >
        <div>
          <span class="title">活动介绍</span>
          <p class="desc">{{ item.taskDesc }}</p>
        </div>
        <div>
          <span class="title">活动奖励</span>
          <p class="desc">{{ item.giftsDesc }}</p>
        </div>
      </div>
    </el-card>

  </div>
</template>
<style scoped>

.card-text {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 100%;
}
  
  .title{
    font-size: 10px;
  }
  .desc {
    margin-left: 10px;
    
  }
  /* h4{
    overflow-wrap: break-word;
  } */
  .el-card {
    overflow-wrap: anywhere;
  }


  .box-card {
    width: 480px;
    margin:10px;
  }
  .el-card__header {
    padding: 8px 20px;
  }
</style>
<script>
import { mapGetters } from 'vuex'
export default {
  name:"TaskView",
  props:["selectTask"],
  computed:{
    ...mapGetters("taskStore",["tasksActive"]) //正在进行的任务
  },
  methods:{
    gotoWork(task){
      console.log(task);
      this.selectTask(task);
    }
  },
  mounted(){
    
  }

}
</script>