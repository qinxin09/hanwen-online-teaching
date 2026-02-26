/**
 *  yws
 *  获取管理员相关信息、
 * 获取管理员菜单
 * 为管理员操作向后端发起请求
 */
import axios from 'axios';


    // axios.interceptors.request.use(
    // config => {
    //     console.log("每次发起请求都拦截一下，是否带上token")
    //     console.log("token==",localStorage.getItem('adminToken'));
    //     const token = localStorage.getItem('adminToken');
    //     if (token!="undefined" && token!=null ) {
    //         console.log("带上token",token)
    //         config.headers.Authorization = `Bearer ${token}`;
    //     }
    //     return config;
    // },
    // error => {
    //     return Promise.reject(error);
    // }
    // );

const adminUtils = {
    setAdmin: function (Admin) {
        localStorage.setItem('Admin', JSON.stringify(Admin))
    },
    setToken: function (token) {
        localStorage.setItem('adminToken', token)
    },
    getAdmin: function () {
        return JSON.parse(localStorage.getItem('Admin'))
    },
    getToken: function () {
        return localStorage.getItem('adminToken');
    },
    clearToken: function () {
        localStorage.removeItem('adminToken');
    },
    clearAdmin: function () {
        localStorage.removeItem('Admin');
        this.clearToken();
    },
    checkIsLogin:function(){
        return localStorage.getItem('Admin')!=null;
    },
    getUserList: async function(vc){
        try {
            return await axios.get(vc.$myInterface.getUserList,{
                headers: {
                    'Authorization': `Bearer ${this.getToken()}`
                }
            });
        } catch (error) {
            console.log("获取用户列表失败！");
            console.error(error);
            return {};
        }
    },
    editUserState: async function(vc,data){
        try {
            return await axios.post(vc.$myInterface.editUserState,data,{
                headers: {
                    'Authorization': `Bearer ${this.getToken()}`
                }
            });
        } catch (error) {
            console.log("修改用户状态失败！");
            console.error(error);
            return {};
        }
    },
    deleteUser: async function(vc,data){
        try {
            return await axios.post(vc.$myInterface.deleteUser,data,{
                headers: {
                    'Authorization': `Bearer ${this.getToken()}`
                }
            });
        } catch (error) {
            console.log("删除用户失败！");
            console.error(error);
            return {};
        }
    },
    getGroupList: async function(vc){
        try {
            return await axios.get(vc.$myInterface.getGroupList,{
                headers: {
                    'Authorization': `Bearer ${this.getToken()}`
                }
            });
        } catch (error) {
            console.log("修改用户状态失败！");
            console.error(error);
            return {};
        }
    },
    editGroupState: async function(vc,data){
        return await axios.post(vc.$myInterface.editGroupState,data,{
            headers: {
                'Authorization': `Bearer ${this.getToken()}`
            }
        })
    },
    deleteGroup: async function(vc,data){
        return await axios.post(vc.$myInterface.adminDeleteGroup,data,{
            headers: {
                'Authorization': `Bearer ${this.getToken()}`
            }
        })
    },
    // getAdminMenu: async function(vc){
    //     try {
    //         let res = await axios.get(vc.$myInterface.getAdminMenu,{
    //             headers: {
    //                 'Authorization': `Bearer ${this.getToken()}`
    //             }
    //         });
    //         return res;
    //     } catch (e) {
    //         console.error("获取菜单失败!",e);
    //         return {}
    //     }
    // },
    getAdminList: async function(vc){
        try{
            let res = await axios.get(vc.$myInterface.getAdminList,{
                headers: {
                    'Authorization': `Bearer ${this.getToken()}`
                }
            });
            return res;
        } catch(e){
            console.error("e=",e);
        }
    },
    updAdmin: async function(vc,data){
        try{
            let res = await axios.post(vc.$myInterface.updAdmin,data,{
                headers: {
                    'Authorization': `Bearer ${this.getToken()}`
                }
            });
            return res;
        } catch(e){
            console.error("e=",e);
            return {};
        }
    },
    deleteAdmin: async function(vc,data){
        try{
            let res = await axios.post(vc.$myInterface.deleteAdmin,data,{
                headers: {
                    'Authorization': `Bearer ${this.getToken()}`
                }
            });
            return res;
        } catch(e){
            console.error("e=",e);
            return {};
        }
    }

    
}
adminUtils.install = function(Vue){
    Vue.prototype.$adminUtils = adminUtils;
}
export default adminUtils;