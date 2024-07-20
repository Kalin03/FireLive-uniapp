<template>
	<view>
	<Navbar :showLeft = "true" title="用户数据"></Navbar>
		<template>
			<view class="user-info">
				<view class="avatar">
					<view class="avatar-box">
						
						<uni-file-picker fileMediatype="image" limit="1"  ref="files"
							:image-styles="imageStyles"
							@select="select" 
							@progress="progress" 
							@success="success" 
							@fail="fail" 
							@delete="deleteFile" >	
							<view class="user-avatar">
								<image class="image" :src="formData.avatar" mode="aspectFill"></image>
							</view>
						</uni-file-picker>
					</view>
					<view style="font-size: 30rpx;">点击修改头像</view>
				</view>
				<view class="info">
					<uni-forms ref="form" class="user-info" :modelValue="formData" :border="true" :rules="rules">
						<uni-forms-item label="昵称" name="nickName" label-width="180rpx" >
							<uni-easyinput  v-model="formData.nickName" :input-border="false" :clearable="false" placeholder="请输入昵称" />
						</uni-forms-item>
						<uni-forms-item label="性别" name="sex" label-width="180rpx" >
							<uni-data-checkbox v-model="formData.sex" :localdata="sexs" />
						</uni-forms-item>
						<uni-forms-item label="身高(cm)" name="height" label-width="180rpx" >
							<uni-easyinput type="digit" v-model="formData.height" :input-border="false" :clearable="false" placeholder="请输入身高" />
						</uni-forms-item>
						<uni-forms-item label="体重(kg)" name="weight" label-width="180rpx" >
							<uni-easyinput type="digit" v-model="formData.weight" :input-border="false" :clearable="false" placeholder="请输入体重" />
						</uni-forms-item>
						<uni-forms-item label="出生日期" name="birthday" label-width="180rpx" >
							<uni-datetime-picker type="date" return-type="string" v-model="formData.birthday" :border="false" :clear-icon="false"/>
						</uni-forms-item>
						<uni-forms-item label="目标" label-width="100rpx" >
							<uni-data-checkbox v-model="formData.target" :localdata="targets" />
						</uni-forms-item>
						<uni-forms-item label="个性签名" label-width="180rpx" name="signature">
							<uni-easyinput type="textarea" v-model="formData.signature" :input-border="false" placeholder="请输入个性签名" />
						</uni-forms-item>
					</uni-forms>
					<button style="width: 200rpx; background-color: #9060df;" type="primary" @click="submitForm(form)">提交</button>
				</view>
			</view>
		</template>
	</view>
</template>

<script setup> 
	import Navbar from "../../components/Navbar.vue";
	import {useUserInfoStore} from "@/stores/userinfo.js";
	import {updateUserInfo} from '@/api/user.js'
	import { onMounted ,reactive} from "vue";
	import { ossUpload } from '../components/js_sdk/jason-alioss-upload/oss.js';
	const userInfoStore = useUserInfoStore();
	import { ref } from 'vue';
	const imageStyles = {
	  width: "200rpx",
	  height: "200rpx",
	  border: {
	    radius: "30rpx"
	  } 
	};
	
	const form = ref(null);
	const files = ref(null)
	var formData = ref(userInfoStore.getUserInfoNoAnalyse());
	let isUploadAvatar = false;
	
	const avatarUpload = async () => {
	      const {success,data} = await ossUpload(formData.value.avatar, formData.value.avatar, 'user/avatar/')
	      if(success) {
	        formData.value.avatar = data;
	      } else {
	        uni.showToast({icon:'none', title: data})
	      };
	};
	
	const userInfoUpload = async (e) =>{
		if(e){
			await avatarUpload();
		}
		userInfoStore.setUserInfo(formData.value);
	}
	
	const submitForm = (form) => {
	    form.validate().then((res) => {
			uni.showLoading({
				title: '上传中',
			})
			userInfoUpload(isUploadAvatar)
				.then(response => {
					files.value.clearFiles();
					updateUserInfo(Object.setPrototypeOf(userInfoStore.getUserInfoNoAnalyse(), null))
						.then(response => {
							uni.hideLoading();
							uni.showToast({
								title: '提交成功',
								icon: 'success'
							});
						})
						.catch(error => {
							 console.error('修改信息失败：', error);
						});
				})
				.catch(error => {
					console.log(error);
				});
	    }).catch((err) => {
	        console.log('表单错误信息：', err);
	    });
	};
	
	const sexs = ref([
	  { text: '男', value: '男' },
	  { text: '女', value: '女' },
	]);
	
	const targets =ref([
		{ text: '减肥', value: '减肥' },
		{ text: '增重', value: '增重' },
		{ text: '健身', value: '健身' },
		{ text: '保持', value: '保持' },
	]);
	
	const rules = {
	    nickName: {
	        rules: [{
	            required: true,
	            errorMessage: '昵称不能为空'
	        }]
	    },
		sex: {
			rules: [{
			    required: true,
			    errorMessage: '性别不能为空'
			}]
		},
	    height: {
	        rules: [{
	            required: true,
	            errorMessage: '身高不能为空'
	        }],
		},
		weight: {
		    rules: [{
		        required: true,
		        errorMessage: '体重不能为空'
		    }],
		},
		birthday: {
		    rules: [{
		        required: true,
		        errorMessage: '出生日期不能为空'
		    }]
	    },
	};
	
	// 获取上传状态
	const select = (e) => {	
		console.log('选择文件：', e);
		formData.value.avatar = e.tempFilePaths[0];
		isUploadAvatar = true;
		
	};
	
	// 获取上传进度
	const progress = (e) => {
	  console.log('上传进度：', e);
	};
	
	// 上传成功
	const success = (e) => {
		debugger;
	  console.log('上传成功');
	};
	
	// 上传失败
	const fail = (e) => {
	  console.log('上传失败：', e);
	};
	
	// 移除文件
	const deleteFile = (e) => {
	  //console.log('移除文件：', e);
	  formData.value.avatar = userInfoStore.avatar;
	  isUploadAvatar = false;
	};
	
</script>

<style lang="scss" scoped>
	.user-info {
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		margin-left: 30rpx;
		margin-top: 30rpx;
		padding-bottom: 16rpx;
		width: 690rpx;
		border-radius: 32rpx;
		opacity: 1;
		background: #FFFFFF;
		// border: 5rpx solid #000000;
		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);
		.avatar {
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			margin-top: 16rpx;
			height: 244rpx;
			.avatar-box {
				margin-bottom: 16rpx;
				.user-avatar {
					//position: absolute;
					overflow: hidden;
					display: flex;
					flex-direction: column;
					justify-content: center;
					align-items: center;
					width: 166rpx;
					height: 166rpx;
					border-radius: 28rpx;
					opacity: 1;
					.image {
						width: 166rpx;
					}
				}
			}
		}
		.info {
			margin-top: 16rpx;
			margin-left: 16rpx;
			padding-right: 16rpx;
			.user-info {
				
			}
		}
	}
	::v-deep .uni-date-x {
		margin-right: 16rpx;
		justify-content: flex-end;
	}
	::v-deep .uni-easyinput__content-input {
	    text-align: right;
	}
	::v-deep .uni-data-checklist .checklist-group .checklist-box{
		margin-top: 20rpx;
		margin-right: 40rpx;
	}
	::v-deep .uni-data-checklist .checklist-group .checklist-box .checklist-content .checklist-text {
		color: #000;
	}
	::v-deep .uni-forms-item__label {
		color: #000;
	}
	::v-deep .uni-easyinput {
		margin-top: 8rpx;
		text-align: right;
	}
</style> 
