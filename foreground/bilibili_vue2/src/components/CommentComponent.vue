<!--
组员：shibowen
功能：展示评论区
-->
<template>
  <div class="card" style="width:100%;">
    <div style="padding:20px;">
        <div style="margin-bottom: 10px;font-size: 30px;">评论区</div>
        <div style="width:80%; border-bottom:1px solid #e7e3e3;padding-bottom:10px;" >
            <el-container>
                <el-aside width="60px">
                    <div style="line-height:100px;margin-top:10px;">
                        <img :src="getUserIcon()" style="object-fit:cover;width:50px;height:50px;border-radius:50%;border:1px solid gray;" />
                    </div>
                </el-aside>
                    <el-container>
                        <el-main>
                            <div style="width:100%;height:100px;display:flex;padding:5px;">
                                <textarea placeholder="评论一下吧" style="width:80%;margin-right:10px;display:flex;background-color: rgba(221, 221, 221, 0.633);padding:10px;height:80px;max-height:80px;border-radius:3px;" v-model="textarea"></textarea>
                                    
                                <!-- </div> -->
                                <el-button type="primary" size="medium" @click.native="sentCommend">提交评论</el-button>
                               
                            </div>
                        </el-main>
                    </el-container>
            </el-container>
        </div>


          <div style="width:80%; border-bottom:1px solid #e7e3e3;" v-for="(item,index) in props_.commentList" :key="item.createTime+index">
              <el-container>
                  <el-aside width="60px">
                      <div style="line-height:100px;margin-top:10px;">
                          <img :src="getUserIcon(item.userId)" style="object-fit:cover;width:50px;height:50px;border-radius:50%;border:1px solid gray;" />
                      </div>
                  </el-aside>
                      <el-container>
                          <el-header >
                              <div style="width:100%;position:relative;text-align:left;">
                                  <div>
                                    <span style="line-height: unset;">{{ item.name }}</span>
                              
                                    <span style="line-height: unset;margin-left:10px;font-size:10px;color:gray;">{{ getDate(item.createTime) }}</span>
                                  </div>
                              </div>
                          </el-header>
                          <el-main>
                              <div>
                                <p>{{ item.content }}</p> 
                              </div>
                          </el-main>
                      </el-container>
              </el-container>
              <!-- <hr/> -->
          </div>
    </div>
    </div>
</template>

<script>
export default {
    name:"CommentComponent",
    data(){
        return {
            textarea:""
        }
    },
    props:["props_"],
    methods:{
      async sentCommend(){
        if(!this.$userUtils.checkIsLogin()){
          return this.$message.warning("请先登录！");
        }
        let data = {
          "content":this.textarea,
          "groupId":this.props_.groupid
        }
        let res =await this.$userUtils.sendComment(this,data);
        console.log("sffs",res)
        if(res.data.code==1){
          this.$message.success("评论成功！");
          console.log("通知刷新评论区");
          this.textarea='';
          this.$bus.$emit(this.$myEvents.COMMENT_SUCCESS);
        }else{
          this.$message.error("评论失败！");
        }
      },
      getDate(date) {
        const d = new Date(date);
        const year = d.getFullYear();
        const month = ("0" + (d.getMonth() + 1)).slice(-2);  // 月份加一，并且将小于10的数转换为双位数
        const day = ("0" + d.getDate()).slice(-2);  // 将日期转换为双位数
        const hour = ("0" + d.getHours()).slice(-2);  // 将小时转换为双位数
        const minute = ("0" + d.getMinutes()).slice(-2);  // 将分钟转换为双位数
        const second = ("0" + d.getSeconds()).slice(-2);  // 将秒数转换为双位数
        return `${year}-${month}-${day} ${hour}:${minute}:${second}`;  // 返回年月日、时分秒的拼接结果
      },
      getUserIcon(userid){
        //获取自己的id
        if(userid==null){
          let user = this.$userUtils.getUser()
          if(user==null){
            console.log("未登录，获取头像失败！");
            return ;
          }
          userid=user.id;
        }
        let data={
          userid:userid,
          type:"icon"
        }
        return this.$userUtils.getUserIcon(this,data);
      }
    }
}
</script>

<style scoped>
    .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }
  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 160px;
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }
  
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }

  .el-aside {
    background:unset;
  }
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    text-align:left;
    /* padding:0; */
    padding:0 20px;
    background:unset;
}
  .el-main {
    background-color: unset;
    color: #333;
    /* text-align: center; */
    line-height: 0;
    text-align: left;
    /* padding: 0; */
    padding:0 20px;
  }

  textarea {
    border: 1px solid rgba(128, 128, 128, 0.795);
  }
  textarea:focus {
    border: 1px solid rgba(128, 128, 128, 0.795);
  }


</style>