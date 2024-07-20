<template>
	<view>
	<Navbar :showLeft = "true" title="运动周报"></Navbar>
		<template>
			<view>
				<week></week>
			</view>
			<view class="reach">
				<view class="trophy">
					<view class="back">
						<image class="image" v-if="reachDays > 3" src="@/static/icon/iconList/user/weekly/奖杯.png"></image>
						<image class="image" v-else src="@/static/icon/iconList/user/weekly/灰奖杯.png"></image>
					</view>
				</view>
				<view class="reach-days">达成目标天数：{{reachDays}}天</view>
				<view class="reach-fight">{{slogan}}</view>
			</view>
			<view class="reach-data">
				<view class="chart-bar" >
					<l-echart ref="chartRef1" ></l-echart> 
				</view>
				<view class="reach-text">
					<!-- 热量摄入显示区域 -->
					<view style="font-size: 36rpx;">
						消耗总热量
						<image style="width: 36rpx;height: 36rpx;margin-left: -16rpx;"
							src="@/static/icon/iconList/title/fire.svg" />
					</view>
					<view style="font-size: 40rpx;white-space: nowrap;">{{outkcal}}千卡</view>
					<view style="font-size: 36rpx;">≈{{conversionkcal(outkcal)}}个鸡腿</view>
				</view>
			</view>
			<view class="consume">
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
	import { ref, onMounted, watch} from 'vue';
	const echarts = require('../../uni_modules/lime-echart/static/echarts.min.js');
	import {currentWeekStore} from "@/subpkg/stores/currentWeek.js";
	const currentWeek = currentWeekStore();
	import {weekSport} from '../api/weekly/weekly.js';
	import { targetStore } from '@/stores/target.js';
	const target = targetStore();
	const weekTarget = target.motionTarget * 7;

	let reachDays = ref(0);
	let slogan = ref(' ');
	let reachFight = (e) =>{
		switch(e){
			case 0:
			case 1:
			case 2:
			case 3: return "继续努力，下周要加油哦"; break;
			case 4:
			case 5: return "继续加油，能做的更好"; break;
			case 6:
			case 7: return "状态不错，请继续保持"; break;
		}
	};
	
	//实时摄入的热量
	const outkcal = ref(0);
	
	//热量换算
	const conversionkcal = (e) => {
		let unit = 0;
		unit = parseFloat(e / 150).toFixed(1);
		return unit;
	};
	
	var option1 = {
	  textStyle: {
	      fontStyle: 'italic',
			  fontFamily: 'Impact',
			},
	  series: [
	    {
	      type: 'gauge',
	      min: 0,
	      max: weekTarget,
		  radius: '100%',
		  center: ["50%", "57%"],
	      splitNumber: 0,
	      itemStyle: {
	        color: '#FFCD9E',
	        shadowColor: '#FFCD9E',
	        shadowBlur: 2,
	        shadowOffsetX: 2,
	        shadowOffsetY: 2
	      },
	      progress: {
	        show: true,
	        roundCap: true,
	        width: 15
	      },
	      pointer: {
	        icon: 'path://M2090.36389,615.30999 L2090.36389,615.30999 C2091.48372,615.30999 2092.40383,616.194028 2092.44859,617.312956 L2096.90698,728.755929 C2097.05155,732.369577 2094.2393,735.416212 2090.62566,735.56078 C2090.53845,735.564269 2090.45117,735.566014 2090.36389,735.566014 L2090.36389,735.566014 C2086.74736,735.566014 2083.81557,732.63423 2083.81557,729.017692 C2083.81557,728.930412 2083.81732,728.84314 2083.82081,728.755929 L2088.2792,617.312956 C2088.32396,616.194028 2089.24407,615.30999 2090.36389,615.30999 Z',
	        length: '75%',
	        width: 8,
	        offsetCenter: [0, '15%']
	      },
	      axisLine: {
	        roundCap: true,
	        lineStyle: {
				color:[[1,'#EBEDED']],
	          width: 15
	        }
	      },
	      axisTick: {
	        show:false,
	        splitNumber: 2,
	        lineStyle: {
	          width: 2,
	          color: '#999'
	        }
	      },
	      splitLine: {
	        show:false,
	        length: 12,
	        lineStyle: {
	          width: 3,
	          color: '#999'
	        }
	      },
	       axisLabel: {
	        axisTick: {//坐标轴刻度
		   			show: false
		   		},
	        show: false,
	        distance: 30,
	        color: '#999',
	        fontSize: 20
	      },
	      detail: {
	        formatter: function (value) {
	          return '{value|' + value.toFixed(0) + '}{unit|Kcal}';
	        },
	        rich: {
	          value: {
	            fontSize: 20,
	            fontWeight: 'bolder',
	            color: '#3D3D3D',
	          },
	          unit: {
	            fontSize: 10,
	            color: '#999',
	            padding: [0, 0, -5, 5]
	          }
	        }
	      },
	      data: [
	        {
	          value: 0
	        }
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
			formatter: function(params){
				return params.name + "：消耗"+ params.value.toFixed(1) + "千卡";
			}
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
		},300);
		watch(() => currentWeek.startTime, () => {
			clearTimeout(timeoutId);
			timeoutId = setTimeout(() => {
				weekSport(currentWeek.startTime,currentWeek.endTime)
					.then(response => {
						outkcal.value = response.sportConsumptionSum.toFixed(1);
						option1.series[0].data[0].value = response.sportConsumptionSum;
						option2.series[0].data = response.kcalList;
						reachDays.value = response.kcalList.reduce(
							(sum, item) => item >= target.motionTarget ? sum + 1 : sum , 0
						);
						slogan.value = reachFight(reachDays.value);
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
	})
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
		background: #B8FAEA;
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
		.chart-bar {
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
	.consume {
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
