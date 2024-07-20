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
│   ├── health
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

### 注意事项

1、在health-uniapp\src\main\java\com\kalin\utils目录下ChatGLM.java文件里面需要单独将ChatGLM的API_SECRET_KEY写在变量上。
