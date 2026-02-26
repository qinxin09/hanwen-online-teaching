<!-- 
  组员：xiashaocong
  功能：管理员登陆
 -->
<template>
  <div class="login-container">
      
    <transition name="login-transition">
      <el-card class="login-card" v-loading="isLoading">
        <h1 class="login-title">Bilibili 管理员登录</h1>
        <el-form ref="form" :model="form" :rules="formRule" label-width="70px">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model.trim="form.phone" placeholder="请输入手机号"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model.trim="form.password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item label-width="0" >
            <el-button class="login-btn" type="primary" :loading="isLoading" @click="submitForm">登录</el-button>
          </el-form-item>
        </el-form>
        <router-link to="/admin/regist">去注册</router-link>
      </el-card>
    </transition>
  
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'LoginPage',
  data () {
    return {
      form: {
        phone: '',
        password: ''
      },
      formRule:{
        phone:[
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^1[34578]\d{9}$/, message: '手机号格式错误', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
      },
      isLoading: false
    }
  },
  methods: {
    submitForm () {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.isLoading = true;
          axios.post(this.$myInterface.adminLogin, this.form).then(response => {
            // console.log("response=",response);
            // 登录成功，跳转到系统首页
            if(response.data.code==1){
              console.log("response",JSON.stringify(response.data));
              let admin = response.data.data.admin;
              let token = response.data.data.token;
              this.$message.success("登录成功");
              this.$adminUtils.setAdmin(admin);
              this.$adminUtils.setToken(token);
              setTimeout(()=>{
                this.$router.push({ path: '/admin/index' });
              },1000);
            }else{
              this.$message.error(response.data.msg);
            }
          }).catch(error => {
            // 登录失败
            this.$message.error("登录失败");
            console.error(error);
          }).finally(() => {
            this.isLoading = false;
          });
        }else{
          // 表单验证失败，不提交数据
          console.log('表单验证失败');
        }
      });
    }
  }
}
</script>

<style scoped>
/* 添加过渡效果 */
.login-transition-enter-active,
.login-transition-leave-active {
  transition: all 0.5s;
}

.login-transition-enter,
.login-transition-leave-to {
  transform: translateX(-100%);
}

/* 其它样式 */
.login-container {
  background: #f0f2f5;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  /* 使用图片作为背景 */
  background-image: url(@/assets/adminbg.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
}

.login-card {
  width: 360px;
  text-align: center;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}

.login-title {
  margin: 20px 0;
  color: #409eff;
  font-size: 24px;
  font-weight: 600;
}

.login-btn {
  width: 100%;
  margin-top: 20px;
}
</style>