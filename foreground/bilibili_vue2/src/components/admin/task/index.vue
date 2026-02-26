<template>
  <div>
    
    <!-- <PublishView v-if="currentMenuItemKey == 'project-publish'"></PublishView> -->
    <TaskList v-if="currentMenuItemKey.match('task-list')" :currentMenuItemKey=currentMenuItemKey></TaskList>
    <CreateView v-if="currentMenuItemKey == 'task-create'"></CreateView>

  </div>
</template>

  <script>
    import TaskList from './taskList.vue';
    import CreateView from './create/create.vue';
    
    export default {
        name:"ProjectView",
        props:["currentMenuItemKey"],
        components:{
          CreateView,
          TaskList
        },
        data(){
          return {
            updTaskDate:{},
          }
        },
        created(){
        },
        
    }
  </script>