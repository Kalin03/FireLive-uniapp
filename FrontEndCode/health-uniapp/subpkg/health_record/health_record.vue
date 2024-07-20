<template>
	<view>
	<Navbar :showLeft = "true" title="健康记录"></Navbar>
		<template>
			<view class="health-record">
				<!--饮食记录-->
				<navigator class="record-child" url="/subpkg/diet_record/diet_record">
					<view class="child-back">
						<image class="child-image" src="@/static/icon/iconList/user/health_record/饮食记录.png"></image>
					</view>
					<view class="child-text">
						饮食记录
					</view>
					<uni-icons class="child-icons" type="right" size="26" color="#878787"></uni-icons>
				</navigator>
				<!--运动记录-->
				<navigator class="record-child" url="/subpkg/motion_record/motion_record">
					<view class="child-back">
						<image class="child-image" src="@/static/icon/iconList/user/health_record/运动记录.png"></image>
					</view>
					<view class="child-text">
						运动记录
					</view>
					<uni-icons class="child-icons" type="right" size="26" color="#878787"></uni-icons>
				</navigator>
				<!--打卡记录-->
				<navigator class="record-child" url="/subpkg/checkIn_record/checkIn_record">
					<view class="child-back">
						<image class="child-image" src="@/static/icon/iconList/user/health_record/打卡记录.png"></image>
					</view>
					<view class="child-text">
						打卡记录
					</view>
					<uni-icons class="child-icons" type="right" size="26" color="#878787"></uni-icons>
				</navigator>
				<!--BMI记录-->
				<navigator class="record-child" url="/subpkg/BMI_record/BMI_record">
					<view class="child-back">
						<image class="child-image" src="@/static/icon/iconList/user/health_record/BMI记录.png"></image>
					</view>
					<view class="child-text">
						BMI记录
					</view>
					<uni-icons class="child-icons" type="right" size="26" color="#878787"></uni-icons>
				</navigator>
			</view>
		</template>
	</view>
</template>

<script setup>
	import Navbar from "../../components/Navbar.vue";
	
</script>

<style lang="scss" scoped>
	.health-record {
		display: flex;
		flex-direction: column;
		justify-content: space-around;
		align-items: center;
		//position: absolute; 
		margin-left: 30rpx;
		margin-top: 30rpx;
		width: 690rpx;
		//height: 588rpx;
		border-radius: 32rpx;
		opacity: 1;
		//padding-bottom: 10rpx;
		padding-bottom: 16rpx;
		background: #FFFFFF;
		
		// border: 5rpx solid #000000;
		
		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);
		.record-child {
			//display: flex;
			//flex-direction: column;
			margin-top: 16rpx;
			width: 658rpx;
			height: 132rpx;
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
			.child-icons {
				float: right;
				margin-top: -40rpx;
			}
		}
	}
</style>
