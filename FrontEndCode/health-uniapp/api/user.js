import {loginStatusStore} from '@/stores/loginStatus.js';

const api = {
	//服务器地址
	domain: "https://localhost:8080",
	//domain: "服务器地址",
	//微信一键登录
	WXLogin: '/my/login',
	//获取用户信息
	getUserInfo: '/my/getUserInfo',
	//更新用户信息
	updateUserInfo: '/my/updateUserInfo',
	// 获取食物记录
	getFoodRecord: '/my/foodConsumptionInfo'
}
//const token = uni.getStorageSync('token');
//获得用户信息
export const getUserInfo = () => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.getUserInfo,
			method: 'GET',
			header: {
				'Authorization': uni.getStorageSync('token'),
			},
			success: function(res) {
				//console.log(res);
				resolve(res);
			},
			fail: function(err) {
				reject(err);
			}
		});
	});
}
//更新用户信息
export const updateUserInfo = (dataList) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.updateUserInfo,
			method: 'PUT',
			header: {
				'Authorization': uni.getStorageSync('token'),
			},
			data: dataList,
			success: function(res) {
				//  console.log(res.data);
				resolve(res);
			},
			fail: function(err) {
				reject(err);
			}
		});
	});
}