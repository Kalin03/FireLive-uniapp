<template>
	<Navbar :showLeft="true" title="搜索文章"></Navbar>
	<view class="search">
		<view class="search-input">
			<uni-icons class="search-input-search" type="search" size="30"></uni-icons>
			<input class="search-input-int" placeholder="搜索点什么..." v-model="searchStr" />
			<uni-icons class="search-input-clear" type="clear" size="30" @click="clearInput"></uni-icons>
		</view>
		<view class="search-send" @click="searchArticle">
			<text>搜索</text>
		</view>
	</view>
	<view class="searchHistory">
		<view class="searchHistory-view">
			<!-- <uni-icons type="spinner-cycle" size="30"></uni-icons> -->
			<image style="height: 40rpx;width: 40rpx;margin-right: 10rpx;" src="../../static/icon/时钟.svg" mode="">
			</image>
			<text>历史记录</text>
		</view>
		<scroll-view class="searchHistory-scroll" scroll-y="true">
			<view class="searchHistory-item" v-for="(item, index) in history" :key="index">
				<view>
					<text @click="getSearchHistory(index)">{{ item }}</text>
					<uni-icons type="clear" size="20" @click="removeHistory(index)"></uni-icons>
				</view>
			</view>
		</scroll-view>
	</view>
	<view class="rankingList">
		<view class="">
			<image style="height: 40rpx;width: 226rpx;" src="../../static/icon/社区TOP10.svg" mode=""></image>
		</view>
		<view class="rankingList-items">
			<view class="rankingList-item" v-for="(article, index) in articleTopList" :key="index"
				@click="topClick(article)">
				<image :src="topIcons[index]" mode="aspectFill" style="width: 29rpx;height: 50rpx;margin-right: 20rpx;">
				</image>
				<text>{{article.articleTitle}}</text>
			</view>
		</view>
	</view>
</template>

<script setup>
	import {
		ref,
		onMounted,
	} from 'vue'
	import {
		articleTop
	} from '@/api/community.js'

	const topIcons = [
		'@/static/icon/iconList/topIcon/1.svg',
		'@/static/icon/iconList/topIcon/2.svg',
		'@/static/icon/iconList/topIcon/3.svg',
		'@/static/icon/iconList/topIcon/4.svg',
		'@/static/icon/iconList/topIcon/5.svg',
		'@/static/icon/iconList/topIcon/6.svg',
		'@/static/icon/iconList/topIcon/7.svg',
		'@/static/icon/iconList/topIcon/8.svg',
		'@/static/icon/iconList/topIcon/9.svg',
		'@/static/icon/iconList/topIcon/10.svg',
	]
	const searchStr = ref("") // 绑定搜索框的输入内容
	const history = ref([]); // 存储搜索历史记录
	const articleTopList = ref([])

	onMounted(() => {
		fetchData();
		// 当页面挂载时，从本地存储中获取搜索历史
		history.value = uni.getStorageSync('searchHistory') || [];
	});

	//请求后端数据
	const fetchData = async () => {
		//请求文章top10
		let result = await articleTop();
		if (result.data.code === 0) {
			articleTopList.value = result.data.data;
		}
	};

	const searchArticle = () => {
		if (searchStr.value.trim() === '') {
			uni.showToast({
				title: '请输入搜索内容',
				icon: 'none'
			})
			return;
		}
		// 将新的搜索内容添加进历史记录
		history.value.unshift(searchStr.value)
		// 去重
		history.value = [...new Set(history.value)]
		// 保存到本地存储
		uni.setStorageSync('searchHistory', history.value)
		// 清空搜索框
		uni.navigateTo({
			url: `/subpkg/searchAricle/searchAricle?searchStr=${searchStr.value}`
		})
		searchStr.value = ''
	}

	const clearInput = () => {
		searchStr.value = ""
	}

	const getSearchHistory = (index) => {
		searchStr.value = history.value[index]
	}

	const topClick = (item) => {
		uni.navigateTo({
			url: `/subpkg/articleDetails/articleDetails?articleId=${item.articleId}&openid=${item.openid}`
		});
	}

	const removeHistory = (index) => {
		// 移除指定的历史记录
		history.value.splice(index, 1);
		// 更新本地存储
		uni.setStorageSync('searchHistory', history.value);
	}
</script>

<style lang="scss" scoped>
	.search {
		margin-top: 20rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
		// justify-content: space-between;
		margin-bottom: 20rpx;
	}

	.search-input {
		margin: 0 auto;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		height: 70rpx;
		width: 600rpx;
		background-color: #E6E6E6;
		border-radius: 15rpx;
	}

	.search-input-int {
		// margin-top: 15rpx;
		margin-right: 120rpx;
	}

	.search-input-search {
		// margin-top: 12rpx;
		margin-left: 15rpx;
	}

	.search-input-clear {
		// margin-top: 12rpx;
		margin-right: 15rpx;
	}

	.search-send {
		// margin-top: 10rpx;
		margin-right: 30rpx;
	}

	.searchHistory-scroll {
		height: 200rpx;
	}

	.searchHistory-item {
		display: inline-block;
		background-color: #E6E6E6;
		border-radius: 10rpx;
		line-height: 25px;
		margin-left: 20rpx;
		margin-right: 10rpx;
		padding-left: 10rpx;
		padding-bottom: 5rpx;
		padding-right: 10rpx;
		margin-bottom: 20rpx;
	}

	.searchHistory-view {
		margin: 20rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
	}

	.rankingList {
		margin: 20rpx;
	}

	.rankingList-items {
		margin-left: 10rpx;
		font-size: 32rpx;
	}

	.rankingList-item {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-top: 20rpx;
	}
</style>