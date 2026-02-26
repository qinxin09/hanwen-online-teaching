/**
 *  杨万胜
 *  获取用户相关信息、完成用户的请求
 */
import axios from 'axios';
import uuidv4 from './uuid';

axios.interceptors.request.use(
  config => {
    
    const token = JSON.parse(localStorage.getItem('token'));
    if (token && window.location.pathname.match("/admin")==null) {
      console.log("带上token",token);
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);
 
const UserUtils = {
    setUser: function (user) {
      localStorage.setItem('user', JSON.stringify(user))
    },
    setToken: function(token){
      localStorage.setItem('token', JSON.stringify(token))
    },
    getUser: function () {
      return JSON.parse(localStorage.getItem('user'))
    },
    getToken: function() {
      return JSON.parse(localStorage.getItem('token'));
    },
    clearToken: function() {
      localStorage.removeItem('token');
    },
    ensureID: function(){
      let touristId = localStorage.getItem('tourist-id');
      console.log("touristId=",touristId);
      if(touristId==null){
        touristId = localStorage.setItem('tourist-id', uuidv4());
      }
      return touristId;
    },
    clearUser: function () {
      localStorage.removeItem('user');
      this.clearToken();
    },
    checkIsLogin:function(){
      return localStorage.getItem('user')!=null;
    },
    // setAxiosHeader:function(token){
      // 获取token
      // const token = window.localStorage.getItem('token');

      // 添加Authorization请求头
      // 同步的
      
    // },
    showFaildDialog:function(vc){
      vc.$confirm('您的登录已过期，请重新登录！', '登录提醒', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        vc.$bus.$emit(vc.$myEvents.LOGIN_EXIT,false);//NavBar.vue
        vc.$bus.$emit(vc.$myEvents.DIALOG_LOGIIN_SHOW);//LoginRegist.vue
      }).catch(() => {
        
      });
    },
    setInterceptor: function(vc) {
      // 添加响应拦截器，用于拦截401响应并跳转到登录页面
      axios.interceptors.response.use(
        response => {
          // 对响应数据做些什么
          return response;
        },
        error => {
          if (error.response && error.response.status === 401) {
            // 401 Unauthorized 401错误
            this.showFaildDialog(vc);
            return Promise.reject('认证失效，请重新登录');
          }
          return Promise.reject(error);
        }
      );
    },
    getVideoGroup:function(vc){
      this.setInterceptor(vc);
      axios.get(vc.$myInterface.getVideoGroup)
      .then((res)=>{
        console.log("获取用户合集信息",res);
        console.log("要添加的对象",vc.videoGroup);
        let group = res.data.data || [];
        group.sort((a,b)=>{
          return new Date(a.createTime).getTime() - new Date(b.createTime).getTime() ;
        });
        console.log(group);
        //清空数组
        vc.videoGroup.splice(0);
        //放入新的选项
        for(let i=0 ; i<group.length;i++){
          vc.videoGroup.push(group[i]);
        }
        if(vc.videoGroup.length>0){
          // 选择最新增加的那个合集
          vc.ruleForm.selectgroup=vc.videoGroup[vc.videoGroup.length -1];
          vc.ruleForm.selectgroupName=vc.ruleForm.selectgroup.title;
        }
        // vc.ruleForm
        // vc.ruleForm.selectgroup = vc.videoGroup[vc.videoGroup.length -1];
      })
      .catch((e)=>{
        console.log("获取合集出错了！",e);
      })
    },
    addVideoGroup:function(data,vc){
      console.log(data);
      // let token = this.getToken();
      this.setInterceptor(vc);
      axios.post(vc.$myInterface.addVideoGroup,data)
      .then((res)=>{
        console.log("后端返回数据",res);
        if(res.data.code==1){
          this.getVideoGroup(vc);
        }
      })
      .catch((e)=>{
        console.log("添加合集出错了",e);
      })
    },
    // addVideo:function(data,vc){
    //   this.setInterceptor(vc);
    //   axios.post(this.$myInterface.addVideo,data)
    //   .then((res)=>{
    //     console.log("添加视频,后端返回",res);
    //   })
    //   .catch((e)=>{
    //     console.log("出错了！",e);
    //   })
    // },
    updPassword:function(data,vc){
      axios.post(vc.$myInterface.updPassword,data)
      .then((res)=>{
        console.log("修改密码,后端返回",res);
        if(res.data.code==1){
          vc.$message.success("密码修改成功");
        }
      })
      .catch((e)=>{
        console.log("出错了！",e);
      })
    },
    updUser:function(data,vc){
      axios.post(vc.$myInterface.updUser,data)
      .then((res)=>{
        console.log("修改user,后端返回");
        console.log(res)
        if(res.data.code==1){
          vc.$message.success("修改成功");
          this.setUser(res.data.data.user);
          window.location.reload();
        }
      })
      .catch((e)=>{
        console.log("出错了！",e);
      })
    },
    requestGroup:function(vc,data,fresh){
      this.setInterceptor(vc);
      axios.post(vc.$myInterface.requestGroup,data)
      .then((res)=>{
        console.log("获取个人中心页合集 后端返回数据",res);
        console.log("按照播放量降序")
        if(res.data.code==1){
          if(data.byPerson){
            //个人中心获取，直接存入即可
          }
          
          vc.videoGroupData=res.data.data;
          if(fresh!=null){
            console.log("获取成功,通知更新",vc.$myEvents.FRESH_GROUP,fresh);
            vc.$bus.$emit(vc.$myEvents.FRESH_GROUP,fresh);
          }else{
            console.log("不需要通知更新");
          }
        }
      })
      .catch((e)=>{
        console.log("获取合集出错了",e);
        //后端服务没有启动
        
      })
    },
    deleteGroup:async function(vc,data) {
      try {
        const response = await axios.delete(vc.$myInterface.deleteGroup, data)
        console.log(response.data)
        // 在这里处理删除成功后的逻辑
      } catch (err) {
        console.error(err)
        // 在这里处理处理删除失败后的逻辑
      }
    },
    getUserIcon:function(vc,data){
      //在 getUserIcon 函数中加上 timestamp 参数可以避免浏览器缓存图片，保证页面每次都能获取最新的图片。
      return `${vc.$myInterface.userIcon}?userid=${data.userid}&type=${data.type}&timestamp=${Date.now()}` ;
    },
    getCover:function(vc,data){
      //在 getUserIcon 函数中加上 timestamp 参数可以避免浏览器缓存图片，保证页面每次都能获取最新的图片。
      return `${vc.$myInterface.coverApi}?userid=${data.userid}&type=${data.type}&groupid=${data.groupid}&timestamp=${Date.now()}` ;
    },
    concern: async function(vc,data){
      return await axios.post(vc.$myInterface.concern,data);
    },
    like: async function(vc,data){
      return await axios.post(vc.$myInterface.like,data);
    },
    collect: async function(vc,data){
      return await axios.post(vc.$myInterface.collect,data);
    },
    getStatistics: async function(vc,data){
      return await axios.post(vc.$myInterface.getStatistics,data);
    },
    getInteraction:async function(vc,data){
      return await axios.post(vc.$myInterface.interaction,data);
    },
    sendComment:async function(vc,data){
      try{
        let res= await axios.post(vc.$myInterface.sendComment,data);
        return res;
      }catch(e){
        console.log("出错了")
        return {data:null};
      }
    },
    getAuthor:async function(vc,data){
      try {
        return await axios.post(vc.$myInterface.getAuthor,data);
      } catch (error) {
        console.log("获取作者信息失败！");
        return {}
      }
    },
    addVideoInfo: function(vc,data){
      axios.post(vc.$myInterface.addVideoInfo,data)
      .then((res)=>{
        if(res.data.code==1){
          console.log("上传视频信息成功！");
          window.location.reload();
        }
      }).catch((e)=>{
        console.log("上传视频信息失败！",e);
      })
    },
    async addPlayCount(vc,data){
      try {
        let res = await axios.post(vc.$myInterface.addPlayCount,data);
        if(res.data.code==1){
          console.log("播放量++");
        }
      } catch (e) {
        console.log("出错了！",e);
      }
      
    },
    downloadFile(vc,data){
      axios.post(vc.$myInterface.downLoad,data,{
        responseType:'blob' // 设置响应类型为 blob
      })
      .then( (response)=>{
          console.log(response)
          const url = window.URL.createObjectURL(new Blob([response.data]));
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download",data.fileName);
          document.body.appendChild(link);
          link.click();//触发点击下载
          document.body.removeChild(link);//下载完成后移除<a>元素
      }).catch((error) =>{
          console.log(error)
      });
    },
    groupOption: function(){
      return [
        {
            value: 'it',
            label: 'IT互联网',
            children: [
              { value: 'python', label: 'Python' }, 
              { value: 'foreground', label: '前端开发', children: [{
                  value: 'vue',
                  label: 'Vue'
                }, 
                {
                  value: 'layui',
                  label: 'layui'
                }, 
                {
                  value: 'other',
                  label: '其它'
                }
              ]
              },
              { value: 'background', label: '后端开发', children: [{
                  value: 'springboot',
                  label: 'springboot'
                }]
              },
              { value: 'java', label: 'Java' }
            
            ]
          },{
            value:"other",
            label:"其它"
          }
        ];
    }
  }
// 定义 install 方法
UserUtils.install = function (Vue) {
  Vue.prototype.$userUtils = UserUtils ;
}
export default UserUtils;