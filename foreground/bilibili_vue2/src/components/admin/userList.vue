<!--
组员：夏少聪
功能：展示用户信息
-->
<template>
  <el-table
    :data="tableData"
    style="width: 100%">
    <el-table-column
      prop="name"
      label="用户名"
      >
    </el-table-column>
    <el-table-column
      prop="sex"
      label="性别"
      >
      <template slot-scope="scope">
        <span >{{ scope.row.sex==1?'男':'女' }}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="age"
      label="年龄"
      >
    </el-table-column>
    <el-table-column
      prop="phone"
      label="手机号"
      >
    </el-table-column>
    
    <el-table-column
      prop="status"
      label="状态"
      sortable
      >
      <template slot-scope="scope">
        <span >{{ scope.row.status==1?'正常':'封禁' }}</span>
      </template>
    </el-table-column>
    
    <el-table-column
      v-if="userListProp!='user-list-all'"
      label="操作"
      >
      <template slot-scope="scope" >
        <el-button-group >
          <el-button v-if="roleAdmin['编辑']!=undefined" @click="handleClick(scope.row)" :type="scope.row.status==1?'danger':'primary'" size="small" >{{scope.row.status==1?'封禁':'解禁'}}</el-button>
          <!-- <el-button v-if="admin.lv==0" @click="deleteUser(scope.row)" type="danger" size="small" >{{ "删除" }}</el-button> -->
          <el-button v-if="roleAdmin['删除']!=undefined" @click="deleteUser(scope.row)" type="danger" size="small" >{{ "删除" }}</el-button>
        </el-button-group>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { mapGetters } from 'vuex';
  export default {
    name:"UserList",
    props:["userListProp","admin","permissionList","roles"],
    data(){
      return {
        userListData:{},
        allUserList:[]
      }
    },
    async created(){
      //获取用户数据 并初始化
      // this.$bus.$on( this.$myEvents.FRESH_USERLIST,this.freshUserList);
      await this.freshUserList();
      console.log(this.userListData);
    },
    methods: {
      async handleClick(row) {
        if(row.status==1){
          console.log("执行封号,userid=",row.id);
        }else if(row.status==2){
          console.log("解除封号,userid",row.id);
        }
        let res = await this.$adminUtils.editUserState(this,{userId:row.id,status:row.status==1?2:1});
        if(res.data.code==1){
          console.log("修改成功");
          this.freshUserList();
        }
      },
      async deleteUser(row){
         this.$confirm("是否确认删除用户"+row.name+"?")
          .then( async (value)=>{
            if(value){
              console.log("确认删除！");
              let data={
                userId:row.id
              };
              
              let res = await this.$adminUtils.deleteUser(this,data);
              if(res.data.code==1){
                console.log("删除成功！");
                this.freshUserList();
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
      mergeUserList(){
        this.allUserList=[];
        if(this.userListData.normal==undefined){
          return this.allUserList=[];
        }
        
        let block = this.userListData.block;
        let normalarr = (this.userListData.normal).slice();
        let userList = block.slice();
        userList.push(...normalarr);
        console.log("合并数组",this.userListData.block.length,block.length)

        console.log(userList);
        this.allUserList=userList.sort((a,b)=>{
          return a.id - b.id;
        });
      },
      async freshUserList(){
        let response= await this.$adminUtils.getUserList(this);
        this.userListData = response.data.data;
      },
      // checkRoleDelete(){
      //   let permissionList = this.permissionList;
      //   let admin = this.admin;
      //   let roles = this.roles;
      //   if(admin.lv==0){
      //     return true;
      //   }
      //   let targetRole=roles["删除"];
      //   if(admin!=null ){
      //     if(permissionList!=null){
      //       let myRoles='';
            
      //       for(let p in permissionList){
      //         if(permissionList[p].leval === admin.lv){
      //           myRoles=permissionList[p].roles;
      //         }
      //       }
            
      //       let rolesArr = myRoles.split(",") || [];
            
      //       for(let i=0;i<rolesArr.length;i++){
      //         if(targetRole+'' === rolesArr[i]){
                
      //           return true;
      //         }
      //       }
      //     }
      //   }
      //   return false;
      // }
    },
    computed:{
      ...mapGetters("admin",["roleAdmin"]),
      tableData(){
        //显示所有用户列表
        if(this.userListProp=="user-list-all"){
          //合并数组
          this.mergeUserList();
          return this.allUserList
        }
        //解禁操作
        if(this.userListProp=="user-list-left"){
          return this.userListData.block;
        }
        //封禁操作
        if(this.userListProp=="user-list-block"){
          return this.userListData.normal;
        }
        //删除操作
        if(this.userListProp=="user-list-delete"){
          this.mergeUserList();
          return this.allUserList
        }

        return [];
      },
      
    },
    mounted(){
      
    }

  }
</script>