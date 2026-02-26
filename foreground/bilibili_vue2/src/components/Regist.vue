<!-- 
  组员：xiashaocong
  功能：用户注册的表单
 -->
 <template>
  <div>
  <el-form
    :model="ruleForm"
    status-icon
    :rules="rules"
    ref="ruleForm"
    label-width="100px"
    class="demo-ruleForm"
  >
    <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
      <el-input v-model.trim="ruleForm.name"></el-input>
    </el-form-item>

    <el-form-item label="性别" prop="sex" :label-width="formLabelWidth">
      <!-- <el-input v-model.trim="ruleForm.sex"></el-input> -->

      <el-select v-model="ruleForm.sex" placeholder="请选择性别">
        <el-option label="男" :value="1"></el-option>
        <el-option label="女" :value="0"></el-option>
      </el-select>

    </el-form-item>


    <el-form-item label="年龄" prop="age" :label-width="formLabelWidth">
      <el-input type="number" v-model.number="ruleForm.age"></el-input>
    </el-form-item>

    <el-form-item label="手机号" prop="phone" :label-width="formLabelWidth">
      <el-input v-model.number="ruleForm.phone"></el-input>
    </el-form-item>
  
    <el-form-item label="密码" prop="password" :label-width="formLabelWidth">
      <el-input
        type="password"
        v-model="ruleForm.password"
        autocomplete="off"
      ></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="checkPass" :label-width="formLabelWidth">
      <el-input
        type="password"
        v-model="ruleForm.checkPass"
        autocomplete="off"
      ></el-input>
    </el-form-item>
    
    <!-- <el-form-item :label-width="formLabelWidth">
      <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item> -->

    
  </el-form>
  <span slot="footer" class="dialog-footer" style="width:100%;display:flex;justify-content: center;align-items:center;">
      <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </span>

  </div>
</template>
<script>
import axios from 'axios' ;
export default {
  name:"RegistComponent",
  data() {
    var checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("年龄不能为空"));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error("请输入数字值"));
        } else {
          if (value < 18  || value>120) {
            callback(new Error("年龄限制18岁 ~ 120岁"));
          } else {
            callback();
          }
        }
      }, 1000);
    };

    var checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("号码不能为空"));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
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
    var checkSex = (rule, value, callback) => {
      if (value !=1 && value != 0) {
        return callback(new Error("性别不能为空"));
      }
      callback();
    };
    var checkName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("姓名不能为空"));
      }
      callback();
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        password: "",
        checkPass: "",
        age: "",
        sex:"",
        name:"",
        phone:""
      },
      rules: {
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
        
        name:[{ validator: checkName, trigger: "blur" }],
        sex: [{ validator: checkSex, trigger: "blur" }],
        age: [{ validator: checkAge, trigger: "blur" }],
        phone:[{ validator: checkPhone, trigger: "blur" }],
        password: [{ validator: validatePass, trigger: "blur" }]
      },
      formLabelWidth: "70px"
    };
  },
  methods: {
    Login(){
      var data = {...this.ruleForm};
      console.log(typeof data, data);
      axios.post(this.$myInterface.regist, data, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then((res) => {
        console.log("后端返回：", res);
        if(res.data.code==1){
          this.$message({
            message: '注册成功',
            type: 'success'
          });
          setTimeout(()=>{
            console.log(this.$bus);
            this.$bus.$emit(this.$myEvents.DIALOG_LOGIIN_SHOW);
          },1000)
        }else{
          this.$message({
            message: res.data.msg,
            type: 'error'
          });
        }
      })
      .catch((e) => {
        console.log("出错了", e);
        this.$message({
          message: "访问服务器失败！",
          type: 'error'
        });
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {console.log(formName,valid);
          this.Login();
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>