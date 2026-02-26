<!--
组员：xiashaocong
功能：修改管理员权限
-->
<template>
    <el-dialog title="编辑管理员" :visible.sync="dialogFormVisible">
        <el-form :model="form" :rules="rules" ref="ruleForm">
            <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="选择权限" prop="lv" :label-width="formLabelWidth">
            <el-select v-model="form.lv" placeholder="请选择权限">
                <el-option v-for="(option,index) in selectOptions" :label="option.name+' (Lv'+option.leval+')'" :value="option.leval" :key="index"></el-option>
            </el-select>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitForm">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
export default {
    name:"EditDialog",
    data(){
        return {
            dialogFormVisible: false,
            selectOptions:[],
            form: {
                name: '',
                lv: '',
                id:''
            },
            rules:{
                name:[ { required: true, message: '请输入管理员姓名', trigger: 'blur' }, ],
                lv:[ { required: true, message: '请选择管理员权限', trigger: 'blur' }, ]
            },
            formLabelWidth: '120px'
        };
    },
    created(){
        this.$bus.$on(this.$myEvents.SHOW_ADMIN_DIALOG,this.showDialog)
    },
    beforeDestroy(){
        this.$bus.$off(this.$myEvents.SHOW_ADMIN_DIALOG);
    },
    methods:{
        showDialog(data){
            this.form.name=data.admin.name;
            this.form.lv=data.admin.lv;
            this.form.id=data.admin.id;
            this.selectOptions=data.permissionList;
            this.dialogFormVisible=true;
        },
        async submitForm(){
            this.$refs["ruleForm"].validate( async(valid) => {
            if (valid) {
                let res = await this.$adminUtils.updAdmin(this,this.form);
                if(res.data && res.data.code==1){
                    this.$message.success("修改成功!");
                    this.$bus.$emit(this.$myEvents.FRESH_ADMINLIST);
                    this.dialogFormVisible=false;//关闭对话框
                }else{
                    this.$message.error("修改失败!");
                }
            } else {
                console.log('error submit!!');
                return false;
            }
            });
            // this.dialogFormVisible = false;
        }
    }
  };
</script>

<style scoped>
.el-select{
    width: 300px;
}
.el-input {
    width: 300px;
}
</style>