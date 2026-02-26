<!--
组员：杨万胜
功能：后台管理首页
二次开发（展示菜单列表）
-->
<template>
  <div class="admin-home">
    <el-container class="container">
      <el-header class="header">
          <div class="title">后台管理系统</div>
          <el-menu class="menu" mode="horizontal" :background-color="'#333'" :text-color="'#fff'" :active-text-color="'#ffd04b'">
            
            <el-submenu index="1">
              <template slot="title">
                <div style="display: inline-flex; justify-content: center; align-items: center;">
                  <img class="imgicon" src="@/assets/admin.png" >
                  <div>
                    <span style="margin-right: 6px;color:skyblue;font-size: 10px;">Lv{{ admin.lv }}</span>
                    <span>{{ admin.name }}</span>
                  </div>
                </div>
                
              </template>
              <el-menu-item index="1-1" @click.native="exitLogin()">退出</el-menu-item>
            </el-submenu>

          </el-menu>

      </el-header>
      <el-container>
        <el-aside class="aside">
          <!-- 左侧菜单栏，可以根据需要添加子菜单项 -->
          <!-- <el-menu @select="handleSelect" default-active="user-list" :background-color="'#304156'" :text-color="'#bfcbd9'" :active-text-color="'#409EFF'" class="el-menu-vertical-demo">
            <el-submenu index="user">
              <template slot="title">
                <i class="el-icon-user"></i>
                <span>用户管理</span>
              </template>
              <el-menu-item index="user-list" >用户列表</el-menu-item>
            </el-submenu>
            <el-submenu>
              <template slot="title">
                <i class="el-icon-video-camera"></i>
                <span>合集管理</span>
              </template>
              <el-menu-item index="group-list-all">全部</el-menu-item>
              <el-menu-item index="group-list-wait">待审核</el-menu-item>
              <el-menu-item index="group-list-passed">已通过</el-menu-item>
              <el-menu-item index="group-list-illegal">已违规</el-menu-item>
            </el-submenu>

          </el-menu> -->

          <el-menu  @select="handleSelect" :default-active="defaultActive" :background-color="'#304156'" :text-color="'#bfcbd9'" :active-text-color="'#409EFF'" class="el-menu-vertical-demo">
            <el-submenu v-for="(item,index) in myMenu" :key="index" :index="item.icon+index">
              <template slot="title">
                <i :class="item.icon"></i>
                <span>{{item.treename}}</span>
              </template>
              <el-menu-item v-for="(item2,index2) in item.adminTreeList" :key="index+''+index2" :index="index+','+index2">{{item2.treename}}</el-menu-item>
            </el-submenu>
          </el-menu>

        </el-aside> 
        <el-main class="main">
          <div style="width:100%;height:100%;margin: 60px 20px;">
            <div style="width:1100px;">
                <!-- 已打开的窗口 -->
                <div style="position:fixed;z-index: 1;width:1100px;background-color:white;">
                  <TabsComponent/>
                </div>
                <div style="width:100%;height:60px;"></div>
                <!-- 内容区域 -->
                <div style="width:100%;height:100%;">
                  <UserList v-if="currentMenuItemKey.match('user-list')" :userListProp="currentMenuItemKey" :admin="admin" :permissionList="permissionList" :roles="roles"></UserList>
                  <GroupList v-if="currentMenuItemKey.match('group-list')" :groupListProp="currentMenuItemKey" :admin="admin" :permissionList="permissionList" :roles="roles"></GroupList>
                  <AdminList v-if="currentMenuItemKey.match('admin-list')" :adminListProp="currentMenuItemKey" :admin="admin" :permissionList="permissionList"></AdminList>
                  <AddAdmin v-if="currentMenuItemKey.match('admin-add')"></AddAdmin>
                  <PermissionList v-if="currentMenuItemKey.match('permission-list')" :menuList="myMenu"></PermissionList>
                  <Task v-if="currentMenuItemKey.match('task-')" :currentMenuItemKey="currentMenuItemKey"></Task>
                  <WorkList v-if="currentMenuItemKey == 'works-list'"></WorkList>

                </div>
            </div>
              
          </div>
          
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
    import UserList from './userList.vue';
    import GroupList from './groupList.vue';
    import AdminList from './adminList.vue';
    import AddAdmin from './addAdmin.vue';
    import TabsComponent from './Tabs.vue';
    import PermissionList from './permission/permissionList.vue';
    import Task from '@/components/admin/task/index.vue';
    import { mapState,mapActions } from 'vuex';
    import WorkList from './task/works.vue';

    export default {
        name:"AdminIndex",
        components:{
          UserList,GroupList,AdminList,AddAdmin,TabsComponent,PermissionList,Task,WorkList
        },
        data(){
          return {
            // admin:{},
            currentMenuItemKey:'',
            // userListData:{},
            // userListProp:{},
            // groupListProp:{},
            // adminListProp:{},
            // myMenu:[],
            lv:'',
            defaultActive:"0"//默认
          }
        },
        computed:{
          ...mapState("admin",["myMenu","admin","permissionList","roles"])
        },
        async created(){
          console.log("admin index created")
          // 获取管理员信息
          if(!this.$adminUtils.checkIsLogin()){
            this.$confirm("页面错误！请先登录");
            setTimeout(function(){
              window.location.href=window.location.origin+"/admin/login";
            },3000)
          }
          /*
          //获取管理员菜单：
          let res = await this.$adminUtils.getAdminMenu(this);
          if(res.data.code==1){
            this.myMenu = res.data.data.adminMenu;
            this.admin=res.data.data.admin
            this.serverInitSystem();
            this.defaultActive="0,0";
          }
          */

          // 获取菜单列表、管理员信息
          await this.serverInitSystem();
          this.defaultActive="0,0";
          // this.admin=this.$adminUtils.getAdmin() || {};
          
          this.$bus.$on(this.$myEvents.SWITCH_MENU_LIST,(data)=>{
            let key = data.key;
            let active = data.active;
            this.setCurrentKey(key);
            this.defaultActive=active;
          });
          this.$bus.$on(this.$myEvents.INIT_STSTEM_SUCCESS,()=>{
            //初始化完毕(获取菜单列表，管理员信息)
            console.log("初始化完毕")
            if(this.myMenu.length>0){
              if(this.myMenu[0].adminTreeList[0].treepath)
              this.initProp(this.myMenu[0].adminTreeList[0].treepath);
            }
            
          })
        },
        beforeDestroy(){
          this.$bus.$off(this.$myEvents.SWITCH_MENU_LIST);
        },
        
        methods:{
          ...mapActions("admin",["serverInitSystem"]),
          ...mapActions("taskStore",["serverInitTask_Admin"]),
          exitLogin(){
            this.admin={}
            this.$adminUtils.clearAdmin();
            this.$adminUtils.clearToken();
            window.location.href=window.location.origin+"/admin/login";
          },
          setCurrentKey(key){
            this.currentMenuItemKey=key;
            // this.groupListProp=key;
            // this.userListProp=key;
            // this.adminListProp=key;
          },
          handleSelect(index_index2) {
            let index=index_index2.split(',')[0];
            let index2=index_index2.split(',')[1];
            let key = this.myMenu[index].adminTreeList[index2].treepath;
            console.log("切换了菜单列表")
            this.setCurrentKey(key);
            let tab = {
              title:this.myMenu[index].adminTreeList[index2].treename,
              name:this.myMenu[index].adminTreeList[index2].treename,
              content:"",
              key:key,
              active:index_index2,
            };
            this.addTab(tab);
          },
          addTab(tab){
            this.$bus.$emit(this.$myEvents.SHOW_TAB,tab);
          },
          initProp(key){
            console.log("初始：切换了菜单列表")
            this.currentMenuItemKey=key;
            // this.userListProp = key;
            let tab={
              title:this.myMenu[0].adminTreeList[0].treename,
              name:this.myMenu[0].adminTreeList[0].treename,
              content:"",
              key:key
            }
            this.addTab(tab);
          }
        },
        mounted(){
          //初始化tasks
          this.serverInitTask_Admin();
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
  padding: 0px;
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
  .el-header {
    position: fixed;
    width: 100%;
    z-index:1;
  }
  .el-aside {
    margin-top: 60px;
  }
</style>
