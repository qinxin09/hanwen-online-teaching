<!--
组员：sunRan
功能：上传用户头像
-->
<template>
  <div style="">
    <div style="margin:10px;">
       
      <el-upload
            ref="upload"
            :data="iconData"
            class="avatar-uploader"
            :action="uploadVideoGroupCoverUrl"
            :headers="requestHeaders"
            :show-file-list="false"
            :file-list="fileList"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :on-change="fileChange"
            :multiple="true"
            :auto-upload="true"

            list-type="picture"
            >
        <img v-if="coverImageUrl" :src="coverImageUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        <!-- <div class="el-upload__text" style="margin:bottom:10px;color:gray;"> 点击+上传</div> -->
        </el-upload>
    </div>
  </div>
</template>

<script>
export default {
    name:"UploadIcon",
    props:["props_"],
    data(){
        return {
            coverImageUrl: '',//选择的封面转url
            fileList:[]
        }
    },
    computed:{
        iconData(){
            return {
                type:"icon"
            }
        },
        uploadVideoGroupCoverUrl(){
            return this.$myInterface.uploadIcon
        },
        requestHeaders(){
            return { Authorization: `Bearer ${JSON.parse(localStorage.getItem('token'))}` };
        }
    },
    methods:{
        handleAvatarSuccess(res, file) {
        res,file;
        console.log("上传完成")
        // this.coverImageUrl = URL.createObjectURL(file.raw);
        this.$refs.upload.clearFiles();//清空已选择的文件
        this.fileList = [];
        console.log(res);
        if(res.code==1){
          this.$message.success("上传成功");
        //   this.coverImageUrl="uploaded"
          console.log("上传完成",this.coverImageUrl);
          console.log("通知更新头像");
          setTimeout(()=>{
            this.$bus.$emit(this.$myEvents.FRESH_ICON);//NavBar.vue
          })
          // this.$nextTick(()=>{
          //   this.$bus.$emit(this.$myEvents.FRESH_ICON);//NavBar.vue
          // })
        }
      },
      beforeAvatarUpload(file) {
        console.log("上传之前");
        const img = file.type === 'image/jpeg' || file.type === 'image/png';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!img) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return img && isLt2M;
      },
      fileChange(file,fileList){
        console.log("文件改变了")
        
        this.fileList = [file];
        fileList.splice(0)
        fileList.push(file);
        if(this.coverImageUrl!="uploaded"){console.log("没完成",this.coverImageUrl)
          this.coverImageUrl = URL.createObjectURL(file.raw);
        }else{
          console.log("文件已上传，清空已选择的文件")
          this.fileLis=[];
          this.dialogFormVisible = false;
        //   this.coverImageUrl = "";
        }
        console.log(this.coverImageUrl);
      }
    }

}
</script>

<style>

</style>