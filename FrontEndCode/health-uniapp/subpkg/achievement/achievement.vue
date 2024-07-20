<template>
	<view>
	<Navbar :showLeft="true" title="成就"></Navbar>
		<template>
			<view>
				成就
			</view>
			<!--成就-->
			<view class="achievement">
				<navigator class="achievement-child" url="/subpkg/achievement/achievement">
					<view class="child-back">
						<image class="child-image" src=""></image>
					</view>
					<view class="child-text">
						成就
					</view>
					<uni-icons class="child-icons" type="right" size="26" color="#878787"></uni-icons>
				</navigator>
				<view class="achievement-box" >
					<image class="achievement-imag" v-for="(item, i) in achievementList" :key="i" :src="item.url"></image>
				</view>
			</view>
			<view class="login" @click="WXLogin()">登陆</view>
			<image :src="avatar"></image>
		</template>
	</view>
</template>

<script setup>
	
</script>

<style lang="scss" scoped>
	.login {
		width: 100rpx;
		height: 50rpx;
		background-color: aquamarine;
	}
	.achievement {
		//position: absolute;
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-left: 30rpx;
		margin-top: 30rpx;
		width: 690rpx;
		height: 280rpx;
		border-radius: 32rpx;
		opacity: 1;
		background: #FFFFFF;
		// border: 5rpx solid #000000;
		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);
		.achievement-child {
			//position: absolute;
			margin-top: 16rpx;
			width: 658rpx;
			height: 248rpx;
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
		.achievement-box {
			display: flex;
			flex-direction: row;
			justify-content: space-around;
			position: absolute;
			//margin-left: 16rpx;
			margin-top: 148rpx;
			width: 636rpx;
			height: 100rpx;
			border-radius: 32rpx;
	
			background: #FFFFFF;
			.achievement-imag {
				margin-top: 5rpx;
				width: 90rpx;
				height: 90rpx;
			}
		}
		
	}
</style>
