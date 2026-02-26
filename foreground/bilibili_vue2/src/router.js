// router.js
import Vue from 'vue'
import VueRouter from 'vue-router'
import IndexPage from './components/Index.vue'
import MenuPage from './test_ui/menu.vue'
import PersonalPage from './components/personalPage.vue'
import PlayPage from './components/playPage.vue'
import PublishVideo from './components/publish/publishVideo.vue'
import AdminLogin from './components/admin/login.vue'
import AdminRegist from './components/admin/regist.vue'
import AdminIndex from './components/admin/index.vue'
import CreateCenter from './components/createCenter/index.vue'
import AnotherVideo from './components/anotherVideo/anotherVideo.vue'
// 引入 VueRouter 并使用
Vue.use(VueRouter)

const routes = [
  {
    path: '/', component: IndexPage,
    children: [
      // {
      //   path:"menu",
      //   components:{
      //     componentName:MenuPage
      //   }
      // }
    ]
  },
  {
    path: "/menu",
    component: MenuPage
  },
  {
    path:"/personalCenter",
    component:PersonalPage
  },
  {
    path:"/playPage",
    component:PlayPage
  },
  {
    path:"/publishVideo",
    component:PublishVideo
  },
  {
    path:"/admin/login",
    component:AdminLogin
  },
  {
    path:"/admin/regist",
    component:AdminRegist
  },
  {
    path:"/admin/index",
    component:AdminIndex
  },
  {
    path:"/createCenter",
    component:CreateCenter
  },
  {
    path:"/anotherVideo",
    component:AnotherVideo
  }

]

const router = new VueRouter({
  mode: 'history',
  hash: false,
  routes
})

export default router;