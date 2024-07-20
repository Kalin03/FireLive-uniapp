<template>
	<view>
		<Navbar :showLeft="true" title="BMI记录"></Navbar>
		<template>
			<view class="chart-line">
				<view style="text-align: center;margin-top: 16rpx;z-index: 1;">
					BMI折线图
				</view>
				<view>
					<l-echart ref="chartRef" @finished="init"></l-echart>
				</view>
			</view>
			<view class="BMI-advice">
				<view class="title">
					<view class="image-box">
						<image class="image"
							src="@/static/icon/iconList/user/health_record/BMI.png">
						</image>
					</view>
					<view class="text">BMI分析</view>
				</view>
				<view class="health-data">
					<view class="text">
						<view>BMI</view>
						<view>{{BMI}}</view>
					</view>
					<view class="text">
						<view>健康范围</view>
						<view>{{analyse}}</view>
					</view>
				</view>
				<view class="advice">
					<view class="text">
						<view style="font-weight: bold;">健康分析</view>
					</view>
					<view style="white-space: pre-wrap;text-indent: 2em;">{{userInfoStore.healthAnalyse}}</view>
				</view>
			</view>
		</template>
	</view>
</template>

<script setup>
	import Navbar from "../../components/Navbar.vue";
	import {useUserInfoStore} from "@/stores/userinfo.js";
	const userInfoStore = useUserInfoStore();
	const echarts = require('../../uni_modules/lime-echart/static/echarts.min.js');
	import {ref,onMounted} from 'vue';
	import {getUserBmi} from "../api/record/record.js"

	const BMI = ref(0);

	const analyse = ref('');

	const BMIAnalyse = (e) => {
		if (e < 18.5) {
			analyse.value = '过轻'
		} else if (e >= 18.5 && e < 24) {
			analyse.value = '正常'
		} else if (e >= 24 && e < 28) {
			analyse.value = '超重'
		} else {
			analyse.value = '肥胖'
		}
	}

	let option = {
		dataZoom: [{
			show: true,
			realtime: true,
			startValue: 95,
			endValue: 100,
			minValueSpan: 5,
			bottom: "2%",
			xAxisIndex: [0, 1]
		}],
		grid: {
			top: '15%', //相对位置
			left: "10%",
			right: "10%",
			height: '65%', //整体高度
			//width: '90%'
		},
		xAxis: {
			type: 'category',
			boundaryGap: false,
			data: []
		},
		yAxis: {
			type: 'value',
			min: 10,
			splitLine: { //坐标轴在grid区域中的分割线
				show: false
			},
		},
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'cross',
			}
		},
		series: [{
			data: [],
			type: 'line',
			markPoint: {
				data: [{
						type: 'max',
						name: 'Max'
					},
					{
						type: 'min',
						name: 'Min'
					}
				]
			},
			markLine: {
				data: [{
					name: '一般标准',
					yAxis: 21.7
				}]
			},
			lineStyle: {
				width: 1.5,
				color: 'rgba(58,77,233,0.7)'
			},
			showSymbol: false,
			areaStyle: {
				color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						offset: 0,
						color: 'rgba(58,77,233,0.8)'
					},
					{
						offset: 1,
						color: 'rgba(58,77,233,0.0)'
					}
				])
			},
			//smooth: true
		}]
	};

	const chartRef = ref(null);
	let myChart = null;
	onMounted(() => {
		// 组件能被调用必须是组件的节点已经被渲染到页面上
		setTimeout(async () => {
			if (!chartRef.value) return;
			myChart = await chartRef.value.init(echarts);
			myChart.setOption(option);
		}, 0);
		getUserBmi()
			.then(response => {
				const bmiArray = response.map(item => item.bmi);
				const dateArray = response.map(item => item.updateTime.slice(0, 10));
				BMI.value = bmiArray[bmiArray.length - 1];
				BMIAnalyse(BMI.value);
				option.series[0].data = bmiArray;
				option.xAxis.data = dateArray;
				myChart.setOption(option);
			})
			.catch(error => {
				console.log(error);
			});
	})
</script>

<style lang="scss" scoped>
	.chart-line {
		display: flex;
		flex-direction: column;
		margin-left: 30rpx;
		margin-top: 30rpx;
		margin-bottom: 30rpx;
		padding-bottom: 16rpx;
		width: 690rpx;
		border-radius: 32rpx;
		opacity: 1;
		background: #ffffff;
		// border: 5rpx solid #000000;
		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);
		z-index: 1;
	}

	.BMI-advice {
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

		.health-data {
			display: flex;
			box-sizing: border-box;
			flex-direction: column;
			justify-content: space-between;
			margin-top: 16rpx;
			padding: 16rpx 24rpx 16rpx 24rpx;
			border-radius: 32rpx;
			width: 642rpx;
			opacity: 1;
			background: #ECEFF1;
		}

		.advice {
			display: flex;
			box-sizing: border-box;
			flex-direction: column;
			justify-content: space-between;
			margin-top: 24rpx;
			padding: 16rpx 24rpx 16rpx 24rpx;
			border-radius: 32rpx;
			width: 642rpx;
			opacity: 1;
			background: #ECEFF1;
		}

		.text {
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			margin-bottom: 6rpx;
		}
	}
</style>