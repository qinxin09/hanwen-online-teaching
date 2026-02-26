import UserUtils from "@/UserUtils";

const user={
    namespaced:true,
    actions:{
        init(context){
            context.commit("SET_ISLOGIN",UserUtils.checkIsLogin());
        }
    },
    mutations:{
        SET_ISLOGIN(state,value){
            console.log("state,value",state,value);
            state.isLogin=value;
        }
    },
    state:{
        isLogin:false
    }
}
export default user;