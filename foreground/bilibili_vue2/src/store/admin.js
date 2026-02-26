import adminUtils from '@/adminUtils';
import myEvents from '@/eventsName';
import myInterface from '@/interface';
import axios from 'axios';
import Vue from 'vue';
const admin = {
    namespaced:true,
    actions:{
        requestPermissionList(context){
            axios.get(myInterface.requestPermissionList,{
                headers: {
                    'Authorization': `Bearer ${adminUtils.getToken()}`
                }
            }).then(
                response=>{
                    if(response.data.code==1){
                        let permissionList = response.data.data.permissionsList || [];
                        if(permissionList!=null && permissionList.length>0)
                        context.commit("SET_PERMISSION_LIST",permissionList);
                    }
                },
                error=>{
                    console.error("requestPermissionList error",error);
                }
            )
            console.log("获取permissionList",arguments)
        },
        serverSavePermission(context,data){
            let newMenu = JSON.parse(JSON.stringify(data.newMenu));
            axios.post(myInterface.savePermission,data,{
                headers: {
                    'Authorization': `Bearer ${adminUtils.getToken()}`
                }
            }).then(
                response=>{
                    if(response.data.code==1){
                        context.commit("PUT_PERMISSION",data.permissions);
                        Vue.prototype.$message.success("操作成功");
                        context.commit("SET_MY_MENU",newMenu);
                    }
                },
                error=>{
                    console.error("requestPermissionList error",error);
                }
            )
        },
        serverDeletePermission(context,data){
            console.log(data)
            axios.post(myInterface.deletePermission,data,{
                headers: {
                    'Authorization': `Bearer ${adminUtils.getToken()}`
                }
            }).then(
                response=>{
                    if(response.data.code==1){
                        console.log(response.data);
                        context.commit("SET_PERMISSION_LIST",response.data.data.permissionList);
                        Vue.prototype.$message.success("操作成功");
                        context.commit("SET_MY_MENU",response.data.data.adminMenu);
                    }
                },
                error=>{
                    console.error("requestPermissionList error",error);
                }
            )
        },
        serverInitSystem(context){
            axios.get(myInterface.getAdminMenu,{
                headers: {
                    'Authorization': `Bearer ${adminUtils.getToken()}`
                }
            }).then(
                response =>{
                    if(response.data.code==1){
                        context.commit("SET_MY_MENU",response.data.data.adminMenu);
                        context.commit("SET_ADMIN",response.data.data.admin);
                        context.commit("SET_ROLES",response.data.data.roles);
                        context.commit("SET_PERMISSION_LIST",response.data.data.permissionList);
                        Vue.prototype.$bus.$emit(myEvents.INIT_STSTEM_SUCCESS);
                    }
                    
                },
                error =>{
                    if(error.response.status==401){
                        Vue.prototype.$confirm("登录已过期！请重新登录").then(()=>[
                            window.location.href=window.location.origin+"/admin/login"
                        ])
                    }
                    console.log("出错了，",error);
                }
            );
        }
    },
    mutations:{
        SET_PERMISSION_LIST(state,value){
            state.permissionList=value;
        },
        SET_MY_MENU(state,value){
            state.myMenu=value;
        },
        SET_ADMIN(state,value){
            state.admin=value;
        },
        SET_ROLES(state,value){
            state.roles=value;
        },
        PUT_PERMISSION(state,value){
            console.log("这里插入权限数据",value,state.permissionList);
            state.permissionList = state.permissionList || [];
            for(let i=0;i<state.permissionList.length;i++){
                if(state.permissionList[i].leval==value.leval){
                    state.permissionList.splice(i,1,value);
                    console.log("更新permission")
                    return;
                }
            }
            console.log("push permssion")
            state.permissionList.push(value);
        },
    },
    getters:{
        roleAdmin(state){
            /**
             {
                ...
                roles:{
                    查询:4,
                    ...
                }
             }
             */
            //超级管理员
            const SUPPER_ADMIN_LV=0;
            console.log(state.admin.lv==SUPPER_ADMIN_LV)
            if(state.admin.lv==SUPPER_ADMIN_LV){
                return {
                    ...state.admin,
                    ...state.roles
                }
            }
            //非超级管理员
            for (let i = 0; i < state.permissionList.length; i++) {
                if (state.admin.lv === state.permissionList[i].leval) {
                    let rolesArr = state.permissionList[i].roles.toString();
                    rolesArr = rolesArr !== "" ? rolesArr.split(",") : [];
                    let roles = {};
                    for (let j in state.roles) {
                        for (let k in rolesArr) {
                            console.log(rolesArr[k] , state.roles[j].toString(),roles)
                        if (rolesArr[k] === state.roles[j].toString()) {
                            roles[j] = state.roles[j];
                        }
                        }
                    }
                    return { ...state.admin, ...roles };
                }
            }
            return {}
        }
    },
    state:{
        permissionList:[],
        myMenu:[],
        admin:{},
        roles:{}
    }
}
export default admin;