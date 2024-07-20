import {loginStatusStore} from '@/stores/loginStatus.js';
import {getUserInfo} from '@/api/user.js';
import {useUserInfoStore} from '@/stores/userinfo.js'
	
const loginStatus = loginStatusStore();
const userInfo = useUserInfoStore();

//判断是否存在token token是否过期
export const isToken = () => {
	return new Promise((resolve, reject) => {
		const token = uni.getStorageSync('token');
		if(token){
			getUserInfo()
				.then(response => {
					//如果token过期 登出
					if(response.statusCode == 401 && response.data.code == 0){
						resolve(false);
						//提示登录信息过期
						uni.showModal({
							title: '登录信息过期',
							content: '是否重新登录',
							showCancel: true,
							cancelText: '取消',
							confirmText: '确定',
							success: function(res) {
								if (res.confirm) {
									// 用户点击了确定按钮
									uni.switchTab({
										url: "/pages/user/user",
									});
								} else if (res.cancel) {
									// 用户点击了取消按钮
									reject(new Error('用户取消了登录'));
								}
							}
						})
						//登出
						uni.removeStorageSync('token');
						loginStatus.loginStatus = false;
						loginStatus.token = "";
					}else {
						//获取用户信息 更新登录状态
						userInfo.setUserInfo(response.data.data);
						loginStatus.loginStatus = true;
						loginStatus.token = uni.getStorageSync('token');
						resolve(loginStatus.loginStatus);
					}
				})
				.catch(error => {
					console.error('当前请求用户信息失败：', error);
				});
		}else {
			//没有token 没有登录
			loginStatus.loginStatus = false;
			loginStatus.token = "";
			resolve(loginStatus.loginStatus);
		}
	});
}

// 返回一个 Promise 对象，根据登录状态解决或拒绝该 Promise
export const isLogin = () => {
	return new Promise((resolve, reject) => {
		if (loginStatus.loginStatus) {
			// 如果已登录，解决 Promise
			resolve(true);
		} else {
			resolve(false);
			uni.showModal({
				title: '未登陆',
				content: '使用此功能请先登陆',
				showCancel: true,
				cancelText: '取消',
				confirmText: '跳转登陆',
				success: function(res) {
					if (res.confirm) {
						// 用户点击了确定按钮
						uni.switchTab({
							url: "/pages/user/user",
						});
						// 登录后解决 Promise
						resolve();
					} else if (res.cancel) {
						// 用户点击了取消按钮，拒绝 Promise
						reject(new Error('用户取消了登录'));
					}
				}
			});
		}
	});
}

//微信登录
export const WXLogin = () => {
	uni.login({
		"provider": "weixin",
		//"onlyAuthorize": true, // 微信登录仅请求授权认证
		success: function(event){
			const {code} = event
			//客户端成功获取授权临时票据（code）,向业务服务器发起登录请求。
			uni.request({
				url: 'https://localhost:8080/my/login', 
			    //url: '服务器地址', 
					method: 'POST',
			    data: {
			        code: event.code,
			    },
			    success: (res) => {
			        //获得token完成登录
			    	uni.setStorageSync('token',res.header.Authorization);
			    	if(res.data.data.weight === 0 && res.data.data.height === 0){
			    		userInfo.setUserInfo(res.data.data);
			    		uni.navigateTo({
			    			url: '/subpkg/user_info/user_info',
			    		})
			    		
			    	}else {
			    		userInfo.setUserInfo(res.data.data);
			    	}
			    	loginStatus.loginStatus = true;
			    	loginStatus.token = uni.getStorageSync('token');
			    },
			    fail: (res) => {
			    	console.log("4",res);
			    }
			});
		},
		fail: function (err) {
	        // 登录授权失败
	        // err.code是错误码
	    }
	})
}

//退出登录
export const WXlogout = () => {
	uni.showModal({
		title: '提示',
		content: '是否退出登录',
		showCancel: true, // 是否显示取消按钮
		cancelText: '取消', // 取消按钮的文字，showCancel 为 true 时有效
		confirmText: '确定', // 确定按钮的文字
		success: function (res) {
			if (res.confirm) {
			// 用户点击了确定按钮
			//console.log('用户点击了确定按钮');
			uni.removeStorageSync('token');	
			loginStatus.loginStatus = false;
			loginStatus.token = "";
			} else if (res.cancel) {
			// 用户点击了取消按钮
			//console.log('用户点击了取消按钮');
			}
		}
	});
}