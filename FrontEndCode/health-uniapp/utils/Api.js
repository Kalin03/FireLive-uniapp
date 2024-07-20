const api = {
    //接口域名
    domain: "https://www.kalin.asia:8080",//填自己电脑的ip地址，只有通过自己的ip才能访问外部部署服务
    //图片域名
    imageUrl: "/api/file/getImage/",
    //验证码
    checkCode: "/account/Code",
    //注册
    register: "/account/login",
    //微信自动登录
    autoLogin: "/account/autoLogin",
    //登录
    login: "/account/login",
    //提交打卡
    checkIn: "/index/checkIn",
    //获取打卡记录
    checkInRecord: "/user/checkInRecord",
    //社区分类
    loadAllCategory: "/community/loadAllCategory",
    //分享
    loadShareInfo: "/share/loadShareList",
    //获取文章列表
    loadCategory:"/community/loadCategory",
    //查询文章详情
    getArticleInfo: "/community/getArticleInfo",
    //根据文章id查询作者
    getArticleAuthor: "/community/getArticleAuthor",
    //获取评论
    comment: "/community/comment",
    //收藏
    addCollect: "/community/addCollect",
    //取消收藏
    cancelCollect: "/community/cancelCollect",
    //喜欢
    addLike:"/community/addLike",
    //取消喜欢
    cancelLike:"/community/cancelLike",
    //搜索
    searchArticle: "/community/searchArticle",
    //前十排行榜
    articleTop: "/community/articleTop",
    //发布文章
    uploadArticle:"/community/uploadArticle",
    //评论文章(父评论)
    fatherComment: "/community/fatherComment",
    //回复他人评论（子评论）
    childComment: "/community/childComment",
    //根据文章id查询所有评论
    findArticleComment: "/community/findArticleComment",
    //删除评论
    delComment: "/community/delComment",
    //上传食品
    getFood: "/index/getFood",
    //上传打卡
    getPunchCard :"/index/getPunchCard",
    //上传头像
    uploadAvatar: "/api/my/uploadAvatar",
    //更新用户信息
    updateUserInfo: "/my/updateAvatar",
    //获取用户信息
    getUserInfo: "/my/getUserInfo",
    //我的收藏
    myCollect: "/my/loadCollect",
    //收藏详情
    getCollectNext: "/my/getCollectNext",
    //问题反馈
    loadFeedback: "/my/loadFeedback",
    //查询回复
    loadFeedbackReply:"/my/loadFeedbackReply",
    //发送问题
    sendFeedback: "/my/sendFeedback",
    //检查更新
    checkUpdate: "/update/checkVersion",
    //下载
    downloadApp: "/api/update/download",

}
export default api;

