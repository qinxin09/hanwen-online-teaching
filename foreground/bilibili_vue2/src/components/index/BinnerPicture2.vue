<!--
二次开发
组员：sunRan
功能：轮播图，播放量前十的展示
-->
<template>
  <el-row>
    <el-col :span="14">
      <div>
        <div style="
                    margin-right: 10px;
                    color: rgb(0, 165, 232);
                    font-weight: bold;
                    text-align: center;
                    align-items: center;
                    justify-content: center;
                    display: inline-flex;
                    ">
              <img src="@/assets/热门.png" width="30" alt="">&nbsp;火热推荐</div>


        <el-carousel indicator-position="outside">
          
          <el-carousel-item v-for="(item, index) in topVideos" :key="index">
            <div
              style="
                width: 100%;
                margin: auto;
                height: 100%;
                position: relative;
              "
            >
              <a
                style="
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  height: 100%;
                "
                @click="goToVideoPage(item)"
                class="grid-content"
              >
                <!-- <img src="https://img-home.csdnimg.cn/images/20230608093134.jpg" style="object-fit: cover;" :style="coverimgStyle" alt=""> -->
                <img
                  :src="origin + item.coverUrl"
                  style="object-fit: cover; width: 100%"
                  alt=""
                />
                <div
                  class="img_bottom_line"
                  style="position: absolute; bottom: 0; width: 100%"
                >
                  <div style="position: relative">
                    <div
                      style="
                        position: absolute;
                        left: 0;
                        width: 100%;
                        box-shadow: rgba(0, 0, 0, 0.5) 0px 15px 25px 10px;
                      "
                    ></div>
                  </div>

                  <div>
                    <ul
                      style="
                        display: flex;
                        justify-content: space-between;
                        margin: 0;
                        padding: 0 5px;
                        list-style: none;
                        position: relative;
                        color:rgb(232, 232, 232);
                      "
                    >
                      <li style="float: left">
                        <!-- <span style="margin-right:5px;">播放量</span>
                                <span>评论量</span> -->
                        <!-- 改成图标 -->
                        <span
                          style="
                            margin-right: 5px;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            
                          "
                        >
                          <svg
                            class="icon"
                            style="
                              width: 1.0908203125em;
                              height: 1em;
                              vertical-align: middle;
                              fill: currentColor;
                              overflow: hidden;
                            "
                            viewBox="0 0 1117 1024"
                            version="1.1"
                            xmlns="http://www.w3.org/2000/svg"
                            p-id="2245"
                          >
                            <path
                              d="M186.181818 93.090909a93.090909 93.090909 0 0 0-93.090909 93.090909v651.636364a93.090909 93.090909 0 0 0 93.090909 93.090909h744.727273a93.090909 93.090909 0 0 0 93.090909-93.090909V186.181818a93.090909 93.090909 0 0 0-93.090909-93.090909H186.181818z m0-93.090909h744.727273a186.181818 186.181818 0 0 1 186.181818 186.181818v651.636364a186.181818 186.181818 0 0 1-186.181818 186.181818H186.181818a186.181818 186.181818 0 0 1-186.181818-186.181818V186.181818a186.181818 186.181818 0 0 1 186.181818-186.181818z m566.923637 565.620364a69.818182 69.818182 0 0 0 0-107.240728l-219.694546-183.063272A69.818182 69.818182 0 0 0 418.909091 328.983273v366.033454a69.818182 69.818182 0 0 0 114.501818 53.666909l219.694546-183.063272zM512 378.647273L672.023273 512 512 645.352727v-266.705454z"
                              fill="#CCCCCC"
                              p-id="2246"
                            ></path>
                          </svg>
                          &nbsp;{{
                            item.playCount == null ? 0 : item.playCount
                          }}&nbsp;&nbsp;
                          <svg
                            t="1687168548364"
                            class="icon"
                            style="
                              width: 1.130859375em;
                              height: 1em;
                              vertical-align: middle;
                              fill: currentColor;
                              overflow: hidden;
                            "
                            viewBox="0 0 1158 1024"
                            version="1.1"
                            xmlns="http://www.w3.org/2000/svg"
                            p-id="5601"
                          >
                            <path
                              d="M1045.76318 113.055479v678.332873H174.953354l-26.285399 15.26249-35.612476 20.632625V113.055479h932.707701m113.055479-113.055479H0v1024L205.478333 904.443831H1158.818659V0z"
                              fill="#cdcdcd"
                              p-id="5602"
                            ></path>
                            <path
                              d="M392.302512 384.388628a72.072868 72.072868 0 1 0 72.072868 72.072868 72.072868 72.072868 0 0 0-72.072868-72.072868zM766.516147 384.388628a72.072868 72.072868 0 1 0 72.072868 72.072868 72.072868 72.072868 0 0 0-72.072868-72.072868z"
                              fill="#cdcdcd"
                              p-id="5603"
                            ></path>
                          </svg>
                          &nbsp;{{
                            item.commentCount == null ? 0 : item.commentCount
                          }}
                        </span>

                        <span
                          style="
                            display: flex;
                            justify-content: center;
                            align-items: center;
                          "
                        >
                        </span>
                      </li>
                      <li>{{ item.brief }}</li>
                      <li style="float: right">{{ countTime(item.videos) }}</li>
                    </ul>
                  </div>
                </div>
              </a>
            </div>

            <!--  -->
          </el-carousel-item>
        </el-carousel>
      </div>
    </el-col>
    <el-col :span="8" :offset="1">
      <div style="
              margin-right: 10px;
              color: rgb(0, 165, 232);
              font-weight: bold;
              text-align: center;
              align-items: center;
              justify-content: center;
              display: inline-flex;
              ">
        <img src="@/assets/排行榜.png" width="30" alt="">&nbsp;排行榜</div>
      <!-- <div class="grid-content bg-purple-light">
        


        <ul style="
        padding: 10px 10px;
        width: 90%;
        margin: auto;
        border-radius:5px;
        overflow: hidden;
        ">
            <li v-for="(item, index) in topVideos" :key="index" 
            class="topList"
            style="
            margin:0px 1px;
            padding: 5px;
            border-radius:0px;
            border-bottom: 1px solid #e9e9e9;
            color:skyblue;"
            @click="goToVideoPage(item)"
            >
                {{ (index+1)+"  "+item.brief }}
                <span style="float:right;">
                    {{ countTime(item.videos) }}
                </span>
            </li>
        </ul>

      </div> -->

      <div class="top10_container">
        <div v-for="(item, index) in topVideos" :key="index" class="top10_div" @click="goToVideoPage(item)">
          {{ item.brief }}
                <span style="float:right;">
                    {{ countTime(item.videos) }}
                </span>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "BinnerPicture2",
  props: ["topVideos"],
  data() {
    return {
      coverimgStyle: { height: "100px" },
    };
  },
  computed: {
    origin() {
      return this.$myInterface.origin;
    },
  },
  methods: {
    countTime(videosJson) {
      let videos = [];
      if (videosJson != null) {
        videos = JSON.parse(videosJson);
      }
      let countDuration = 0;
      for (let i = 0; i < videos.length; i++) {
        countDuration += parseInt(videos[i].duration);
      }
      function formatTime(duration) {
        let hours = Math.floor(duration / 3600);
        let minutes = Math.floor((duration - hours * 3600) / 60);
        let seconds = Math.floor(duration - hours * 3600 - minutes * 60);
        hours = hours.toString().padStart(2, "0");
        minutes = minutes.toString().padStart(2, "0");
        seconds = seconds.toString().padStart(2, "0");

        let h = hours == "00" ? "" : hours + ":";
        return `${h}${minutes}:${seconds}`;
      }
      return formatTime(countDuration);
    },
    goToVideoPage(item) {
      console.log("合集信息", item);
      this.$StorageUtils.setVideoGroup(item);
      // this.$router.push({ path: '/playPage', query: { videoId: "1234" } });
      this.$userUtils.addPlayCount(this, { groupId: item.id });
      window.open(window.location.origin + "/playPage", "_blank");
      item.playCount = item.playCount == null ? 0 : item.playCount + 1;
    },
  },
};
</script>

<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
.topList {
  background-color: white;
}
.topList:hover {
  background-color: rgb(225 235 240);
}
.top10_div{
  padding: 5px;
  border: 1px solid #8080801f;
  border-left: none;
  border-right: none;
  border-bottom: none;
}
.top10_container{
  color: gray;
  cursor: pointer;
  user-select: none;
}

.top10_container > :last-child{
  padding: 5px;
  border-bottom: 1px solid #8080801f;
}
.top10_div:hover{
  color: #009eff;
  background-color: rgb(242 242 242);
}
.grid-content{
  padding:0px !important;
  border: 1px solid rgb(225, 223, 223);
}
</style>