<template>
  <div>
    <!-- 顶部导航栏 -->
    <el-menu
      :default-active="activeIndex2"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <el-menu-item index="1">处理中心</el-menu-item>
      <el-submenu index="2">
        <template slot="title">我的工作台</template>
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
      <el-menu-item index="3" disabled>消息中心</el-menu-item>
      <el-menu-item index="4"
        ><a href="https://www.ele.me" target="_blank">订单管理</a></el-menu-item
      >
    </el-menu>
    <!-- 左侧导航栏 -->
    
      <el-row class="tac">
        <el-col :span="12">
            <div class="leftSlideBar">
          <h5>自定义颜色</h5>
          <el-menu
            default-active="2"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
          >
            <el-submenu index="1">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>导航一</span>
              </template>
              <el-menu-item-group>
                <template slot="title">布局</template>
                <el-menu-item index="1-1" @click="addRoute(myComponents.LayoutBase)">基础布局</el-menu-item>
                <el-menu-item index="1-2" @click="addRoute(myComponents.Buttons)">常用按钮</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group title="分组2">
                <el-menu-item index="1-3" @click="addRoute(myComponents.LoadingState)">骨架屏</el-menu-item>
              </el-menu-item-group>
              <el-submenu index="1-4">
                <template slot="title">登录注册</template>
                <el-menu-item index="1-4-1" @click="addRoute(myComponents.LoginDialog)">登录</el-menu-item>
              </el-submenu>
            </el-submenu>
            <el-menu-item index="2">
              <i class="el-icon-menu"></i>
              <span slot="title">导航二</span>
            </el-menu-item>
            <el-menu-item index="3" disabled>
              <i class="el-icon-document"></i>
              <span slot="title">导航三</span>
            </el-menu-item>
            <el-menu-item index="4">
              <i class="el-icon-setting"></i>
              <span slot="title">导航四</span>
            </el-menu-item>
          </el-menu>
        </div>
        </el-col>
        <el-col :span="9">
            <!-- <router-link to="/new" >测试</router-link> -->
            <div style="background-color:white;">
              <component :is="currentComponent"></component>
            </div>
        </el-col>
        
    </el-row>
    <button index="1-4-1" @click="addRoute(myComponents.LoginDialog)">登录</button>
  </div>
</template>


<script>
  import LayoutBase from './layout.vue' ;
  import Buttons from './buttons.vue' ;
  import LoadingState from './骨架屏.vue';
  import LoginDialog from '../components/Login.vue' ;
  const myComponents = {
    LayoutBase,Buttons,LoadingState,LoginDialog
  }
export default {
  name: "MenuComponent",
  data: function () {
    return {
      //顶部导航栏
      activeIndex: "1",
      activeIndex2: "1",
      currentComponent:null,
      myComponents : myComponents
    };
  },
  methods: {
    //顶部导航栏
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    //左侧侧边栏
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    addRoute(component) {
      // 动态添加子组件
      this.currentComponent = component;
    }
  },
};
</script>

<style>
.leftSlideBar {
  width: 300px;
}
</style>