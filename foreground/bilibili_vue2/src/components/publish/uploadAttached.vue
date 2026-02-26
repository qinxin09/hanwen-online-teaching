<!--
二次开发
组员：夏少聪
功能：上传附件
-->
<template>
    <div>
        <el-upload
            class="upload-demo"
            ref="uploadAttachment"
            :action="action"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :on-change="hadleChange"
            :before-remove="beforeRemove"
            :data="attachData"
            :headers="requestHeaders"
            multiple
            :limit="3"
            :on-exceed="handleExceed"
            :file-list="fileList"
            :auto-upload="false">
            <el-button size="small" type="primary" icon="el-icon-upload2"></el-button>

        </el-upload>
        <el-button size="small" type="primary" icon="el-icon-check" @click="submit"></el-button>

    </div>
</template>
<script>
  export default {
    props:["closeDialog","setAttachementNames"],
    name:"UploadAttached",
    data() {
      return {
        fileList: [],
        attachData:{}
      };
    },
    computed:{
      action(){
        return this.$myInterface.addGroupAttachement;
      },
      requestHeaders(){
        return { Authorization: `Bearer ${JSON.parse(localStorage.getItem('token'))}` };
      }
    },
    created(){
      this.$bus.$on(this.$myEvents.UPLOAD_ATTACHEMENT,this.submit);
    },
    methods: {
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      hadleChange(file,fileList){
        console.log(fileList);
        file;
        let names=function(fileList){
          let names = [];
          for(let i=0 ;i<fileList.length;i++){
            names.push(fileList[i].name);
          }
          return names;
        }(fileList);
        this.setAttachementNames(names);

      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, _) {
        _;
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      submit(data){
        this.$set(this.attachData,"groupid",data.groupid);
        this.$set(this.attachData,"type",data.type);
        console.log("传过来的data is ",this.attachData);
        this.$refs.uploadAttachment.submit();
      }
    }
  }
</script>
