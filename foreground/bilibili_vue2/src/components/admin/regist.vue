<!-- 
  组员：夏少聪
  功能：管理员注册
 -->
 <template>
    <div class="login-container">
      <el-card class="login-card">
        <h1 class="login-title">Bilibili 管理员注册</h1>
        <RegistForm :isIndex="isIndex"></RegistForm>
        <router-link to="/admin/login">去登录</router-link>
      </el-card>
    </div>
  </template>

<script>
import RegistForm from '@/components/admin/registForm.vue'
export default {
  name: 'LoginPage',
  props:["isIndex"],
  components:{
    RegistForm
  }
}
</script>

  <style scoped>
  /*———覆盖ElementUI主题颜色———*/
/* $--color-primary: #409eff; */
@import 'element-ui/lib/theme-chalk/index.css';

.login-container {
  background: #f0f2f5;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
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

</style>