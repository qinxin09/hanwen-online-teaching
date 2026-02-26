<!--
组员：杨万胜
功能：创建合集
-->
<template>
  <div style="width:100%;height:100%;">
    <NavBar activeIndex2="4" :myStyle="'width:75%;margin:auto;'"/>
    <!-- 弹窗 -->
    <div style="width:80%;margin:auto;margin-top:20px;">
    
      <el-dialog title="新建合集" :visible.sync="dialogFormVisible">
        <el-form :model="addVideoGroup" :rules="addVideoGroup.rules">
          <el-form-item label="合集名称" :label-width="'100px'">
            <el-input v-model.trim="addVideoGroup.title" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="合集描述" :label-width="'100px'">
            <el-input v-model="addVideoGroup.brief" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="视频分类"  :label-width="'100px'">
            <div class="block">
              <!-- <span class="demonstration">多选选择任意一级选项</span> -->
              <el-cascader-panel
                ref="cascaderPanel"
                :options="options"
                :props="{ multiple: true }"
                @change="handleChange"
                clearable></el-cascader-panel>
            </div>
          </el-form-item>
          <el-form-item label="合集封面"  :label-width="'100px'">
            <div>
              <el-upload
                      ref="upload"
                      :data="groupData"
                      class="avatar-uploader"
                      :action="uploadVideoGroupCoverUrl"
                      :headers="requestHeaders"
                      :show-file-list="false"
                      :file-list="fileList"
                      :on-success="handleAvatarSuccess"
                      :before-upload="beforeAvatarUpload"
                      :on-change="fileChange"
                      :multiple="true"
                      :auto-upload="false"

                      list-type="picture"
                      >
              <img v-if="coverImageUrl" :src="coverImageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </div>
        </el-form-item>
        <el-form-item label="付费资源" :label-width="'100px'">
          <el-switch v-model="addVideoGroup.istoll"></el-switch>
        </el-form-item>

        <el-form-item label="售价" v-show="addVideoGroup.istoll" prop="price" :label-width="'100px'">
          <el-input type="number" v-model.trim="addVideoGroup.price"></el-input>
        </el-form-item>
        <el-form-item label="附件" :label-width="'100px'">
          <UploadAttached :closeDialog="closeDialog" :setAttachementNames="setAttachementNames"/>
        </el-form-item>

      </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false,ruleForm.selectgroup=''">取 消</el-button>
          <el-button type="primary" @click="createVideoGroup()">确 定</el-button>
        </div>
      </el-dialog>

      <div style="width:90%;margin:auto;margin-top:20px;z-index:2;">
        <div class="card" style="width:100%;background-color: white;display: inline-block;">
          <div style="padding:20px;width:50%;margin:auto;">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
              

              <el-form-item label="视频标题" prop="title">
                <el-input v-model.trim="ruleForm.title"></el-input>
              </el-form-item>
              <el-form-item label="选择合集" prop="selectgroupName">
                <el-select v-model="ruleForm.selectgroupName" placeholder="请选择合集">
                  <el-option  @click.native="showSelectValue(item)" :label="item.title" :value="item.title" v-for="(item,index) in videoGroup" :key="index"></el-option>
                  <el-option label="新建合集" value="add" @click.native="showCreateVideoGroupDialog()"></el-option>
                </el-select>
              </el-form-item>
              
              
              <el-form-item label="视频描述" prop="desc">
                <el-input v-model.lazy="ruleForm.desc"></el-input>
              </el-form-item>

              <el-form-item label="上传视频" >
                <UploadVideo v-if="checkFillData" :props_="ruleForm" ></UploadVideo>
                <el-button v-if="!(checkFillData)" disabled>请先选择填写视频信息</el-button>
              </el-form-item>

              <!-- <el-form-item label="上传附件" >
                <UploadVideo v-if="checkFillData" :props_="ruleForm"></UploadVideo>
                <el-button v-if="!(checkFillData)" disabled>请先选择填写视频信息</el-button>
              </el-form-item> -->
              
            </el-form>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
 import NavBar from '../NavBar.vue';
 import UploadVideo from './uploadVideo.vue' ;
 import UploadAttached from './uploadAttached.vue'

export default {
    name:"PublishVideo",
    data() {
      var checkPrice = (rule,value,callback)=>{
        if(this.addVideoGroup.price){
            if(value<=0){
              callback("价格应该大于零！");
            }else if(value>=1000){
              callback("价格不超过1000");
            }
        }
        callback();
      };
      var checkSelectGroup = (rule,value,callback)=>{
        // console.log("valueid",value==null,value.id==null);
        console.log("检查add：",value=="add")
        if(value=="add" || value==""){
          // console.log("show error");
          if(value=="add"){
            this.ruleForm.selectgroupName="";
          } 
          callback(new Error("请选择合集！"));
        }
        callback();
        
      };
      // checkSelectGroup;
      return {
        // fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
        coverImageUrl: '',//选择的封面转url
        fileList:[],
        groupData:{
          type:"videoGroupCover",
          groupid:""
        },
        dialogFormVisible:false,
        addVideoGroup:{
          title:'',
          brief:'',
          catagory:[],
          price:null,
          istoll:false,
          attachementNames:"",
          rules:{
            price:[
              { validator: checkPrice, trigger: 'blur' }
            ],
          } 
        },
        videoGroup:[],//待选择的合集列表
        ruleForm: {
          title: '',
          selectgroup: {},
          selectgroupName:'',
          desc: ''
        },
        checkFillData:false,
        rules: {
          title: [
            { required: true, message: '请输入标题', trigger: 'blur' },
            // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          selectgroupName: [
           { validator: checkSelectGroup, trigger: 'change' }
            // { required: true, a:checkSelectGroup.toString(), message: '请选择合集', trigger: 'change' }
          ],
          
          desc: [
            { required: true, message: '请填该视频的描述', trigger: 'blur' }
          ]
        }
      };
    },
    components:{
        NavBar,UploadVideo,UploadAttached
    },
    computed:{
      requestHeaders(){
        return { Authorization: `Bearer ${JSON.parse(localStorage.getItem('token'))}` };
      },
      
      uploadVideoGroupCoverUrl(){
        let s =this.$myInterface.uploadVideoGroupCoverUrl;
        return s;
      },
      // checkFillData(){
      //   // let r = this.ruleForm.selectgroup.id!=null && this.ruleForm.title.length>0 && this.ruleForm.price>0 && this.ruleForm.desc.length>10 ;
      //   let r = this.ruleForm.desc.length>10 && this.ruleForm.title.length>0;

      //   console.log(r);
      //   return r;
      // },
        
      options(){
        return this.$userUtils.groupOption();
      }
    },
    watch:{
      ruleForm:{
        deep:true,
        handler(newValue){
          if(newValue && newValue.selectgroup){
            this.checkFillData= newValue.selectgroup!=null && newValue.title.length>0 && newValue.desc.length>0 ;
          }
          
          // console.log("result="+this.checkFillData);
        }
      }
    },
    
    methods:{
      
      showCreateVideoGroupDialog(){
        // this.ruleForm.selectgroupName='';
        this.ruleForm.selectgroup={};
        this.dialogFormVisible = true;
      },
      createVideoGroup(){
        const regex = /^[\u4E00-\u9FA5a-zA-Z0-9]+$/;
        let title = this.addVideoGroup.title;
        let message="添加失败";
        if(title && regex.test(title) && title.length<20 && title.length>0){
          let brief = this.addVideoGroup.brief.trim() ;
          if(brief && brief.length<100){
            if(this.addVideoGroup.catagory.length>0){
              // this.videoGroup.push({title:this.addVideoGroup.title});
              let price = this.addVideoGroup.price;
              if(price>=1000){
                return this.$message.error("价格不能超过1000");//最大值999.99
              }
              this.addVideoGroup.brief='';
              this.addVideoGroup.title='';
              let groupid = this.$uuidv4();
              let istoll = this.addVideoGroup.istoll;
              let coverUrl = this.$userUtils.getCover(this,{
                userid:this.$userUtils.getUser().id,
                groupid:groupid,
                type:"videoGroupCover"

              })
              let attachementNames = this.addVideoGroup.attachementNames;
              let data = {
                id:groupid,
                title:title,
                brief:brief,
                coverUrl:coverUrl,
                istoll:istoll,
                price:price,
                catagory : (()=>{
                  let arr = [];
                  let catagory=this.addVideoGroup.catagory;
                  for(let i=0 ;i<catagory.length; i++){
                    arr.push(catagory[i].join('/'));
                  }
                  return JSON.stringify(arr);
                })(),
                attachementNames:attachementNames
              }
              this.groupData.groupid=groupid;

              this.$bus.$emit(this.$myEvents.UPLOAD_ATTACHEMENT,{
                type:"attachement",
                groupid:groupid+""
              });//算了，异步就异步吧
              // if(groupid!=null){
              //   console.log("data",data);
              //   alert("打断施法")
              //   return ;
              // }

              this.$userUtils.addVideoGroup(data,this);
              this.$refs.upload.submit();
              
            }else{
              message="请选择视频类型";
              this.$message.warning(message);
            }
            
          }else{
            message="描述1~100字";
            this.$message.warning(message);
          }
        }else{
          message="名称1~20，仅文字、数字、字母";
          this.$message.warning(message);
        }
      },
      handleChange(value) {
        console.log(value);
        this.addVideoGroup.catagory=value;
      },

      // 上传
      handleAvatarSuccess(res, file) {
        file;
        console.log("上传完成")
        // this.coverImageUrl = URL.createObjectURL(file.raw);
        this.$refs.upload.clearFiles();//清空已选择的文件
        this.$refs.cascaderPanel.clearCheckedNodes();
        this.fileList = [];
        console.log(res);
        if(res.code==1){
          this.$message.success("上传成功");
          this.coverImageUrl="uploaded"
          console.log("上传完成",this.coverImageUrl);
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

        
        // fileList.slice(0,1);

        if(this.coverImageUrl!="uploaded"){console.log("没完成",this.coverImageUrl)
          this.coverImageUrl = URL.createObjectURL(file.raw);
        }else{
          console.log("文件已上传，清空已选择的文件")
          this.fileLis=[];
          // alert("这里关闭弹窗")
          this.dialogFormVisible=false;
          
          this.coverImageUrl = "";
        }
        console.log(this.coverImageUrl);
      },
      showSelectValue(selectgroup){
        this.ruleForm.selectgroup=selectgroup;
      },
      closeDialog(){
        this.dialogFormVisible = false;
      },
      setAttachementNames(names){
        this.addVideoGroup.attachementNames = names.join(",");
      }
    },
    mounted(){
      this.$userUtils.getVideoGroup(this);
    }
}
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px !important;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>