<!-- 
  组员：xiashaocong
  功能：用户注册
 -->
 <template>
  <el-form ref="form" :model="form" label-width="70px" :rules="formRule" v-loading="isRegist">
        <el-form-item label="用户名" prop="name">
        <el-input v-model="form.name" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
        <el-input v-model.trim="form.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="form.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label-width="0">
        <el-button class="regist-btn" type="primary" :loading="isRegist" @click="submitForm">注册</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import axios from 'axios';
export default {
  name: 'LoginPage',
  props:["isIndexPage"],
  data () {
    return {
      form: {
        name: '',
        phone:'',
        password: ''
      },
      formRule:{
        phone:[
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^1[34578]\d{9}$/, message: '手机号格式错误', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
      },
      isRegist: false
    }
  },
  methods: {
    submitForm () {
      this.$refs.form.validate(valid => {
        if (valid) {
          // 表单验证通过，可以提交数据
          console.log(this.form);
          this.isRegist = true;
          axios.post(this.$myInterface.adminRegist, this.form)
          .then((response) => {
            // 注册成功，跳转到系统首页
            if(response.data.code==1){
              console.log("response=",response);
              this.$message.success("注册成功！");
              console.log("重置表单",this.isIndexPage);
              if(this.isIndexPage){
                console.log("重置表单")
                setTimeout(()=>{
                    this.$refs.form.resetFields();
                },1000);
              }else{
                setTimeout(()=>{
                    this.$router.push({ path: '/admin/login' });
                },1000);
              }
            }else{
              this.$message.warning(response.data.msg);
            }
          }).catch(error => {
            // 注册失败
            console.log("服务器故障！",error);
          }).finally(() => {
            this.isRegist = false;
          });

        } else {
          // 表单验证失败，不提交数据
          console.log('表单验证失败');
        }
      });
    }
  }
}
</script>

<style>
.regist-btn {
  width: 100%;
  margin-top: 20px;
}
</style>