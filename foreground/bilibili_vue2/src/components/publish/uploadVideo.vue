<!--
组员：杨万胜
功能：上传视频
-->
<!-- <template>
    <div>
      <el-upload
        ref="upload"
        :action="action"
        :headers="requestHeaders"
        :data="requestData"
        :before-upload="beforeUpload"
        :on-change="onFileChange"
        :on-progress="handleProgress"
        :on-success="handleSuccess"
        :show-file-list="false"
        :auto-upload="false"
      >
        <el-button type="primary" @click="clickSelectBtn">选择视频</el-button>
      </el-upload>
      <div v-if="showProgress">
        <el-progress :text-inside="true" :stroke-width="40" :percentage="percent" />
      </div>
      <div v-show="showVideo">
        <video ref="videoplayer" controls autoplay></video>
      </div>
      <el-button v-if="showVideo" type="primary" @click="submitAll">点击上传</el-button>
    </div>
  </template> -->
  
  <template>
    <div>
      <el-upload
        ref="upload"
        :action="action"
        :headers="requestHeaders"
        :data="requestData"
        :before-upload="beforeUpload"
        :on-change="onFileChange"
        :on-progress="handleProgress"
        :on-success="handleSuccess"
        :show-file-list="false"
        :auto-upload="false"
      >
        <div class="videoContent" style="display:flex;justify-content: center;">
          <video @click.prevent ref="videoplayer" controls autoplay></video>
          <div class="showSelectBtn">
            <div>
              <el-button @click="clickSelectBtn" type="primary" icon="el-icon-plus" circle></el-button>
              <!-- <el-button type="danger" icon="el-icon-delete" circle></el-button> -->
            </div>
          </div>
          
        </div>
      </el-upload>
      <div v-if="showProgress">
        <el-progress :text-inside="true" :stroke-width="40" :percentage="percent" />
      </div>
      <div>
        <div v-if="showVideo">
          <el-button type="primary" @click="submitAll">点击上传</el-button>
        </div>      
      </div>
    </div>
  </template>

  <script>
  export default {
    name:"UploadVideo",
    props:["props_"],
    
    data() {
      return {
        action: this.$myInterface.addVideo,
        showProgress: false,
        percent: 0,
        videoUrl: null,
        duration:0,
        showVideo:false
      }
    },
    computed:{
        requestHeaders(){
            return { Authorization: `Bearer ${JSON.parse(localStorage.getItem('token'))}` };
        },
        requestData(){
          console.log(this.props_.selectgroup.videos!="");
          console.log(this.props_.selectgroup.videos);
          let videos = this.props_.selectgroup.videos!=""?JSON.parse(this.props_.selectgroup.videos):[];
          console.log("已获取到的视频：",videos);
          let index = 1; 
          if(videos!=null){
            index=videos.length+1;
          }
          
          let newname=this.$uuidv4()+".mp4";
          let data = {
            type:"videoGroupList",
            title:this.props_.title,
            desc:this.props_.desc,
            index:index,
            groupid:this.props_.selectgroup.id,
            newname:newname,
            duration:this.duration
          }
          return data;
        }

    },
    methods: {
      beforeUpload(file) {
        if (file.type !== 'video/mp4') {
          this.$message.error('只能上传 MP4 格式的视频!');
          return false;
        }
        this.showProgress = true;

      },
      handleProgress(event, file, fileList) {
        file,fileList;
        this.percent = event.percent;
      },
      handleSuccess(response, file, fileList) {
        if(response.code==1){
          console.log("上传视频完成",response);
          file,fileList;
          this.showProgress = false;
          this.percent = 0;
          this.$message.success("上传完成");
          let data = Object.assign({},this.requestData);
          // data.newname=response.data.videoName;
          data.newname=response.data.fileName;
          // this.$refs.upload.data=this.requestData;
          console.log("beforeUpload 参数this.$refs.upload.data ：",data);
          this.$userUtils.addVideoInfo(this,data);
        
        // setTimeout(function(){
        //     window.location.reload();
        //   },1000);
        }else{
          this.$message.error("上传失败！")
        }
      },
      onFileChange(file){
        this.showVideo=true;
        // let videoElement = document.createElement("video");

        console.log(this.$refs)
        let videoElement = this.$refs.videoplayer;

        videoElement.preload = "metadata";
        videoElement.onloadedmetadata = function() {
          let duration = Math.floor(videoElement.duration);
          console.log("视频时长：" + duration + "秒");
          // this.duration=duration;
          this.duration=duration;
          console.log(this.requestData.duration);
          // 将视频时长传递给后端保存
          // this.saveVideoDuration(duration);
        }.bind(this);
        setTimeout(()=>{
          videoElement.src = URL.createObjectURL(file.raw);
        })
      },
      clickSelectBtn(){
        console.log("点击了按钮");
        //选择文件之前先清空已选择过的文件
        console.log(this.$refs.upload)
        this.$refs.upload.clearFiles();
      },
      submitAll(){
        this.$refs.upload.submit();
      }
    },
    mounted(){
      console.log("mounted",this.props_);
    }
  }
  </script>

<style scoped>
  video {
    width:100%;
    height:200px;
    border:1px solid #c1c1c1c1;
    border-radius:5px;
    display: flex;
  }
  .showSelectBtn {
    position: absolute;
    display: none;
  }
  .videoContent:hover .showSelectBtn {
    display: block;
  }

</style>