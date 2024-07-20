<template>
	<view>
		<Navbar :showLeft="true" title="健康数据"></Navbar>
		<template>
			<view class="health-data">
				<!--个人信息-->
				<navigator class="data-child" url="/subpkg/user_info/user_info">
					<view class="child-back">
						<image class="child-image"
							src="@/static/icon/iconList/user/health_data/个人信息.png">
						</image>
					</view>
					<view class="child-text">
						个人信息
					</view>
					<view class="personal-info">
						<view class="info-child">
							<view>性别</view>
							<view>{{userInfoStore.sex}}</view>
						</view>
						<view class="info-child">
							<view>身高</view>
							<view>{{userInfoStore.height}}</view>
						</view>
						<view class="info-child">
							<view>体重</view>
							<view>{{userInfoStore.weight}}</view>
						</view>
						<view class="info-child">
							<view>出生日期</view>
							<view>{{userInfoStore.birthday}}</view>
						</view>
					</view>
				</navigator>
			</view>
			<!--健康分析-->
			<view class="health-analysis">
				<view class="data-child">
					<view class="child-back">
						<image class="child-image"
							src="@/static/icon/iconList/user/health_data/健康分析.png">
						</image>
					</view>
					<view class="child-text">
						健康分析
					</view>
					<view class="analysis-content">
						{{userInfoStore.healthAnalyse}}
					</view>
				</view>
			</view>
		</template>
	</view>
</template>

<script setup>
	import Navbar from "../../components/Navbar.vue";
	import {
		useUserInfoStore
	} from "@/stores/userinfo.js";
	import {
		defineProps
	} from 'vue'
	const userInfoStore = useUserInfoStore();
</script>

<style lang="scss" scoped>
	.health-data {
		display: flex;
		flex-direction: row;
		justify-content: center;
		//position: absolute; 
		margin-left: 30rpx;
		margin-top: 30rpx;
		width: 690rpx;
		border-radius: 32rpx;
		opacity: 1;
		padding-bottom: 16rpx;
		background: #FFFFFF;

		// border: 5rpx solid #000000;

		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);

		.personal-info {
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			margin-left: 24rpx;
			margin-top: 60rpx;
			padding-bottom: 8rpx;
			width: 624rpx;
			height: 200rpx;

			.info-child {
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				font-size: 30rpx;
			}
		}
	}

	.health-analysis {
		display: flex;
		flex-direction: row;
		justify-content: center;
		//position: absolute; 
		margin-left: 30rpx;
		margin-top: 30rpx;
		width: 690rpx;
		border-radius: 32rpx;
		opacity: 1;
		padding-bottom: 16rpx;
		background: #FFFFFF;

		// border: 5rpx solid #000000;

		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);

		.analysis-content {
			word-wrap: break-word;
			white-space: pre-wrap;
			text-indent: 2em;
			margin-left: 24rpx;
			margin-top: 60rpx;
			padding-bottom: 8rpx;
			width: 624rpx;
		}
	}

	.data-child {
		//display: flex;
		//flex-direction: column;
		margin-top: 16rpx;
		padding-bottom: 16rpx;
		width: 664rpx;
		//height: 360rpx;
		border-radius: 32rpx;
		opacity: 1;
		background: #ECEFF1;

		.child-back {
			position: absolute;
			margin-left: 16rpx;
			margin-top: 16rpx;
			width: 100rpx;
			height: 100rpx;
			border-radius: 32rpx;
			opacity: 1;
			background: #FFFFFF;

			.child-image {
				margin-left: 5rpx;
				margin-top: 5rpx;
				width: 90rpx;
				height: 90rpx;
			}
		}

		.child-text {
			font-size: 30rpx;
			margin-left: 132rpx;
			margin-top: 40rpx;
		}
	}
</style>