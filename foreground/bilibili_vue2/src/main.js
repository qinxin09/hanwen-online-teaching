import Vue from 'vue'
import App from './App.vue'

//引入路由配置文件
import router from './router.js'

//测试ui组件
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

//绑定自定义方法到Vue
import UserUtils from './UserUtils';
import myInterface from './interface';
import uuidv4 from './uuid.js';
import StorageUtils from './StorageUtils.js';
import myEvents from './eventsName.js';
import adminUtils from './adminUtils';
import store from './store';


Vue.use(uuidv4);
Vue.use(myInterface);
// 注册 UserUtils 插件
Vue.use(UserUtils);
Vue.use(adminUtils);
Vue.use(StorageUtils);
Vue.use(ElementUI);
Vue.use(myEvents);

Vue.config.productionTip = false
new Vue({
  router,//注册路由
  render: h => h(App),
  store,
  beforeCreate(){
    Vue.prototype.$bus=this;
  }
}).$mount('#app')
