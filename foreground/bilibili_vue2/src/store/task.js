import UserUtils from "@/UserUtils";
import adminUtils from "@/adminUtils";
import myInterface from "@/interface";
import axios from "axios";

const taskStore = {
    namespaced:true,
    actions:{
        serverCreateTask(context,value){
            axios.post(myInterface.saveTask,value.data,{
                headers:{
                    'Authorization': `Bearer ${adminUtils.getToken()}`
                }
            }).then(
                response => {
                    value.msg=response.data.msg;
                    if(response.data.code==1){
                        context.commit("SAVE_TASK",value);
                    }else{
                        return value.callback({
                            type:"error",
                            message:value.msg
                        })
                    }
                },
                error => {
                    console.error(error);
                    return value.callback({
                        type:"error",
                        message:"访问失败！"
                    })
                }
            )
        },
        serverDeleteTask(context,value){
            axios.post(myInterface.deleteTask,value.data,{
                headers:{
                    'Authorization': `Bearer ${adminUtils.getToken()}`
                }
            }).then(
                response => {
                    value.msg=response.data.msg;
                    if(response.data.code==1){
                        context.commit("DELETE_TASK",value);
                    }else{
                        return value.callback({
                            type:"error",
                            message:value.msg
                        })
                    }
                },
                error => {
                    console.error(error);
                    return value.callback({
                        type:"error",
                        message:"访问失败！"
                    })
                }
            )
        },
        serverInitTask(context,callback){
            //确保游客id已存入本地缓存
            UserUtils.ensureID();
            axios.get(myInterface.touristGetTasks).then(
                response =>{
                    if(response.data.code==1){
                        context.commit("SET_TASKS",response.data.data);
                    }else{
                        console.error("获取任务列表失败！");
                    }
                },
                error =>{
                    console.log(error)
                    if(error.code=="ERR_NETWORK"){
                        return callback("ERR_NETWORK")
                    }
                }
            )
        },
        serverInitTask_Admin(context){
            //确保游客id已存入本地缓存
            UserUtils.ensureID();
            axios.get(myInterface.adminGetTasks,{
                headers:{
                    Authorization:`Bearer ${adminUtils.getToken()}`
                }
            }).then(
                response =>{
                    if(response.data.code==1){
                        context.commit("SET_TASKS",response.data.data);
                    }else{
                        console.error("获取任务列表失败！");
                    }
                },
                
            )
        },


    },
    mutations:{
        SAVE_TASK(state,value){
            let data = value.data;
            //查重
            for(let i=0;i<state.tasks.length;i++){
                if(state.tasks[i].id==data.id){
                    state.tasks.splice(i,1,data);
                    return value.callback({
                        type:"success",
                        message:value.msg
                    });
                }
            }
            state.tasks.push(data);
            return value.callback({
                type:"success",
                message:value.msg
            });
        },
        SET_TASKS(state,value){
            value.forEach(element => {
                element.dateStart = new Date(element.dateStart);
                element.dateStop = new Date(element.dateStop);
            });
            state.tasks=value;
        },
        DELETE_TASK(state,value){
            let data = value.data;
            //寻找
            for(let i=0;i<state.tasks.length;i++){
                if(state.tasks[i].id==data.id){
                    let name=data.name+"";
                    state.tasks.splice(i,1);
                    return value.callback({
                        type:"success",
                        message:`删除任务${name}成功！`
                    });
                }
            }
            return value.callback({
                type:"success",
                message:`删除任务${value.name}失败！`
            });

        }
    },
    getters:{
        tasksWait(state){
            return state.tasks.filter((task)=>{
                if(task.state==0){
                    return true;
                }
                return false;
            });
        },
        tasksActive(state){
            return state.tasks.filter((task)=>{
                if(task.state==1){
                    if(new Date(task.dateStop).getTime() > new Date().getTime() ){
                        return true;
                    }
                }
                return false;
            });
        },
        tasksEnded(state){
            return state.tasks.filter((task)=>{
                    if(new Date(task.dateStop).getTime() < new Date().getTime() ){
                        return true;
                    }
                
                return false;
            });
        }
    },
    state:{
        tasks:[]
        
    }
}
export default taskStore;