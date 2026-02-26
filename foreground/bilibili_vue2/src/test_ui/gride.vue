<template>
  <div>
    <el-row type="flex" justify="center" :gutter="20" v-for="(row) in rows" :key="'row'+row">
      <el-col  :span="span" v-for="(col) in colums" :key="'col'+col">
        <div v-if="arr[(row-1)*colums+(col-1)]!=null" class="grid-content bg-purple">
          {{ arr[(row-1)*colums+(col-1)].name}}
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
        colums:5,//列数
        
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
</style>