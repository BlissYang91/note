
## 陪伴项目前端设计

[配置更新](config_update.md)
[发布更新](changelog.md)

#### 安装淘宝镜像
 1. 以使用淘宝镜像如下 通过config命令
  npm config set registry https://registry.npm.taobao.org

2. npm info underscore

#### 安装cli
 1. npm install --global vue-cli
 2. 查看 vue 版本
 vue --version
 #### vue创建项目：
vue create 项目名称
或者：vue ui
 #### 运行vue项目
 1.  切换到目录  $ cd hello-vue
 2.  $ npm run serve 

 1. 切换到要创建项目的路径下，hello-vue 是项目名称，小写
 >vue create hello-vue 

 #### 升级vue
 1. npm uninstall -g vue-cli
 2. npm install -g @vue/cli

 ### 调试单个vue文件
 vue serve demo.vue

#### 安装webpack
1. npm install -g webpack
2. 查看webpack信息
npm info webpack
webpack -v

#### 1、安装Node环境

1. 请确保你的开发（test || uat || prod）环境中安装有 [Node.js](https://nodejs.org/zh-cn/) 环境，本项目依赖它构建；

2. 我们选择使用 [npm](https://www.npmjs.com/) 作为包管理工具，所以请安装 `npm` 到相关开发环境，由于一些众所周知的原因，请选择性的安装淘宝官方 `npm镜像`，[cnpm](http://npm.taobao.org/)；

#### 2、开始构建

1. 项目使用 [webpack](https://doc.webpack-china.org/) 作为打包工具，首先安装 `webpack`：
```sh
# 建议全局安装 webpack
npm install -g webpack
```

2. 我们使用 `vue-cli` 快速构建一个基于 `webpack` 的项目：
```sh
# 安装 vue-cli
npm install -g vue-cli
```

3. 生成项目：
```sh
# 在当前目录下创建我们的项目，假设目录为 dst/
vue init webpack dst

# 进入到项目
cd dst

# 安装项目依赖
cnpm install # 建议使用淘宝npm镜像安装依赖

# 启动项目
npm run dev

# 打包项目(一般在一个阶段工作完成，待交付时运行一次就行，日常开发不用频繁打包)
npm run build
```

4. 查看npm的全局路径
npm config get prefix

5. 查看全局安装了哪些工具
npm list --depth --global

6. npm 查看安装了哪些包
npm list --depth=0   
–depth 表示深度，我们使用的模块会有依赖，深度为零的时候，不会显示依赖模块

![image](https://note.youdao.com/yws/api/personal/file/FC21D29897FF426A9D4A04B30F4EF28C?method=download&shareKey=541f8364328ea359b40369cdce0e65de)

7. 下载sass支持
npm install node-sass sass-loader --save-dev

8. 安装epubjs
 npm install epubjs --save
