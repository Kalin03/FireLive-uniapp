<template>
	<view>
		<Navbar :showLeft="true" title="饮食记录"></Navbar>
		<template>
			<view class="datetime-picker">
				<uni-datetime-picker type="date" :clear-icon="false" v-model="single" :border="false"
					@change="maskClick" />
			</view>
			<view>
				<view class="diet-day">
					<view class="title">
						<view class="image-box">
							<image class="image"
								src="@/static/icon/iconList/user/health_record/饮食记录.png">
							</image>
						</view>
						<view class="text">记录详情</view>
					</view>
					<view style="display: flex;flex-direction: row;font-size: 32rpx;margin-top: 8rpx;">
						<view style="margin-left: 28rpx;">名称</view>
						<view style="margin-left: 164rpx;">重量</view>
						<view style="margin-left: 218rpx;">时间</view>
					</view>
					<view v-if="isDiet">
						<view  class="diet-data" v-for="(item,index) in dietData" :key="index">
							<view class="diet-foods">
								<view style="width: 120rpx;margin-left: 16rpx;">{{item.foodName}}</view>
								<view style="width: 140rpx;margin-left: 108rpx;">{{item.foodWeight}}g</view>
								<uni-dateformat style="width: 80rpx;margin-left: 140rpx;" :date=item.updateTime
									format="hh:mm"></uni-dateformat>
							</view>
						</view>
					</view>
					<view v-else class="diet-null"> 暂无数据 </view>
				</view>
			</view>
		</template>
	</view>
</template>

<script setup>
	import Navbar from "../../components/Navbar.vue";
	import {ref,onMounted,watch} from 'vue';
	import { foodConsumptionInfo } from "../api/record/record.js"

	const now = new Date();
	let single = ref(now.toISOString().slice(0,10));
	
	let dietData = ref([]);
	let isDiet = ref(false);
	
	const maskClick = () => {
		foodConsumptionInfo(single.value)
			.then(response => {
				dietData.value = response;
				isDiet.value = dietData.value.length !== 0;
			})
			.catch(error => {
				console.log(error);
			});
	};
	
	onMounted(() => {
		foodConsumptionInfo(single.value)
			.then(response => {
				dietData.value = response;
				isDiet.value = dietData.value.length !== 0;
				})
			.catch(error => {
				console.log(error);
			});
	})
</script>

<style lang="scss" scoped>
	.datetime-picker {
		position: fixed;
		width: 100%;
		background: #ffffff;
		box-shadow: 0rpx 0rpx 16rpx 0rpx rgba(0, 0, 0, 0.2);
	}

	.diet-day {
		display: flex;
		box-sizing: border-box;
		flex-direction: column;
		margin-left: 30rpx;
		margin-top: 88rpx;
		margin-bottom: 30rpx;
		padding: 16rpx;
		width: 690rpx;
		border-radius: 32rpx;
		opacity: 1;
		background: #ffffff;
		// border: 5rpx solid #000000;
		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);

		.title {
			display: flex;
			flex-direction: row;
			align-items: center;

			.image-box {
				display: flex;
				align-items: center;
				justify-content: center;
				height: 76rpx;
				width: 76rpx;
				border-radius: 32rpx;
				background: #ECEFF1;

				.image {
					width: 60rpx;
					height: 60rpx;
				}
			}

			.text {
				margin-left: 16rpx;
				font-size: 36rpx;
			}
		}

		.diet-data {
			display: flex;
			flex-direction: column;
			opacity: 1;
			.diet-foods {
				display: flex;
				box-sizing: border-box;
				flex-direction: row;
				margin-top: 16rpx;
				padding: 12rpx;
				width: 664rpx;
				border-radius: 16rpx;
				font-size: 28rpx;
				background: #ECEFF1;
			}
		}
		.diet-null {
			display: flex;
			box-sizing: border-box;
			flex-direction: row;
			justify-content: center;
			margin-top: 16rpx;
			padding: 12rpx;
			width: 664rpx;
			border-radius: 16rpx;
			font-size: 28rpx;
			background: #ECEFF1;
		}
	}
	::v-deep .uni-date-x {
		margin-right: 24rpx;
	}
</style>