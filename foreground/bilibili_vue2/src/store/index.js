import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex);
import admin from './admin.js'
import indexPage from './indexPage.js'
import taskStore from './task.js'
import works from './works.js'
import user from './user.js'

export default new Vuex.Store({
    modules:{
        admin,indexPage,taskStore,works,user
    }
});