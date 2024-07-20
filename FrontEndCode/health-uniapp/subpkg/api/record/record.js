const api = {
	//服务器地址
	domain: "https://localhost:8080",
	//domain: "服务器地址",
	//饮食记录
	foodConsumptionInfo: "/my/foodConsumptionInfo",
	//运动记录
	sportConsumptionInfo: "/my/sportConsumptionInfo",
	//打卡记录
	checkInRecord: "/my/checkInRecord",
	//BMI记录
	getUserBmi: "/my/getUserBmi",
}

import {loginStatusStore} from '@/stores/loginStatus.js';

//获得饮食记录
export const foodConsumptionInfo = (date) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.foodConsumptionInfo,
			method: 'POST',
			header: {
				'Authorization': loginStatusStore().token,
				'content-type': 'application/x-www-form-urlencoded'
			},
			data: {
				 'findTime': date,
			},
			success: function(res) {
				resolve(res.data.data);
			},
			fail: function(err) {
				reject(err);
			}
		});
	});
}

//获得运动记录
export const sportConsumptionInfo = (date) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.sportConsumptionInfo,
			method: 'POST',
			header: {
				'Authorization': loginStatusStore().token,
				'content-type': 'application/x-www-form-urlencoded'
			},
			data: {
				 'findTime': date,
			},
			success: function(res) {
				resolve(res.data.data);
			},
			fail: function(err) {
				reject(err);
			}
		});
	});
}

//打卡记录
export const checkInRecord = () => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.checkInRecord,
			method: 'GET',
			header: {
				'Authorization': loginStatusStore().token,
			},
			success: function(res) {
				resolve(res.data.data);
			},
			fail: function(err) {
				reject(err);
			}
		});
	});
}

//BMI记录
export const getUserBmi = () => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.getUserBmi,
			method: 'GET',
			header: {
				'Authorization': loginStatusStore().token,
			},
			success: function(res) {
				resolve(res.data.data);
			},
			fail: function(err) {
				reject(err);
			}
		});
	});
}