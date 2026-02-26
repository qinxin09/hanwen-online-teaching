<!--
组员：杨万胜
功能：展示发布的作品

-->
<template>
  <div>
    <NavBar activeIndex2="3" :myStyle="'width:75%;margin:auto;'"/>
    <div class="myBody" style="width:80%;margin:auto;margin-top:20px;">
        <div style="margin: auto; width: 90%;">
          <div class="card" style=" width: 100%; height: 100%; display: inline-block; border-radius: 5px; margin-bottom: 10px;" >
            
            <div style="padding:20px;" v-if="!errorNetwork">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane label="作品展示" name="works">
                        <WorksView></WorksView>

                    </el-tab-pane>
                    <el-tab-pane label="任务中心" name="tasks">
                        <TaskView :selectTask="selectTask"></TaskView>
                    </el-tab-pane>
                    <el-tab-pane label="发布视频" name="publish">
                        <PublishView :task="task" :cleanTask="cleanTask"></PublishView>
                    </el-tab-pane>
                </el-tabs>
            </div>
            <el-result icon="error" title="访问失败！" subTitle="请稍后重试" v-if="errorNetwork">
              <template slot="extra">
                <el-button type="primary" size="medium" @click="reload">重试</el-button>
              </template>
            </el-result>

          </div>
          
        </div>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import WorksView from '@/components/createCenter/works/works.vue'
import PublishView from '@/components/createCenter/publish/publish.vue'
import TaskView from '@/components/createCenter/tasks/tasks.vue'

import { mapActions } from 'vuex'
export default {
    name:"CreateCenter",
    components:{
        NavBar,WorksView,PublishView,TaskView
    },
    data() {
      return {
        activeName: 'works',
        errorNetwork:false,
        task:{s:1234}
      };
    },
    methods: {
    ...mapActions("taskStore",["serverInitTask"]),
      handleClick(tab, event) {
        console.log(tab,event);
        
      },
      selectTask(value){
        this.activeName="publish";
        this.task=value;
      },
      cleanTask(){
        this.task={};
      },
      reload(){
        window.location.reload()
      }
    },
    mounted(){
      let callback = (msg)=>{
        if(msg=="ERR_NETWORK"){
          this.errorNetwork=true;
        }
      }
      this.serverInitTask(callback);
    }
}

</script>

<style>

</style>