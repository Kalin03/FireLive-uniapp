const api = {
	//服务器地址
	domain: "https://localhost:8080",
	//domain: "服务器地址",
	//获取文章列表
	getArticleList: "/community/loadCategory",
	//查询文章详情
	findArticleDetails: "/community/getArticleInfo",
	//查询文章作者信息
	findAuthorDate: "/community/getArticleAuthor",
	//发布文章
	sendArticle: "/community/uploadArticle",
	//修改文章
	updateArticle: "/community/updateArticle",
	//删除文章
	delArticle: "/community/delArticle",
	//添加收藏
	addCollect: "/community/addCollect",
	//判断用户是否收藏文章
	isCollect: "/community/isCollect",
	//取消收藏
	cancelCollect: "/community/cancelCollect",
	//点赞文章
	addLike: "/community/addLike",
	//取消点赞
	cancelLike: "/community/cancelLike",
	//搜索文章
	searchArticle: "/community/searchArticle",
	//排行榜
	articleTop: "/community/articleTop",
	//评论文章
	commentArticle: "/community/fatherComment",
	//回复评论
	replyComment: "/community/childComment",
	//查询文章评论
	findArticleComment: "/community/findArticleComment",
	//删除评论
	delComment: "/community/delComment",
	//上传文件
	uploadFile: "/uploadFile"
}

//获取文章列表
export const getArticleList = (pageNum, pageSize) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.getArticleList}`,
			data: {
				pageNum: pageNum,
				pageSize: pageSize
			},
			method: 'GET',
			header: {
				'Authorization': token
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//查询文章详情
export const findArticleDetails = (articleId) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.findArticleDetails}`,
			data: {
				articleId: articleId
			},
			method: 'POST',
			header: {
				'Authorization': token,
				'content-type': 'application/x-www-form-urlencoded' // post的param应该为x-www-form-urlencoded
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}

			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//查询文章作者信息
export const findAuthorDate = (openid) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.findAuthorDate}`,
			data: {
				openid: openid
			},
			method: 'POST',
			header: {
				'Authorization': token,
				'content-type': 'application/x-www-form-urlencoded' // post的param应该为x-www-form-urlencoded
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//发布文章
export const sendArticle = (ArticleData) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.sendArticle}`,
			data: {
				articleImage: ArticleData.articleImage,
				articleTitle: ArticleData.articleTitle,
				articleContent: ArticleData.articleContent
			},
			method: 'POST',
			header: {
				'Content-Type': 'application/json',
				'Authorization': token
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//修改文章
export const updateArticle = (ArticleData) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.updateArticle}`,
			data: {
				articleId: ArticleData.articleId,
				articleImage: ArticleData.articleImage,
				articleTitle: ArticleData.articleTitle,
				articleContent: ArticleData.articleContent
			},
			method: 'POST',
			header: {
				'Content-Type': 'application/json',
				'Authorization': token
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//删除文章
export const delArticle = (articleId) => {
	const token = uni.getStorageSync('token');
	console.log(articleId);
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.delArticle}`,
			data: {
				articleId: articleId
			},
			method: 'DELETE',
			header: {
				'Authorization': token,
				'content-type': 'application/x-www-form-urlencoded' // post的param应该为x-www-form-urlencoded
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//添加收藏
export const addCollect = (articleId) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.addCollect}`,
			data: {
				articleId: articleId
			},
			method: 'POST',
			header: {
				'Authorization': token,
				'content-type': 'application/x-www-form-urlencoded' // post的param应该为x-www-form-urlencoded
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//取消收藏
export const cancelCollect = (articleId) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.cancelCollect}`,
			data: {
				articleId: articleId
			},
			method: 'DELETE',
			header: {
				'Authorization': token,
				'content-type': 'application/x-www-form-urlencoded' // post的param应该为x-www-form-urlencoded
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//判断用户是否收藏文章
export const isCollect = (articleId, openid) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.isCollect}`,
			data: {
				articleId: articleId,
				openid: openid
			},
			method: 'POST',
			header: {
				'Authorization': token,
				'content-type': 'application/x-www-form-urlencoded' // post的param应该为x-www-form-urlencoded
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//点赞文章
export const addLike = (articleId) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.addLike}`,
			data: {
				articleId: articleId
			},
			method: 'POST',
			header: {
				'Authorization': token,
				'content-type': 'application/x-www-form-urlencoded' // post的param应该为x-www-form-urlencoded
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//取消点赞
export const cancelLike = (articleId) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.cancelLike}`,
			data: {
				articleId: articleId
			},
			method: 'POST',
			header: {
				'Authorization': token,
				'content-type': 'application/x-www-form-urlencoded' // post的param应该为x-www-form-urlencoded
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//搜索文章
export const searchArticle = (searchStr, pageNum, pageSize) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.searchArticle}`,
			data: {
				searchStr: searchStr,
				pageNum: pageNum,
				pageSize: pageSize
			},
			method: 'GET',
			header: {
				'Authorization': token,
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
					console.log(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//排行榜
export const articleTop = () => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.articleTop}`,
			method: 'POST',
			header: {
				'Authorization': token
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//评论文章
export const commentArticle = (comment) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.commentArticle}`,
			data: comment,
			method: 'POST',
			header: {
				'Content-Type': 'application/json',
				'Authorization': token
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//回复评论
export const replyComment = (comment) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.replyComment}`,
			data: comment,
			method: 'POST',
			header: {
				'Content-Type': 'application/json',
				'Authorization': token
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//查询文章评论
export const findArticleComment = (articleId) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.findArticleComment}`,
			data: {
				articleId: articleId
			},
			method: 'POST',
			header: {
				'Authorization': token,
				'content-type': 'application/x-www-form-urlencoded' // post的param应该为x-www-form-urlencoded
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

//删除评论
export const delComment = (recordId) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.request({
			url: `${api.domain}${api.delComment}`,
			data: {
				recordId: recordId
			},
			method: 'DELETE',
			header: {
				'Authorization': token,
				'content-type': 'application/x-www-form-urlencoded' // post的param应该为x-www-form-urlencoded
			},
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 0) {
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};

// 上传文件
export const uploadFile = (file) => {
	const token = uni.getStorageSync('token');
	return new Promise((resolve, reject) => {
		uni.uploadFile({
			url: `${api.domain}${api.uploadFile}`,
			filePath: file,
			name: 'file',
			timeout: 120000,
			header: {
				'Authorization': token
			},
			success: (res) => {
				if (res.statusCode === 200) {
					console.log(res);
					resolve(res);
				} else if (res.statusCode == 401) {

				} else {
					reject(new Error('请求失败:', res));
				}
			},
			fail: (err) => {
				console.log(err);
				reject(new Error('网络请求失败:', err));
			}
		});
	});
};