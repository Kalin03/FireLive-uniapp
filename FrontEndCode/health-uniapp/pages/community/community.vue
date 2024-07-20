<!-- 本示例未包含完整css，获取外链css请参考上文，在hello uni-app项目中查看 -->
<template>
	<view class="contain">
		<Navbar :showLeft="false" title="社区"></Navbar>
		<view>
			<view class="community-top">
				<view>
					<image class="community-top-title" src="../../static/icon/社区分享.svg"></image>
					<navigator hover-class="none" url="/subpkg/communitySearch/communitySearch">
						<image class="community-top-search" src="../../static/icon/搜索框.svg"></image>
					</navigator>
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
							<view
								style="display: flex;flex-direction: row;justify-content: space-between;margin-top: 5rpx;">
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
							<view
								style="display: flex;flex-direction: row;justify-content: space-between;margin-top: 5rpx;">
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
				<view class="publish-article">
					<navigator url="/subpkg/publishArticle/publishArticle" hover-class="none">
						<uni-icons type="plus" size="120rpx" color="#000000"></uni-icons>
					</navigator>
				</view>
			</view>
		</view>
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
		getArticleList
	} from '@/api/community.js';

	// 定义组件的数据
	const data = reactive({
		list: []
	});
	const column = ref(2);
	const currentPage = ref(1);
	const pageSize = ref(10);
	const totalPages = ref(0);
	const screenHeight = ref(0);

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
		const result = await getArticleList(page, pageSize.value);

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
		fetchData(1);
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
			// console.log(data.height - screenHeight.value);
			// console.log(scrollTop);
			if (scrollTop > data.height - screenHeight.value) {
				throttleLazyLoad();
			}
		}).exec();
	});
</script>
<style>
	.contain{
		width:100vw;
		height:100vh;
		overflow-y:scroll
	}
	
	.contain::-webkit-scrollbar{
		width:0;
		height:0;
		color:transparent;
		display:none
	}
	
	.community-top {
		margin-top: 20rpx;
		padding-left: 20rpx;
		padding-right: 20rpx;
	}

	.community-top-title {
		height: 90rpx;
		width: 280rpx;
	}

	.community-top-search {
		margin-left: -38rpx;
		height: 129rpx;
		width: 789rpx;
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