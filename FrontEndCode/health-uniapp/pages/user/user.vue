<template>
	<view>
	<Navbar :showLeft = "false" title="我的主页 "></Navbar>
		<template >
			<view >
				<!--用户-->
				<view v-if="loginStatus.loginStatus">
					<navigator class="user" url="/subpkg/user_info/user_info">
						<view class="user-image">
							<image class="image" :src="userInfoStore.avatar" mode="aspectFill"></image>
						</view>
						<view class="user-info">
							<view class="user-name">{{userInfoStore.nickName}}</view>
							<view class="signature">{{userInfoStore.signature}}</view>
						</view>
					</navigator>
				</view>
				<view v-else>
					<view class="login">
						<image src="@/static/icon/iconList/user/login.svg"
								style="width: 160rpx;height: 80rpx;"
								@click="Login()">
						</image>
					</view>
				</view>
				<!--健康-->
				<view class="health">
					<!--健康数据-->
					<view class="health-child" @click="loginNavigato('/subpkg/health_data/health_data')">
						<view class="child-back">
							<image class="child-image" src="@/static/icon/iconList/user/健康数据.png"></image>
						</view>
						<view class="child-text">
							健康数据
						</view>
						<uni-icons class="child-icons" type="right" size="26" color="#878787"></uni-icons>
					</view>
					<!--健康记录-->
					<view class="health-child" @click="loginNavigato('/subpkg/health_record/health_record')">
						<view class="child-back">
							<image class="child-image" src="@/static/icon/iconList/user/健康记录.png"></image>
						</view>
						<view class="child-text">
							健康记录
						</view>
						<uni-icons class="child-icons" type="right" size="26" color="#878787"></uni-icons>
					</view>
					<!--饮食周报-->
					<view class="health-child" @click="loginNavigato('/subpkg/diet_weekly/diet_weekly')">
						<view class="child-back">
							<image class="child-image" src="@/static/icon/iconList/user/饮食周报.png"></image>
						</view>
						<view class="child-text">
							饮食周报
						</view>
						<uni-icons class="child-icons" type="right" size="26" color="#878787"></uni-icons>
					</view>
					<!--运动周报-->
					<view class="health-child" @click="loginNavigato('/subpkg/motion_weekly/motion_weekly')">
						<view class="child-back">
							<image class="child-image" src="@/static/icon/iconList/user/运动周报.png"></image>
						</view>
						<view class="child-text">
							运动周报
						</view>
						<uni-icons class="child-icons" type="right" size="26" color="#878787"></uni-icons>
					</view>
					<!--帮助与反馈-->
					<navigator class="health-child" url="/subpkg/help_feedback/help_feedback">
						<view class="child-back">
							<image class="child-image" src="@/static/icon/iconList/user/帮助与反馈.png"></image>
						</view>
						<view class="child-text">
							帮助与反馈
						</view>
						<uni-icons class="child-icons" type="right" size="26" color="#878787"></uni-icons>
					</navigator> 
				</view>
				<!-- 退出登录 -->
				<view v-if="loginStatus.loginStatus" class="logout" @click="Logout()">
					<view class="logout-text">退出登录</view>
				</view>
			</view>
		</template>
		<!-- 登录弹窗 -->
		<view>
			<uni-popup ref="popupLogin" type="bottom">
				<view class="popup-login">
					<!-- 标题 -->
					<view class="title">
						<image class="image" src="@/static/icon/iconList/Fire.svg"></image>
						<view class="text">FireLive智能助手</view>
					</view>
					<view class="prompt">
						<view>登录提示：</view>
						<view>登录后需要您填入相关信息</view>
						<view>小程序会根据您的信息给出更好的使用体验</view>
					</view>
					<!-- 按钮区域 -->
					<view class="button">
						<!-- 取消按钮 -->
						<view class="cancel" @click="closeLogin(0)">
							<view>取消</view>
						</view>
						<!-- 确认按钮 -->
						<view class="confirm" @click="closeLogin(1)">
							<view>登录</view>
						</view>
					</view>
				</view>
			</uni-popup>
		</view>
	</view>
</template>

<script setup>
	import Navbar from "../../components/Navbar.vue";
	import {useUserInfoStore} from "@/stores/userinfo.js";
	import {ref,onMounted} from 'vue';
	import {getUserInfo,updateUserInfo} from '../../api/user.js';
	import {WXLogin,WXlogout} from '../../components/isLogin.js';
	import {loginStatusStore} from '@/stores/loginStatus.js';
	import {isLogin} from '../../components/isLogin.js'
	
	const userInfoStore = useUserInfoStore();
	const loginStatus = loginStatusStore();
	
	onMounted(() => {
		
	})
	//登陆跳转
	const loginNavigato = (NavigatoUrl) => {
		isLogin()
			.then(response => {
				if(response){
					uni.navigateTo({
						url: NavigatoUrl
					})
				}
			})
			.catch(error => {
			})
	}
	
	//登录弹窗
	const popupLogin = ref(null);
	
	//登陆
	const Login = () => {
		popupLogin.value.open();
	}
	
	//关闭弹窗
	const closeLogin = (e) => {
		if(e){
			WXLogin();
			popupLogin.value.close();
		}else {
			popupLogin.value.close();
		}
	}
	
	//登出
	const Logout = () => {
		WXlogout();
	}
</script>

<style lang="scss" scoped>
	.user {
		//position: absolute;
		display: flex;
		margin-left: 30rpx;
		margin-top: 30rpx;
		width: 690rpx;
		height: 200rpx;
		border-radius: 32rpx;
		opacity: 1;
		background: #FFFFFF;
		// border: 5rpx solid #000000;
		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);
		.user-image {
			//position: absolute;
			overflow: hidden;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			margin-left: 18rpx;
			margin-top: 18rpx;
			width: 166rpx;
			height: 166rpx;
			border-radius: 28rpx;
			opacity: 1;
			.image {
				width: 166rpx;
			}
		}
		.user-info {
			margin-left: 20rpx;
			margin-top: 20rpx;
			.user-name {
				font-size: 40rpx;
			}
			.user-id {
				font-size: 20rpx;
			}
			.signature {
				word-wrap: break-word;
				margin-top: 20rpx;
				font-size: 30rpx;
			}
		}
	}
	.login {
		display: flex;
		justify-content: center;
		align-items: center;
		margin-left: 30rpx;
		margin-top: 30rpx;
		width: 690rpx;
		height: 200rpx;
		border-radius: 32rpx;
		opacity: 1;
		background: #FFFFFF;
		// border: 5rpx solid #000000;
		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);
	}
	
	
	.health {
		display: flex;
		flex-direction: column;
		justify-content: space-around;
		align-items: center;
		//position: absolute; 
		margin-left: 30rpx;
		margin-top: 30rpx;
		width: 690rpx;
		//height: 720rpx;
		border-radius: 32rpx;
		opacity: 1;
		padding-bottom: 16rpx;
		background: #FFFFFF;
		
		// border: 5rpx solid #000000;
		
		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);
		.health-child {
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
	.logout{
		display: flex;
		align-items: center;
		justify-content: center;
		margin-top: 60rpx;
		margin-left: auto;
		margin-right: auto;
		width: 480rpx;
		height: 120rpx;
		border-radius: 60rpx;
		background-color: #ECEFF1;
		.logout-text{
			color: orangered;
			font-size: 36rpx;
			font-weight: bold;
		}
	}
	.popup-login {
		display: flex;
		box-sizing: border-box;
		flex-direction: column;
		width: 690rpx;
		margin-left: 30rpx;
		margin-bottom: 30rpx;
		background-color: #ffffff;
		border-radius: 32rpx;
		padding: 36rpx;
		.title {
			display: flex;
			flex-direction: row;
			align-items: center;
			.image {
				width: 80rpx;
				height: 80rpx;
				border-radius: 40rpx;
			}
			.text {
				margin-left: 16rpx;
			}
		}
		.prompt {
			display: flex;
			flex-direction: column;
			margin-left: 16rpx;
			margin-top: 24rpx;
			margin-bottom: 24rpx;
			font-size: 30rpx;
			color: #3D3D3D;
		}
		.button {
			display: flex;
			flex-direction: row;
			align-self: flex-end;
			margin-top: 8rpx;
			font-weight: bold;
			.cancel{
				display: flex;
				justify-content: center;
				align-items: center;
				width: 140rpx;
				height: 70rpx;
				border-radius: 35rpx;
				background-color: #FFFFFF;
				color: #48E1AA;
				box-shadow: 0rpx 6rpx 12rpx 0rpx rgba(0, 0, 0, 0.2);
			}
			.confirm{
				display: flex;
				justify-content: center;
				align-items: center;
				margin-left: 24rpx;
				width: 140rpx;
				height: 70rpx;
				border-radius: 35rpx;
				background-color: #48E1AA;
				color: #FFFFFF;
				box-shadow: 0rpx 6rpx 12rpx 0rpx rgba(0, 0, 0, 0.2);
			}
		}
	}
</style>
