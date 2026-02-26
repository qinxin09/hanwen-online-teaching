// import UserUtils from '@/UserUtils';
import myInterface from '@/interface';
import axios from 'axios';
const indexPage = {
    namespaced:true,
    actions:{
        initIndexData(context){
            // let token = UserUtils.getToken() || "null";
            axios.get(myInterface.selectVideoGroupInfoByCategory).then(
                response => {
                    if(response.data.code==1){
                        context.commit("SET_TOP_VIDEOS",response.data.data.top10);
                        
                    }
                }
            )
        }
    },
    mutations:{
        SET_TOP_VIDEOS(state,value){
            state.topVideos=value;
        }
    },
    state:{
        topVideos:[]
    }
}
export default indexPage;