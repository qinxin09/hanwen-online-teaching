<template>
  <el-table :data="tableData" style="width: 100%;" height="580">
    <el-table-column type="expand">
      <template slot-scope="props">
        <el-row>
          <el-col :span="8" :offset="1">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="标题">
                <span>{{ props.row.title }}</span>
              </el-form-item>
              <el-form-item label="创建时间">
                <span>{{ new Date(props.row.createTime).toLocaleDateString() }}</span>
              </el-form-item>
              <el-form-item label="付费">
                <span>{{ props.row.istoll ? "是" : "否" }}</span>
              </el-form-item>
              <el-form-item label="作者">
                <span>{{ props.row.name }}</span>
              </el-form-item>
              <el-form-item label="价格">
                <span>{{ props.row.price == null ? '-' : props.row.price }}</span>
              </el-form-item>
              <el-form-item label="介绍">
                <span>{{ props.row.brief }}</span>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="8">
            <!-- cover start -->
            <div class="grid-content bg-purple">
              <div style="width:100%;position:relative;">
                <a style="display:flex;justify-content: center;align-items: center;"
                  @click="goToVideoPage(props.row)">
                  <!-- <img src="https://img-home.csdnimg.cn/images/20230608093134.jpg" style="object-fit: cover;" :style="coverimgStyle" alt=""> -->
                  <img :src="origin + props.row.coverUrl" style="object-fit: cover;height:200px;" alt="">
                  <div class="img_bottom_line" style="position:absolute;bottom:0;width: 100%;">

                    <div style="position:relative;">
                      <div style="position:absolute;left:0;width:100%;
                      box-shadow: rgba(0, 0, 0, 0.5) 0px 15px 25px 25px;">
                      </div>
                    </div>

                    <div>
                      <ul
                        style="display:flex;justify-content:space-between;margin:0;padding:0 5px;list-style:none;position:relative;color:#c1c1c1;">
                        <li style="float:left;">
                          <!-- <span style="margin-right:5px;">播放量</span>
                        <span>评论量</span> -->
                          <!-- 改成图标 -->
                          <span style="
                          margin-right:5px;
                          display: flex;
                          justify-content: center;
                          align-items: center;">
                            <svg class="icon"
                              style="width: 1.0908203125em;height: 1em;vertical-align: middle;fill: currentColor;overflow: hidden;"
                              viewBox="0 0 1117 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2245">
                              <path
                                d="M186.181818 93.090909a93.090909 93.090909 0 0 0-93.090909 93.090909v651.636364a93.090909 93.090909 0 0 0 93.090909 93.090909h744.727273a93.090909 93.090909 0 0 0 93.090909-93.090909V186.181818a93.090909 93.090909 0 0 0-93.090909-93.090909H186.181818z m0-93.090909h744.727273a186.181818 186.181818 0 0 1 186.181818 186.181818v651.636364a186.181818 186.181818 0 0 1-186.181818 186.181818H186.181818a186.181818 186.181818 0 0 1-186.181818-186.181818V186.181818a186.181818 186.181818 0 0 1 186.181818-186.181818z m566.923637 565.620364a69.818182 69.818182 0 0 0 0-107.240728l-219.694546-183.063272A69.818182 69.818182 0 0 0 418.909091 328.983273v366.033454a69.818182 69.818182 0 0 0 114.501818 53.666909l219.694546-183.063272zM512 378.647273L672.023273 512 512 645.352727v-266.705454z"
                                fill="#CCCCCC" p-id="2246"></path>
                            </svg>
                            &nbsp;{{ props.row.playCount==undefined ?"0":props.row.playCount }}&nbsp;&nbsp;
                            <svg t="1687168548364" class="icon"
                              style="width: 1.130859375em;height: 1em;vertical-align: middle;fill: currentColor;overflow: hidden;"
                              viewBox="0 0 1158 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5601">
                              <path
                                d="M1045.76318 113.055479v678.332873H174.953354l-26.285399 15.26249-35.612476 20.632625V113.055479h932.707701m113.055479-113.055479H0v1024L205.478333 904.443831H1158.818659V0z"
                                fill="#cdcdcd" p-id="5602"></path>
                              <path
                                d="M392.302512 384.388628a72.072868 72.072868 0 1 0 72.072868 72.072868 72.072868 72.072868 0 0 0-72.072868-72.072868zM766.516147 384.388628a72.072868 72.072868 0 1 0 72.072868 72.072868 72.072868 72.072868 0 0 0-72.072868-72.072868z"
                                fill="#cdcdcd" p-id="5603"></path>
                            </svg>
                            &nbsp;{{ props.row.commentCount==undefined ?"0":props.row.commentCount }}
                          </span>

                          <span style="
                          display: flex;
                          justify-content: center;
                          align-items: center;">
                          </span>
                        </li>
                        <!-- <li style="float:left;">评论量</li> -->
                        <li style="float:right;">{{ countTime(props.row.videos) }}</li>
                      </ul>
                    </div>



                  </div>
                </a>
              </div>
            </div>
            <!-- end cover -->
          </el-col>
          
        </el-row>

      </template>
    </el-table-column>
    <el-table-column label="标题" prop="title">
    </el-table-column>
    <el-table-column label="收费" prop="istoll" width="200">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.istoll ? '是' : '否' }}</span>
      </template>
    </el-table-column>
    <el-table-column label="作者" prop="name">
    </el-table-column>
    <el-table-column label="状态" prop="state" sortable>
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ getState(scope.row.state) }}</span>
      </template>
    </el-table-column>
    <el-table-column v-if="this.groupListProp != 'group-list-all'"  label="操作">
      <template slot-scope="scope">
        <!-- <el-button @click="handleClick(scope.row)" :type="scope.row.status==1?'danger':'primary'" size="small" >{{scope.row.status==1?'封禁':'解封'}}</el-button> -->
        <el-button-group>
          <el-button v-if="roleAdmin['编辑']!=undefined" size="small" type="success" :disabled="scope.row.state == 1"
            @click="handlePass(scope.$index, scope.row, tableData)">通过</el-button>
          <el-button v-if="roleAdmin['编辑']!=undefined" size="small" type="warning" :disabled="scope.row.state == 2"
            @click="handleBlock(scope.$index, scope.row, tableData)">违规</el-button>
          <!-- <el-button size="small" v-if="admin.lv == 0" type="danger" -->
          <el-button v-if="roleAdmin['删除']!=undefined" size="small" type="danger"
            @click="handleDelete(scope.$index, scope.row, tableData)">删除</el-button>
        </el-button-group>
      </template>
    </el-table-column>

  </el-table>
</template>

<style>
.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>

<script>
import { mapGetters } from 'vuex';
export default {
  name: "GroupList",
  props: ["groupListProp", "admin","permissionList","roles"],
  data() {
    return {
      groupListData: {},
      allGroupList: [],
      // tableData: [{
      //   id: '12987122',
      //   name: '好滋好味鸡蛋仔',
      //   category: '江浙小吃、小吃零食',
      //   desc: '荷兰优质淡奶，奶香浓而不腻',
      //   address: '上海市普陀区真北路',
      //   shop: '王小虎夫妻店',
      //   shopId: '10333'
      // }]
      tableData: []
    }
  },
  watch: {
    //监视切换，并切换数据
    groupListProp: {
      handler(value) {
        console.log("切换到", value);
        this.getTableData();
      }
    },
  },
  computed:{
    ...mapGetters("admin",["roleAdmin"]),
    origin(){
      return this.$myInterface.origin;
    },
  },

  async created() {
    //获取到数据
    await this.freshGroupList();
    console.log("获取到数据");
    //同步数据到到状态
    this.getTableData();

  },
  methods: {
    getState(state) {
      switch (state) {
        case 0:
          return "待审核";
        case 1:
          return "已通过";
        case 2:
          return "已违规";
        default:
          return ''
      }
    },
    async freshGroupList() {
      let response = await this.$adminUtils.getGroupList(this);
      this.groupListData = response.data.data;

      let temp = this.groupListData.groupList || [];
      if (temp.length == undefined) {
        temp = [];
      }
      this.allGroupList = temp.slice();
      console.log(this.groupListData);
    },
    getTableData() {
      console.log("同步数据到到状态");
      if (this.groupListProp == "group-list-all") {
        this.tableData = this.allGroupList;
      } else if (this.groupListProp == "group-list-wait") {
        // 等待修改数据库，获取state
        console.log("等待修改数据库，获取state")
        this.tableData = this.allGroupList.filter((video) => {
          if (video.state == 0) {
            return true;
          }
          return false;
        });
      } else if (this.groupListProp == "group-list-passed") {
        this.tableData = this.allGroupList.filter((video) => {
          if (video.state == 1) {
            return true;
          }
          return false;
        });
      } else if (this.groupListProp == "group-list-illegal") {
        this.tableData = this.allGroupList.filter((video) => {
          if (video.state == 2) {
            return true;
          }
          return false;
        });
      } else {
        this.tableData = [];
      }
    },
    async handlePass(index, row, tableData) {
      console.log(index, row);
      try {
        let res = await this.$adminUtils.editGroupState(this, { id: row.id, state: 1 });
        if (res.data.code == 1) {
          console.log("编辑成功！");
          tableData.splice(index, 1);
          this.freshGroupList();
        } else {
          console.log("编辑失败！")
        }
      } catch (e) {
        console.log("操作失败！");
      }
    },
    async handleBlock(index, row, tableData) {
      console.log(index, row);
      try {
        let res = await this.$adminUtils.editGroupState(this, { id: row.id, state: 2 });
        if (res.data.code == 1) {
          console.log("编辑成功！");
          tableData.splice(index, 1);
          this.freshGroupList();
        } else {
          console.log("编辑失败！")
        }
      } catch (e) {
        console.log("操作失败！");
      }
    },
    async handleDelete(index, row, tableData) {
      this.$confirm("是否确认删除合集" + row.name + "?")
        .then(async (value) => {
          if (value) {
            console.log("确认删除！");
            let data = {
              groupId: row.id,
              userId: row.authorId
            };

            let res = await this.$adminUtils.deleteGroup(this, data);
            if (res.data.code == 1) {
              console.log("删除成功！");
              tableData.splice(index, 1);
              this.freshGroupList();
            } else {
              console.log("删除失败");
            }

          } else {
            console.log("取消删除")
          }
        }).catch((e) => {
          console.trace("出错了", e);
        })
    },
    countTime(videosJson){
      let videos=[];
      if(videosJson!=null){
        videos = JSON.parse(videosJson);
      }
      let countDuration=0;
      for(let i=0;i<videos.length;i++){
        countDuration+=parseInt(videos[i].duration);
      }
      function formatTime(duration) {
        let hours = Math.floor(duration / 3600);
        let minutes = Math.floor((duration - (hours * 3600)) / 60);
        let seconds = Math.floor(duration - (hours * 3600) - (minutes * 60));
        hours = hours.toString().padStart(2, '0');
        minutes = minutes.toString().padStart(2, '0');
        seconds = seconds.toString().padStart(2, '0');

        let h=hours=="00"?'':hours+":";
        return `${h}${minutes}:${seconds}`;
      }
      return formatTime(countDuration);
    },
    goToVideoPage(item){
      console.log("合集信息",item);
      //设置审核模式，不显示关注、评论、点赞、收藏
      let a = {checkMode:true};
      Object.assign(a,item);
      this.$StorageUtils.setVideoGroup(a);
      window.open(window.location.origin+"/playPage", '_blank');
    },
    checkRoleDelete(){
        let permissionList = this.permissionList;
        let admin = this.admin;
        let roles = this.roles;
        if(admin.lv==0){
          return true;
        }
        let targetRole=roles["删除"];
        if(admin!=null ){
          if(permissionList!=null){
            let myRoles='';
            
            for(let p in permissionList){
              if(permissionList[p].leval === admin.lv){
                myRoles=permissionList[p].roles;
              }
            }
            
            let rolesArr = myRoles.split(",") || [];
            
            for(let i=0;i<rolesArr.length;i++){
              if(targetRole+'' === rolesArr[i]){
                
                return true;
              }
            }
          }
        }
        return false;
      }
    
  }
}
</script>
