<template>
  <div>
    <el-row type="flex" justify="center" :gutter="20" v-for="(row) in rows" :key="'row'+row">
      <el-col  :span="span" v-for="(col) in colums" :key="'col'+col">
        <!-- {{ arr[(row-1)*colums+(col-1)].name}} -->
        <div v-if="arr[(row-1)*colums+(col-1)]!=null" class="grid-content bg-purple">
            <div style="width:100%;position:relative;">
              <a href="" style="display:flex;justify-content: center;align-items: center;">
                <img src="https://img-home.csdnimg.cn/images/20230608093134.jpg" style="object-fit: cover;height:100px;" alt="">
                <div class="img_bottom_line" style="position:absolute;bottom:0;width: 100%;">
                  <div>
                    <ul style="display:flex;justify-content:space-between;margin:0;padding:0 5px;list-style:none;">
                      <li style="float:left;">
                        <span style="margin-right:5px;">播放量</span>
                        <span>评论量</span>
                      </li>
                      <!-- <li style="float:left;">评论量</li> -->
                      <li style="float:right;">时长</li>
                    </ul>
                  </div>
                  
                  <div style="position:relative;">
                    <div style="position:absolute;left:0;width:100%;
                      box-shadow: rgba(0, 0, 0, 0.5) 0px 0px 25px 25px;">
                    </div>
                  </div>

                </div>
              </a>
            </div>
        </div>
        <div>
          <div style="margin: 5px;">
            <div style="text-overflow: ellipsis; white-space: nowrap; overflow: hidden;">title000000000000000000000000000000000</div>
            <div style="height:18px;display: inline-flex;justify-content: center;align-items: center;">
              <img src="@/assets/UP_gray.png" style="height:100%;" alt="">
              <span style="margin-left:5px;color:gray;position:relative;bottom:1px;">author name</span></div>
          </div>
        </div>

        
        <!-- <div class="grid-content bg-purple">
          {{ "col="+col+"row"+row}}
        </div> -->
      </el-col>
    </el-row>

  </div>
</template>

<script>
export default {
    name:"GroupList",
    data(){
      let arr=new Array();
      for(let i=0;i<446;i++){
          let o={
            name:"张三"+i,
            age:23
          }
          arr.push(o);
      }
      return {
        arr,
        colums:4,//列数
        
      }
    },
    computed:{
      rows(){
        return Math.ceil(this.arr.length/this.colums);
      },
      span(){
        return Math.floor(24/(this.colums));// span:一行长是24,每个占的长度是 24/列数
      }
    }
}
</script>

<style>
.el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }

  .grid-content {
    padding-left:0px !important;
  }
  .grid-content:hover .img_bottom_line {
    display: none;
  }
</style>