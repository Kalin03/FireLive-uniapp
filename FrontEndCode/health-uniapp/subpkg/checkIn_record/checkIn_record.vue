<template>
	<view>
	<Navbar :showLeft = "true" title="打卡记录"></Navbar>
		<template>
			<view >
				<view class="check-in-day" >
					<view class="title">
						<view class="image-box">
							<image class="image" src="@/static/icon/iconList/user/health_record/打卡记录.png"></image>
						</view>
						<view class="text">记录详情</view>
					</view>
					<view class="check-in-area" v-for="(item,index) in checkInData.reverse()" :key="index">
						<view class="check-in-time">
							<uni-dateformat :date=item.updateTime format="yyyy.M.d"></uni-dateformat>
						</view>
						<view class="check-in-data">
							<view class="check-in-item">
								<view class="check-in-img" :class="{'checkIn':item.earlyRising}" >
									<image style="width:88rpx ;height: 82rpx;" src="@/static/icon/iconList/check_in/morning.png"/>
								</view>
								<view class="check-in-tag">
									<image style="width:80rpx ;height: 40rpx;" src="@/static/icon/iconList/check_in/morning.svg"/>
								</view>
							</view>
							<view class="check-in-item">
								<view class="check-in-img" :class="{'checkIn':item.running}" >
									<image style="width:100rpx ;height: 90rpx;" src="@/static/icon/iconList/check_in/run.png"/>
								</view>
								<view class="check-in-tag">
									<image style="width:80rpx ;height: 40rpx;" src="@/static/icon/iconList/check_in/run.svg"/>
								</view>
							</view>
							<view class="check-in-item">
								<view class="check-in-img" :class="{'checkIn':item.fitness}" >
									<image style="width:110rpx ;height: 70rpx;" src="@/static/icon/iconList/check_in/fitness.png"/>
								</view>
								<view class="check-in-tag">
									<image style="width:80rpx ;height: 40rpx;" src="@/static/icon/iconList/check_in/fitness.svg"/>
								</view>
							</view>
							<view class="check-in-item">
								<view class="check-in-img" :class="{'checkIn':item.earlyBedtime}" >
									<image style="width:80rpx ;height: 80rpx;" src="@/static/icon/iconList/check_in/bedEarly.png"/>
								</view>
								<view class="check-in-tag">
									<image style="width:80rpx ;height: 40rpx;" src="@/static/icon/iconList/check_in/bedEarly.svg"/>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</template>
	</view>
</template>

<script setup>
	import Navbar from "../../components/Navbar.vue";
	import { ref, onMounted} from 'vue';
	import {checkInRecord} from "../api/record/record.js"
	
	let checkInData = ref([]);
	onMounted( ()=>{
		checkInRecord()
			.then(response => {
				checkInData.value = response;
				//删除无效打卡
				for(let i = 0 ; i < checkInData.value.length ;i++){
					if(checkInData.value[i].earlyRising === 0 && checkInData.value[i].running === 0 && 
						checkInData.value[i].fitness === 0 && checkInData.value[i].earlyBedtime === 0){
							checkInData.value.splice(i,1);
					}
				}
			})
			.catch(error => {
				console.log(error);
			});
	})
</script>

<style lang="scss" scoped>
	.check-in-day {
		display: flex;
		box-sizing: border-box;
		flex-direction: column;
		margin-left: 30rpx;
		margin-top: 30rpx;
		margin-bottom: 30rpx;
		padding: 24rpx;
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
		.check-in-area {
			display: flex;
			box-sizing: border-box;
			flex-direction: column;
			width: 642rpx;
			margin-top: 24rpx;
			padding: 24rpx;
			border-radius: 32rpx;
			background: #ECEFF1;
			.check-in-time{
				display: flex;
				justify-content: center;
				align-items: center;
				width: 200rpx;
				height: 55rpx;
				border-radius: 30rpx;
				background-color: #FFFFFF;
				font-weight: bold;
				font-style: italic;
				color: #3D3D3D;
			}
			.check-in-data {
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				.check-in-item {
					display: flex;
					flex-direction: column;
					.check-in-img {
						display: flex;
						flex-direction: column;
						justify-content: flex-end;
						align-items: center;
						position: relative;
						width: 100rpx;
						height: 100rpx;
						filter: grayscale(1);
						&.checkIn {
							filter: grayscale(0);
						}
					}
					.check-in-tag {
						display: flex;
						justify-content: center;
						align-items: center;
						width: 100rpx;
						height: 40rpx;
						margin-top: 16rpx;
						border-radius: 30rpx;
						background-color: #FFFFFF;
					}
				}
			}
		}
	}
</style>
