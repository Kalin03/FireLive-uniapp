<template>
	<Navbar :showLeft="true" title="搜索结果"></Navbar>
	<view class="search">
		<view class="search-input">
			<uni-icons class="search-input-search" type="search" size="30"></uni-icons>
			<input class="search-input-int" placeholder="搜索点什么..." v-model="searchStr" />
			<uni-icons class="search-input-clear" type="clear" size="30" @click="clearInput"></uni-icons>
		</view>
		<view class="search-send" @click="searchArticleSend">
			<text>搜索</text>
		</view>
	</view>
	<view class="grid-container">
		<custom-waterfalls-flow ref="waterfallsFlowRef" :value="data.list" :column="column" :columnSpace="1.5"
			@wapperClick="wapperClick" @imageClick="imageClick" :seat="2">
			<view class="grid-item" v-for="(item,index) in data.list" :key="index" slot="slot{{index}}">
				<view class="grid-item-text">
					<view>
						<text>{{ item.articleTitle }}</text>
					</view>
					<view style="display: flex;flex-direction: row;justify-content: space-between;margin-top: 5rpx;">
						<view style="display: flex;flex-direction: row;align-items: center;">
							<image :src="item.avatar" mode="aspectFill"
								style="height: 35rpx;width: 35rpx;border-radius: 50%;object-fit: cover;">
							</image>
							<text style="font-size: 25rpx;margin-left: 10rpx;">{{ item.nickName }}</text>
						</view>
						<view style="display: flex;flex-direction: row;align-items: center;">
							<uni-icons type="heart-filled" size="30rpx" color="#E5404F"></uni-icons>
							<text style="font-size: 25rpx;display: block;">{{ item.likesNumber }}</text>
						</view>
					</view>
				</view>
			</view>
			<template v-slot:default="item">
				<view class="grid-item-text">
					<view>
						<text>{{ item.articleTitle }}</text>
					</view>
					<view style="display: flex;flex-direction: row;justify-content: space-between;margin-top: 5rpx;">
						<view style="display: flex;flex-direction: row;align-items: center;">
							<image :src="item.avatar" mode="aspectFill"
								style="height: 35rpx;width: 35rpx;border-radius: 50%;object-fit: cover;">
							</image>
							<text style="font-size: 25rpx;margin-left: 10rpx;">{{ item.nickName }}</text>
						</view>
						<view style="display: flex;flex-direction: row;align-items: center;">
							<uni-icons type="heart-filled" size="30rpx" color="#E5404F"></uni-icons>
							<text style="font-size: 25rpx;display: block;">{{ item.likesNumber }}</text>
						</view>
					</view>
				</view>
			</template>
		</custom-waterfalls-flow>
	</view>
</template>

<script setup>
	import {
		onPullDownRefresh,
		onPageScroll
	} from '@dcloudio/uni-app'
	import {
		ref,
		onMounted,
		onUnmounted,
		onBeforeMount,
		reactive
	} from 'vue';
	import {
		onLoad
	} from '@dcloudio/uni-app'
	import {
		searchArticle
	} from '@/api/community.js'

	const searchStr = ref("") // 绑定搜索框的输入内容
	const history = ref([]); // 存储搜索历史记录
	const articleList = ref([])
	// 定义组件的数据
	const data = reactive({
		list: []
	});
	const column = ref(2);
	const currentPage = ref(1);
	const pageSize = ref(10);
	const totalPages = ref(0);
	const screenHeight = ref(0);

	// 使用onLoad钩子来接收页面参数
	onLoad((options) => {
		if (options.searchStr) {
			searchStr.value = options.searchStr;
		}
		fetchData();
		// 当页面挂载时，从本地存储中获取搜索历史
		history.value = uni.getStorageSync('searchHistory') || [];
	});

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

	const waterfallsFlowRef = ref(null);
	const reset = () => {
		waterfallsFlowRef.value.refresh();
	}

	//请求数据
	const fetchData = async (page = 1) => {

		//向后端发送请求
		const result = await searchArticle(searchStr.value, page, pageSize.value);

		if (result.statusCode === 200 && result.data.code === 0) {
			const newData = result.data.data.items; // 服务器返回的数据包含data.list字段
			totalPages.value = result.data.data.total; // 服务器返回的总页数
			data.list = page === 1 ? newData : data.list.concat(newData);
			currentPage.value = page;
		}

		if (page == 1)
			reset();
	};

	//图片点击事件
	const imageClick = (item) => {
		uni.navigateTo({
			url: `/subpkg/articleDetails/articleDetails?articleId=${item.articleId}&openid=${item.openid}`
		});
	}

	//标题点击事件
	const wapperClick = (item) => {
		uni.navigateTo({
			url: `/subpkg/articleDetails/articleDetails?articleId=${item.articleId}&openid=${item.openid}`
		});
	}

	//懒加载
	const lazyLoad = () => {
		fetchData(currentPage.value + 1);
	}

	const throttleLazyLoad = throttle(lazyLoad, 1000);

	// 下拉刷新事件处理
	onPullDownRefresh(() => {
		fetchData();
		setTimeout(function() {
			uni.stopPullDownRefresh();
		}, 500);
	})

	// 页面加载
	onMounted(() => {
		setTimeout(function() {}, 500);
		uni.startPullDownRefresh();
		// 获取系统信息
		uni.getSystemInfo({
			success: (res) => {
				// 获取屏幕高度
				screenHeight.value = res.screenHeight;
			}
		});
	});

	//监听页面滑动
	onPageScroll((e) => {
		const scrollTop = e.scrollTop;
		uni.createSelectorQuery().select('.grid-container').boundingClientRect(data => {
			if (scrollTop > data.height - screenHeight.value) {
				throttleLazyLoad();
			}
		}).exec();
	});

	const searchArticleSend = () => {
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
		fetchData();
		reset();
		// 清空搜索框
		searchStr.value = ''
	}

	const clearInput = () => {
		searchStr.value = ""
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

	.grid-container {
		margin-left: 20rpx;
		margin-right: 20rpx;
	}

	.grid-item {
		/* 根据需要设置样式 */
		width: 100%;
		overflow: hidden;
	}

	.grid-item-image {
		border-radius: 10px;
		width: 100%;
		height: auto;
	}

	.grid-item-text {
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}

	.publish-article {
		position: fixed;
		right: 0;
		bottom: 100rpx;
	}
</style>