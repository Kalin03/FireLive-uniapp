# FireLive-uniapp

一款建议饮食摄入、运动消耗及监控记录的微信小程序，其主要功能在于对用户的身高体重以及综合信息进行各方面建议。小程序主要分为三个页面，<u>*首页*</u>是对用户的摄入、消耗的记录以及反馈，另外附加一个打卡功能；<u>*社区*</u>页面，用于用户的分享，里面有评论区、收藏、搜索、排行榜等诸多功能；<u>*我的*</u>页面是对用户的信息进行综合记录，健康记录包含用户摄入、消耗及打卡数据，以及用户体重变化的BMI值，还有对用户数据的周报分析。

## 项目目录结构

```txt
FireLive-uniapp
│
├── BackEndCode
│   ├── health-uniapp
│   │   ├── ...
├── FrontEndCode
│   ├── font
│   │   ├── ...
│   ├── health-uniapp
│   │   ├── ...
├── MySql-sql
│   ├── ...
├── README.md
```

## 后端

本款小程序采用Java语言进行编写，并使用的springboot框架，其中涉及到AliOss、ChatGLM、微信APPID的相关配置，因此需要对配置文件进行单独处理，对于相关信息需要注册对应的开发者平台得到。AliOss用于对用户头像、社区上传的图片等进行存储；ChatGLM用于在用户登录、修改信息时对用户的健康指数进行Ai建议，对应”我的“页面的健康数据/健康分析页面；微信APPID用户微信一键登录的开发；

### 配置文件

```yml
# 微信小程序配置
APPID: 
APPSECRET: 

# ChatGLM apikey
API_SECRET_KEY: 

# AliOss配置
aliyun:
  oss:
    ENDPOINT: 
    ACCESS_KEY_ID: 
    ACCESS_KEY_SECRET: 
    BUCKET_NAME: 
```

### 启动

写好配置文件，把注意事项做了，启动HealthUniappApplication就ok了

### 注意事项

1、在health-uniapp\src\main\java\com\kalin\utils目录下ChatGLM.java文件里面需要单独将ChatGLM的API_SECRET_KEY写在变量上。

## 前端

小程序使用vue3编写 uniapp HBuilderX 开发
直接使用HBuilderX打开health-uniapp项目即可运行
小程序需要配置微信小程序AppID
```json
    /* 文件目录：health-uniapp\manifest.json */
       "mp-weixin" : {
        "appid" : "",
    }
```
小程序图表使用echarts工具开发

### 注意事项

1、小程序大小可能超出微信小程序开发的大小限制
  可将echarts依赖分离出主包
    echarts依赖目录：health-uniapp\uni_modules\lime-echart\static\echarts.min.js
  或将项目中的静态文件static上传至服务器

2、小程序在上传用户头像时使用前端上传至阿里oss服务器
  需要配置服务器信息
    文件目录：health-uniapp\subpkg\components\js_sdk\jason-alioss-upload\oss.js
```js
      // 下面这3个信息必填 阿里云对象服务器oss的密钥
      const url = ''
      const OSSAccessKeyId = ''
      const OssAccesskeySercet= ''
```
  或发送至后端上传

3、小程序首页使用“优设标题黑”字体，并在项目health-uniapp\App.vue中全局设置
  字体文件目录：FrontEndCode\font\YouSheBiaoTiHei-2.ttf
```css
    <style>
    /* 首页字体设置 */
      /* @font-face{
	        font-family: 'YouSheBiaoTiHei';
	        该路径为ttf文件的项目位置 可以上传到oss服务器远程下载
	        src: url('FireLive-uniapp/FrontEndCode/font/YouSheBiaoTiHei-2.ttf');
      } */
    </style>
      
```

## 数据库

在MySql-sql目录下有三个文件，health-uniapp.sql、food_table.sql、sport_table.sql，分别对应整个数据库的结构，食物信息，运动信息（用户可选的食物和运动）

## 页面展示

<img src="https://www.kalin.asia/ftp/firelive-img/1.jpg" alt="display" style="width: 24%;" /><img src="https://www.kalin.asia/ftp/firelive-img/2.jpg" alt="display" style="width:24%;" /><img src="https://www.kalin.asia/ftp/firelive-img/3.jpg" alt="display" style="width:24%;" /><img src="https://www.kalin.asia/ftp/firelive-img/4.jpg" alt="display" style="width:24%;" />

## 制作不易 感谢支持(记得备注)

<img src="https://www.kalin.asia/ftp/firelive-img/6.jpg" alt="display" style="width: 24%;" />

<img src="https://www.kalin.asia/ftp/firelive-img/7.jpg" alt="display" style="width: 24%;" />
