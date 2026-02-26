<template>
  <div style="margin-top:20px;">
    <!-- <el-table :data="works" style="width: 100%" :row-class-name="tableRowClassName"> -->
    <el-table :data="myWorks" style="width: 100%" :row-class-name="tableRowClassName">
                    <el-table-column type="expand">
                      <template slot-scope="props">
                        <el-row>
                          <el-col :span="12">
                            <div style="padding:10px;">
                              <el-form label-position="left" inline class="demo-table-expand">
                                <el-form-item label="作者">
                                    {{ props.row.userName }}
                                </el-form-item>
                                <el-form-item label="所属任务">
                                    <el-button @click="showBelongTask(props.row)" size="mini">点击查看</el-button>
                                </el-form-item>
                                <el-form-item label="视频名称">
                                  <span>{{ props.row.videoName.substring(36) }}</span>
                                </el-form-item>
                                <el-form-item label="介绍">
                                  <span>{{ props.row.videoDesc }}</span>
                                </el-form-item>
                              </el-form>
                            </div>
                          </el-col>

                          <el-col :span="12">
                            <div>
                              <video :src="origin+props.row.url" style="border: 1px solid gray;height: 255px; background:black;width: 400px;" controls @click="setCurrentPlayVideo"></video>
                            </div>
                          </el-col>
                        </el-row>
                      </template>
                    </el-table-column>
                    <el-table-column label="作者" prop="userName"></el-table-column>
                    <el-table-column label="时长" prop="videoLength">
                        <template slot-scope="props">
                            <span>{{ countTime(parseInt(props.row.videoLength)) }}</span>
                        </template>
                    </el-table-column>
                    
                    <el-table-column label="状态" prop="state" sortable>
                        <template slot-scope="props">
                            <span v-if="props.row.state==0" v-text="'待审核'"></span>
                            <span v-if="props.row.state==1" style="color:green" v-text="'审核通过'"></span>
                            <span v-if="props.row.state==2" style="color:red" v-text="'已违规'"></span>
                        </template>
                    </el-table-column>
                    <el-table-column label="活动状态" prop="active" sortable>
                        <template slot-scope="props">
                            <span v-text="props.row.active?'进行中':'已结束'"></span>
                        </template>
                    </el-table-column>

                    <el-table-column label="得票" prop="sum" sortable></el-table-column>

                    <el-table-column label="操作" width="200">
                        <template slot-scope="props">
                            <el-button-group>
                                <el-button size="small" type="success" :disabled="props.row.state==1" v-show="roleAdmin['编辑']!=undefined"
                                @click="updateWork(props.row,1)">通过</el-button>
                            <el-button size="small" type="warning" :disabled="props.row.state==2" v-show="roleAdmin['编辑']!=undefined"
                                @click="updateWork(props.row,2)">违规</el-button>
                            <el-button size="small" type="danger"
                                @click="deleteWork(props.row,props.$index)"  v-show="roleAdmin['删除']!=undefined" >删除</el-button>
                            </el-button-group>
                        </template>
                    </el-table-column>
                </el-table>

  
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from 'vuex'
export default {
    name:"WorkLists",
    data(){
      return {
        myWorks:{},
        currentPlayVideo:null //正在播放的播放器
      }
    },
    computed:{
      ...mapGetters("admin",["roleAdmin"]),
      ...mapState("works",["works"]),
      ...mapGetters("works",["worksWait","worksPassed","worksIllegal"]),
      ...mapState("taskStore",["tasks"]),
      origin(){
        return this.$myInterface.origin;
      }
    },
    watch:{
      works:{
        deep:true,
        immediate:true,
        handler(){
          this.initMyWorks();
        }
      }
    },
    methods:{
      ...mapActions("works",["serverInitWorks_Admin","serverUpdateWork","servereDeleteWork"]),
      updateWork(work,state){
        this.serverUpdateWork({
          data:{...work,state:state},
          callback:(msg)=>{
            this.$message(msg);
          }
        });
      }, 
      deleteWork(works,index){
        this.$confirm(`确定删除作品：${works.id}吗？`).then(
          ()=>{
            console.log("删除作品",index);
            this.servereDeleteWork({
              works,
              index,
              callback:(msg)=>{
                this.$message(msg);
              }
            })
          }
        ).catch((e)=>{
          console.log("取消",e);
        })
      },
      tableRowClassName({row}) {
        console.log("tableRowClassName",row);
        if(row.active){
          return 'success-row';
        }else if (!row.active) {
          return 'warning-row';
        }
        return '';
      },
      countTime(duration){ 
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
        return formatTime(duration);
      },
      showBelongTask(work){
        for(let i in this.tasks){
          if(work.taskid == this.tasks[i].id){
            let t=this.tasks[i];
            console.log(t)
            this.$alert(`
              名称：<span>${t.name}</span><br>
              介绍：<span>${t.taskDesc}</span><br>
              奖励：<span>${t.giftsDesc}</span><br>
              活动时间：<span>${new Date(t.dateStart).toLocaleDateString()} -- ${new Date(t.dateStop).toLocaleDateString()}</span>
            `, '任务详情', {
              dangerouslyUseHTMLString: true
            }).catch(()=>{});

            return;
          }
        }
        return this.$confirm("没有找到任务！");
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
      initMyWorks(){
        let result=[];
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
                result.push({...element,active:true})
              }else{
                result.push({...element,active:false})
              }
            }
          }
          
        });
        this.myWorks=result;
      }
    },
    mounted(){
      this.serverInitWorks_Admin();
    }
}
</script>

<style scoped>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #378c0a;
  }

  table {
  border-collapse: collapse; /* 将表格边框合并为单一边框 */
}

table, th, td {
  border: 1px solid black; /* 设置表格、表头和单元格的边框样式 */
}
</style>李四1688654036296