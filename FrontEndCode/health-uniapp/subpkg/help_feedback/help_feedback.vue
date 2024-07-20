<template>
	<view>
	<Navbar :showLeft = "true" title="帮助与反馈"></Navbar>
		<template>
			<view class="help-feedback">
				<view class="help-heath">
					<view>关于健康助手</view>
					<view class="user-info">
						<view style="font-size: 14px; font-weight: bold;">个人信息的作用：</view>
						<view style="font-size: 12px; ">{{userInfo}}</view>
					</view>
					<view class="diet">
						<view style="font-size: 14px; font-weight: bold;">计算饮食摄入：</view>
						<view style="font-size: 12px;">{{countDiet}}</view>
					</view>
					<view class="motion">
						<view style="font-size: 14px; font-weight: bold;">计算运动热量：</view>
						<view style="font-size: 12px;">{{countMotionKcal}}</view>
					</view>
				</view>
				<view class="help-our">
					<view>关于我们</view>
					<view class="our-table">
						<view>
							<view class="title">作者</view>
							<view>Kalin</view>
							<view>TDbroke</view>
						</view>
						<view >
							<view class="title">GitHub</view>
							<view>Kalin03</view>
							<view>TDbroke</view>
						</view>
						<view >
							<view class="title">开发板块</view>
							<view>后端、前端</view>
							<view>前端</view>
						</view>
					</view>
					<view class="our-tech">
						<view class="title">技术栈</view>
						<view>SpringBoot   uniapp   vue3   Echarts</view>
						<view class="title">开发工具</view>
						<view>IDEA HBuilderX   微信开发者工具</view>
					</view>
					
				</view>
				<!-- openPoppup() -->
				<view class="feedback-child" @click="openPoppup() ">
					<view class="child-back">
						<image class="child-image" src="@/static/icon/iconList/user/help_feedback/feedback.png"></image>
					</view>
					<view class="child-text">
						意见反馈
					</view>
					<uni-icons class="child-icons" type="right" size="26" color="#878787"></uni-icons>
				</view> 
			</view>
		</template>
	</view>
	<!-- 反馈弹出层 -->
	<view>
		<uni-popup ref="popupFeedback" type="center">
			<view class="feedback-popup">
				<view class="popup-title">
					<image class="image" src="@/static/icon/iconList/Fire.svg"></image>
					<view class="title-text">欢迎对FireLive健康助手提出建议</view>
				</view>
				<view class="input">
						<uni-easyinput
							primaryColor="#9060DF"
							placeholderStyle="color:#CCCECD;font-size:14px"
							type="textarea" 
							autoHeight 
							v-model="feedbackText" 
							trim="both"
							placeholder="有什么建议和对作者想说的话都可以写下来❤">
						</uni-easyinput>
				</view>
				<view class="button">
					<view class="submit" @click="submitFeedback()">提交</view>
				</view>
				<view v-if="isLike" class="like">
					<image class="like-svg" src="@/static/icon/iconList/ILikeU.svg"></image>
				</view>
			</view>
		</uni-popup>
	</view>
</template>


<script setup>
	import Navbar from "../../components/Navbar.vue";
	import {ref} from 'vue';
	import {isLogin} from "../../components/isLogin.js"
	import {sendFeedback} from "../api/feedback/feedback.js";
	import {useUserInfoStore} from "@/stores/userinfo.js";
	const userInfoStore = useUserInfoStore();
	
	const userInfo = '小程序将通过您的性别、身高、体重、出生日期等信息来计算各项数据，为保证数据尽量准确，请您在“我的”>“个人信息”页完善您的性别、身高、体重、出生日期等信息。';const likeString = "我喜欢你";
	const countDiet = '小程序采用了中国疾病预防控制中心营养与健康所与中国营养学会权威发布的食物营养成分，并根据您的身高、体重、年龄等因素为您制定了不同的营养摄入计划，计算了饮食产生的热量摄入。';
	const countMotionKcal = '小程序采用了科学专业的方法计算您每次运动所产生的热量，最终数据综合了您的身高、体重、运动类型、运动时长等因素。运动产生的热量消耗数据，将根据您的基础信息进行计算。'
	
	const isLike = ref(false);
	const feedbackText = ref('');
	const popupFeedback = ref(null);

	const openPoppup = () => {
		isLogin()
			.then(response => {
				if(response){
					popupFeedback.value.open();
				}
			})
			.catch(error => {
				console.log(error);
			})
	}
	
	const submitFeedback = () => {
		if(feedbackText.value === ''){
			uni.showToast({
				title: '内容不能为空',
				icon: 'error',
				duration: 2000
			});
		}else {
			if(feedbackText.value.includes(likeString)){
				isLike.value = true;
				setTimeout(() => {
					isLike.value = false;
				},520);
			}
			sendFeedback(userInfoStore.nickName + "_反馈：" + feedbackText.value)
				.then(response => {
					uni.showToast({
						title: '提交成功',
						icon: 'success',
						duration: 2000
					});
					feedbackText.value = '';
				})
				.catch(error => {
					console.log(error);
				})
		}
	}
</script>

<style lang="scss" scoped>
	.help-feedback {
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
		.help-heath {
			display: flex;
			box-sizing: border-box;
			flex-direction: column;
			justify-content: space-between;
			margin-top: 16rpx;
			padding: 16rpx 24rpx 16rpx 24rpx;
			width: 658rpx;
			border-radius: 32rpx;
			opacity: 1;
			background: #ECEFF1;
			.user-info, .diet, .motion {
			  margin-top: 30rpx;
			  color: #3D3D3D;
			}

		}
		.help-our {
			display: flex;
			box-sizing: border-box;
			flex-direction: column;
			margin-top: 16rpx;
			padding: 16rpx 24rpx 16rpx 24rpx;
			width: 658rpx;
			border-radius: 32rpx;
			opacity: 1;
			background: #ECEFF1;
			.our-table {
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				font-size: 14px;
			}
			.our-tech {
				display: flex;
				flex-direction: column;
				font-size: 14px;
			}
			.title {
				margin-top: 12rpx;
				font-weight: bold;
				color: #3D3D3D;
			}
		}
		.feedback-child {
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
	.feedback-popup {
		display: flex;
		box-sizing: border-box;
		flex-direction: column;
		justify-content: space-around;
		width: 690rpx;
		padding: 32rpx;
		border-radius: 32rpx;
		font-size: 32rpx;
		background: #EBEDED;
		.popup-title {
			display: flex;
			flex-direction: row;
			align-items: center;
			.image{
				height: 108rpx;
				width: 108rpx;
				border-radius: 32rpx;
			}
			.title-text {
				margin-left: 8rpx;
				font-weight: bold;
				color: #3D3D3D;
			}
		}
		.input {
			margin-top: 24rpx;
		}
		.button {
			display: flex;
			justify-content: center;
			.submit {
				display: flex;
				justify-content: center;
				align-items: center;
				width: 160rpx;
				height: 64rpx;
				border-radius: 64rpx;
				border: solid 6rpx #9060DF;
				margin-top: 32rpx;
				font-size: 36rpx;
				color: #9060DF;
				font-weight: bold;
			}
		}
		.like {
			position: absolute;
			width: 520rpx;
			top: 85rpx;
			left: 85rpx;
			opacity: 520;
			.like-svg {
				width: 520rpx;
			}
		}
	}
	
	::v-deep .is-input-border{
		padding: 8rpx;
		border-radius: 32rpx;
	}
	::v-deep .uni-easyinput__content-textarea {
		min-height: 120px;
	}
</style>
