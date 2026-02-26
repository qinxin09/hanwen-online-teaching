<template>
  <div style="width:100%;height:100%;">
    <router-view/>
  </div>
</template>

<script>
  export default {
    name: 'App',
  }
</script>
<style>
  body {
    margin:0;
  }
  html,body {
    width:100%;
    height:100%;
    background-color: #f4f5f7;
  }
  .card {
    border: 1px solid #eee;
    background-color:white;
    border-radius: 5px;
  }
</style>

