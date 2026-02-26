<!--
组员：夏少聪
功能：添加管理员
-->
<template>
    <div style="width:50%;margin:20px;">
        <el-card class="box-card">
            <AdminRegist :isIndexPage="true"></AdminRegist>
        </el-card>
    </div>
</template>

<script>
import AdminRegist from '@/components/admin/registForm.vue';
export default {
    name:"AddAdmin",
    components:{
        AdminRegist
    }
}
</script>

<style>

</style>