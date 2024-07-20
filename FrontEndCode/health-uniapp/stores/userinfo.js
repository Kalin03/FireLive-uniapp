import {defineStore} from "pinia"

export const useUserInfoStore = defineStore('userInfo',{
	state:()=>{
	    return{
				openid: "",
	        	avatar: "",
				nickName: '微信用户',
				sex: "",
				height: 0,
				weight: 0,
				birthday: '2000-01-01',
				target: '保持',
				signature: '',
				healthAnalyse: "",
	    }
	},
	actions: {
	   setUserInfo(userInfo){
			this.openid = userInfo.openid;
			this.avatar= userInfo.avatar;
			this.nickName= userInfo.nickName;
			this.sex= userInfo.sex;
			this.height= userInfo.height;
			this.weight=  userInfo.weight;
			this.birthday= userInfo.birthday;
			this.target= userInfo.target;
			this.signature=  userInfo.signature;
			this.healthAnalyse= userInfo.healthAnalyse;
		},
	   getUserInfo(){
			return {
				openid: this.openid,
				avatar: this.avatar,
				nickName: this.nickName,
				sex: this.sex,
				height: this.height,
				weight: this.weight,
				birthday: this.birthday,
				target: this.target,
				signature: this.signature,
				healthAnalyse: this.healthAnalyse,
			}
	   },
	   getUserInfoNoAnalyse(){
	   			return {
	   				openid: this.openid,
	   				avatar: this.avatar,
	   				nickName: this.nickName,
	   				sex: this.sex,
	   				height: this.height,
	   				weight: this.weight,
	   				birthday: this.birthday,
	   				target: this.target,
	   				signature: this.signature,
	   			}
	   }
	}
})