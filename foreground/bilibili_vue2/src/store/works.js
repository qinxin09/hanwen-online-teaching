import UserUtils from "@/UserUtils";
import adminUtils from "@/adminUtils";
import myInterface from "@/interface";
import axios from "axios";
import Vue from "vue";

const works={
    namespaced:true,
    actions:{
        serverInitWorks_Admin(context){
            axios.get(myInterface.adminGetWorks,{
                headers:{
                    'Authorization': `Bearer ${adminUtils.getToken()}`
                }
            }).then(
                response =>{
                    console.log(response)
                    if(response.data.code==1){
                        context.commit("SET_WORKS",response.data.data);
                        // console.log(context,value);
                    }else{
                        console.log("获取作品失败！");
                    }
                },
                error =>{
                    console.error("出错了！",error);
                }
            )
        },
        serverUpdateWork(context,value){
            axios.post(myInterface.updateWork,value.data,{
                headers:{
                    Authorization:`Bearer ${adminUtils.getToken()}`
                }
            }).then(
                response =>{
                    if(response.data.code==1){
                        console.log(response.data.msg);
                        context.commit("PUT_WORK",{...value.data});
                        value.callback({
                            type:"success",
                            message:"操作成功"
                        })
                    }else{
                        value.callback({
                            type:"error",
                            message:"操作失败"
                        })
                    }
                },
                error => {
                    console.error("出错了",error);
                    value.callback({
                        type:"error",
                        message:"出错了"
                    })
                }
            )
            console.log(value);
            context;
        },
        touristGetWorks(context){
            axios.get(myInterface.touristGetWorks).then(
                response =>{
                    console.log(response.data)
                    if(response.data.code==1){
                        context.commit("SET_WORKS",response.data.data);
                    }
                },
                error =>{
                    console.error("获取作品失败！",error);
                    if(error.response.status == 401){
                        UserUtils.showFaildDialog(Vue.prototype);
                    }
                }
            );
        },
        serverSaveVote(context,value){
            let touristId=UserUtils.ensureID();
            let vote={
                touristId,
                worksId:value.worksId,
                num:value.num
            }
            axios.post(myInterface.setVotes,vote).then(
                response=>{
                    if(response.data.code==1){
                        vote.date=response.data.data.date;
                        Vue.prototype.$message.success(response.data.msg);
                        console.log("同步记录")
                        context.commit("UPDATE_VOTE",{
                            index:value.index,
                            vote
                        });
                        
                    }else{
                        Vue.prototype.$message.warning(response.data.msg);
                        value.callback(false,response.data.data.num);
                    }
                },
                error=>{
                    Vue.prototype.$message.error("出错了！");
                    console.error(error);
                }
            )
        },
        servereDeleteWork(context,value){
            axios.post(myInterface.adminDeleteWork,value.works,{
                headers:{
                    Authorization:`Bearer ${adminUtils.getToken()}`
                }
            }).then(
                response=>{
                    if(response.data.code==1){
                        context.commit("DELETE_WORKS",{
                            id:value.works.id,
                            index:value.index
                        });
                        value.callback({
                            type:"success",
                            message:"删除成功！"
                        })
                    }else{
                        value.callback({
                            type:"error",
                            message:"删除失败！"
                        })
                    }
                },
                error =>{
                    console.error("操作失败！",error);
                    value.callback({
                        type:"error",
                        message:"删除失败！"
                    })
                }
            )
        }
    },
    mutations:{
        SET_WORKS(state , value){
            //遍历查找今天所投的票，及票数
            let date=new Date().toJSON().substring(0,10);
            let touristId = UserUtils.ensureID();
            for(let i=0;i<value.length;i++){
                let v=value[i].voteList;
                for(let j=0;j<v.length;j++){
                    //找到该游客
                    if(v[j].touristId==touristId){
                        //找到今天的投票信息
                        if(v[j].date==date){
                            value[i].tempVoteNum=v[j].num;
                            break;
                        }
                    }
                }
            }
            state.works = value;
        },
        PUT_WORK(state,value){
            for(let i in state.works){
                if(state.works[i].id == value.id){
                    state.works.splice(i,1,value);
                }
            }
        },
        UPDATE_VOTE(state,value){
            let vote = value.vote;
            //更新指定值
            console.log("state,value",state,value)
            let t = state.works[value.index].voteList || [];
            for(let i=0;i<t.length;i++){
                //index决定了哪个作品,找到该游客，今天的那次投票，修改投票值
                console.log("t[i].touristId,vote.touristId , t[i].date,vote.date");
                console.log(t[i].touristId,vote.touristId , t[i].date,vote.date)
                if(t[i].touristId==vote.touristId && t[i].date==vote.date){
                    console.log("UPDATE_VOTE success",t[i].num , vote.num);
                    t[i].num = vote.num;
                    return;
                }
            }
            //没有找到说明这是新增的
            t.push(vote);
        },
        DELETE_WORKS(state,value){
            state.works.splice(value.index,1);
        }

    },
    getters:{
        worksWait(state){
            return state.works.filter((works)=>{
                return works.state==0;
            })
        },
        worksPassed(state){
            return state.works.filter((works)=>{
                return works.state==1;
            })
        },
        worksIllegal(state){
            return state.works.filter((works)=>{
                return works.state==2;
            })
        }

        
    },
    state:{
        works:[
            {
                name:"test"
            }
        ]
    }
}
export default works;