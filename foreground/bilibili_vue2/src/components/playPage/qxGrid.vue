<!--
二次开发
组员：yws
功能：附件图标，展示附件项目-->
<template>
    <div style="width:100%;padding:10px;">
        
        <el-empty description="空无一物" v-if="gridDataSource.data.length==0"></el-empty>
      <el-row  v-for="(r) in rows" :key="'row'+r">
      <el-col  :span="span" v-for="(c) in columns" :key="'col'+c">
        <div v-if="gridDataSource.data[(r-1)*columns+(c-1)]!=null">
            <!-- {{ gridDataSource.data[(r-1)*columns+(c-1)].name}} -->

            <div style="display: flex; /* 将父元素设置为flex布局 */
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
    height: 100vh; /* 设置父元素的高度，这里使用了100vh表示占满整个视口的高度 */">
                <div style="width:90%;" :title="gridDataSource.data[(r-1)*columns+(c-1)].name">
                    <div style="position: relative;">
                        <div class="mygrid" style="display: inline-flex;width:100%;height:100%;position:absolute">
                            <div style="width: 100%;display: inline-flex;justify-content: center;align-items: center;position: relative;bottom: 10px;">
                                <div>
                                    <el-button type="warning" icon="el-icon-view" circle title="预览" @click="viewFile((r-1)*columns+(c-1))"></el-button>
                                    <el-button type="primary" icon="el-icon-download" circle title="下载" @click="downloadFile((r-1)*columns+(c-1))"></el-button>
                                </div>
                            </div>
                        </div>
                        <img style="width: 80%;margin: auto;height: 100%;display: flex;" src="@/assets/配套管理.png" alt=""/>
                        <span 
                        style="
                            display: inline-flex;
                            width: 100%;
                            justify-content: center;
                            white-space: nowrap; 
                            overflow: hidden; 
                            text-overflow: ellipsis; 
                            max-width: 100%;
                        ">
                        {{ gridDataSource.data[(r-1)*columns+(c-1)].name}}
                        </span>
                    </div>
                </div>
            </div>
        </div>
      </el-col>
    </el-row>
      
    </div>
  </template>

<script>
export default {
    name:"QxGrid",
    props:["gridDataSource"],

    computed:{
        columns(){
            return this.gridDataSource.column
        },
        rows(){
            return Math.ceil(this.gridDataSource.data.length/this.gridDataSource.column);
        },
        span(){
            return Math.floor(24/(this.gridDataSource.column));// span:一行长是24,每个占的长度是 24/列数
        }
    },
    methods:{
        viewFile(index){
            console.log("预览文件");
            this.$bus.$emit(this.$myEvents.SHOW_PREVIEW,this.gridDataSource,index);
        },
        downloadFile(index){
            console.log("下载文件",this.gridDataSource,index);
            if(!this.$userUtils.checkIsLogin()){
                return this.$confirm("请先登录！").then(()=>{
                    console.log("打开登录弹窗");
                    this.showDialog();
                }).catch(()=>{
                    console.log("取消登录")
                });
            }
      
            let data = {
                fileName:this.gridDataSource.data[index].name,
                type:"attachement",
                groupid:this.gridDataSource.groupid+"",
                authorId:this.gridDataSource.authorId,
            }
            this.$userUtils.downloadFile(this,data);
        },
        showDialog(){
        this.$bus.$emit(this.$myEvents.DIALOG_LOGIIN_SHOW);
        },
    }
}
</script>

<style scoped>


    .mygrid {
        border-radius: 5px;
        background-color: rgba(255, 255, 255, 0);
        cursor: pointer;
    }
    .mygrid:hover {
        background-color: rgb(155 155 155 / 24%);
    }
    .mygrid div {
        display: none;
        width: 60%;
    }
    .mygrid:hover div {
        display: inline-flex;
        justify-content: space-around;
    }
</style>