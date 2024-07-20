<template>
	<Navbar :showLeft="true" title="文章详情"></Navbar>
	<view class="Details-box">
		<!-- 文章图片 -->
		<view class="Details-box-img">
			<image :src="article.articleImage" mode="widthFix"></image>
		</view>
		<!-- 作者信息 -->
		<view class="Details-box-user">
			<image :src="userData.avatar" mode="aspectFill"
				style="height: 60rpx;width: 60rpx;border-radius: 50%;object-fit: cover;">
			</image>
			<text style="font-size: 25rpx;margin-left: 10rpx;">{{ userData.nickName }}</text>
		</view>
		<!-- 文章内容 -->
		<view class="Details-box-title">
			<view class="Details-box-title-view">
				<text>{{article.articleTitle}}</text>
			</view>
			<view class="Details-box-title-view">
				<text style="font-size: 26rpx;">{{article.articleContent}}</text>
			</view>
			<view class="Details-box-title-view">
				<text style="font-size: 22rpx;">{{article.createTime.replace('T', " ")}}
					重庆</text>
				<view v-if="article.openid === userInfo.openid" style="" @click="DelArticle">
					<image style="width: 25rpx;height: 25rpx;margin-left: 10rpx;" src="../../static/icon/del.svg"
						mode="aspectFill">
					</image>
				</view>
			</view>
		</view>
		<!-- 评论区 -->
		<view class="Details-box-comment">
			<text>评论区</text>
			<view class="comment-section">
				<!-- 递归渲染评论列表 -->
				<template v-for="comment in commentList" :key="comment.recordId">
					<view class="comment-thread">
						<view class="comment">
							<view class="comment-body">
								<!-- 显示评论者的头像和名字 -->
								<view v-if="comment.userData" class="comment-author">
									<image :src="comment.userData.avatar" alt="Avatar" class="comment-avatar"
										mode="aspectFill" />
									<view class="comment-content">
										<!-- 显示评论内容 -->
										<!-- 上半区 -->
										<view>
											<text class="comment-nickname">{{ comment.userData.nickName }}</text>
										</view>
										<!-- 下半区 -->
										<view style="margin-left: 10rpx;margin-top: 5rpx;">
											<text>{{ comment.commentContent }}</text>
											<view>
												<view style="display: inline-flex;" @click="showPopup(comment)">
													<text style="font-size: 24rpx;">回复</text>
												</view>
												<view v-if="comment.userData.openid === userInfo.openid"
													style="display: inline-flex;" @click="DelComment(comment.recordId)">
													<text style="font-size: 24rpx;margin-left: 10rpx;">删除</text>
												</view>
											</view>
										</view>
									</view>
								</view>
							</view>
							<!-- 如果评论有子评论，递归地渲染子评论 -->
							<template v-if="comment.childCommentList">
								<view v-for="child in comment.childCommentList" :key="child.recordId"
									class="comment-child">
									<!-- 显示子评论者的头像和名字 -->
									<view v-if="child.userData" class="comment-author">
										<image :src="child.userData.avatar" alt="Avatar" class="comment-avatar"
											mode="aspectFill" />
										<!-- 显示子评论内容 -->
										<view class="comment-content">
											<!-- 上半区 -->
											<view style="display: flex;flex-direction: row;align-items: center;">
												<text class="comment-nickname">{{ child.userData.nickName }}</text>
												<!-- 显示子评论的回复信息 -->
												<view v-if="child.toUserData" class="reply-info">
													<uni-icons type="right" size="30rpx" color="#aaa9a9"></uni-icons>
													<text class="reply-nickname">{{ child.toUserData.nickName }}</text>
												</view>
											</view>
											<!-- 下半区 -->
											<view style="margin-left: 10rpx;margin-top: 5rpx;">
												<text>{{ child.commentContent }}</text>
												<view>
													<view style="display: inline-flex;" @click="showPopup(child);">
														<text style="font-size: 24rpx;">回复</text>
													</view>
													<view v-if="child.userData.openid === userInfo.openid"
														style="display: inline-flex;"
														@click="DelComment(child.recordId)">
														<text style="font-size: 24rpx;margin-left: 10rpx;">删除</text>
													</view>
												</view>
											</view>
										</view>
									</view>
								</view>
							</template>
						</view>
					</view>
				</template>
			</view>
		</view>
		<!-- 底部状态栏 -->
		<view class="Details-box-createComment">
			<view class="Details-box-input">
				<view style="display: flex;flex-direction: row;align-items: center;" @click="showPopup('')">
					<uni-icons type="compose" size="40rpx" style="margin-left: 15rpx;" color="#000"></uni-icons>
					<text style="font-size: 24rpx;font-weight: bold;">说点什么吧...</text>
				</view>
				<!-- popup -->
				<uni-popup ref="popup" type="bottom">
					<view class="popup-content">
						<view style="margin-left: 20rpx;">
							<input :focus="isFocus" class="popup-content-input" :placeholder="toUserName"
								v-model="inputValue" />
						</view>
						<view class="popup-content-sendBottom" @click="sending">
							发送
						</view>
					</view>
				</uni-popup>
			</view>
			<view class="Details-box-flexRow" style="margin-right: 30rpx;">
				<view class="Details-box-flexRow">
					<uni-icons type="chat" size="60rpx" color="#000000"></uni-icons>
					<text>{{ article.commentsNumber }}</text>
				</view>
				<view class="Details-box-flexRow" @click="throttleCollectArticle">
					<view v-if="collect">
						<uni-icons type="star-filled" size="60rpx" color="#ffaa00"></uni-icons>
					</view>
					<view v-else>
						<uni-icons type="star" size="60rpx" color="#000"></uni-icons>
					</view>
					<text>{{ article.collectNumber }}</text>
				</view>
				<view class="Details-box-flexRow" @click="throttleLikeAricle">
					<view v-if="isLike">
						<uni-icons type="heart-filled" size="60rpx" color="#E5404F"></uni-icons>
					</view>
					<view v-else>
						<uni-icons type="heart" size="60rpx" color="#000000"></uni-icons>
					</view>
					<text>{{ article.likesNumber }}</text>
				</view>
			</view>
		</view>
		<!-- 底部空白 -->
		<view
			style="height: 300rpx;width: 100%;border-top: 2rpx solid #eee;border-bottom: 2rpx solid #eee;text-align: center;">
			<view style="margin-top: 70rpx;">
				<text style="color: #a7a7a7;">-THE END-</text>
			</view>
		</view>
	</view>
</template>

<script setup>
	import {
		onLoad
	} from '@dcloudio/uni-app'
	import {
		ref,
		onMounted,
		watch,
		onBeforeMount
	} from 'vue'
	import {
		findArticleDetails,
		findAuthorDate,
		findArticleComment,
		commentArticle,
		replyComment,
		isCollect,
		addCollect,
		cancelCollect,
		addLike,
		cancelLike,
		delComment,
		delArticle
	} from '@/api/community.js'
	import {
		useUserInfoStore
	} from "@/stores/userinfo.js";

	//初始化数据

	//当前登录用户信息
	const userInfo = useUserInfoStore();

	//文章信息
	const article = ref({})

	//作者信息
	const userData = ref({})

	//上一个页面传递进来的参数
	//作者id
	const openid = ref("")
	//文章id
	const articleId = ref("")

	//评论区数据
	const commentList = ref([]);

	// 评论数据
	// 用户输入的数据
	const inputValue = ref('');
	// 评论数据模型
	const commentData = ref();
	// 评论的状态 root或者child
	const commentStatus = ref('root')

	// 评论输入框的状态 显示或者隐藏
	const popup = ref(null)

	// 控制输入框输入框获取焦点
	const isFocus = ref(false)

	// 回复的用户名字 输入框内容
	const toUserName = ref("请输入内容...")

	// 判断用户是否收藏本篇文章
	const collect = ref(false)

	// 判断用户是点赞
	const isLike = ref(false)

	// 定义函数来打开弹出层
	const showPopup = (comment) => {
		if (comment != '') {
			toUserName.value = "回复@" + comment.userData.nickName;
			commentData.value = comment;
			commentStatus.value = 'child';
		} else {
			toUserName.value = "请输入内容...";
			commentStatus.value = 'root';
		}
		isFocus.value = true;
		popup.value.open();
	};

	// 评论发送按钮
	const sending = async () => {
		console.log('用户点击确定，输入的内容是：' + inputValue.value);
		if (inputValue.value == '') {
			uni.showToast({
				title: '请输入评论',
				icon: 'none'
			});
		} else if (commentStatus.value === 'root') {
			const data = {
				articleId: articleId.value,
				//待修改这个应该是 登录用户的id
				openid: userInfo.openid,
				commentContent: inputValue.value
			};
			const result = await commentArticle(data);
			if (result.data.code === 0) {
				uni.showToast({
					title: '评论成功',
					icon: 'none'
				});
				fetchData();
			} else {
				uni.showToast({
					title: '评论失败',
					icon: 'none'
				});
			}
		} else if (commentStatus.value === 'child') {
			let rootId = 0;
			if (commentData.value.rootId == null) {
				rootId = commentData.value.recordId;
			} else {
				rootId = commentData.value.rootId;
			}
			const data = {
				articleId: articleId.value,
				//待修改这个应该是 登录用户的id
				openid: userInfo.openid,
				toUserId: commentData.value.userData.openid,
				rootId: rootId,
				commentContent: inputValue.value
			};
			const result = await replyComment(data);
			if (result.data.code === 0) {
				uni.showToast({
					title: '回复成功',
					icon: 'none'
				});
				fetchData();
			} else {
				uni.showToast({
					title: '评论失败',
					icon: 'none'
				});
			}
		}
		inputValue.value = "";
		isFocus.value = false;
		popup.value.close();
	};

	//删除评论
	const DelComment = async (recordId) => {
		uni.showModal({
			title: '温馨提示',
			content: '确定删除此评论？',
			success: async function(res) {
				if (res.confirm) {
					try {
						// 将 await delComment(recordId) 放入异步函数中
						const result = await delComment(recordId);
						if (result.data.code === 0) {
							uni.showToast({
								title: '删除成功',
								icon: 'none',
								duration: 1000
							});
							fetchData();
						}
					} catch (error) {
						// 这里可以处理错误
						uni.showToast({
							title: '删除失败',
							icon: 'none',
							duration: 1000
						});
					}
				} else if (res.cancel) {
					uni.showToast({
						title: '取消删除',
						icon: 'none',
						duration: 1000
					});
				}
			}
		});
	};

	//删除文章
	const DelArticle = async () => {
		uni.showModal({
			title: '温馨提示',
			content: '确定删除此文章？',
			success: async function(res) {
				if (res.confirm) {
					try {
						console.log(1);
						const result = await delArticle(articleId.value);
						if (result.data.code === 0) {
							uni.showToast({
								title: '删除成功',
								icon: 'none',
								duration: 1000
							});
							uni.navigateBack();
						}
					} catch (error) {
						console.log(2);
						// 这里可以处理错误
						uni.showToast({
							title: '删除失败',
							icon: 'none',
							duration: 1000
						});
					}
				} else if (res.cancel) {
					uni.showToast({
						title: '取消删除',
						icon: 'none',
						duration: 1000
					});
				}
			}
		});
	};

	// 监听软键盘高度变化
	const onKeyboardHeightChange = (event) => {
		console.log("键盘高度" + event.height)
		if (event.height == 0)
			isFocus.value = false;
	};

	//请求后端数据
	const fetchData = async () => {
		//请求文章详情
		let result = await findArticleDetails(articleId.value);
		if (result.data.data == null) {
			uni.navigateBack();
		}
		article.value = result.data.data;
		//请求作者信息
		result = await findAuthorDate(openid.value);
		userData.value = result.data.data;
		//请求评论信息
		result = await findArticleComment(articleId.value);
		commentList.value = result.data.data;
		//请求检查当前用户是否收藏本文章
		result = await isCollect(articleId.value, userInfo.openid); //这个应该是当前登录用户的openid
		collect.value = result.data.data;
	};

	//节流函数
	const throttle = (fn, delay) => {
		// 设置一个触发开关
		let canUse = true
		return function() {
			//如果为true，就触发技能，否则就不能触发
			if (canUse) {
				fn.apply(this, arguments)
				//触发技能后，关闭开关
				canUse = false
				//在3秒后打开开关
				setTimeout(() => canUse = true, delay)
			}
		}
	}

	//添加收藏
	const collectArticle = async () => {
		if (!collect.value) {
			let result = await addCollect(articleId.value);
			if (result.data.code === 0) {
				uni.showToast({
					title: '收藏成功',
					icon: 'none',
					duration: 1000
				});
			}
			fetchData();
		} else {
			let result = await cancelCollect(articleId.value);
			uni.showToast({
				title: '取消收藏',
				icon: 'none',
				duration: 1000
			});
			fetchData();
		}
	}

	//点赞文章
	const likeAricle = async () => {
		if (!isLike.value) {
			const result = await addLike(articleId.value);
			isLike.value = true;
			fetchData();
			uni.showToast({
				title: '点赞成功',
				icon: 'none',
				duration: 1000
			});
		} else {
			const result = await cancelLike(articleId.value);
			isLike.value = false;
			fetchData();
			uni.showToast({
				title: '取消成功',
				icon: 'none',
				duration: 1000
			});
		}
	}

	//添加收藏节流
	const throttleCollectArticle = throttle(collectArticle, 1000);
	//添加点赞节流
	const throttleLikeAricle = throttle(likeAricle, 1000);

	// 页面加载时，监听软键盘高度变化
	onMounted(() => {
		wx.onKeyboardHeightChange(onKeyboardHeightChange);
	});

	// 页面加载
	onLoad((options) => {
		articleId.value = options.articleId;
		openid.value = options.openid;
	})

	// 组件实例进入页面之前
	onBeforeMount(() => {
		fetchData();
	})
</script>

<style lang="scss" scoped>
	.Details-box {}

	.Details-box-img {}

	.Details-box-img image {
		width: 100%;
	}

	.Details-box-user {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-top: 15rpx;
		margin-left: 20rpx;
	}

	.Details-box-title {
		margin-top: 15rpx;
	}

	.Details-box-title-view {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-top: 10rpx;
		margin-left: 20rpx;
		margin-right: 20rpx;
	}

	.Details-box-comment {
		margin-top: 20rpx;
		margin-left: 20rpx;
	}

	/* 评论区样式定义 */
	.comment-section {
		margin-top: 20rpx;
		margin-right: 20rpx;
	}

	.comment-thread {}

	.comment-body {}

	.comment {
		border-top: 2rpx solid #eee;
		// border-bottom: 2rpx solid #eee;
		padding: 10rpx;
	}

	.comment-author {
		width: 100%;
		display: flex;
		flex-direction: row;
	}

	.comment-avatar {
		width: 50rpx;
		height: 50rpx;
		border-radius: 50%;
	}

	.comment-nickname {
		// font-weight: bold;
		font-size: 26rpx;
		margin-left: 10rpx;
		margin-right: 10rpx;
	}

	.comment-content {
		margin-bottom: 10rpx;
	}

	.reply-info {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-bottom: 5rpx;
		padding-top: 5rpx;
	}

	.reply-nickname {
		margin-left: 10rpx;
		font-size: 26rpx;
	}

	.comment-child {
		margin-left: 50rpx;
	}

	// 底部状态栏
	.Details-box-createComment {
		position: fixed;
		height: 60px;
		width: 100%;
		background-color: #ffffff;
		bottom: 0;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		border-top: 2rpx solid #eee;
	}

	.Details-box-flexRow {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-left: 10rpx;
	}

	.Details-box-input {
		height: 60rpx;
		width: 100%;
		background-color: #f3f3f3;
		border-radius: 30rpx;
		margin-left: 20rpx;
		display: flex;
		flex-direction: column;
		justify-content: center;
	}

	/* 评论输入框弹出层样式 */
	.popup-content {
		height: 100rpx;
		background-color: #e8e8e8;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
	}

	.popup-content-input {
		height: 60rpx;
		width: 570rpx;
		border-radius: 30rpx;
		background-color: #fff;
		padding-left: 20rpx;
		margin-top: 20rpx;
		margin-bottom: 20rpx;
	}

	.popup-content-sendBottom {
		color: #fff;
		height: 60rpx;
		width: 100rpx;
		background-color: #55aaff;
		border-radius: 30rpx;
		margin-right: 20rpx;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}
</style>