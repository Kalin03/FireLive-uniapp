<template>
	<view>
		<Navbar :showLeft="true" title="发布文章"></Navbar>
		<view class="box">
			<view class="box-file">
				<uni-file-picker fileMediatype="image" :limit="1" @select="handleSelect"
					:image-styles="{ width: '600rpx', height: '800rpx', border: { color: '#efefef', width: '2rpx', style: 'solid', radius: '10rpx' } }">
				</uni-file-picker>
			</view>
			<view class="box-title">
				<input class="box-title-input" placeholder="请填写标题..." v-model="articleData.articleTitle" />
			</view>
			<view class="box-content">
				<textarea class="box-content-textarea" placeholder="添加正文..." v-model="articleData.articleContent" />
			</view>
			<view class="box-publishBottom" @click="throttleUploadArticle">
				<text>发布</text>
			</view>
		</view>
	</view>
</template>

<script setup>
	import {
		ref
	} from 'vue';
	import {
		uploadFile,
		sendArticle
	} from '@/api/community.js'
	//数据模型
	const articleData = ref({
		articleImage: '',
		articleTitle: '',
		articleContent: ''
	});

	//存储文件
	const imageFiles = ref('');

	//获取文件
	const handleSelect = async (e) => {
		imageFiles.value = e.tempFilePaths;
	}

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

	//上传文章
	const uploadArticle = async () => {
		uni.showLoading({
			title: '加载中'
		});
		if (articleData.value.articleTitle == '') {
			uni.showToast({
				title: '请填写标题',
				icon: 'none',
				duration: 1000
			});
		} else if (imageFiles.value === '') {
			uni.showToast({
				title: '请上传图片',
				icon: 'none',
				duration: 1000
			});
		} else {
			//上传文件
			const filePath = imageFiles.value[0];
			console.log(filePath);
			let result = await uploadFile(filePath);
			const data = JSON.parse(result.data);
			if (result.statusCode === 200) {
				articleData.value.articleImage = data.data;
				console.log(articleData.value);
				//上传文章
				result = await sendArticle(articleData.value);
				if (result.statusCode === 200) {
					articleData.value.articleContent = '';
					articleData.value.articleTitle = '';
					uni.showToast({
						title: '发布成功',
						icon: 'none',
						duration: 1000
					});
					uni.navigateBack();
				} else {
					uni.showToast({
						title: '发布失败',
						icon: 'none',
						duration: 1000
					});
				}
			} else {
				uni.showToast({
					title: '上传失败',
					icon: 'none',
					duration: 1000
				});
			}
		}
		uni.hideLoading();
	}

	//定义上传文章节流函数
	const throttleUploadArticle = throttle(uploadArticle, 5000);
</script>

<style lang="scss" scoped>
	.box {
		margin: 30rpx;
	}

	.box-file {
		margin-top: 65rpx;
		margin-left: 65rpx;
	}

	.box-title {
		margin-top: 20rpx;
		border-bottom: 2rpx solid #efefef;
		font-size: 36rpx;
	}

	.box-title-input {
		height: 60rpx;
		padding: 20rpx;
	}

	.box-content {
		border-bottom: 2rpx solid #efefef;
		font-size: 30rpx;
	}

	.box-content-textarea {
		height: 300rpx;
		padding: 20rpx;
	}

	.box-publishBottom {
		position: fixed;
		bottom: 45rpx;
		height: 80rpx;
		width: 690rpx;
		border-radius: 40rpx;
		background: linear-gradient(to right, #9060df, #6356de);
		color: #ffffff;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}
</style>