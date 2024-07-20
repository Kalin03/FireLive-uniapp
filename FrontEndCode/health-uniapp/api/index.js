import {loginStatusStore} from '@/stores/loginStatus.js';

const api = {
	//服务器地址
	domain: "https://localhost:8080",
	//domain: "服务器地址",
	//获得食物信息
	getFoodInfo: "/index/food",
	//提交食物摄入
	postFoodConsum: "/index/foodConsumption",
	//获得单日摄入
	getIntakeSum: "/index/intakeSum",
	//获得运动信息
	getSportInfo: "/index/sport",
	//提交运动消耗
	postSportConsum: "/index/sportConsumption",
	//获得单日消耗
	getConsumSum: "/index/consumeSum",
	//获得打卡信息
	postFindCheckin: "/index/findCheckIn",
	//提交打卡信息
	postCheckinInfo: "/index/checkIn",
	//获得展示食物信息
	getShowFoodInfo: "/index/showFood",
	//获得展示运动信息
	getShowSportInfo: "/index/showSport",
	//获得目标值
	getTarget: "/index/target",
	//运动记录
	sportConsumptionInfo: "/my/sportConsumptionInfo",
}

loginStatusStore().token;

// 获得食物信息
export const getFoodInfo = () => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.getFoodInfo,
			method: 'GET',
			header: {
				'Authorization': loginStatusStore().token,
			},
			success: function(res) {
				//  console.log(res);
				resolve(res.data.data);
			},
			fail: function(err) {
				reject(err);
			}
		});
	});
};

//提交食物摄入
export const postFoodConsum = (dataList) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.postFoodConsum,
			method: 'POST',
			header: {
				'Authorization': loginStatusStore().token,
			},
			data: dataList,
			success: function(res) {
				resolve();
			},
			fail: function(err) {
				console.log('请求失败', err);
			}
		})
	});
	
}

//获得单日摄入
export const getIntakeSum = () => {
	return new Promise((resolve, reject) => {
		uni.request({
			url:api.domain + api.getIntakeSum,
			method: 'GET',
			header: {
				'Authorization': loginStatusStore().token,
			},
			success:function(res){
				resolve(res.data.data);
			},
			fail:function(err){
				reject(err);
			}
		})
	})
}

//获得运动信息
export const getSportInfo = () => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.getSportInfo,
			method: 'GET',
			header: {
				'Authorization': loginStatusStore().token,
			},
			success: function(res) {
				//console.log('运动信息',res.data);
				resolve(res.data.data);
			},
			fail: function(err) {
				reject(err);
			}
		});
	});
}

//提交运动消耗
export const postSportConsum = (dataList) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.postSportConsum,
			method: 'POST',
			header: {
				'Authorization': loginStatusStore().token,
			},
			data: dataList,
			success: function(res) {
				//console.log('请求成功', res.data);
				resolve();
			},
			fail: function(err) {
				console.log('请求失败', err);
			}
		})
	});
	
}

//获得单日消耗
export const getConsumSum = () => {
	return new Promise((resolve, reject) => {
		uni.request({
			url:api.domain + api.getConsumSum,
			method: 'GET',
			header: {
				'Authorization': loginStatusStore().token,
			},
			success:function(res){
				//console.log('单日消耗',res.data.data);
				resolve(res.data.data);
			},
			fail:function(err){
				reject(err);
			}
		})
	})
}

//获得当前日期
const nowDate = () => {
	// 创建一个新的Date对象
	const now = new Date();
	
	// 获取年、月、日
	const year = now.getFullYear();
	const month = now.getMonth() + 1; // 月份从0开始，所以需要+1
	const day = now.getDate();
	
	// 将月份和日期转换为两位数
	const formattedMonth = month < 10 ? '0' + month : month.toString();
	const formattedDay = day < 10 ? '0' + day : day.toString();
	
	// 拼接成完整的日期字符串，格式为YYYY-MM-DD
	const currentDate = `${year}-${formattedMonth}-${formattedDay}`;
	
	// 返回当前日期
	return currentDate;
}

//获得打卡信息
export const postFindCheckin = () => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.postFindCheckin,
			method: 'POST',
			header: {
				'Authorization': loginStatusStore().token,
				'content-type': 'application/x-www-form-urlencoded' // post的param应该为x-www-form-urlencoded
			},
			data: {
				 'findTime': nowDate(),
			},
			success: function(res) {
				//console.log('请求成功', res.data);
				resolve(res.data.data);
			},
			fail: function(err) {
				reject(err);
			},
		});
	});
}

//提交打卡信息
export const postCheckinInfo = (dataList) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.postCheckinInfo,
			method: 'POST',
			header: {
				'Authorization': loginStatusStore().token,
			},
			data: dataList,
			success: function(res) {
				//console.log('请求成功', res.data);
				resolve(res.data.data);
			},
			fail: function(err) {
				reject(err);
			}
		});
	});
}

//获得展示食物信息
export const getShowFoodInfo = () => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.getShowFoodInfo,
			method: 'GET',
			success: function(res) {
				//console.log('请求成功', res.data);
				resolve(res.data.data);
			},
			fail: function(err) {
				reject(err);
			}
		});
	});
}
//获得展示运动信息
export const getShowSportInfo = () => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.getShowSportInfo,
			method: 'GET',
			success: function(res) {
				//console.log('请求成功', res.data);
				resolve(res.data.data);
			},
			fail: function(err) {
				reject(err);
			}
		});
	});
}

//获得目标值
export const getTarget = () => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.getTarget,
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

//获得运动记录
export const sportConsumptionInfo = () => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: api.domain + api.sportConsumptionInfo,
			method: 'POST',
			header: {
				'Authorization': loginStatusStore().token,
				'content-type': 'application/x-www-form-urlencoded'
			},
			data: {
				 'findTime': nowDate(),
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