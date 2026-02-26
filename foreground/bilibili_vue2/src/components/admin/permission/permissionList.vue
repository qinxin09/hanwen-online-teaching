<template>
  <div>
    <AddDialog :permissionList="permissionList" :menuList="menuList" :roles="roles"/>
    <div style="margin:10px 0;">
      <el-card>
        <el-row>
          <el-button type="primary" size="small" @click.native="showDialogAddPermission">添加权限</el-button>
        </el-row>
      </el-card>
      
    </div>
    <el-table :data="permissionList" style="width: 100%">
      <!-- <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="标题">
              <span>{{ props.row.title }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column> -->
      <el-table-column label="权限名称" prop="name" width="100"> </el-table-column>
      <el-table-column label="权限等级" prop="leval" width="100"></el-table-column>
      <el-table-column label="可浏览栏目" prop="items" width="400">
        <template slot-scope="scope">
          <span>{{ (scope.row.items+"").length==0?"--":"" }}</span>
          <span >{{ scope.row.items.replaceAll(","," | ") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="绑定角色" prop="roles" width="200">
        <template slot-scope="scope">
          <span>{{ (scope.row.roles+"").length==0?"--":"" }}</span>
          <span >{{ transRoles(scope.row.roles) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" >
        <template slot-scope="scope">
          <el-button-group>
            <el-button
              size="medium"
              type="warning"
              @click="handleEdit(scope.row)"><i class="el-icon-edit"></i></el-button>
            <el-button
              size="medium"
              type="danger"
              @click="handleDelete(scope.row)"><i class="el-icon-delete"></i></el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
  </div>
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
import { mapActions, mapState } from "vuex";
import AddDialog from "./addDialog.vue";

export default {
  name: "PermissionList",
  props:["menuList"],
  components:{
    AddDialog
  },
  data() {
    return {
      
    };
  },
  created(){
    this.$bus.$on(this.$myEvents.SAVE_PERMISSION,this.addPermission)
  },
  beforeDestroy(){
    this.$bus.$off(this.$myEvents.SAVE_PERMISSION);
  },
  computed: {
    tableData(){
      // console.log("table data changed===================================")
      return this.permissionList;
    },
    ...mapState("admin", ["permissionList","roles"]),
  },
  
  mounted() {
    this.requestPermissionList();
  },
  methods: {
    ...mapActions("admin", ["requestPermissionList","serverSavePermission","serverDeletePermission"]),
    showDialogAddPermission(){
      this.$bus.$emit(this.$myEvents.PERMISSION_SHOW_DIALOG);
    },
    addPermission(data){
      // console.log("要保存的权限是；",data);
      this.serverSavePermission(data);
    },
    handleEdit(row){
      this.$bus.$emit(this.$myEvents.PERMISSION_SHOW_DIALOG,row);
    },
    handleDelete(row){
      this.$confirm("确认删除权限"+row.name+"?")
      .then(()=>{
        this.serverDeletePermission({
          leval:row.leval
        });
      }).catch((e)=>{
        console.trace("已取消",e);
      })
    },
    transRoles(rolesStr){
      if(this.roles!=null){
        let rolesArr=rolesStr.split(",");
        // console.log(rolesArr);
        let result=[];
        for(let r in rolesArr){
          for(let key in this.roles){
            if(rolesArr[r]===this.roles[key]+''){
              result.push(key);
            }
          }
        }
        return result.join(' | ');
      }
      return rolesStr+"||"
    }
  },

};
</script>