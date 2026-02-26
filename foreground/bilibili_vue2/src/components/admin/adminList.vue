<!--
组员：夏少聪
功能：显示管理员列表
-->
<template>
  <div>
    <EditDialog></EditDialog>
    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="name"
        label="姓名"
        >
      </el-table-column>
      <el-table-column
        prop="phone"
        label="手机号"
        >
      </el-table-column>
      
      <el-table-column 
        prop="lv"
        label="等级"
        sortable
        >
        <template slot-scope="scope">
          <span >{{ "lv"+scope.row.lv }}</span>
        </template>
      </el-table-column>
      
      <el-table-column
        v-if="operation!='all'"
        label="操作"
        >
        <template slot-scope="scope" >
          <el-button-group >
            <el-button v-if="operation=='edit'" type="primary" @click="handleGrant(scope.row)" size="small" >编辑</el-button>
            <el-button type="danger" v-if="operation=='delete' && admin.lv==0" @click="deleteAdmin(scope.row)" size="small" >{{ "删除" }}</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import EditDialog from './adminUpdDialog.vue';
  export default {
    name:"adminList",
    props:["adminListProp","admin","permissionList"],
    components:{
      EditDialog
    },
    data(){
      return {
        adminListData:{},
        alladminList:[],
      }
    },
    async created(){
      //获取用户数据 并初始化
      // this.$bus.$on( this.$myEvents.FRESH_adminList,this.freshadminList);
      await this.freshadminList();
      this.$bus.$on(this.$myEvents.FRESH_ADMINLIST,this.freshadminList);
      console.log(this.adminListData);
    },
    beforeDestroy(){
      this.$bus.$off(this.$myEvents.FRESH_ADMINLIST);
    },
    methods: {
      async handleGrant(row) {
          row;
          console.log(this)
          this.$bus.$emit(this.$myEvents.SHOW_ADMIN_DIALOG,{
            admin:row,
            permissionList:this.permissionList
          });
      },
      async deleteAdmin(row){
         this.$confirm("是否确认删除管理员"+row.name+"?")
          .then( async (value)=>{
            if(value){
              console.log("确认删除！");
              let data={
                adminId:row.id
              };
              
              let res = await this.$adminUtils.deleteAdmin(this,data);
              if(res.data.code==1){
                console.log("删除成功！");
                this.freshadminList();
              }else{
                console.log("删除失败");
              }

            }else{
              console.log("取消删除")
            }
          }).catch((e)=>{
            console.trace("已取消",e);
          })
      },
      async freshadminList(){
        console.log("刷新adminlist")
        let response= await this.$adminUtils.getAdminList(this);
        this.adminListData = response.data.data.adminList;
        this.alladminList = this.adminListData.slice();
      }
    },
    computed:{
      tableData(){
        //显示所有用户列表
        if(this.adminListProp=="admin-list-all"){
          console.log("show all")
          return this.alladminList
        }else if(this.adminListProp=="admin-list-edit"){
          console.log("show edit");
          return this.alladminList
        }else if(this.adminListProp=="admin-list-delete"){
          console.log("show delete");
          return this.alladminList
        }
        return [];
      },
      operation(){
        if(this.adminListProp=="admin-list-all"){
          return "all";
        }else if(this.adminListProp=="admin-list-edit"){
          return "edit";
        }else if(this.adminListProp=="admin-list-delete"){
          return "delete";
        }
        return '';
      }
    }

  }
</script>