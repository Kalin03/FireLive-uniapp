import {defineStore} from "pinia"

export const loginStatusStore = defineStore('loginStatus',{
	state:()=>{
	    return{
			loginStatus: false,
			token: ""
	    }
	},
	actions: {
	   
	}
})