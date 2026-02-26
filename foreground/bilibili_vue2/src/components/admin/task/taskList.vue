<template>
    <div>
        <el-card class="box-card">
            <div style="width:700px;">
                <el-dialog
                    title="修改任务"
                    :visible.sync="dialogVisible"
                    width="50%">
                    <CreateView v-if="dialogVisible" :editTask="editTask" :callback="closeDialog"></CreateView>
                </el-dialog>

                <el-table :data="filterData" style="width: 100%">
                    <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                        <el-form-item label="任务名称">
                            <span>{{ props.row.name }}</span>
                        </el-form-item>
                        <el-form-item label="开始时间">
                            <span>{{ new Date(props.row.dateStart).toLocaleDateString() }}</span>
                        </el-form-item>
                        <el-form-item label="结束时间">
                            <span>{{ new Date(props.row.dateStop).toLocaleDateString() }}</span>
                        </el-form-item>
                        <el-form-item label="任务详情">
                            <span>{{ props.row.taskDesc }}</span>
                        </el-form-item>
                        <el-form-item label="任务奖励">
                            <span>{{ props.row.giftsDesc }}</span>
                        </el-form-item>
                        <el-form-item label="状态">
                            <template>
                                <span v-if="props.row.state==0" v-text="'待发布'"></span>
                                <span v-if="currentMenuItemKey=='task-list-active'" v-text="'活动进行中'"></span>
                                <span v-if="currentMenuItemKey=='task-list-ended'" v-text="'已完结'"></span>
                            </template>
                        </el-form-item>
                        </el-form>
                    </template>
                    </el-table-column>
                    <el-table-column label="任务名称" prop="name"></el-table-column>
                    <el-table-column label="开始时间" prop="dateStart">
                        <template slot-scope="props">
                            <span>{{new Date(props.row.dateStart).toLocaleDateString()}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="结束时间" prop="dateStop">
                        <template slot-scope="props">
                            <span>{{new Date(props.row.dateStop).toLocaleDateString()}}</span>
                        </template>
                    </el-table-column>
                    
                    <el-table-column label="状态" prop="state">
                        <template slot-scope="props">
                            <span v-if="props.row.state==0" v-text="'待发布'"></span>
                            <span v-if="currentMenuItemKey=='task-list-active'" v-text="'活动进行中'"></span>
                            <span v-if="currentMenuItemKey=='task-list-ended'" v-text="'已完结'"></span>
                        </template>
                    </el-table-column>

                    <el-table-column label="操作" width="200">
                        <template slot-scope="props">
                            <el-button-group>
                                <el-button size="small" type="primary" v-if="!props.row.state == 1"
                                @click="handlePublish(props.row)">发布</el-button>
                            <el-button size="small" type="warning" v-if="!props.row.state == 1"
                                @click="handleEdit(props.row)">编辑</el-button>
                            <el-button size="small" type="danger"
                                @click="handleDelete(props.row)">删除</el-button>
                            </el-button-group>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </el-card>
    </div>
</template>
  
  <style scoped>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
  
  <script>
  import CreateView from "./create/create.vue"
import { mapActions, mapState,mapGetters } from 'vuex';

export default {
  name: "ProjectList",
  props:["currentMenuItemKey"],
  components:{
    CreateView
  },
  data() {
    return {
      dialogVisible: false,
      editTask:{}
    };
  },
  computed:{
    ...mapState("taskStore",["tasks"]),
    ...mapGetters("taskStore",["tasksWait","tasksActive","tasksEnded"]),
    filterData(){
        //等待发布的
        if(this.currentMenuItemKey == "task-list-publish"){
            return this.tasksWait;
        }else if(this.currentMenuItemKey == "task-list-active"){
            //正在进行中
            return this.tasksActive;
        }else if(this.currentMenuItemKey == "task-list-ended"){
            //已结束
            return this.tasksEnded;
        }
        return []
    }
  },
  methods:{
    ...mapActions("taskStore",["serverCreateTask","serverDeleteTask"]),
    handlePublish(row){
        console.log("这里发布",row);
        this.serverCreateTask({
            data:{...row,state:1},
            callback:(msg)=>{
                this.$message(msg);
            }
        })
    },
    handleEdit(data){
        this.editTask={...data};
        this.editTask.editMode=true;
        this.dialogVisible = true;
        console.log("this.editTask=",this.editTask);
    },
    handleDelete(row){
        this.$confirm(`确认删除任务 ${row.name} ?`).then(
            ()=>{
                console.log("移除");
                this.serverDeleteTask({
                    data:row,
                    callback:(msg)=>{
                        this.$message(msg);
                    }
                })
            }
        ).catch(()=>{
            console.log("取消");
        })
        
    },
    closeDialog(msg){
        //操作成功才关闭
        if(msg.type=="success"){
            this.dialogVisible=false;
        }
    }
  },
  mounted(){
    console.log("this.tasks",this.tasks);

  }
};
</script>
    
