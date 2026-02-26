<!--
组员：孙然
功能：展示、更新用户信息
-->
<template>
  <div style="width:80%;height:50%;margin:auto;">
  

  <el-form ref="passwordForm"  
    :model="passwordForm"
    :rules="passwordRule" class="demo-personinfoForm" label-width="100px"
    v-show="editPassword"
    :disabled="!editable2"
    >
    <el-form-item label="密码" prop="pass">
      <el-input
        type="password"
        v-model="passwordForm.pass"
        autocomplete="off"
      ></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="checkPass">
      <el-input
        type="password"
        v-model="passwordForm.checkPass"
        autocomplete="off"
      ></el-input>
    </el-form-item>
  </el-form>

  <el-form
    v-show="!editPassword"
    :model="user"
    status-icon
    :rules="personinfoRules"
    ref="personinfoForm"
    label-width="100px"
    class="demo-personinfoForm"
    :disabled="!editable1"
  >
  
    
    <el-form-item label="姓名" prop="name">
      <el-input v-model.trim="user.name"></el-input>
    </el-form-item>
    <el-form-item label="年龄" prop="age">
      <el-input v-model.number="user.age"></el-input>
    </el-form-item>
    
    
  </el-form>

  <el-form lable-position="right" label-width="50%">
    <el-form-item v-if="props_.isSelf && !editPassword" >
        <el-button @click="handleEditPersonInfo(false)" circle :icon="editable1?'el-icon-check':'el-icon-edit'"></el-button>
        <el-button v-show="editable1" @click="handleEditPersonInfo(true)" circle icon="el-icon-close"></el-button>
    </el-form-item>
    <el-form-item v-if="props_.isSelf && editPassword" >
        <el-button @click="handleEditPassword(false)"  circle :icon="editable2?'el-icon-check':'el-icon-edit'"></el-button>
        <el-button v-show="editable2" @click="handleEditPassword(true)" icon="el-icon-close" circle></el-button>
    </el-form-item>
  </el-form>

</div>
</template>

<script>
export default {
  name: "PersonInfo",
  props: ["props_"],
  mounted() {
    
  },
  data(){
    var checkAge = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('年龄不能为空'));
        }
        setTimeout(() => {
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字值'));
          } else {
            if (value < 18) {
              callback(new Error('必须年满18岁'));
            }else if(value>200){
              callback(new Error('年龄小于200！'));
            } else {
              callback();
            }
          }
        }, 1000);
      };
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.passwordForm.checkPass !== '') {
            this.$refs.passwordForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.passwordForm.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      var checkName = (rule,value,callback)=>{
        if(value==null || value===""){
          callback(new Error("姓名不能为空！"));
        }else{
          if(value.length>8){
           callback(new Error("姓名长度不超过8位！"))
          }
          callback();
        }
      };
      
      return {
        editable1:false,
        editable2:false,
        user:{},
        editPassword:'',
        passwordForm:{
          pass: '',
          checkPass: '',
        },
        passwordRule:{
          pass: [
            { validator: validatePass, trigger: 'change' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'change' }
          ],
        },
        personinfoRules: {
          name:[
          { validator: checkName, trigger: 'change' }
          ],
          age: [
            { validator: checkAge, trigger: 'change' }
          ],
          
        }
      };

  },
  watch:{
    props_:{
      immediate:true,
      deep:true,
      handler(value){
        console.log("this is form components: ",this.$refs)
        console.log("password",this.$refs.passwordForm);
        console.log("password",this.$refs.personinfoForm);
        console.log(value);
        this.user={...value.user},
        this.editPassword=value.editPassword
        if(this.editPassword){
          // console.log(this.$refs);
          // console.log(this.$refs.passwordForm);
          // window.test=this.$refs
          // setTimeout(()=>{
            this.$nextTick(()=>{
              // this.$refs.passwordForm.resetFields();
              this.$refs.personinfoForm.resetFields();
            })
          // })
        }else{
          this.$nextTick(()=>{
            this.$refs.personinfoForm.resetFields();
          })
          
        }
      }
    },
    user:{
      deep:true,
      immediate:true,
      handler(){
        
      }
    }
    
  },
  methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if(formName=="passwordForm"){
              this.editable2=false;
              this.$userUtils.updPassword({password:this.passwordForm.pass},this);
            }
            if(formName=="personinfoForm"){
              this.editable1=false;
              this.$userUtils.updUser(
                {
                  name:this.user.name,
                  age:this.user.age
                },this);
            }

            return true;
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      handleEditPersonInfo(cancle){
        if(cancle){
          this.editable1=!this.editable1;
          this.user={...(this.props_.user)};
          return console.log("取消");
        }
        if(this.editable1){
          console.log("提交修改personinfoForm");
          if(this.submitForm('personinfoForm')){
            this.editable1=false;
            console.log("提交成功");
          }
        }else{
          console.log("编辑模式");
          this.editable1=!this.editable1
        }
        
      },

      handleEditPassword(cancle){
        if(cancle){
          this.editable2=!this.editable2;
          this.passwordForm.pass='';
          this.passwordForm.checkPass='';
          this.$refs.passwordForm.resetFields();
          return console.log("取消");
        }
        if(this.editable2){
          console.log("提交修改passwordForm");
          this.submitForm('passwordForm');
        }else{
          console.log("编辑模式");
          this.editable2=!this.editable2
        }
        
      }
    }
};
</script>

<style>
.el-col-12 {
    width: 100%;
}
</style>