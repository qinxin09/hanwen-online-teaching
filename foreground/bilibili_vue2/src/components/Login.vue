<!-- 
  组员：xiashaocong
  功能：用户登录
 -->
 <template>
      <div>
        <el-form 
        :model="login" 
        :rules="rules" 
        status-icon
        ref="login"
        class="demo-ruleForm"
        v-if='rule=="登录"'>
          <el-form-item label="手机号" prop="phone" :label-width="formLabelWidth">
            <el-input v-model.number="login.phone"  autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密    码" prop="password" :label-width="formLabelWidth">
            <el-input
              v-model.trim="login.password"
              type="password"
              autocomplete="off"
              @keydown.enter.native="Login('login')"
            ></el-input>
          </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer" style="width:100%;display:flex;justify-content: center;align-items:center;">
        <el-button @click="closeDialog()">取 消</el-button>
        <el-button type="primary" @click="Login('login')">确 定</el-button>
      </span>


      
    </div>
</template>

<script>
import axios from 'axios' ;
import { mapActions } from 'vuex';
export default {
  name: "LoginComponent",
  data() {
    var checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("号码不能为空"));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {console.log(Number.isInteger(value),value)
          callback(new Error("请输入数字值"));
        } else {
          if (!(/^1[3-9]\d{9}$/.test(value))) {
            callback(new Error("号码格式错误"));
          } else {
            callback();
          }
        }
      }, 1000);
    };
    var checkPassword =  (rule, value, callback) => {
      if(value == ""){
        callback(new Error("密码不能为空！"));
      }
      callback();
    }
    return {
      rule: "登录",
      login: {
        phone: null,
        password: "",
      },
      rules:{
        phone:[{ validator: checkPhone, trigger: "blur" }],
        password:[{ validator: checkPassword, trigger: "blur" }],
      },
      formLabelWidth: "70px",
    };
  },
  methods: {
    ...mapActions("user",["init"]),
    // Login() {
    //   console.log("登录信息", this.login);
    //   this.form = {};
    // },
    Login(formName) {
      console.log(formName,this.$refs[formName])
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.login);
          let data = {...this.login};
          console.log(this.$myInterface.login);
          
          axios
            .post(this.$myInterface.login,data)
            .then((res)=>{
              console.log("服务端返回数据",res);
              if(res.data.code==1){
                this.$message({
                  message:res.data.msg,
                  type:"success"
                });
                //保存用户信息
                this.$userUtils.setUser(res.data.data.user);
                this.$userUtils.setToken(res.data.data.token);
                this.init();
                setTimeout(()=>{
                  this.closeDialog();
                },1000);
              }else{
                this.$message({
                  message: res.data.msg,
                  type: 'error'
                });
              }
            })
            .catch((e)=>{
              console.error("登录失败",e);
              this.$message({
                message: "访问服务器失败！",
                type: 'error'
              });
            })

        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    closeDialog(){
      console.log("通知登录成功",this.$myEvents.LOGIN_SUCCESS);
      this.$bus.$emit(this.$myEvents.LOGIN_SUCCESS);//NavBar.vue
    }
    
  }
};
</script>