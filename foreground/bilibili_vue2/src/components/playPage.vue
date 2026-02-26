<!--
组员：shibowen
功能：视频播放页面
-->
<template>
  <div>
    <!-- <NavBar activeIndex2="-1"/> -->
    
    <div style="margin: auto; width: 80%;margin-bottom:200px;">
      <div class="card" style="margin:20px 0;height:100%;width:100%;background-color:white;display: inline-block;border-radius:5px;margin-bottom: 20px;padding-bottom:20px;">
        <div style="padding-left:30px;padding-top:20px;">
          <!-- 视频标题 -->
          <div>
            <h1 class="video-title tit">{{"【"+videoGroup.title+"】"+currentVideoTitle}}</h1>
            <div class="video-data"><div class="video-data-list"><!---->
              
            <!-- <span class="view item">
              <svg style="width:20px;height:20px;" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 20 20" width="20" height="20">
                <path d="M10 4.165041666666667C7.901483333333334 4.165041666666667 6.069166666666667 4.2718 4.775741666666667 4.376683333333333C3.876841666666667 4.449575 3.1800916666666668 5.136266666666667 3.1 6.027116666666667C3.0050583333333334 7.083166666666667 2.916666666666667 8.475833333333334 2.916666666666667 9.998333333333333C2.916666666666667 11.520916666666668 3.0050583333333334 12.913583333333333 3.1 13.969624999999999C3.1800916666666668 14.860458333333336 3.876841666666667 15.547166666666667 4.775741666666667 15.620083333333334C6.069166666666667 15.724916666666669 7.901483333333334 15.831666666666669 10 15.831666666666669C12.09875 15.831666666666669 13.931166666666668 15.724916666666669 15.224666666666668 15.620041666666669C16.123416666666667 15.547125 16.82 14.860666666666667 16.900083333333335 13.970041666666669C16.995 12.914416666666666 17.083333333333336 11.522041666666667 17.083333333333336 9.998333333333333C17.083333333333336 8.474708333333334 16.995 7.082308333333334 16.900083333333335 6.026708333333334C16.82 5.136083333333334 16.123416666666667 4.4496 15.224666666666668 4.376708333333333C13.931166666666668 4.271816666666667 12.09875 4.165041666666667 10 4.165041666666667zM4.674708333333334 3.1307750000000003C5.9936 3.023825 7.860483333333334 2.9150416666666668 10 2.9150416666666668C12.139750000000001 2.9150416666666668 14.00675 3.0238416666666668 15.325666666666667 3.1308C16.823416666666667 3.252266666666667 18.009833333333333 4.4106499999999995 18.145083333333336 5.914775000000001C18.242416666666667 6.997416666666667 18.333333333333336 8.428375 18.333333333333336 9.998333333333333C18.333333333333336 11.568375 18.242416666666667 12.999333333333334 18.145083333333336 14.081999999999999C18.009833333333333 15.586083333333333 16.823416666666667 16.744458333333334 15.325666666666667 16.86595833333333C14.00675 16.972916666666666 12.139750000000001 17.081666666666667 10 17.081666666666667C7.860483333333334 17.081666666666667 5.9936 16.972916666666666 4.674708333333334 16.866C3.1767166666666666 16.7445 1.9902583333333335 15.585791666666669 1.8550250000000001 14.081583333333334C1.75765 12.9985 1.6666666666666667 11.567333333333334 1.6666666666666667 9.998333333333333C1.6666666666666667 8.429416666666667 1.75765 6.998225 1.8550250000000001 5.915191666666667C1.9902583333333335 4.410933333333333 3.1767166666666666 3.252241666666667 4.674708333333334 3.1307750000000003z" fill="currentColor"></path><path d="M12.261500000000002 9.140625C12.923041666666666 9.522583333333333 12.923041666666666 10.477500000000001 12.261458333333334 10.859416666666666L9.6135 12.388250000000001C8.951958333333334 12.770166666666666 8.125 12.292750000000002 8.125008333333334 11.528833333333333L8.125008333333334 8.47125C8.125008333333334 7.707341666666667 8.951958333333334 7.229891666666667 9.6135 7.61185L12.261500000000002 9.140625z" fill="currentColor">
                  
                </path>
              </svg>
              1211.7万
            </span>
            <span title="历史累计弹幕数52807" class="dm item"><svg style="width:20px;height:20px;" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 20 20" width="20" height="20"><path d="M10 4.165041666666667C7.901483333333334 4.165041666666667 6.069166666666667 4.2718 4.775741666666667 4.376683333333333C3.876841666666667 4.449575 3.1800916666666668 5.136266666666667 3.1 6.027116666666667C3.0050583333333334 7.083166666666667 2.916666666666667 8.475833333333334 2.916666666666667 9.998333333333333C2.916666666666667 11.520916666666668 3.0050583333333334 12.913583333333333 3.1 13.969624999999999C3.1800916666666668 14.860458333333336 3.876841666666667 15.547166666666667 4.775741666666667 15.620083333333334C6.069166666666667 15.724916666666669 7.901483333333334 15.831666666666669 10 15.831666666666669C12.09875 15.831666666666669 13.931166666666668 15.724916666666669 15.224666666666668 15.620041666666669C16.123416666666667 15.547125 16.82 14.860666666666667 16.900083333333335 13.970041666666669C16.995 12.914416666666666 17.083333333333336 11.522041666666667 17.083333333333336 9.998333333333333C17.083333333333336 8.474708333333334 16.995 7.082308333333334 16.900083333333335 6.026708333333334C16.82 5.136083333333334 16.123416666666667 4.4496 15.224666666666668 4.376708333333333C13.931166666666668 4.271816666666667 12.09875 4.165041666666667 10 4.165041666666667zM4.674708333333334 3.1307750000000003C5.9936 3.023825 7.860483333333334 2.9150416666666668 10 2.9150416666666668C12.139750000000001 2.9150416666666668 14.00675 3.0238416666666668 15.325666666666667 3.1308C16.823416666666667 3.252266666666667 18.009833333333333 4.4106499999999995 18.145083333333336 5.914775000000001C18.242416666666667 6.997416666666667 18.333333333333336 8.428375 18.333333333333336 9.998333333333333C18.333333333333336 11.568375 18.242416666666667 12.999333333333334 18.145083333333336 14.081999999999999C18.009833333333333 15.586083333333333 16.823416666666667 16.744458333333334 15.325666666666667 16.86595833333333C14.00675 16.972916666666666 12.139750000000001 17.081666666666667 10 17.081666666666667C7.860483333333334 17.081666666666667 5.9936 16.972916666666666 4.674708333333334 16.866C3.1767166666666666 16.7445 1.9902583333333335 15.585791666666669 1.8550250000000001 14.081583333333334C1.75765 12.9985 1.6666666666666667 11.567333333333334 1.6666666666666667 9.998333333333333C1.6666666666666667 8.429416666666667 1.75765 6.998225 1.8550250000000001 5.915191666666667C1.9902583333333335 4.410933333333333 3.1767166666666666 3.252241666666667 4.674708333333334 3.1307750000000003z" fill="currentColor"></path><path d="M13.229166666666668 8.958333333333334L8.229166666666668 8.958333333333334C7.883991666666667 8.958333333333334 7.604166666666667 8.6785 7.604166666666667 8.333333333333334C7.604166666666667 7.988158333333333 7.883991666666667 7.708333333333334 8.229166666666668 7.708333333333334L13.229166666666668 7.708333333333334C13.574333333333335 7.708333333333334 13.854166666666668 7.988158333333333 13.854166666666668 8.333333333333334C13.854166666666668 8.6785 13.574333333333335 8.958333333333334 13.229166666666668 8.958333333333334z" fill="currentColor"></path><path d="M14.479166666666668 12.291666666666668L9.479166666666668 12.291666666666668C9.134 12.291666666666668 8.854166666666668 12.011833333333334 8.854166666666668 11.666666666666668C8.854166666666668 11.3215 9.134 11.041666666666668 9.479166666666668 11.041666666666668L14.479166666666668 11.041666666666668C14.824333333333335 11.041666666666668 15.104166666666668 11.3215 15.104166666666668 11.666666666666668C15.104166666666668 12.011833333333334 14.824333333333335 12.291666666666668 14.479166666666668 12.291666666666668z" fill="currentColor"></path><path d="M6.5625 8.333333333333334C6.5625 8.6785 6.282675 8.958333333333334 5.9375 8.958333333333334L5.520833333333334 8.958333333333334C5.175658333333334 8.958333333333334 4.895833333333334 8.6785 4.895833333333334 8.333333333333334C4.895833333333334 7.988158333333333 5.175658333333334 7.708333333333334 5.520833333333334 7.708333333333334L5.9375 7.708333333333334C6.282675 7.708333333333334 6.5625 7.988158333333333 6.5625 8.333333333333334z" fill="currentColor"></path><path d="M7.8125 11.666666666666668C7.8125 12.011833333333334 7.532675000000001 12.291666666666668 7.1875 12.291666666666668L6.770833333333334 12.291666666666668C6.425658333333334 12.291666666666668 6.145833333333334 12.011833333333334 6.145833333333334 11.666666666666668C6.145833333333334 11.3215 6.425658333333334 11.041666666666668 6.770833333333334 11.041666666666668L7.1875 11.041666666666668C7.532675000000001 11.041666666666668 7.8125 11.3215 7.8125 11.666666666666668z" fill="currentColor"></path></svg>
            5.3万
            </span> -->

        <span class="pudate-ip item"><span title="2018-10-19 18:04:08" class="pudate"><span class="pudate-text">
            {{ getDate(null) }}
          </span></span><!----></span><span title="未经作者授权，禁止转载" class="copyright item"><i class="van-icon-info_prohibit"></i><span class="item-text">未经作者授权，禁止转载</span></span><!----></div><!----></div>
          </div> 
          
          <!-- 播放器 -->
          <div class="bpx-player-video-wrap">

            <!-- <video data-v-560f3af5="" class="wbpv-tech" playsinline="playsinline" webkit-playsinline="true" x5-playsinline="true" x5-video-player-type="h5" x5-video-player-fullscreen="false" id="wbpv_video_467_html5_api" tabindex="-1" role="application" preload="meta" 
            src="//f.video.weibocdn.com/003cJUHLlx07w8z6vhew01041200wsP60E010.mp4?label=mp4_hd&amp;template=852x480.24.0&amp;trans_finger=ac6fb6d5c49a67fe2901ae638b222ab2&amp;media_id=4404141351474101&amp;tp=8x8A3El:YTkl0eM8&amp;us=0&amp;ori=1&amp;bf=4&amp;ot=h&amp;ps=3lckmu&amp;uid=3ZoTIp&amp;ab=&amp;Expires=1685365028&amp;ssig=lk15a%2BneR%2F&amp;KID=unistore,video"></video> -->
              <div>
                <VideoPlayer videoSrc="../assets/40834654901.mp4" ></VideoPlayer>
                
                <el-row>
                  <el-col :span="12"><div >

                    <div 
                      v-if="!videoGroup.checkMode"
                      style="
                      padding: 0;
                      border-radius: 10px;
                      margin-top:20px;
                      display: inline-flex;
                      background-color: rgba(221, 221, 221, 0.766);">
                        <div class="authorIcon" @click="concern()">
                          <img :src="getUserIcon(videoGroup.authorId)" style="object-fit:cover;width:40px;height:40px;border-radius:50%;border:1px solid gray;" />
                          <span style="padding:10px;color:gray;">{{interaction.isConcerned?'已':''}}关注</span>
                        </div>

                      
                        <ul style="margin:0;padding:0;">
                          <li style="list-style: none;margin:5px;float:left;cursor:pointer;" @click="like()">
                            <div style="display:flex;justify-content: center;align-items: center;;">
                              <svg width="36" height="36" viewBox="0 0 36 36" xmlns="http://www.w3.org/2000/svg" class="video-like-icon video-toolbar-item-icon" :class="likeData.isLiked?'interaction_icon_select':''"><path fill-rule="evenodd" clip-rule="evenodd" d="M9.77234 30.8573V11.7471H7.54573C5.50932 11.7471 3.85742 13.3931 3.85742 15.425V27.1794C3.85742 29.2112 5.50932 30.8573 7.54573 30.8573H9.77234ZM11.9902 30.8573V11.7054C14.9897 10.627 16.6942 7.8853 17.1055 3.33591C17.2666 1.55463 18.9633 0.814421 20.5803 1.59505C22.1847 2.36964 23.243 4.32583 23.243 6.93947C23.243 8.50265 23.0478 10.1054 22.6582 11.7471H29.7324C31.7739 11.7471 33.4289 13.402 33.4289 15.4435C33.4289 15.7416 33.3928 16.0386 33.3215 16.328L30.9883 25.7957C30.2558 28.7683 27.5894 30.8573 24.528 30.8573H11.9911H11.9902Z" fill="currentColor"></path></svg>
                              <span style="padding:10px;color:gray;">{{ likeData.count!=null?likeData.count:0 }}</span>
                            </div>
                          </li>

                          <li style="list-style: none;margin:5px;float:left;cursor:pointer;" @click="collect()">
                            <div style="display:flex;justify-content: center;align-items: center;;">
                              <svg width="28" height="28" viewBox="0 0 28 28" xmlns="http://www.w3.org/2000/svg" class="video-fav-icon video-toolbar-item-icon" :class="collectData.isCollected?'interaction_icon_select':''" data-v-912a7b4a=""><path fill-rule="evenodd" clip-rule="evenodd" d="M19.8071 9.26152C18.7438 9.09915 17.7624 8.36846 17.3534 7.39421L15.4723 3.4972C14.8998 2.1982 13.1004 2.1982 12.4461 3.4972L10.6468 7.39421C10.1561 8.36846 9.25639 9.09915 8.19315 9.26152L3.94016 9.91102C2.63155 10.0734 2.05904 11.6972 3.04049 12.6714L6.23023 15.9189C6.96632 16.6496 7.29348 17.705 7.1299 18.7605L6.39381 23.307C6.14844 24.6872 7.62063 25.6614 8.84745 25.0119L12.4461 23.0634C13.4276 22.4951 14.6544 22.4951 15.6359 23.0634L19.2345 25.0119C20.4614 25.6614 21.8518 24.6872 21.6882 23.307L20.8703 18.7605C20.7051 17.705 21.0339 16.6496 21.77 15.9189L24.9597 12.6714C25.9412 11.6972 25.3687 10.0734 24.06 9.91102L19.8071 9.26152Z" fill="currentColor"></path></svg>
                              <span style="padding:10px;color:gray;">{{ collectData.count!=null?collectData.count:0 }}</span>
                            </div>
                          </li>

                        </ul>
                    </div>

                  </div></el-col>
                  <el-col :span="12"><div >
                    <div 
                      style="
                      padding: 0;
                      border-radius: 10px;
                      margin-top:20px;
                      display: inline-flex;
                      background-color: rgba(221, 221, 221, 0.766);">
                          
                     <ShowDrawer :dataResource="getDrawerResource"></ShowDrawer>
                    </div>
                  </div></el-col>
                </el-row>

                <div>

                
                

              </div>

                <!-- 视频简介 -->
                <div style="margin:10px 0;border-top:1px solid #d4d4d4;;border-bottom :1px solid #d4d4d4;">
                  <pre style="font-size:18px;" v-if="videos.length>0">{{videos[playIndex].desc}}</pre>
                </div>

              </div>
            
          </div>

          <!-- 右侧播放列表 -->
          <div 
            style="
            float: left;
            width: 35%;
            background-color: #e7e7e7;
            margin-left: 20px;
            margin-bottom:20px;
            padding: 5px;
            border-radius: 10px;
            ">
            <!-- <ul>
              <li v-for="o in 5" :key="o">list 1</li> 
            </ul> -->
            <!-- <el-row>
              <el-col :span="24"><div class="grid-content bg-purple-dark" >播放列表</div></el-col>
            </el-row> -->
            <div style="
                  margin: 5px;
                  padding: 5px;
                  color: #4eb0ff;
                  font-weight: bold;
            ">
              <span >播放列表</span>
            </div>
            <hr>
            <div style="height:100%;min-height: 20px;min-height: 340px;max-height: 340px;overflow:scroll;overflow-x: hidden;">
               <el-row v-for="(item,index) in videos" :key="index">
              <el-col :span="24"><div :class="getClass(index)" @click="switchPlayList(index)" >{{ (index+1)+" "+item.title }}</div></el-col>
            </el-row>
            </div>
           
          </div>

           <!-- 用户信息 -->
           <div 
              v-if="!videoGroup.checkMode"
              style="
              float: left;
              width: 35%;
              background-color: #e7e7e7;
              margin: 20px;
              margin-top:0;
              padding: 5px;
              border-radius: 10px;
            ">
              <div class="authorIcon" style="background:unset;border:none;">
                  <img :src="getUserIcon(videoGroup.authorId)" style="object-fit:cover;width:40px;height:40px;border-radius:50%;border:1px solid gray;" />
                  <span style="padding:10px;color:gray;">{{videoGroup.name}}</span>
              </div>
              <div>
                <!-- <el-row>
                  <el-button type="primary" plain>主要按钮</el-button>
                  <el-button type="primary" plain>主要按钮</el-button>
                </el-row> -->

                <el-row type="flex" class="row-bg" >
                  <el-col :span="6"><el-button type="primary" @click.native="concern()">{{interaction.isConcerned?'已':''}}关注</el-button></el-col>
                  <el-col :span="6"><el-button type="primary" @click="goAuthorIndex()">查看主页</el-button></el-col>
                  <!-- <el-col :span="6"><div class="grid-content bg-purple"></div></el-col> -->
                </el-row>

              </div>
            </div>
        </div>
        
         
      </div>
      <CommentComponent v-if="!videoGroup.checkMode" :props_="commentProps"></CommentComponent>
      
    </div>
  </div>
</template>

<script>
// import NavBar from './NavBar.vue';
//sdfsdfsdsfdsfsd 这里改变里
import VideoPlayer from './player.vue' ;
import CommentComponent from './CommentComponent.vue';
import ShowDrawer from './playPage/drawer.vue'
export default {
        
    name:"PlayPage",  
    data (){
      return {
        currentVideoTitle:"",
        interaction:{},
        videoGroup:{},
        likeData:{},
        collectData:{},
        videos:[],
        videoId:null,      
        url:"https://f.video.weibocdn.com/003cJUHLlx07w8z6vhew01041200wsP60E010.mp4?label=mp4_hd&amp;template=852x480.24.0&amp;trans_finger=ac6fb6d5c49a67fe2901ae638b222ab2&amp;media_id=4404141351474101&amp;tp=8x8A3El:YTkl0eM8&amp;us=0&amp;ori=1&amp;bf=4&amp;ot=h&amp;ps=3lckmu&amp;uid=3ZoTIp&amp;ab=&amp;Expires=1685365028&amp;ssig=lk15a%2BneR%2F&amp;KID=unistore,video",
        playIndex:0
      }
    },
    watch:{
      interaction:{
        deep:true,
        immediate:true,
        handler(){
          console.log("interaction 变化了！")
        }
      }
    },
    computed:{
      getDrawerResource(){
        if(this.videoGroup==null){
          return {};
        }
        return {
          attachementNames:this.videoGroup.attachementNames+"",
          authorId:this.videoGroup.authorId+"",
          id:this.videoGroup.id+""
        }
      },
      commentProps(){
        let r= {};
        if(this.videoGroup!=null && this.videoGroup.id!=null){
          r = {groupid:this.videoGroup.id};
        }
        if(this.interaction!=null){
          r.commentList=this.interaction.myCommentList;
        }
        return r;
      }
    },
    created() {
      const { videoId } = this.$route.query;//获取传递过来的参数
      console.log(videoId)
      this.videoId = videoId;
      this.url="//f.video.weibocdn.com/003cJUHLlx07w8z6vhew01041200wsP60E010.mp4?label=mp4_hd&amp;template=852x480.24.0&amp;trans_finger=ac6fb6d5c49a67fe2901ae638b222ab2&amp;media_id=4404141351474101&amp;tp=8x8A3El:YTkl0eM8&amp;us=0&amp;ori=1&amp;bf=4&amp;ot=h&amp;ps=3lckmu&amp;uid=3ZoTIp&amp;ab=&amp;Expires=1685365028&amp;ssig=lk15a%2BneR%2F&amp;KID=unistore,video";
      this.videoGroup=this.$StorageUtils.getVideoGroup();
      console.log("this.videoGroup.videos==null",this.videoGroup.videos==null);
      console.log(JSON.parse(this.videoGroup.videos));
      let vs = this.videoGroup.videos!=null?(JSON.parse(this.videoGroup.videos)):[];
      this.videos = vs.sort((a,b)=>{return a.index - b.index});
      this.$bus.$on(this.$myEvents.COMMENT_SUCCESS,this.freshComments);
      
    },
    beforeDestroy(){
      this.$bus.$off(this.$myEvents.FRESH_COMMENT);
    },
    components:{
      // NavBar,
      VideoPlayer,CommentComponent,ShowDrawer
    },
    methods:{
      getClass(index){
        if(this.playIndex==index){
            return 'grid-content bg-purple-dark current';
        }
        return 'grid-content bg-purple-dark';
      },
      goAuthorIndex(){
        if(!this.$userUtils.checkIsLogin()){
          return this.$message.warning("请先登录！");
        }
        //暂停播放，或者新窗口打开，否则报错
        let fun = ()=>{
          // this.$router.push({ path: '/personalCenter', query: { userId: this.videoGroup.authorId,isSelf:"false" } });
          window.location.href=window.location.origin+"/personalCenter?userId="+this.videoGroup.authorId+"&isSelf=false";

        };
        this.$bus.$emit(this.$myEvents.PLAYER_PAUSE,fun);
        
        
      },
      switchPlayList(index){
        console.log("switchPlayList",this.videos)
        if(this.videos.length>0){
          this.playIndex = index;
          this.currentVideoTitle=this.videos[index].title;
          this.$bus.$emit(this.$myEvents.PLAYER_DATA,{groupid:this.videoGroup.id,authorid:this.videoGroup.authorId,filename:this.videos[index].newname});
        }
      },
      async concern(){
        let data = {
          authorId:this.videoGroup.authorId
        }
        let res = await this.$userUtils.concern(this,data);
        if(res.data.code==1){
          this.interaction.isConcerned=!this.interaction.isConcerned;
        }else{
          console.log(res.data.msg)
          this.$message.warning(res.data.msg);
        }
      },
      async like(onlySelect){
        let data = {
          //合集id
          groupId:this.videoGroup.id,
          onlySelect:onlySelect
        }
        let res = await this.$userUtils.like(this,data);
        if(res.data.code==1){
          this.likeData=res.data.data;
        }else{
          if(!onlySelect){
            console.log(res.data.msg)
            this.$message.warning(res.data.msg);
          }
        }
      },
      async collect(onlySelect){
        let data = {
          groupId:this.videoGroup.id,
          onlySelect:onlySelect
        }
        let res = await this.$userUtils.collect(this,data);
        if(res.data.code==1){
          this.collectData=res.data.data;
        }else{
          if(!onlySelect){
            console.log(res.data.msg)
            this.$message.warning(res.data.msg);
          }
        }
      },
      async getInteraction(){
        let data ={
          authorid:this.videoGroup.authorId,
          groupid:this.videoGroup.id
        };
        let res = {};
        try{
          res = await this.$userUtils.getInteraction(this,data);
          if(res.data.code==1){
            console.log("获取interaction成功",res.data);
            //按时间排序
            res.data.data.myCommentList.sort((a,b)=>{
              let t1 = new Date(a.createTime);
              let t2 = new Date(b.createTime);
              return t1-t2;
            });
            this.interaction = res.data.data || {};
          }
        }catch(e){
          console.log("获取评论区失败");
        }
        
      },
      freshComments(){
        console.log("刷新评论区");
        this.getInteraction();
      },
      getUserIcon(userid){
        //获取自己的id
        if(userid==null){
          let user = this.$userUtils.getUser()
          userid=user.id;
        }
        let data={
          userid:userid,
          type:"icon"
        }
        return this.$userUtils.getUserIcon(this,data);
      },
      getDate(date) {
        if(date==null){
          date=this.videoGroup.createTime;
        }
        const d = new Date(date);
        const year = d.getFullYear();
        const month = ("0" + (d.getMonth() + 1)).slice(-2);  // 月份加一，并且将小于10的数转换为双位数
        const day = ("0" + d.getDate()).slice(-2);  // 将日期转换为双位数
        const hour = ("0" + d.getHours()).slice(-2);  // 将小时转换为双位数
        const minute = ("0" + d.getMinutes()).slice(-2);  // 将分钟转换为双位数
        const second = ("0" + d.getSeconds()).slice(-2);  // 将秒数转换为双位数
        return `${year}-${month}-${day} ${hour}:${minute}:${second}`;  // 返回年月日、时分秒的拼接结果
      },
      
      
    },
    mounted(){
      this.switchPlayList(this.playIndex);
      //初始获取评论区
      this.getInteraction();
      this.like(true);
      this.collect(true);
    }

}
</script>

<style>
.video-title {
    font-size: 20px;
    font-family: PingFang SC, HarmonyOS_Regular, Helvetica Neue, Microsoft YaHei, sans-serif;
    font-weight: 500;
    -webkit-font-smoothing: antialiased;
    color: #18191C;
    color: var(--text1);
    line-height: 24px;
    margin-bottom: 6px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
/* 标题下方 */
.video-data {
  font-size: 13px;
    color: #9499A0;
    color: #9499a0;
    display: flex;
    align-items: center;
    height: 24px;
    line-height: 18px;
    position: relative;
    overflow: hidden;
    margin: 0;
    padding: 0;
    font-family: PingFang SC, HarmonyOS_Regular, Helvetica Neue, Microsoft YaHei, sans-serif;
    font-weight: 400;
    -webkit-font-smoothing: antialiased;
}
.video-data-list {
    display: flex;
    align-items: center;
    overflow: hidden;
    box-sizing: border-box;
    line-height: 18px;
}
.view{
    display: inline-flex;
    align-items: center;
    overflow: hidden;
    flex-shrink: 0;
    margin-right: 12px;
    font-size: 13px;
    color: #9499A0;
    color: #9499a0;
    line-height: 18px;
}
.view{
    display: inline-flex;
    align-items: center;
    overflow: hidden;
}
.item {
   display: inline-flex;
    flex-shrink: 0;
    margin-right: 12px;
    overflow: hidden;
}
.video-data {
    font-size: 13px;
    color: #9499A0;
    color: #9499a0;
    display: flex;
    align-items: center;
    height: 24px;
    line-height: 18px;
    position: relative;
    overflow: hidden;
}
/* 视频播放器 */
.bpx-player-video-wrap {
    /* -webkit-box-sizing: border-box;
    box-sizing: border-box;
    display: block;
    height: 400px;
    width: 700px; */
    display:flex;
    float:left;
    /* background-color: black; */
}

/* 播放列表
 */
 .el-row {
    margin-bottom: 5px;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
    padding-left: 10px;
    justify-content: left;
    align-items: center;
    display: flex;
    cursor: pointer;
    white-space: nowrap; /* 保持文本在一行 */
    overflow: hidden; /* 隐藏超出容器范围的文本 */
    text-overflow: ellipsis; /* 文本超出时显示省略号 */
}
.bg-purple-dark {
    background: unset;
}

.bg-purple-dark.current {
    background: white;
    color:#00aeec;
}
.bg-purple-dark:hover {
    background: white;
}

/* 点赞 */
.video-like-icon,.video-fav-icon {
  color:#9f9f9fcc;
}
.interaction_icon_select {
  color:#00aeec;
}

/* 关注 */
.authorIcon {
  display:inline-flex;
  justify-content: center;
  align-items: center;
  padding-left:10px;
  margin-right:30px;
  border-radius: 10px;
  cursor:pointer;
  border: 1px solid #00000000;
}
.authorIcon:hover {
  background-color:rgb(255, 248, 248);
  border: 1px solid #3333;
}
.row-bg {
    padding: 10px 0;
    background-color: unset;
    margin-left:15px;
  }

</style>