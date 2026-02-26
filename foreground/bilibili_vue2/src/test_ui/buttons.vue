<template>
    <div>
      <h2>Button</h2>
      <el-button>Default</el-button>
      <el-button type="primary">Primary</el-button>
      <el-button type="success">Success</el-button>
      <el-button type="info">Info</el-button>
      <el-button type="warning">Warning</el-button>
      <el-button type="danger">Danger</el-button>
      <br /><br />
      <h2>Input</h2>
      <el-input v-model="inputValue" placeholder="请输入内容"></el-input>
        <p>你输入的内容是: {{ inputValue }}</p>
      <br /><br />
      <h2>Radio</h2>
      <el-radio v-model="radio">Radio 1</el-radio>
      <el-radio v-model="radio" label="2">Radio 2</el-radio>
      <el-radio v-model="radio" label="3">Radio 3</el-radio>



    <el-switch
        v-model="value"
        active-color="#13ce66"
        inactive-color="#ff4949">
    </el-switch>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        inputValue:"你好",
        value:"value",
        radio: '1'
      }
    },
    name: 'UIComponentsPreview'
  }
  </script>
  
  <style>
  
  h2 {
    font-size: 20px;
    margin-bottom: 10px;
  }
  
  </style>
