<!--
组员：shibowen
功能：视频播放器组件
-->
<template>
  <div>
  <div style="width:700px;height:400px;border:1px solid gray;">
  <div class="video-player" :class="{ 'is-fullscreen': isFullscreen }" ref="videoPlayerContainer">
    <video @click="togglePlay" ref="videoPlayer" name="media" :src="videoSrc" @play="play" @pause="pause" style="width:100%;height:100%;" autoplay></video>
    <div class="controls" v-if="showControls">
      <!-- <button class="play-pause-button" @click="togglePlay"> -->
        <!-- <i :class="isPlaying ? 'fas fa-pause' : 'fas fa-play'"></i> -->
        <!-- <i :class="isPlaying?'el-icon-video-pause':'el-icon-video-play'"></i> -->
      <!-- </button> -->
      <i  @click="togglePlay" :class="isPlaying?'playBtn el-icon-video-pause':'playBtn el-icon-video-play'"></i>
      <span v-text="currentTime"></span>
      <div class="progress" @click="setProgress($event)">
        <div class="progress-bar" :style="{ width: progressPercent }" ></div>
      </div>
      <span v-text="durationTime"></span>
      <!-- <button class="fullscreen-button" @click="toggleFullscreen">
        <i :class="isFullscreen ? 'el-icon-full-screen' : 'el-icon-full-screen'"></i>
      </button> -->
      <i @click="toggleFullscreen" :class="isFullscreen ? 'playBtn el-icon-full-screen' : 'playBtn el-icon-full-screen'" ></i>
    </div>
  </div>
  
  <!-- 下周三（7）下午补 -->
  
  </div>
  
  </div>
</template>

<script>
import axios from 'axios' ;
export default {
  name:"VideoPlayer",
  // props: {
  //   videoSrc: {
  //     type: String,
  //     required: true
  //   }
  // },
  data() {
    return {
      canplay:false,
      videoSrc:"",
      showControls: false,
      isPlaying: false,
      progress: 0,
      duration: 0,
      isFullscreen: false,
      currentTime:"00:00",
      durationTime:"00:00"
    }
  },
  created(){
    this.$bus.$on(this.$myEvents.PLAYER_DATA,this.getLink);
    this.$bus.$on(this.$myEvents.PLAYER_PAUSE,this.pausePlay);
  },
  beforeDestroy(){
    this.$bus.$off(this.$myEvents.PLAYER_DATA);
    this.$bus.$off(this.$myEvents.PLAYER_PAUSE);
  },
  methods: {
    play() {
      if(this.canplay){
        this.isPlaying = true
      }else{
        this.$message.warning("没有视频！");
      }
      
    },
    pause() {
      this.isPlaying = false
    },
    togglePlay() {
      if (this.isPlaying) {
        this.$refs.videoPlayer.pause()
      } else {
        if(this.canplay){
          this.$refs.videoPlayer.play()
        }else{
          this.$message.warning("没有视频！");
        }
      }
    },
    async pausePlay(fun){
      console.log("暂停播放")
      if (this.isPlaying) {
        this.isPlaying=false;
        await this.$refs.videoPlayer.pause();
        console.log("已暂停")
        fun();
      }else{
        fun();
      }
    },
    updateProgress() {
      if(this.isPlaying){
        this.progress = this.$refs.videoPlayer.currentTime
        this.duration = this.$refs.videoPlayer.duration

        const seconds = this.$refs.videoPlayer.currentTime;
        this.currentTime = new Date(seconds * 1000).toISOString().substr(14, 5);
        // 这里将时间格式化成了 mm:ss 的格式，如 01:12 表示 1 分 12 秒
      }
      
    },
    toggleFullscreen() {
      if (!this.isFullscreen) {
        this.$refs.videoPlayerContainer.requestFullscreen()
      } else {
        document.exitFullscreen()
      }
      this.isFullscreen = !this.isFullscreen
    },
    handleFullscreenChange() {
      // setTimeout(()=>{
      //   this.isFullscreen = !!document.fullscreenElement
      //   this.updateProgress()
      // },500)
      //切换完成之后再获取进度
      this.$nextTick(function(){
        this.isFullscreen = !!document.fullscreenElement
        this.updateProgress()
      })
    },
    setProgress(event) {
      const boundingRect = event.currentTarget.getBoundingClientRect()
      const percent = (event.clientX - boundingRect.left) / boundingRect.width
      this.$refs.videoPlayer.currentTime = percent * this.duration;
    },
    async getLink(data){
      console.log("数据",data);
      this.canplay=true;
      const groupid = data.groupid;
      const authorid = data.authorid;
      const filename = data.filename; // 视频文件名
      
      const url = this.$myInterface.videoUrl +"?groupid="+groupid+"&authorid="+authorid+"&filename="+filename;
      console.log("url=",url);
      // const res = await this.$http.get(url, { responseType: 'blob' }); // 使用 axios 发起 HTTP 请求
      const res = await axios.get(url, { responseType: 'blob' })
      const blob = new Blob([res.data], { type: 'video/mp4' }); // 将响应数据转换为 blob 类型
      this.videoSrc = URL.createObjectURL(blob); // 将 blob 对象转换为 URL 地址，并赋值给 videoUrl
  
    }
  },
  computed: {
    progressPercent() {
      return (this.progress / this.duration) * 100 + '%'
    }
  },
  mounted() {
    this.$refs.videoPlayer.addEventListener('timeupdate', this.updateProgress)
    this.$refs.videoPlayer.addEventListener('mousemove', () => {
      this.showControls = true
      clearTimeout(this.hideControlsTimeout)
      this.hideControlsTimeout = setTimeout(() => {
        this.showControls = false
      }, 2000)
    })
    document.addEventListener('fullscreenchange', this.handleFullscreenChange);

    // 获取 videoPlayer 实例
    const videoPlayer = this.$refs.videoPlayer;
    
    // 加载成功时获取视频总时长
    videoPlayer.onloadedmetadata = () => {
      const durationSeconds = videoPlayer.duration;
      const durationTime = new Date(durationSeconds * 1000).toISOString().substr(14, 5);
      console.log(durationTime); // 输出格式为 HH:MM:SS 的时间字符串
      this.durationTime = durationTime;
    }
    
  },
  beforeUnmount() {
    this.$refs.videoPlayer.removeEventListener('timeupdate', this.updateProgress)
    document.removeEventListener('fullscreenchange', this.handleFullscreenChange)
  }
}
</script>

<style>
.video-player {
  position: relative;
  width:100%;
  height:100%;
  background-color: black;
}

.video-player.is-fullscreen {
  position: fixed;
  width: 100%;
  height: 100%;
}

.controls {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 10px;
  font-size: 20px;
}
.controls span {
  font-size: 15px;
}

.play-pause-button {
  background: none;
  border: none;
  color: white;
  font-size: 24px;
  cursor: pointer;
}

.fullscreen-button {
  background: none;
  border: none;
  color: white;
  font-size: 24px;
  cursor: pointer;
}

.progress {
  height: 5px;
  width: 100%;
  background-color: white;
  margin: 0 10px;
  cursor: pointer;
}

.playBtn {
  padding:10px;
  cursor:pointer;
}
.playBtn:hover {
  background-color: rgba(185, 184, 184, 0.608);
  border-radius: 6px;
}

.progress-bar {
  height: 100%;
  background-color: rgb(12, 145, 248);
  cursor: pointer;
}
</style>