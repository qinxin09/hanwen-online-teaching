<!--
二次开发
组员：夏少聪
功能：预览附件，pdf、图片-->
<template>
    <div>
        <el-drawer
            :title="viewData.filename"
            size="70%"
            :append-to-body="true"
            :before-close="handleClose"
            :visible.sync="innerDrawer">
            <div style="background-color: #5d5d5d;">
                <div style=" padding: 50px 100px;" v-if="type=='pdf' && showPdf">
                    <!-- vue嵌入PDF预览以及报错    使用定时器解决 http://t.csdn.cn/FY60n -->
                    <div v-for="i in numPages" :key="i">
                        <pdf ref="pdf" :src="pdfUrl" :page="i"> </pdf>
                        <div style="width:100%;">
                            <span style="color:white;display: flex; justify-content: center; margin-bottom: 15px;">第{{i}}页</span>
                        </div>
                    </div>
                    <!-- 第二种，但是浏览器提供了下载 -->
                    <!-- <iframe :src="pdfUrl" style="width: 100%; height: 600px;"></iframe> -->
                    <!-- <embed ref="embed" :src="pdfUrl" style="width: 100%; height: 600px;" type="application/pdf" class="pdf-embed"/> -->
                </div>

                <div v-if="viewData.fileType=='img'">
                    <img :src="origin+imgSrc">
                </div>
                
            </div>
        </el-drawer>
    </div>
  </template>

  <script>
  import axios from 'axios'
  import pdf from 'vue-pdf';
  export default {
    name:"PreView",
    components:{
        pdf
    },
    created(){
        this.$bus.$on(this.$myEvents.SHOW_PREVIEW, (data, index) => {
            this.innerDrawer = true;
            console.log("preview show, data is ", data, index);
            let d = {
                filename: data.data[index].name,
                authorid: data.authorId,
                groupid: data.groupid,
                type: "attachement"
            };
            this.viewData = {...d,fileType:data.data[index].type+""};
            if(this.viewData.filename.endsWith(".pdf")){
                this.type="pdf";
                let api = this.origin + `/video/getPdf?filename=${d.filename}&authorid=${d.authorid}&groupid=${d.groupid}&type=${d.type}`;
                this.showPdfInit(api);
            }else if(this.viewData.filename.endsWith(".jpg") || this.viewData.filename.endsWith(".png")){
                this.type="img";
                this.imgSrc=`/image/getImg?userid=${this.viewData.authorid}&type=${this.viewData.type}&groupid=${this.viewData.groupid}&fileName=${this.viewData.filename}&timestamp=${Date.now()}`
            }
            
        });
    },
    beforeDestroy(){
        this.$bus.$off(this.$myEvents.SHOW_PREVIEW)
    },
    data() {
      return {
        pdfUrl: "",
        innerDrawer: false,
        viewData:{},
        imgSrc:'',
        showPdf:false,
        numPages:0,
        pageLength:0,
        timer:{},
        type:""
      };
    },
    computed:{
        origin(){
            return this.$myInterface.origin;
        }
    },
    methods: {
        handleClose(done) {
            this.type="";
            done();
        },
        // 计算pdf页码总数
        async getNumPages() {
            console.log("this.pdfUrl",this.pdfUrl);
            let loadingTask = pdf.createLoadingTask(this.pdfUrl);
            console.log( "ddddddddddddddddddd"+ this.numPages);
            await loadingTask.promise
            .then((pdf) => {
                
                console.log(  this.numPages);
                this.pageLength = pdf.numPages;


                
                this.timer = setInterval(()=>{
                    if(this.numPages<pdf.numPages){
                        this.numPages++;
                    }else{
                        clearInterval(this.timer);
                    }
                    
                },1000);
                

            })
            .catch((err) => {
                console.error("pdf 加载失败", err);
            });
            


        },
        showPdfInit(api){
            //直接用这个也行 this.pdfUrl = `http://localhost:8088/video/getPdf?filename=${d.filename}&authorid=${d.authorid}&groupid=${d.groupid}&type=${d.type}`
            axios.get(api, { responseType: 'blob' })
                .then(response => {
                    console.log("response:", response);
                    const pdfBlob = new Blob([response.data], { type: 'application/pdf' });
                    const pdfUrl = URL.createObjectURL(pdfBlob);
                    this.pdfUrl = pdfUrl;
                    this.showPdf = true;
                    try{
                        this.getNumPages();
                    }catch(e){console.log(e)}
                })
                .catch(error => {
                    console.error('加载PDF失败', error);
            });
        }
    }
  };
  </script>

  <style>
.pdf-viewer {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: #fff;
  z-index: 9999;
}

.pdf-iframe {
  width: 100%;
  height: 100%;
  border: none;
}
</style>