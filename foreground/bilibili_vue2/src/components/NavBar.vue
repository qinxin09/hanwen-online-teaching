<!--
组员：yws
功能：顶部导航栏
-->
<template>
  <div>
    <!-- 放到这里，没有position=fxed,避免遮罩层遮挡对话框 -->
    <LoginRegist/>
    <div style="
      position: fixed;
      width: 100%;
      margin:auto;
      z-index: 2000;
      background-color: white;
      box-shadow:rgba(0, 0, 0, 0.1) 0px 0.1px 3px 2px;
      "
      >
      <!-- <div style="width:80%;margin:auto;"> -->
      <div :style="myStyle">
        <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect">
          <el-menu-item >
            <router-link to="/" >
              <img src="../assets/bilibili.png" style="height:30px;cursor:pointer;" >
            </router-link>
          </el-menu-item>
          <el-menu-item index="1" ><router-link to="/">首页</router-link></el-menu-item>
          <el-submenu index="2" v-show="false">
            <template slot="title">分类</template>
            <el-menu-item index="2-1">选项1</el-menu-item>
            <el-menu-item index="2-2">选项2</el-menu-item>
            <el-menu-item index="2-3">选项3</el-menu-item>
            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项1</el-menu-item>
              <el-menu-item index="2-4-2">选项2</el-menu-item>
              <el-menu-item index="2-4-3">选项3</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-menu-item index="3">创作者中心</el-menu-item>
          <el-menu-item index="4">发布视频
          </el-menu-item>
          
          <el-menu-item index="5" v-if="!isLogin">
            <button class="el-button el-button--text" @click="showDialog()">
              <span>登录/注册</span>
            </button>
          </el-menu-item>
          <el-submenu index="6" v-if="isLogin">
            <template slot="title"><img class="imgicon" :src="usericon" >{{formatName}}</template>
            <el-menu-item index="6-1" @click="jumpPersonalCenter">个人空间</el-menu-item>
            <el-menu-item index="6-2" @click="exitLogin(true)">退出</el-menu-item>
            
          </el-submenu>
          <el-menu-item index="7" @click="backgroundManage()" >后台管理</el-menu-item>
          <el-menu-item index="8" @click="anotherVideos()" >第三方视频</el-menu-item>

        </el-menu>
        <!-- <div class="line"></div> -->
      </div>
  </div>
  <div style="width:100%;height:61px;background-color: white;"></div>
  </div>
</template>
  
  <script>
import { mapActions, mapState } from 'vuex';
   import LoginRegist from './LoginRegist.vue';
export default {
  name: "NavComponent",
  props:["activeIndex2","myStyle"],
  data() {
    return {
      activeIndex: this.activeIndex2,
      
      user:{},
      usericon:""
    };
  },
  computed:{
    ...mapState("user",["isLogin"]),
    formatName(){
      if(this.user!=null && this.user.name!=null){
        if((this.user.name+"").length>4){
          return this.user.name.substring(0,3) + "..." ;
        }else{
          return this.user.name;
        }
      }
      return "user"
    }
  },
  created(){
    this.$bus.$on(this.$myEvents.FRESH_ICON,this.getUserIcon);//uploadIcon.vue
    this.$bus.$on(this.$myEvents.LOGIN_EXIT,this.exitLogin);//UserUtils.vue
    this.$bus.$on(this.$myEvents.LOGIN_SUCCESS,()=>{
      // this.isLogin=true;
      this.user=this.$userUtils.getUser();
      this.getUserIcon();
    // this.$set(this,"user",user);
    });
  },
  beforeDestroy(){
    console.log("beforeDestory 解绑事件",this.$myEvents.FRESH_ICON);
    this.$bus.$off(this.$myEvents.FRESH_ICON);
    this.$bus.$off(this.$myEvents.LOGIN_EXIT);//NavBar.vue
    this.$bus.$off(this.$myEvents.LOGIN_SUCCESS);//Login.vue
  },
  destroyed(){
    console.log("destoryed")
  },
  methods: {
    setActiveWindow(){
      window.addEventListener('focus', ()=> {
        // 当窗口获得焦点时执行的操作
        //回到窗口
        console.log('窗口已获得焦点');
        this.init();
        this.user=this.$userUtils.getUser();
        this.getUserIcon();
      });
    },
    ...mapActions("user",["init"]),
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
      switch(key){
        case '1':
        this.$router.push({ path: '/'});
          break;
          case '3':
        this.$router.push({ path: '/createCenter'});
          break;
        case '4':
          this.publishVideo();
          break;
      }
    },
    exitLogin(refresh){
      this.$userUtils.clearUser();
      // this.isLogin=false;
      this.init();
      this.user={};
      this.$message({
        message:"已退出登录！",
        type:"success"
      });
      if(refresh){
        window.location.href="/"
      }
    },
    jumpPersonalCenter(){
      // window.location.href="/personalCenter";
      // this.$router.push({ path: '/personalCenter', query: { userId: this.user.id,isSelf:"true" } });
      window.location.href=window.location.origin+"/personalCenter?userId="+this.user.id+"&isSelf=true";
    },
    showDialog(){
      this.$bus.$emit(this.$myEvents.DIALOG_LOGIIN_SHOW);
    },
    publishVideo(){
      
      if(!this.isLogin){
      // 应该是要放在异步函数里使用，否则首次没有反应
        setTimeout(()=>{
          this.$message({
          message: '您还没有登录!',
          center: true,
          type:"warning"
        });
        })
      }else{
        return this.$router.push({path:"/publishVideo"}) ;
      }
    },
    getUserIcon(){
      console.log("获取用户头像")
      console.log(this.user)
      if(this.user && this.user.id!=null){
        this.usericon = this.$userUtils.getUserIcon(this,{
          userid:this.user.id,
          type:"icon"
        });
      }
      
    },
    backgroundManage(){
      window.open(window.location.origin + "/admin/login");
    },
    anotherVideos(){
      return this.$router.push({path:"/anotherVideo"}) ;
    }
  },
  
  mounted(){
    this.init();
    let user=this.$userUtils.getUser();
    this.user=user;
    // this.$set(this,"user",user);
    console.log(this.user);
    // this.$userUtils.getImage(this);
    this.getUserIcon();
    this.setActiveWindow();
  },

  components:{
    LoginRegist
  }
};
</script>

<style scoped>
  .imgicon {
    /* 头像 */
    border: 1px solid gray;
    border-radius: 50%;
    width: 35px;
    height: 35px;
    object-fit: cover;
    margin-right: 10px;
  }
  .is-active {
    color:skyblue !important;
    font-weight: bolder;
  }
  a {
    text-decoration: none;
  }
</style>