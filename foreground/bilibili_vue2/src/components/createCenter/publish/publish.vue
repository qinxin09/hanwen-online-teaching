<!--
二次开发
组员：杨万胜
功能：创作者中心发布作品页面、上传视频、附件
-->
<template>
  <div>
    <el-result v-if="!isLogin" icon="warning" title="请先登录" subTitle="点击下方按钮登录">
      <template slot="extra">
        <el-button type="primary" size="medium" @click="showDialogLogin()">点击登录</el-button>
      </template>
    </el-result>
    <el-form v-if="isLogin" :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="选择任务" prop="taskID">
        <el-select v-model="ruleForm.taskID" placeholder="请选择任务" @change="selectChanged">
          <el-option :label="item.name" :value="item.id" v-for="item in tasksActive" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="上传视频" prop="fileList">
        <el-upload
          ref="upload"
          :action="action"
          :show-file-list="false"
          :headers="requestHeaders"
          :data="ruleForm"
          :on-success="handleSuccess"
          :before-upload="beforeUpload"
          :auto-upload="false"
          :on-change="onFileChange"
          :file-list="fileList"
          :on-progress="handleProgress"
          accept=".mp4"
          >
          <div class="videoContent" style="display:flex;justify-content: center;"  @click="clickSelectBtn">
            <video v-show="fileList.length>0" ref="videoplayer" controls autoplay></video>
            <i v-show="fileList.length==0" class="el-icon-plus avatar-uploader-icon" style="line-height: 208px !important"></i>
            <div class="showSelectBtn" :style="showProgress?'background-color: #c1c1c1ad':''">
            <!-- <div> -->
              <!-- <el-button @click="clickSelectBtn" type="primary" icon="el-icon-plus" circle></el-button> -->
              <!-- <el-button type="danger" icon="el-icon-delete" circle></el-button> -->
            <!-- </div> -->
              <div v-if="showProgress">
                <el-progress type="circle" :percentage="percent"></el-progress>
              </div>
            </div>
            
          </div>
          <div v-show="fileList.length>0" slot="tip" class="el-upload__tip" style="line-height: unset;;">点击视频框，选择上传的视频</div>
          <!-- <div v-if="showProgress">
            <el-progress type="circle" :percentage="percent"></el-progress>
          </div> -->
        </el-upload>
      </el-form-item>

      <el-form-item label="作品介绍" prop="desc">
        <el-input type="textarea" v-model="ruleForm.desc"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import uuidv4 from '@/uuid';
import { mapActions, mapGetters, mapState } from 'vuex';
  export default {
    name:"PublishView",
    props:["task","cleanTask"],
    data() {
      var checkFileList=(rule,value,callback)=>{
        if(this.fileList.length>0){
          callback()
        }else{
          this.$message.warning("请选择上传文件")
        }
        
      }
      return {
        ruleForm: {
          taskID:null,
          duration:0,
          desc:null,
          type:"taskProject",
          uuid:null
        },
        showProgress:false,
        percent:0,
        fileList: [],
        rules: {
          taskID:[
            {required:true,type:"number",message:"请选择视频分类",trigger:"change"}
          ],
          fileList:[
            {validator:checkFileList,trigger: 'change' }
          ],
          desc: [
            { required: true, message: '请填写作品介绍' }
          ]
        },
        // isLogin:this.$userUtils.checkIsLogin()
      };
    },
    watch:{
      task(){
        if(this.task.id != undefined && this.ruleForm!=undefined){
          this.ruleForm.taskID = this.task.id;
        }
      }
    },
    computed:{
      ...mapGetters("taskStore",["tasksActive"]), //正在进行的任务
      ...mapState("user",["isLogin"]),
      action(){
        return this.$myInterface.uploadTaskProject;
      },
      requestHeaders(){
          return { Authorization: `Bearer ${JSON.parse(localStorage.getItem('token'))}` };
      },
    },
    methods: {
      ...mapActions("user",["init"]),
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(this.$userUtils.checkIsLogin()){
              this.ruleForm.uuid=uuidv4();
              this.$refs.upload.submit();
            }else{
              this.$confirm("请先登录！")
            }
            
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs.videoplayer.pause();//暂停播放
        this.$refs[formName].resetFields();
        this.cleanTask();
      },
      selectChanged(){
        this.cleanTask();
      },
      onFileChange(file,fileList){
        this.fileList = fileList ;
        let videoElement = this.$refs.videoplayer;
        console.log("videoElement",videoElement)
        videoElement.preload = "metadata";
        videoElement.onloadedmetadata = function() {
          let duration = Math.floor(videoElement.duration);
          console.log("视频时长：" + duration + "秒");
          // this.duration=duration;
          this.ruleForm.duration=duration;
          console.log(this.ruleForm.duration);
          // 将视频时长传递给后端保存
          // this.saveVideoDuration(duration);
        }.bind(this);
        setTimeout(()=>{
          videoElement.src = URL.createObjectURL(file.raw);
        })
      },
      clickSelectBtn(){
        console.log("选择文件之前先清空已选择过的文件");
        //选择文件之前先清空已选择过的文件
        console.log(this.$refs.upload)
        // this.$refs.videoplayer.load();//重置播放，回到起点，不播放
        this.$refs.videoplayer.pause();//暂停播放

        this.$refs.upload.clearFiles();

      },
      handleProgress(event) {
        this.percent = parseFloat(event.percent).toFixed(1)*1;
      },
      beforeUpload(){
        this.showProgress = true;
      },
      handleSuccess(response) {
        if(response.code==1){
          setTimeout(()=>{
            this.showProgress = false;
            this.$refs.upload.clearFiles();
            this.fileList=[];
            this.resetForm("ruleForm");
          },1000)
          this.$message.success("上传视频成功");
        }else{
          this.$message.error("上传失败！")
        }
      },
      showDialogLogin(){
        this.$bus.$emit(this.$myEvents.DIALOG_LOGIIN_SHOW);
      }
    },
    mounted(){
      this.init(null);
    }
  }
</script>


<style scoped>
  .videoContent {
    width:400px;
    height:225px;
    border:1px solid #dcdcdc;
    border-radius: 10px;
    position:relative;
  }
  
  video {
    width:100%;
    height:100%;
    border:1px solid #c1c1c1c1;
    border-radius:5px;
    display: flex;
  }
  .showSelectBtn {
    position: absolute;
    display: block;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .el-textarea{
    width:41.55%;
  }
</style>