const { defineConfig } =  require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,

  // 代理
  // devServer: {
  //   proxy: {
  //     '/crawler': {
  //       target: 'https://f7.baidu.com',
  //       //把以/atguigu开头的替换成空字符串
  //       pathRewrite:{"^/crawler":""},
  //       ws:true,//支持websocket  默认为true
  //       changeOrigin:true//默认为真（改变本地端口号为目标服务器端口号），用于控制请求头中的host值
  //     }
  //   }
  // },
  // 忽略报错
  devServer: {
    client: {
      overlay: false
    }
  }
})
