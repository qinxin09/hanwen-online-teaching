<template>
    <div >
      <el-card class="box-card">
          <div style="width:700px;">
              <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" size="medium">
                  <el-form-item label="任务名称" prop="name">
                  <el-input v-model="ruleForm.name"></el-input> 
                  </el-form-item>
                  <el-form-item label="活动时间" required>
                  <el-col :span="11">
                      <el-form-item prop="dateStart">
                      <el-date-picker type="date" placeholder="开始日期" v-model="ruleForm.dateStart" style="width: 100%;"></el-date-picker>
                      </el-form-item>
                  </el-col>
                  <el-col class="line" :span="2" >-</el-col>
                  <el-col :span="11">
                      <el-form-item prop="dateStop" >
                          <el-date-picker placeholder="结束日期" v-model="ruleForm.dateStop" style="width: 100%;"></el-date-picker>
                      </el-form-item>
                  </el-col>
                  </el-form-item>
                  <el-form-item label="任务介绍" prop="taskDesc">
                      <el-input type="textarea" v-model="ruleForm.taskDesc"></el-input>
                  </el-form-item>
                  <el-form-item label="任务奖励" prop="giftsDesc">
                      <el-input type="textarea" v-model="ruleForm.giftsDesc"></el-input>
                  </el-form-item>
                  <el-form-item>
                  <el-button type="primary" @click="submitForm('ruleForm')">{{ editTask!=null?"保存修改":"立即创建" }}</el-button>
                  <el-button @click="resetForm()">重置</el-button>
                  </el-form-item>
              </el-form>
          </div>
      </el-card>
    </div>
  </template>
  
    <script>
      import { mapActions } from 'vuex';
      export default {
        name:"CreateView",
        props:["editTask","callback"],
        data() {
          var checkDate2 = (rule,value,callback)=>{
              rule;
              console.log("==="+value);
              if(this.ruleForm.dateStart==undefined){
                  callback()
              }else if(new Date(value).getTime() < new Date(this.ruleForm.dateStart).getTime()){
                  callback(new Error("结束时间应该大于开始时间"));
              }
              callback();
          }
          return {
            ruleForm: {
              name: '',
              dateStart: new Date(),
              dateStop: new Date(),
              taskDesc: '',
              giftsDesc: "",
              state:0,
              editMode:false,
              id:null
            },
            rules: {
              name: [
                { required: true, message: '请输入活动名称', trigger: 'blur' },
              ],
              
              dateStart: [
                { type: 'date', required: true, message: '请选择开始日期', trigger: 'change' }
              ],
              dateStop: [
              //   { type: 'date', required: true, message: '请选择介绍时间', trigger: 'change' }
              { validator: checkDate2, type: 'date',trigger: 'blur' }
              ],
             
              
              taskDesc: [
                { required: true, message: '请填写任务介绍', trigger: 'blur' }
              ],
              giftsDesc: [
                { required: true, message: '请填写任务奖励', trigger: 'blur' }
              ]
            }
          };
        },
        methods: {
          ...mapActions("taskStore",["serverCreateTask"]),
          submitForm(formName) {
            this.$refs[formName].validate((valid) => {
              if (valid) {
                let data = {...this.ruleForm};
                if(this.editTask==null){
                  data.id=Date.now();
                }
                this.serverCreateTask({
                  data:data,
                  callback:this.myCallback
                });
              } else {
                console.log('error submit!!');
                return false;
              }
            });
          },
          myCallback(msg){
            if(msg.type=="success"){
              this.resetForm();
            }
            if(this.callback!=null){
              this.callback(msg);
            }
            this.$message(msg)
          },
          resetForm() {
            this.$refs["ruleForm"].resetFields();
          }
        },
        mounted(){
          if(this.editTask!=null){
            this.ruleForm=this.editTask;
            console.log("应该显示旧的数据")
          }
        }
      }
    </script>