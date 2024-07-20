const api = {
	//服务器地址
	domain: "https://localhost:8080",
	//domain: "服务器地址",
	//提交反馈
	sendFeedback: "/my/sendFeedback",
}

import {loginStatusStore} from '@/stores/loginStatus.js';

//获得饮食记录
export const sendFeedback = (date) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.sendFeedback,
			method: 'POST',
			header: {
				'Authorization': loginStatusStore().token,
				'content-type': 'application/x-www-form-urlencoded'
			},
			data: {
				 'feedbackContent': date,
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