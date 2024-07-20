const api = {
	//服务器地址
	domain: "https://localhost:8080",
	//domain: "服务器地址",
	//食物周报
	weekFood: "/my/weekFood",
	//运动周报
	weekSport: "/my/weekSport",
}

import {loginStatusStore} from '@/stores/loginStatus.js';

//获得食物周报
export const weekFood = (startTime,endTime) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.weekFood,
			method: 'POST',
			header: {
				'Authorization': loginStatusStore().token,
				'content-type': 'application/x-www-form-urlencoded'
			},
			data: {
				 'startTime': startTime,
				 'endTime': endTime
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

//获得运动周报
export const weekSport = (startTime,endTime) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.weekSport,
			method: 'POST',
			header: {
				'Authorization': loginStatusStore().token,
				'content-type': 'application/x-www-form-urlencoded'
			},
			data: {
				 'startTime': startTime,
				 'endTime': endTime
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