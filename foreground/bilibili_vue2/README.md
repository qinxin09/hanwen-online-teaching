# bilibili_vue2

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```
# 安装videoplayer
npm install videojs2-vue --save



### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

在前后端分离的开发模式下，前端用户登录成功后后端服务会给用户颁发一个jwt token。前端(如vue)在接收到jwt token后会将token存储到LocalStorage中。

后续每次请求都会将此token放在请求头中传递到后端服务，后端服务会有一个过滤器对token进行拦截校验，校验token是否过期，如果token过期则会让前端跳转到登录页面重新登录。
————————————————
版权声明：本文为CSDN博主「程序员小明1024」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/china_coding/article/details/127080766
# 测试
安装uuid
npm install uuid --save  

项目开发的问题

测试：完成后（功能性）/开发过程中（微型项目，小项目）
    分类：
    黑盒测试：不考虑内部结构和处理过程，进行功能测试
    白盒测试: （结构测试、透明测试）根据内部结构，
    单体测试：描述整体的一个单体功能：例如：提交一个表单（学生录入）
    集成（联合）测试：测试一个模块对其它模块影响
    压力测试：内存、cpu、磁盘空间、网络带宽
TPS、QPS   
工具：
postman------接口（1次，数据：json/xml/....）
jmeter(主要是做压力测试的) --------- 接口（多次，多线程，吞吐量，协议）    
共同点：先建立测试的接口
2）项目安全：
wireshark协议 
测试流程：前端



# git:
> 使用gitee托管项目源代码

终端进入当前项目路径下，

> 1 `git init` 
>
> 初始化一个新的Git仓库，将当前目录或指定的目录转换为Git仓库，并创建相应的`.git`目录。`.git`目录是Git存储仓库中所有版本控制数据的核心，包括历史提交记录、分支和标签等信息。



> 2 关联远程仓库
>
> #当前（项目）路径 关联到 我指定的 远程仓库`bilibili_vue2`
> #`origin`为我的远程仓库别名
>
> ```bash
> git remote add origin https://gitee.com/qx09/bilibili_vue2.git
> ```
>
> 查看当前路径已关联的远程仓库信息
>
> `git remote -v`
>
> ```bash
> git remote -v
> # 输出一下内容，若无关联则无输出;
> origin  https://gitee.com/qx09/bilibili_vue2.git (fetch)
> origin  https://gitee.com/qx09/bilibili_vue2.git (push)
> ```
>
> 解除本地关联的远程仓库（假设创建的远程仓库的别名是origin）
>
> ```bash
> git remote remove origin
> ```
>
> 
>
> #创建一个新的分支：
> git checkout -b <branch_name>
> 其中，<branch_name>为要创建的分支名称。例如，要创建名为foreground的分支，可以执行以下命令：
>
> ```bash
> git checkout -b foreground
> 输出
> Switched to a new branch 'foreground'
> ```
>
> 这个命令会创建一个新的分支，并将你的本地仓库切换到这个新分支上。



`git add .` =>添加所有文件到暂存区

`git reset` ==>清空缓存(暂存区)

`git status` =>查看状态（删除、更新

`git diff --cached `查看暂存区的缓存？ 如果不小心浏览文件，退不出来，按下`q`键能退出

`git commit -m 备注` =>用于将暂存区（stage或index）中的所有修改保存到本地仓库的历史记录中。每次执行提交操作，Git都会创建一个新的提交对象，保存当前暂存区中的所有修改，并记录相关的提交信息（如提交作者、时间戳、提交注释等）。提交操作后，这些修改就成为了仓库中的永久历史记录，可以随时被查看、回滚、比较和重用。

`git push ` ==>将当前分支上的代码推送到远程仓库中。当你在本地修改了代码并做了提交（commit）之后，执行这个命令就可以将修改后的代码推送到远程仓库中。如果使用这个命令时未指定分支名称，Git 会默认使用当前所在的分支，例如：

`git push -u origin <branch_name>`：将指定分支上的代码推送到远程仓库中，并建立本地分支与远程分支的关联。





`添加文件到暂存区`

git add `filename` 

git add `filePath`

`添加当前路径下的所有文件到暂存区`

子管理员、
页面、推荐、
后端管理，后台。
目录
 管理


# 答辩准备：

## 1 创新点

1.前端使用Vue2作为主要框架，它具有响应式的特性、组件化的开发模式、便于维护和扩展等优点。

2.前端数据请求采用axios，它是一个基于Promise的HTTP库，可以用于浏览器和Node.js，它使用了异步的方式来处理数据请求和响应，让页面加载和数据请求变得更加高效。

3.前端界面使用了ElementUI组件库，它提供了一套美观实用的界面组件，可以实现一系列复杂的布局和功能。

4.后端使用SpringBoot框架，它是一个快速开发框架，可以便捷地创建独立的、基于Spring的应用程序。

5.后端使用JWT实现了用户认证和权限控制，JWT是一个安全的身份验证机制，在信息传递的过程中，不需要保存状态，也不需要在服务端存储session信息。

## 2 技术选型

关于技术选型，前端使用了Vue2、ElementUI等技术，这些技术有着响应式的特性、组件化的开发模式、便于维护和扩展等优点，并且有着广泛的社区支持和经验分享，在实现前端功能和用户界面方面有非常好的表现。

后端采用了SpringBoot、JWT等技术，SpringBoot是基于Spring框架的快速开发框架，可以快速构建Spring应用程序，在提高开发效率的同时，也给应用程序提供了更好的配置和管理能力。JWT是一种安全的身份验证机制，在用户认证和权限控制方面表现良好，同时在信息传递的过程中也不需要保存状态，方便应用程序的扩展和集成。

同时，前后端数据传输采用的是RESTful风格，可以灵活地处理不同类型的请求，JSON格式数据的传输也方便在不同系统之间进行数据交互。在数据库方面，本项目使用了MySQL数据库，并使用了JPA进行数据访问。



## 3 实现的功能

1. 用户登录、注册：支持用户注册和登录功能，用户可以使用已注册的账号登录到系统，或者通过注册页面注册新账号。

2. 个人信息管理：用户可以在个人中心页面上传头像、修改个人信息（姓名、年龄）、修改密码。

3. 视频上传：用户可以在自己创建的合集中上传视频文件，支持视频描述、合集封面的上传。

4. 视频播放：用户可以在首页中，点击合集封面可以进入播放页面，支持视频播放、选集、点赞、收藏、评论这些功能。

5. 后台管理：管理员登录后可以进入后台管理界面，浏览用户列表，并可以对用户的账号进行封号和解封操作，还可以浏览或删除合集、管理用户评论。

6. 其他：本项目还实现了合集的创建、合集页面的展示，包括合集封面、简介以及其中视频的展示。另外还实现了用户的获赞、收藏、关注、粉丝的显示等功能。

## 4 知识点

- vue

  - 生命周期函数
  - v-model 、v-if 、v-show、v-for
  - 全局事件总线  
    - main.js 绑定 Vue.prototype.$bus = this
    - 绑定自定义事件：this.$bus.$on(事件名，调用函数)
    - 触发自定义事件：this.$bus.$emmit(事件名)
  - message
    - this.$message.success()
    - this.$message.warning()
    - this.$message.error()
  - prop 传值
  - component 组件
  - router 
  - ElementUI

- axios

  - axios.post(url,data,{header:header})
  - axios.get(url,{header})
  - 同步 async function(){ await axios.post()}
  - 拦截器

- springboot

- 逆向工程

- jwt

  - 全局过滤器，拦截所有请求，进行token验证

  