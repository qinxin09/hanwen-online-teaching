<!--
二次开发
组员：shibowen
功能：展示爬取的视频列表
-->
<template>
  <div>
      <NavBar activeIndex2="8" :myStyle="'width:75%;margin:auto;'"/>
      <div class="myBody" style="width:80%;margin:auto;margin-top:20px;">
        <!-- <VideoList></VideoList> -->
        <div style="margin: auto; width: 90%;">
          <div class="card" style=" width: 100%; height: 100%; display: inline-block; border-radius: 5px; margin-bottom: 10px;" >
            <div style="padding:20px;">
              <div class="block">
                <el-button @click="crawlerVideos">获取视频</el-button>
                
                
                <el-row  v-for="(r) in rows" :key="'row'+r" >
                  <el-col  :span="span"  v-for="(c) in columns" :key="'col'+c">
                    <div v-if="videos[(r-1)*columns+(c-1)]!=null" style="margin:10px;" >
                      <!-- //封面 -->
                      <div class="grid-content bg-purple">
                        <div style="width:100%;position:relative;">
                          <!-- <a style="display:flex;justify-content: center;align-items: center;height:150px;" -->
                          <a style="display:flex;justify-content: center;align-items: center;min-height:150px;"
                            :href="videos[(r-1)*columns+(c-1)].videoUrl" target="_blank">
                          <!-- <video class="video-small" id="videosmall" autoplay="" loop="" src="https://highlight-video.cdn.bcebos.com/video/6s/cffd7e44-dded-11ed-8b0a-7cd30a6992c0.mp4" style="width: 100%; height: 100%;"></video> -->
                            <img :src="origin+videos[(r-1)*columns+(c-1)].coverUrl" style="object-fit: cover;width:100%;min-height:150px;" alt="">
                              <div class="img_bottom_line" style="position: absolute;bottom: 20px; width: 100%; display: inline-flex; z-index: 9; justify-content: flex-end;">
                                  <span style="position:absolute;float:right;color:white;background-color:#00000096;padding:0 2px;">{{ videos[(r-1)*columns+(c-1)].length }}&nbsp;</span>
                              </div>
                            <div class="img_bottom_line" style="position:absolute;bottom:0;width: 100%;">
                              <div style="position:relative;">
                                
                                <!-- <div style="position:absolute;left:0;width:100%;
                                box-shadow: rgba(0, 0, 0, 0.5) 0px 15px 25px 25px;">
                                </div>
                                <span style="float:right;color:white;">{{ videos[(r-1)*columns+(c-1)].length }}&nbsp;</span> -->

                               
                                <div style="position:relative;left:0;width:100%;
                                box-shadow: rgba(0, 0, 0, 0.5) 0px 15px 25px 25px;">
                                </div>
                                
                              </div>
                            </div>
                          </a>
                        </div>
                    </div>
                        <ul style="margin: 0; padding: 0; list-style: none;">
                          <li style="white-space: nowrap;overflow: hidden; text-overflow: ellipsis;">{{ videos[(r-1)*columns+(c-1)].title }}</li>
                          <li>{{ videos[(r-1)*columns+(c-1)].authorName }}</li>
                          <li>{{ videos[(r-1)*columns+(c-1)].playCount }}</li>
                        </ul>
                        
                    </div>
                  </el-col>
                </el-row>
              </div>
             
            </div>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import axios from 'axios'
import NavBar from '../NavBar.vue'
import myInterface from '@/interface'
export default {
    name:"AnotherVideo",
    components:{
      NavBar
    },
    data(){
      return{
        videos:[],
        origin:myInterface.origin
      }
    },
    computed:{
        columns(){
            return 4;
        },
        rows(){
            return Math.ceil(this.videos.length/this.columns);
        },
        span(){
            return Math.floor(24/(this.columns));// span:一行长是24,每个占的长度是 24/列数
        }
    },
    mounted(){
      this.getVideos();
    },
    methods:{
      async getVideos(){
        await axios.get(myInterface.getAnotherVideos).then(
          response => {
            console.log(response);
            if(response.data.code==1){
              this.videos=response.data.data;
            }
          },
          error=>{
            console.error(error);
          }
        );
      },
      crawlerVideos(){
        axios.get(myInterface.crawlerVideos).then(
          response => {
            console.log(response);
            if(response.data.code==1){
              this.getVideos();
            }
          },
          error=>{
            console.error(error);
          }
        )
      }
    }
}
</script>

<style scoped>
.grid-content{
  padding: 0px;
}
</style>