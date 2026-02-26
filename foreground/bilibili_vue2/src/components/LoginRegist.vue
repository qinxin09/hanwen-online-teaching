<!-- 
  组员：夏少聪
  功能：用户登录与注册的组件的切换
 -->
 <template>
  <div>
    <!-- Form -->
    <!-- <el-button type="text" @click="dialogFormVisible = true"
      style="padding:10px;">登录/注册</el-button
    > -->
    <!-- <button class="el-button el-button--text" @click="dialogFormVisible=true;">
      <span>登录/注册</span>
    </button> -->

    <el-dialog
      
      :destroy-on-close="true"
      :visible.sync="dialogFormVisible"
      width="30%"
      center
    >
      <div style="width:100%;justify-content: center;align-items: center;display:inline-flex;margin-bottom:20px;">
        <el-radio-group v-model="rule" size="medium" center>
          <el-radio-button label="登录"></el-radio-button>
          <el-radio-button label="注册"></el-radio-button>
        </el-radio-group>
      </div>
      <div style="background-color:white;">
        <component :is="currentComponent"></component>
      </div>

      
    </el-dialog>
  </div>
</template>

<script>
import { mapMutations } from 'vuex';
import LoginComponent from './Login.vue';
import RegistComponent from './Regist.vue';
const myComponents = {
  LoginComponent,RegistComponent
}
export default {
  name: "LoginRegist",
  data(){
    return {
      rule:"登录",
      dialogFormVisible:false,
      myComponents : myComponents
    }
  },
  computed:{
    currentComponent(){
      if(this.rule=='登录'){
        return myComponents.LoginComponent;
      }else if(this.rule == '注册'){
        return myComponents.RegistComponent;
      }
      return null;
    }
  },
  methods:{
    ...mapMutations("user",["init"]),
  },
  mounted(){
    //UserUtils.js
    this.$bus.$on(this.$myEvents.DIALOG_LOGIIN_SHOW,()=>{
      this.rule = '登录';
      this.dialogFormVisible=true;
      console.log("this.rule=",this.rule);
    });
    //NavBar.vue  Login.vue
    this.$bus.$on(this.$myEvents.LOGIN_SUCCESS,()=>{
      this.dialogFormVisible = false;
      this.init();
      console.log("登录成功");
    })
  },
  beforeDestroy(){
    this.$bus.$off(this.$myEvents.DIALOG_LOGIIN_SHOW);
    this.$bus.$off(this.$myEvents.LOGIN_SUCCESS);
  }
}
  
</script>