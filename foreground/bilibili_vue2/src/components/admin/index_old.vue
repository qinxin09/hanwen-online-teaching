<template>
  <div class="admin-home">
    <el-container class="container">
      <el-header class="header">
        <div class="title">后台管理系统</div>
        <el-menu class="menu" mode="horizontal" :background-color="'#333'" :text-color="'#fff'" :active-text-color="'#ffd04b'">
          
          <el-submenu index="1">
            <template slot="title"><img class="imgicon" src="@/assets/admin.png" >{{admin.name}}</template>
            <el-menu-item index="1-1" @click.native="exitLogin()">退出</el-menu-item>
          </el-submenu>

        </el-menu>
      </el-header>
      <el-container>
        <el-aside class="aside">
          <!-- 左侧菜单栏，可以根据需要添加子菜单项 -->
          <el-menu @select="handleSelect" default-active="user-list" :background-color="'#304156'" :text-color="'#bfcbd9'" :active-text-color="'#409EFF'" class="el-menu-vertical-demo">
            <el-submenu index="user">
              <template slot="title">
                <i class="el-icon-user"></i>
                <span>用户管理</span>
              </template>
              <el-menu-item index="user-list" >用户列表</el-menu-item>
            </el-submenu>
            <el-submenu index="product">
              <template slot="title">
                <i class="el-icon-video-camera"></i>
                <span>合集管理</span>
              </template>
              <el-menu-item index="group-list-all">全部</el-menu-item>
              <el-menu-item index="group-list-wait">待审核</el-menu-item>
              <el-menu-item index="group-list-passed">已通过</el-menu-item>
              <el-menu-item index="group-list-illegal">已违规</el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main class="main">
          <!-- 内容区域 -->
          <div style="width:100%;height:100%;border:1px solid black;">
            <UserList v-if="currentMenuItemKey=='user-list'" :userListProp="userListProp"></UserList>
            <GroupList v-if="currentMenuItemKey.match('group-list')" :groupListProp="groupListProp"></GroupList>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
    import UserList from './userList.vue';
    import GroupList from './groupList.vue';
    export default {
        name:"AdminIndex",
        components:{
          UserList,GroupList
        },
        data(){
          return {
            admin:{},
            currentMenuItemKey:'',
            userListData:{},
            userListProp:{},
            groupListProp:{}
          }
        },
        created(){
          // 获取管理员信息
          if(!this.$adminUtils.checkIsLogin()){
            this.$confirm("页面错误！请先登录");
            setTimeout(function(){
              window.location.href=window.location.origin+"/admin/login";
            },3000)
          }
          this.admin=this.$adminUtils.getAdmin() || {};
          this.initProp("user-list");
        },
        mounted(){
          
        },
        methods:{
          exitLogin(){
            this.admin={}
            this.$adminUtils.clearAdmin();
            this.$adminUtils.clearToken();
            window.location.href=window.location.origin+"/admin/login";
          },
          handleSelect(key) {
            this.currentMenuItemKey=key;
            this.groupListProp=key;
          },
          initProp(key){
            this.currentMenuItemKey=key;
            this.userListProp = key;
          }
        }
    }
</script>

<style scoped>
.admin-home {
  height: 100%;
}
.container {
  height: 100%;
  width: 100%;
}

.header {
  background-color: #333;
  color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  border-bottom: 1px solid #ddd;
}

.title {
  font-size: 24px;
  font-weight: 700;
}

.menu {
  margin-bottom: 0;
}

.aside {
  background-color: #304156;
}

.main {
  background-color: #f0f2f5;
  padding: 20px;
  overflow: auto;
}

.el-menu-vertical-demo {
  height: 100%;
}
/* 头像 */
.imgicon {
    /* 头像 */
    border: 1px solid gray;
    border-radius: 50%;
    width: 35px;
    height: 35px;
    object-fit: cover;
    margin-right: 10px;
  }

  .el-menu-item.is-active {
    outline: 0;
    /* background-color: #ecf5ff !important; */
    background-color: rgb(30, 52, 69) !important;
  }
</style>
