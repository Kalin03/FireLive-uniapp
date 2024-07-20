<template>
	<view>
		<view class = "navbar"
			:style="{'height':titleBarHeight + 'px','padding-top':statusBarHeight + 'px',}">
			<view class = "left">
				<uni-icons type="left" v-if="showLeft" @click="leftClick()"></uni-icons>
			</view>
			<view class = "content">
				<view v-if="title">{{title}}</view>
				<slot></slot>
			</view>
			<view class = "right">
				<slot name = "right"></slot>
			</view>
		</view>
		<view :style="{'height':titleBarHeight +'px','padding-top':statusBarHeight+'px'}"></view>
	</view>
</template>
<script setup>
import {ref, onMounted} from 'vue';

//标题栏高度
const titleBarHeight = ref(0);
//手机状态栏的高度
const statusBarHeight = ref(0);

onMounted(() => {
	uni.getSystemInfo({
		success: (res) => {
			if (res.model.indexOf('iPhone') !== -1) {
				titleBarHeight.value = 44;
			} else {
				titleBarHeight.value = 48;
			}
				statusBarHeight.value = res.statusBarHeight
		},
	});
});
const props = defineProps({
  title: {
    type:String,
  },
  showLeft:{
    type:Boolean,
    default:true,
  },
  leftClick: {
    type:Function,
  },
});

const leftClick = ()=>{
  if(props.leftClick){
    props.leftClick;
  } else {
    uni.navigateBack();//返回上一层
  }
};
</script>



<style lang="scss" scoped >
//渐变紫色导航栏
.navbar {
  background: linear-gradient(to right, #9060df, #6356de);
  width: 100%;
  position: fixed;
  top: 0;
  z-index: 100;
  display: flex;
  color: #fff;
  align-items: center;
  justify-content: center;
  padding: 0 10px;
  .left {
    width: 20px;
  }
  .content {
    text-align: center;
    flex: 1;
  }
  .right {
    width: 20px;
  }
}
</style>