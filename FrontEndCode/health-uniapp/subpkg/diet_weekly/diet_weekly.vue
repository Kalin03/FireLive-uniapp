<template>
	<view>
	<Navbar :showLeft = "true" title="饮食周报"></Navbar>
		<template>
			<view>
				<week></week>
			</view>
			<view class="reach">
				<view class="trophy">
					<view class="back">
						<image class="image" v-if="reachItems > 3" src="@/static/icon/iconList/user/weekly/奖杯.png"></image>
						<image class="image" v-else src="@/static/icon/iconList/user/weekly/灰奖杯.png"></image>
					</view>
				</view>
				<view class="reach-days">达成营养目标项：{{reachItems}}项</view>
				<view class="reach-fight">{{slogan}}</view>
			</view>
			<view class="reach-data">
				<view class="chart-radar" >
					<l-echart ref="chartRef1" ></l-echart>
				</view>
				<view class="reach-text">
					<!-- 热量摄入显示区域 -->
					<view style="font-size: 36rpx;">
						摄入总热量
						<image style="width: 36rpx;height: 36rpx;margin-left: -16rpx;"
							src="@/static/icon/iconList/title/fire.svg" />
					</view>
					<view style="font-size: 40rpx;white-space: nowrap;">{{inkcal}}千卡</view>
					<view style="font-size: 36rpx;">≈{{conversionkcal(inkcal)}}个鸡腿</view>
				</view>
			</view>
			<view class="ingestion"> 
				<view class="chart-bar" >
					<l-echart ref="chartRef2" ></l-echart>
				</view>
			</view>
		</template>
		
	</view>
</template>

<script setup>
	import Navbar from "../../components/Navbar.vue";
	import week from "../components/statistic-week/statistic-week.vue";
	import { ref, onMounted, watch } from 'vue';
	const echarts = require('../../uni_modules/lime-echart/static/echarts.min.js');
	
	import {currentWeekStore} from "@/subpkg/stores/currentWeek.js";
	const currentWeek = currentWeekStore();
	import {weekFood} from '../api/weekly/weekly.js';
	import { targetStore } from '@/stores/target.js';
	const target = targetStore();
	const weekTarget = [
		target.dietTarget[0] * 7,
		target.dietTarget[1] * 7,
		target.dietTarget[2] * 7,
		target.dietTarget[3] * 7,
		target.dietTarget[4] * 7,
		target.dietTarget[5] * 7];
	
	//如果大于目标值，则重新赋值为目标值
	const reCountIntakeSum = () => {
		for(let i = 0;i < 6;i++){
			if(option1.series[0].data[0].value[i] > weekTarget[i]){
				option1.series[0].data[0].value[i] = weekTarget[i];
			}
		}
	}
	
	let reachItems = ref(0);
	let slogan = ref(' ');
	let reachFight = (e) =>{
		switch(e){
			case 0:
			case 1:
			case 2: return "健康的生活离不开健康的饮食"; break;
			case 3: 
			case 4: return "健康饮食最重要的是均衡"; break;
			case 5: 
			case 6: return "状态不错，请继续保持"; break;
		}
	};
	
	//实时摄入的热量
	const inkcal = ref(0);
	
	//热量换算
	const conversionkcal = (e) => {
		let unit = 0;
		unit = parseFloat(e / 150).toFixed(1);
		return unit;
	};
	
	var option1 = {
		textStyle: {
			fontStyle: 'italic',
			fontWeight: 'bold',
		},
	   radar: {
	      radius: 60,
	      startAngle: 0,
	      splitNumber: 1,
	      nameGap: 4,
	      center: ['46%', '50%'],
	      axisLine: {
			show: false,
	      },
	      splitLine: {
			show: false,
	      },
	      splitArea: {
	        show: true,
	        areaStyle: {
				color: '#EBEDED',
				opacity: 1,
	        }
	      },
	      name:{
	        textStyle: {
	          fontSize: 12,
	          color: '#3D3D3D',
	        },
	      },
	      indicator: [{
					name: '碳水',
					max: weekTarget[0]
				},
				{
					name: '维生素',
					max: weekTarget[1]
				},
				{
					name: '脂肪',
					max: weekTarget[2]
				},
				{
					name: '水',
					max: weekTarget[3]
				},
				{
					name: '无机盐',
					max: weekTarget[4]
				},
				{
					name: '蛋白质',
					max: weekTarget[5]
				}
			]
	    },
		
	    series: [
	      {
	        name: '营养摄入',
	        type: 'radar',
	        symbolSize: 0,
	        data: [
	          {
				//雷达图各项数据
	            value: [0, 0, 0, 0, 0, 0],
	            itemStyle: {
	              normal: {
	                color: '#54E0F3',
	              }
	            },
	            areaStyle: {
	              normal: {
	                opacity: 1,
	                color: '#54E0F3'
	              }
	            },
	          },
	        ]
	      }
	    ]
	};
	
	var option2 = {
	   xAxis: {
	       type: 'category',
	       data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
	       axisLabel: {
	         color: "#000",
	         fontSize: 12,
	       },
	       axisLine: {
	         show: false,
	       },
	       axisTick: {//坐标轴刻度
	   			show: false
	   		}
	     },
	     grid: {
			top: '5%',//相对位置
			left: '2%',
			right: '2%',
			height: '80%',//整体高度
			width: '96%'
	     },
	     yAxis: [{
	   			type: 'value',
	   			splitLine: {//坐标轴在grid区域中的分割线
	   				show: false
	   			},
	   			axisLabel: {//坐标轴刻度标签
	   				show: false
	   			},
	   		}],
		tooltip: {
			formatter: '{b}：摄入{c}千卡',
		},
	     series: [
	       {
	         data: [0, 0, 0, 0, 0, 0, 0],
	         color: '#8CD88E',
	         barWidth: 24,//坐标轴宽度
	         type: 'bar',
	         showBackground: true,
	         backgroundStyle: {
	           borderRadius: 8,
	           color: '#F9F9FB',
	         },
	         itemStyle: {
	           barBorderRadius: 8//柱子的圆角
	         },
	       }
	     ]
	};
	
	
	const chartRef1 = ref(null);
	let myChart1 =  null;
	const chartRef2 = ref(null);
	let myChart2 = null;
	let timeoutId = null;
	
	onMounted( ()=>{
		// 组件能被调用必须是组件的节点已经被渲染到页面上
		setTimeout(async()=>{
		    if(!chartRef1.value) return;
			if(!chartRef2.value) return;
			myChart1 = await chartRef1.value.init(echarts);
			myChart2 = await chartRef2.value.init(echarts);
		    myChart1.setOption(option1);
			myChart2.setOption(option2);
		},0);
		watch(() => currentWeek.startTime, () => {
			clearTimeout(timeoutId); 
			timeoutId = setTimeout(() => {
				weekFood(currentWeek.startTime,currentWeek.endTime)
					.then(response => {
						reachItems.value = 0;
						option1.series[0].data[0].value[0] = response.carbohydrates;
						option1.series[0].data[0].value[1] = response.vitamins;
						option1.series[0].data[0].value[2] = response.fats;
						option1.series[0].data[0].value[3] = response.water / 1000;
						option1.series[0].data[0].value[4] = response.minerals;
						option1.series[0].data[0].value[5] = response.proteins;
						option2.series[0].data = response.kcalList;
						inkcal.value = response.kcalList.reduce((sum, item) => item + sum, 0);
						for(let i = 0 ; i < 6 ; i++ ){
							if(option1.series[0].data[0].value[i] >= target.dietTarget[i]){
								reachItems.value += 1;
							}
						}
						reCountIntakeSum();
						slogan.value = reachFight(reachItems.value);
						//console.log(response);
						
						myChart1.setOption(option1);
						myChart2.setOption(option2);
					})
					.catch(error => {
						console.log(error);
					});
			},1000);
		},
		{
			immediate: true
		});
	});
	
</script>

<style lang="scss" scoped>
	.reach {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		margin-left: 30rpx;
		margin-top: 16rpx;
		padding-bottom: 16rpx;
		width: 690rpx;
		border-radius: 32rpx;
		opacity: 1;
		background: #E7FCDC;
		// border: 5rpx solid #000000;
		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);
		.trophy {
			margin-top: 16rpx;
			.back {
				width: 180rpx;
				height: 180rpx;
				border-radius: 58rpx;
				opacity: 1;
				background: #FFFFFF;
				.image {
					margin-left: 4rpx;
					margin-top: 4rpx;
					width: 172rpx;
					height: 172rpx;
				}
			}
		}
		.reach-days {
			margin-top: 16rpx;
			font-size: 30rpx;
			color: #3D3D3D;
		}
		.reach-fight {
			font-weight: bold;
			color: #3D3D3D;
			margin-top: 12rpx;
			font-size: 30rpx;
		}
	}
	.reach-data {
		display: flex;
		box-sizing: border-box;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		margin-left: 30rpx;
		margin-top: 30rpx;
		padding: 16rpx;
		width: 690rpx;
		border-radius: 32rpx;
		opacity: 1;
		background: #FFFFFF;
		// border: 5rpx solid #000000;
		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);
		.chart-radar {
			width: 350rpx;
			height: 280rpx;
			border-radius: 32rpx;
		}
		.reach-text {
			display: flex;
			box-sizing: border-box;
			flex-direction: column;
			justify-content: space-around;
			align-items: center;
			font-weight: bold;
			color: #3D3D3D;
			width: 290rpx;
			height: 280rpx;
			border-radius: 32rpx;
			background: #EBEDED;
		}
	}
	.ingestion {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		margin-left: 30rpx;
		margin-top: 30rpx;
		width: 690rpx;
		border-radius: 32rpx;
		opacity: 1;
		background: #FFFFFF;
		// border: 5rpx solid #000000;
		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);
		.chart-bar {
			height: 360rpx;
			width: 690rpx;
		}
	}
</style>
