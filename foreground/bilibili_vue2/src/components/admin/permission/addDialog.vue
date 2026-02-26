<template>
    <el-dialog :title="formTitle" :visible.sync="showDialog">
        <el-form :model="form" ref="ruleForm" :rules="rules" size="mini">
            <el-form-item label="权限名称" :label-width="formLabelWidth" prop="name">
                <el-input v-model="form.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="权限等级" :label-width="formLabelWidth" prop="leval">
                <!-- <el-input type="number" v-model.number="form.leval" placeholder="输入权限等级1~9" autocomplete="off"></el-input> -->
                <el-select v-model="form.leval" placeholder="请选择权限" @change="initMenuState">
                    <el-option v-for="(item,index) in levalOptions" :key="index" :label="item.leval+(item.disabled?' (已使用)':'')" :value="item.leval" :disabled="item.disabled || mode=='edit'"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="菜单列表" :label-width="formLabelWidth" prop="menuItems">
                <ul style="border: 1px solid #e7e9ee;padding:10px;border-radius: 5px;">
                    <li v-show="menuItems.length==0" style="color:#b7b7b7;">请先输入权限等级</li>
                    <li style="font-weight: bold;color: #409effba;border-bottom:1px solid #e4e4e4;margin-bottom:10px;" v-for="(item,index1) in menuItems" :key="item.streeid">
                        {{item.treename}}
                        <ul>
                            <el-checkbox :label="subItem.treename"  :checked="subItem.checked" v-for="(subItem,index2) in item.adminTreeList" :key="subItem.checked+''+index2+index1" @change="handleCheck(index1,index2,subItem.checked)"></el-checkbox>
                        </ul>
                    </li>
                </ul>
            </el-form-item>

            <el-form-item label="角色绑定" :label-width="formLabelWidth" prop="roleBand">
                <el-checkbox :label="mRule.code+' '+mRule.name"  :checked="mRule.checked" v-for="(mRule) in form.roles" :key="mRule.name+mRule.checked" @change="handleCheckRoles(mRule)"></el-checkbox>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="closeDialog">取 消</el-button>
            <el-button type="primary" @click="submit">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
export default {
    name:"AddDialog",
    props:["permissionList","menuList","roles"],
    data(){
        var checkLeval = function(rule,value,callback){
            if(value>0 && value<=9){
                return callback();
            }else{
                return callback(new Error("等级1~9"));
            }
        };
        return {
            mode:"",
            testtt:[],
            showDialog:false,
            formLabelWidth:"100px",
            form:{
                name:'',
                leval:'',
                roles:[]
            },
            rules:{
                name:[
                    { required: true, message: '请输入权限名称', trigger: 'blur' },
                ],
                leval:[
                    { validator: checkLeval, trigger: 'blur' },
                ]
            },
            menuItems:[]
        }
    },
    computed:{
        formTitle(){
            if(this.mode=="add"){
                return "添加权限";
            }else if(this.mode=="edit"){
                return "编辑权限";
            }
            return "";
        },
        //除去，已有的权限等级
        levalOptions(){
            let levalOptions=[];
            if(!this.permissionList){
                return [];
            }
            for(let i=1;i<10;i++){
                let disabled=false;
                for(let j=0;j<this.permissionList.length;j++){
                    if(i==this.permissionList[j].leval){
                        disabled=true;
                        break;
                    }
                }
                levalOptions.push({
                    leval:i,
                    disabled:disabled
                })
            }
            return levalOptions;
        }
    },
    watch:{
        menuItems:{
            deep:true,
            immediate:true,
            handler(value){
                value;
                // console.log("changed",value)
            }
        },
        form:{
            deep:true,
            immediate:true,
            handler(){

            }
        }
    },
    created(){
        this.initMenuState();
    },
    mounted(){
        this.$bus.$on(this.$myEvents.PERMISSION_SHOW_DIALOG,(editPermissionData)=>{
            //初始化form.roles
            this.form.roles=[];
            //checked全部值否
            for(let role in this.roles){
                this.form.roles.push({
                    checked:false,
                    name:role+"",
                    code:this.roles[role]+""
                })
            }
            
            if(editPermissionData!=null){
                console.log(editPermissionData)
                this.mode="edit";
                this.form.name=editPermissionData.name;
                this.form.leval=editPermissionData.leval;

                //更新form.roles checked
                let temp=editPermissionData.roles.split(',');
                
                for(let i=0;i<temp.length;i++){
                    for(let j=0 ;j< this.form.roles.length;j++){
                        if(temp[i] === this.form.roles[j].code+''){
                            this.form.roles[j].checked=true;
                        }
                    }
                }
            }else{
                this.mode="add";
            }
            this.showDialog=true;
            this.initMenuState();
        });
    },
    beforeDestroy(){
        this.$bus.$off(this.$myEvents.PERMISSION_SHOW_DIALOG);
    },
    methods:{
        closeDialog(){
            //  清空菜单状态
            this.menuItems=[];
            // 清空表单数据
            this.$refs.ruleForm.resetFields();
            //关闭弹窗
            this.showDialog=false;
        },
        submit(){
            this.$refs["ruleForm"].validate((valid) => {
                if (valid) {
                    let permissions={
                        name:this.form.name,
                        leval:this.form.leval,
                        items:[]
                    }
                    let admintree=[];
                    let m=this.menuItems;
                    let leval = this.form.leval+"";
                    let updLv = function(item){
                        let arr=(item.lv+"").split(",");
                        let checked = item.checked;
                        //是否存入
                        if(checked){//存入
                            // 判断是否已存在
                            for(let i=0;i<arr.length;i++){
                                //已存在
                                if(leval === arr[i]){
                                    return item.lv;
                                }
                            }
                            //不存在
                            arr.push(leval);
                            return arr.join(',');
                        }else{
                            //不存入
                            // 判断存在
                            let exists=false;
                            for(let i=0;i<arr.length;i++){
                                //已存在
                                if(leval === arr[i]){
                                    arr.splice(i,1);
                                    i--;
                                    exists=true;
                                }
                            }
                            if(exists){
                                return arr.join(',');
                            }
                            //不存在
                            return item.lv;
                        }
                    }

                    for(let i=0;i<m.length;i++){//遍历每个父菜单

                        let item = m[i];
                        let ckecked = false;
                        for(let j=0;j<item.adminTreeList.length;j++){//遍历每个子菜单
                            //更新lv
                            let item2=item.adminTreeList[j];
                            item2.lv=updLv(item.adminTreeList[j]);
                            if(item2.checked==true){
                                ckecked=true;
                                permissions.items.push(item2.treename);
                            }
                            admintree.push({...item2});
                        }
                        item.checked=ckecked;
                        item.lv = updLv(item);
                        admintree.push({...item});
                    }
                    permissions.items=permissions.items.join(',');
                    
                    let result = [];
                    for(let i=0;i<this.form.roles.length;i++){
                        if(this.form.roles[i].checked){
                            result.push(this.form.roles[i].code);
                        }
                    }

                    permissions.roles=result.join(",");
                       
                    this.$bus.$emit(this.$myEvents.SAVE_PERMISSION,{
                        permissions:permissions,
                        admintree:admintree,
                        newMenu:this.menuItems,
                        mode:this.mode
                    });
                    // 清空表单数据
                    this.$refs["ruleForm"].resetFields();
                    //关闭弹窗
                    this.showDialog=false;
                }
            });
        },
        initMenuState(){
            //每个菜单列表都添加一个属性 checked
            let lv = this.form.leval;
            if(lv==""){
                console.log("未选中权限等级！")
                this.menuItems=[];
                return;
            }
            // let menu = this.menuList.slice() ;潜复制，对象还是被引用了！
            let menu = JSON.parse(JSON.stringify(this.menuList));
            // Object.assign(menu,this.menuList);
            for(let i=0;i<menu.length;i++){
                let items=menu[i].adminTreeList;
                menu[i].checked=false;
                for(let j=0;j<items.length;j++){
                    let lvs=items[j].lv.split(",");
                    let checked=false;
                    for(let index in lvs){
                        if(parseInt(lvs[index]) == lv){
                            checked=true;
                            menu[i].checked=true;
                            break;
                        }
                    }
                    items[j].checked=checked;
                }
            }
            this.menuItems=menu;
        },
        handleCheck(index1,index2,value){
            this.$set(this.menuItems[index1].adminTreeList[index2],"checked",!value)
        },
        handleCheckRoles(mRole){
            mRole.checked=!mRole.checked;
            // console.log(mRole+"的checked取反");
        }
    }
};
</script>

<style>
ul {
    list-style: none;
}
</style>