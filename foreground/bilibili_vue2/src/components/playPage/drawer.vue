<!--
二次开发
组员：yws
功能：展示附件
-->
<template>
    <div>
      <div>
            <LoginRegist/>
        </div>
        <el-button @click="showAttchementFiles"><i class="el-icon-paperclip"></i>附件</el-button>
        <el-drawer
            title="附件资料"
            size="80%"
            :visible.sync="drawer"
            :direction="direction"
            :before-close="handleClose">
            <!-- <span>{{dataResource}}</span> -->
            <div>
              <qx-grid :gridDataSource="gridDataSource"></qx-grid>
            </div>
            <PreView></PreView>
        </el-drawer>
    </div>
</template>

<script>
import QxGrid from './qxGrid.vue'
import PreView from './preView2.vue';
import LoginRegist from '@/components/LoginRegist.vue';

  export default {
    name:"ShowDrawer",
    components:{
      "qx-grid":QxGrid,
      PreView,
      LoginRegist
    },
    props:["dataResource"],
    data() {
      return {
        drawer: false,
        direction: 'rtl',//right to left
      };
    },
    computed:{
      gridDataSource(){
        
        let names=this.dataResource.attachementNames.split(",");
        if(this.dataResource.attachementNames=="null"){
          names=[];
        }
        let data=[];
        let res={
          column:6,
          authorId:this.dataResource.authorId+"",
          groupid:this.dataResource.id
        }
        for(let i=0;i<names.length;i++){
          let e = {name:names[i]};
          e.type= function(){
            if(names[i].endsWith(".png") || names[i].endsWith(".jpg")){
              return "img";
            }
            if(names[i].endsWith(".zip")){
              return "zip";
            }
            return "other";
          }();
          data.push(e);
        }
        res.data=data;
        return res;
      }
    },
    methods: {
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(() => {
            done();
          })
          .catch(() => {});
      },
      showAttchementFiles(){
        console.log("显示附件");
        this.drawer = true;
      }
    }
  };
</script>
