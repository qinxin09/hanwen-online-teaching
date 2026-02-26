<!--
组员：夏少聪
功能：标签栏，切换菜单列表（实现组件的切换）
-->
<template>
    <div>
        
    <el-tabs v-model="editableTabsValue" type="card" closable @tab-remove="removeTab" @tab-click="clickTab">
        <el-tab-pane
            v-for="(item, index) in editableTabs"
            :key="item.name+index"
            :label="item.title"
            :name="item.name"
        >
        </el-tab-pane>
        </el-tabs>
    </div>
</template>
<script>
  export default {
    name:"TabsComponent",
    created(){
      //绑定显示标签事件
      this.$bus.$on(this.$myEvents.SHOW_TAB,(tab)=>{
        console.log("切换菜单列表到",tab);
        // 添加到标签
        // 查重
        for(let i=0;i<this.editableTabs.length;i++){
          if(this.editableTabs[i].key==tab.key){
            //已存在此tab不需要新增
            //切换到此标签
            return this.editableTabsValue=tab.name;
          }
        }
        //添加到标签
        this.addTab(tab);
      });
    },
    beforeDestroy(){
      //解绑显示标签事件
      this.$bus.$off(this.$myEvents.SHOW_TAB);
    },
    data() {
      return {
        editableTabsValue: '1',
        editableTabs: [
          {
            title: 'HOME',
            name: 'home',
            key:""
          }
        ],
        index:0
      }
    },
    methods: {
      addTab(tab) {
        this.editableTabs.push(tab);
        this.editableTabsValue=tab.name;
        //限制长度不超过6个
        this.index=this.editableTabs.length-1;
        if(this.index >6){
          this.editableTabs.splice(1,1);
          this.index=this.index-1;
        }
        
      },
      removeTab(targetName) {
        console.log(targetName)
        if((targetName+"").toLowerCase()=="home"){
          console.log("HOME不能关闭");
          return false;
        }
        let tabs = this.editableTabs;
        let activeName = this.editableTabsValue;
        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            if (tab.name === targetName) {
              let nextTab = tabs[index + 1] || tabs[index - 1];
              if (nextTab) {
                activeName = nextTab.name;
              }
            }
          });
        }
        
        this.editableTabsValue = activeName;
        this.editableTabs = tabs.filter(tab => tab.name !== targetName);
        if(this.index==this.editableTabs.length){
          this.index = this.index-1;
          let currentTab = this.editableTabs[this.index];
          this.$bus.$emit(this.$myEvents.SWITCH_MENU_LIST,{
            key:currentTab.key,
            active:currentTab.active
          });
        }

      },
      clickTab(tab){
        if(tab.index==this.index){
          return console.log("点击的是原来的位置！");
        }
        // if(tab.index==0){
        //   // 第一个不能被关闭
        //   return;
        // }
        let key = this.editableTabs[tab.index].key;
        let active = this.editableTabs[tab.index].active;
        this.index = tab.index;
        console.log("tab:切换到",key);
        this.$bus.$emit(this.$myEvents.SWITCH_MENU_LIST,{
          key:key,
          active:active
        });
      }
    }
  }
</script>

<style scoped>
.el-tabs__header {
    /* padding: 0;
    position: relative; */
    margin: 100px !important;
}
</style>