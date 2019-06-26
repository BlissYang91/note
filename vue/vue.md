
## 陪伴项目前端设计

[配置更新](config_update.md)
[发布更新](changelog.md)

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

![image](D:文档/vue.png)