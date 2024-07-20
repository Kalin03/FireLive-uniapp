<template>
	<view>
		<Navbar :showLeft="false" title="个人健康管理"></Navbar>
		<template>
			<view style="font-family: 'YouSheBiaoTiHei',sans-serif; color: #3D3D3D;" @touchstart="handleTouchStart"
				@touchmove="handleTouchMove" @touchend="handleTouchEnd">
				<!-- 摄入和消耗的标题，滑动后更改样式 -->
				<view class="title">
					<image class="title-text" @click="leftSlider()"
						:src="left?'../../static/icon/iconList/title/in.svg':'../../static/icon/iconList/title/selectIn.svg'">
					</image>
					<image class="title-text" @click="rightSlider()"
						:src="left?'../../static/icon/iconList/title/selectOut.svg':'../../static/icon/iconList/title/out.svg'">
					</image>
				</view>
				<!-- 滑动条 -->
				<view class="slider" :class="{'left-slider':left,'right-slider':right}"></view>
				<!-- 滑动区域 -->
				<view class="slider-area" :class="{'left-slider-area':left,'right-slider-area':right}">
					<!-- 图标和数据区域 -->
					<view class="statistics">
						<!-- 饮食区域 -->
						<view class="echarts-diet">
							<!-- 饮食营养雷达图 -->
							<view class="chart-radar">
								<l-echart ref="chartRef1"></l-echart>
							</view>
							<view class="diet-data" @click="popupDietData.open()">
								<!-- 热量摄入显示区域 -->
								<view class="diet-data-text">
									<view style="font-size: 40rpx;">
										摄入总热量
										<image style="width: 40rpx;height: 40rpx;margin-left: -16rpx;"
											src="@/static/icon/iconList/title/fire.svg" />
									</view>
									<view style="font-size: 48rpx;white-space: nowrap;">{{inkcal}}千卡</view>
									<view style="font-size: 40rpx;white-space: nowrap;">≈{{conversionkcal(inkcal)}}个鸡腿</view>
									<view style="font-size: 20rpx;">点击查看详情</view>
								</view>
							</view>
						</view>
						<!-- 运动区域 -->
						<view class="echarts-motion">
							<!-- 运动数据环形图 -->
							<view class="chart-bar">
								<l-echart ref="chartRef2"></l-echart>
							</view>
							<view class="motion-data" @click="popupMotionData.open()">
								<!-- 热量消耗显示区域 -->
								<view class="motion-data-text">
									<view style="font-size: 40rpx;">
										消耗总热量
										<image style="width: 40rpx;height: 40rpx;margin-left: -16rpx;"
											src="@/static/icon/iconList/title/fire.svg" />
									</view>
									<view style="font-size: 48rpx;white-space: nowrap;">{{outkcal}}千卡</view>
									<view style="font-size: 40rpx;white-space: nowrap;">≈{{conversionkcal(outkcal)}}个鸡腿</view>
									<view style="font-size: 20rpx;">点击查看详情</view>
								</view>

							</view>

						</view>
					</view>
					<!-- 食物和运动的图标列表 -->
					<view class="diet-motion">
						<!-- 食物列表 -->
						<view class="diet">
							<!-- 标题 -->
							<view class="title-child">
								<image style="width: 100rpx;height: 50rpx;"
									src="@/static/icon/iconList/title/diet.svg"></image>
							</view>
							<!-- 添加框 -->
							<view class="add-area">
								<!-- 已食用食物 -->
								<scroll-view class="add-foods" scroll-x>
									<view class="food-item" v-for="(item,index) in dietQualityItemList" :key="index">
										<view
											style="display: flex;justify-content: center;align-items: center;height: 100%;">
											{{item.foodName}}
										</view>
										<!-- 移除按钮 -->
										<view class="remove">
											<uni-icons type="clear" size="40rpx" color="#8D8D8D"
												@click="removeDietQualityItem(index)"></uni-icons>
										</view>
									</view>
								</scroll-view>
								<!-- 提交按钮 -->
								<view class="submit" @click="submitDietQualityItemList()">
									<uni-icons type="plusempty" size="84rpx" color="#8D8D8D"></uni-icons>
								</view>
							</view>
							<!-- 食物图标区域 -->
							<swiper class="swiper-area" vertical="true">
								<swiper-item v-for="(item,index) in dietDataItemList" :key="index">
									<view class="view">
										<view class="row" v-for="(item1,index1) in item" :key="index1"
											:style="style(index1)">
											<!-- 点击后触发食物popup的open事件 -->
											<view class="item" style="background-color: #ADFFC3;"
												v-for="(item2,index2) in item1" :key="index2" @click="openDiet(item2)">
												<view style="white-space: pre-line;">{{textWrap(item2.foodName)}}</view>
											</view>
										</view>
									</view>
								</swiper-item>
							</swiper>
						</view>
						<!-- 运动列表 -->
						<view class="motion">
							<!-- 标题 -->
							<view class="title-child">
								<image style="width: 100rpx;height: 50rpx;"
									src="@/static/icon/iconList/title/motion.svg">
								</image>
							</view>
							<!-- 添加框 -->
							<view class="add-area">
								<scroll-view class="add-sports" scroll-x>
									<view class="sport-item" v-for="(item,index) in motionConsumptionItemList"
										:key="index">
										<view
											style="display: flex;justify-content: center;align-items: center;height: 100%;">
											{{item.sportName}}
										</view>
										<!-- 移除按钮 -->
										<view class="remove">
											<uni-icons type="clear" size="40rpx" color="#8D8D8D"
												@click="removeMotionConsumptionItem(index)"></uni-icons>
										</view>
									</view>
								</scroll-view>
								<!-- 提交按钮 -->
								<view class="submit" @click="submitMotionConsumptionItemList()">
									<uni-icons type="plusempty" size="84rpx" color="#8D8D8D"></uni-icons>
								</view>
							</view>
							<!-- 运动图标区域 -->
							<swiper class="swiper-area" vertical="true">
								<swiper-item v-for="(item,index) in motionDataItemList" :key="index">
									<view class="view">
										<view class="row" v-for="(item1,index1) in item" :key="index1"
											:style="style(index1)">
											<!-- 点击后触发运动popup的open事件 -->
											<view class="item" style="background-color: #95F3F9;"
												v-for="(item2,index2) in item1" :key="index2"
												@click="openMotion(item2)">
												<view style="white-space: pre-line;">{{textWrap(item2.sportName)}}</view>
											</view>
										</view>
									</view>
								</swiper-item>
							</swiper>
						</view>
					</view>
				</view>
			</view>
			<!-- 打卡区域 -->
			<view class="check-in-area">
				<!-- 标题 -->
				<view class="title-child">
					<image style="width: 100rpx;height: 50rpx;"
						src="@/static/icon/iconList/title/checkIn.svg"></image>
				</view>
				<!-- 打卡图标区域 -->
				<view class="check-in-data">
					<!-- 点击后修改打卡的值，并触发打卡动画-->
					<view class="check-in-item">
						<view class="check-in-img" :class="{'checkIn':checkItem.earlyRising}" @click="checkImg(1)">
							<image style="width:98rpx ;height: 92rpx;"
								src="@/static/icon/iconList/check_in/morning.png" />
						</view>
						<view class="check-in-tag">
							<image style="width:80rpx ;height: 40rpx;"
								src="@/static/icon/iconList/check_in/morning.svg" />
						</view>
					</view>
					<view class="check-in-item">
						<view class="check-in-img" :class="{'checkIn':checkItem.running}" @click="checkImg(2)">
							<image style="width:110rpx ;height: 100rpx;"
								src="@/static/icon/iconList/check_in/run.png" />
						</view>
						<view class="check-in-tag">
							<image style="width:80rpx ;height: 40rpx;"
								src="@/static/icon/iconList/check_in/run.svg" />
						</view>
					</view>
					<view class="check-in-item">
						<view class="check-in-img" :class="{'checkIn':checkItem.fitness}" @click="checkImg(3)">
							<image style="width:125rpx ;height: 80rpx;"
								src="@/static/icon/iconList/check_in/fitness.png" />
						</view>
						<view class="check-in-tag">
							<image style="width:80rpx ;height: 40rpx;"
								src="@/static/icon/iconList/check_in/fitness.svg" />
						</view>
					</view>
					<view class="check-in-item">
						<view class="check-in-img" :class="{'checkIn':checkItem.earlyBedtime}" @click="checkImg(4)">
							<image style="width:92rpx ;height: 92rpx;"
								src="@/static/icon/iconList/check_in/bedEarly.png" />
						</view>
						<view class="check-in-tag">
							<image style="width:80rpx ;height: 40rpx;"
								src="@/static/icon/iconList/check_in/bedEarly.svg" />
						</view>
					</view>
				</view>
			</view>
		</template>
		<!-- popup弹出层 -->
		<view style="font-family: 'YouSheBiaoTiHei'; color: #3D3D3D;">
			<!-- 摄入详情弹窗 -->
			<uni-popup ref="popupDietData" type="center">
				<!-- 各项营养的列表 -->
				<view class="diet-data-list">
					<view class="list-text" >
						<view class="nutrition-list" >
							<view>营养成分</view>
							<view v-for="(item,index) in dietDataList" :key="index">
								{{item.name}}({{item.unit}})
							</view>
						</view>
						<view class="intake-list" >
							<view>营养摄入</view>
							<view v-for="(item,index) in dietDataList" :key="index">
								{{item.data}}
							</view>
						</view>
						<view class="target-list" >
							<view>目标</view>
							<view v-for="(item,index) in target.dietTarget" :key="index">
								{{item}}
							</view>
						</view>
					</view>
				</view>
			</uni-popup>
			<!-- 消耗详情弹窗 -->
			<uni-popup ref="popupMotionData" type="center">
				<view class="motion-data-list">
					<view class="title">
						<view >
							<view>目标</view>
							<view>{{target.motionTarget}}Kcal</view>
						</view>
						<view >
							<view>热量消耗</view>
							<view>{{outkcal}}Kcal</view>
						</view>
					</view>
					<view class="motion-list">
						<view>运动详情</view>
						<view v-if="outkcal > 0">
							<view class="motion-data" v-for="(item,index) in motionData" :key="index">
								<view class="motion-sports" >
									<uni-dateformat :date=item.updateTime format="hh:mm"></uni-dateformat>
									<view>{{item.sportName}}</view>
									<view>{{item.sportNumber}}{{item.sportUnit}}</view>
									<view>消耗{{item.sportConsumption}}Kcal</view>
								</view>
							</view>
						</view>
						<view v-else class="motion-null"> 暂无数据 </view>
					</view>
				</view>
			</uni-popup>
			<!-- 食物的弹窗 -->
			<uni-popup ref="popupDiet" type="bottom">
				<view class="popup-diet" :class="{'move-up-popup':moveUpPopup}">
					<!-- 食物标题区域 -->
					<view class="popup-title">
						<view class="title-head">
							<!-- 食物名称 -->
							<view class="title-name">
								<view>{{dietItem.foodName}}</view>
							</view>
							<!-- 食物重量输入框 -->
							<view class="title-input">
								<view style="font-size: 32rpx;">已食用</view>
								<input class="input-box" type="number" v-model="dietQuality" maxlength="5"
									@focus="moveUpPopup = true" @blur="moveUpPopup = false" :adjust-position="false"
									@input="countDietQuality()" />
								<view>g</view>
							</view>
						</view>
						<!-- 食物提示文字 -->
						<view class="food-tip">{{dietItem.foodTip}}</view>
					</view>
					<!-- 营养成分列表 -->
					<view class="component">
						<view class="component-area">
							<view class="component-item">
								<view class="component-name">
									<view>碳水</view>
								</view>
								<!-- 每100g所含营养 -->
								<view class="component-value">{{dietItem.carbohydrates+'g'}}</view>
								<!-- 根据食物重量计算的营养 -->
								<view class="component-value">{{dietQualityItem.carbohydrates +'g'}}</view>
							</view>
							<view class="component-item">
								<view class="component-name">
									<view>维生素</view>
								</view>
								<view class="component-value">{{dietItem.vitamins+'mg'}}</view>
								<view class="component-value">{{dietQualityItem.vitamins +'mg'}}</view>
							</view>
							<view class="component-item">
								<view class="component-name">
									<view>脂肪</view>
								</view>
								<view class="component-value">{{dietItem.fats+'g'}}</view>
								<view class="component-value">{{dietQualityItem.fats +'g'}}</view>
							</view>
							<view class="component-item">
								<view class="component-name">
									<view>无机盐</view>
								</view>
								<view class="component-value">{{dietItem.minerals+'mg'}}</view>
								<view class="component-value">{{dietQualityItem.minerals +'mg'}}</view>
							</view>
							<view class="component-item">
								<view class="component-name">
									<view>蛋白质</view>
								</view>
								<view class="component-value">{{dietItem.proteins+'g'}}</view>
								<view class="component-value">{{dietQualityItem.proteins +'g'}}</view>
							</view>
						</view>
						<view class="component-every">
							<view>/100g</view>
							<view>已摄入</view>
						</view>
					</view>
					<!-- 按钮区域 -->
					<view class="button">
						<!-- 取消按钮 -->
						<view class="cancel" @click="closeDiet(0)">
							<view>取消</view>
						</view>
						<!-- 确认按钮 -->
						<view class="confirm" @click="closeDiet(1)">
							<view>确认</view>
						</view>
					</view>
				</view>
			</uni-popup>
			<!-- 运动的弹窗 -->
			<uni-popup ref="popupMotion" type="bottom">
				<!-- 运动数据显示区域 -->
				<view class="popup-motion" :class="{'move-up-popup':moveUpPopup}">
					<!-- 运动标题区域 -->
					<view class="popup-title">
						<view class="title-head">
							<!-- 运动名称 -->
							<view class="title-name">
								<view>{{motionItem.sportName}}</view>
							</view>
							<!-- 运动输入框 -->
							<!-- 不同类型的运动显示不同样式 -->
							<view v-if="!motionItem.sportPercent" class="title-input">
								<view style="font-size: 32rpx;">已运动</view>
								<!-- 运动时间输入框 -->
								<input class="input-box" type="number" v-model="motionTime" maxlength="4"
									@focus="moveUpPopup = true" @blur="moveUpPopup = false" :adjust-position="false"
									@input="countMotionCal()" />
								<view>min</view>
							</view>
						</view>
						<!-- 运动提示文字 -->
						<view class="motion-tip">{{motionItem.sportTip}}</view>
					</view>
					<!-- 运动数据区域 -->
					<view class="motion-data-area">
						<!-- 运动强度 -->
						<view v-if="!motionItem.sportPercent" class="intensity-area">
							<view class="intensity" v-for="(item,index) in intensityButton" :key="index"
								:class="{'on-focus': onFocusIntensity === index}" @click="intensityOnFocus(index)">
								<view>{{item}}</view>
							</view>
						</view>
						<!-- 运动数量和重量 -->
						<view v-else class="numb-weight">
							<view style="font-size: 32rpx;">已锻炼</view>
							<view class="data-area-input">
								<input class="input-box" type="number" v-model="motionNumb" maxlength="4"
									@focus="moveUpPopup = true" @blur="moveUpPopup = false" :adjust-position="false"
									@input="countMotionCal()" />
								<view>个</view>
							</view>
							<view class="data-area-input">
								<input class="input-box" type="number" v-model="motionWeight" maxlength="4"
									@focus="moveUpPopup = true" @blur="moveUpPopup = false" :adjust-position="false"
									@input="countMotionCal()" />
								<view>kg</view>
							</view>
						</view>
						<!-- 运动消耗 -->
						<view class="motion-consumption">
							<view v-if="motionConsumptionItem.sportConsumption > 9">
								已消耗{{motionConsumptionItem.sportConsumption}}千卡
							</view>
							<view v-else>
								已消耗{{motionConsumptionItem.sportConsumption * 1000}}卡
							</view>
						</view>
					</view>

					<!-- 按钮区域 -->
					<view class="button">
						<!-- 取消按钮 -->
						<view class="cancel" @click="closeMotion(0)">
							<view>取消</view>
						</view>
						<!-- 确认按钮 -->
						<view class="confirm" @click="closeMotion(1)">
							<view>确认</view>
						</view>
					</view>
				</view>
			</uni-popup>
		</view>
	</view>
</template>

<script setup>
	import Navbar from "../../components/Navbar.vue";
	import {
		useUserInfoStore
	} from "@/stores/userinfo.js";
	import {
		loginStatusStore
	} from '@/stores/loginStatus.js';

	const echarts = require('../../uni_modules/lime-echart/static/echarts.min.js');
	
	import {
		ref,
		onMounted,
		onBeforeMount,
		nextTick,
		watch
	} from 'vue';
	import {
		getFoodInfo,
		postFoodConsum,
		getIntakeSum,
		getSportInfo,
		postSportConsum,
		getConsumSum,
		postFindCheckin,
		postCheckinInfo,
		getShowFoodInfo,
		getShowSportInfo,
		getTarget,
		sportConsumptionInfo
	} from '../../api/index.js';
	import { targetStore } from '@/stores/target.js';
	import {
		isLogin,
		isToken
	} from '../../components/isLogin.js';

	const userInfoStore = useUserInfoStore();
	const loginStatus = loginStatusStore();
	const target = targetStore();
	
	//实例挂载前
	onBeforeMount(() => {
		//判断是否存在token
		isToken()
			.then(response => {
				if (!response) {
					//获得展示食物信息
					getShowFoodInfo()
						.then(response => {
							dietItemList = response;
							// 调用把数据 push 进渲染运动布局样式的二维数组的函数
							formattedDietData();
						})
						.catch(error => {
							console.error('请求食物信息失败：', error);
						});
					//获得展示运动信息
					getShowSportInfo()
						.then(response => {
							motionItemList = response;
							formattedMotionData();
						})
						.catch(error => {
							console.error('请求运动信息失败', error);
						})
				}
			})
			.catch(error => {
				console.log(error);
			});
	});

	let timeoutId = null;
	//在页面加载完事件
	onMounted(() => {
		// 组件能被调用必须是组件的节点已经被渲染到页面上
		// 把图标实例渲染上去
		setTimeout(async () => {
			if (!chartRef1.value) return;
			if (!chartRef2.value) return;
			myChart1 = await chartRef1.value.init(echarts);
			myChart2 = await chartRef2.value.init(echarts);
			myChart1.setOption(option1);
			myChart2.setOption(option2);
		}, 0);
		//监听打卡是否变化 3秒后提交数据
		watch(checkItem.value, () => {
			clearTimeout(timeoutId);
			timeoutId = setTimeout(() => {
				//console.log(checkItem.value);
				//向后端发送请求
				postCheckinInfo(checkItem.value);
			}, 1500);
		});
		//监听登录状态
		watch(loginStatus, () => {
			if (loginStatus.loginStatus) {
				//先清空之前的食物和运动信息
				dietDataItemList.value = [
					[]
				];
				motionDataItemList.value = [
					[]
				];
				//变为登录
				getFoodInfo()
					.then(response => {
						dietItemList = response;
						// 调用把数据 push 进渲染食物布局样式的二维数组的函数
						formattedDietData();
					})
					.catch(error => {
						console.error('请求食物信息失败：', error);
					});
				//获得运动信息
				getSportInfo()
					.then(response => {
						motionItemList = response;
						// 调用把数据 push 进渲染运动布局样式的二维数组的函数
						formattedMotionData();
					})
					.catch(error => {
						console.error('请求运动信息失败：', error);
					});
				//获得打卡信息
				postFindCheckin()
					.then(response => {
						checkItem.value.earlyRising = response.earlyRising;
						checkItem.value.running = response.running;
						checkItem.value.fitness = response.fitness;
						checkItem.value.earlyBedtime = response.earlyBedtime;
					})
					.catch(error => {
						console.error('请求打卡信息失败：', error);
					});
				
				//刷新统计图
				async function refreshChart() {
					await Promise.all([
						//获得摄入
						getIntakeSum()
							.then(response => {
								//计算
								countIntakeSum(response);
							})
							.catch(error => {
								console.log(error);
							}), 
						//获得消耗
						getConsumSum()
							.then(response => {
								option2.series[0].data[0].value = response.sportConsumption;
								outkcal.value = option2.series[0].data[0].value.toFixed(1);
							})
							.catch(error => {
								console.log(error);
							}),
						//获得目标值
						getTarget()
							.then(response => {
								target.dietTarget[0] = response.carbohydrates;
								target.dietTarget[1] = response.vitamins;
								target.dietTarget[2] = response.fats;
								target.dietTarget[3] = response.water;
								target.dietTarget[4] = response.minerals;
								target.dietTarget[5] = response.proteins;
								target.motionTarget = response.sportConsumption;
								option1.radar.indicator[0].max = response.carbohydrates;
								option1.radar.indicator[1].max = response.vitamins;
								option1.radar.indicator[2].max = response.fats;
								option1.radar.indicator[3].max = response.water;
								option1.radar.indicator[4].max = response.minerals;
								option1.radar.indicator[5].max = response.proteins;
								option2.series[0].max = response.sportConsumption;
							})
							.catch(error => {
								console.log(error);
							}),
							//获得当日运动信息
							sportConsumptionInfo()
								.then(response => {
									motionData.value = response;
								})
								.catch(error => {
									console.log(error);
								}),
						]);
					// 三个函数都执行完毕后，执行别的命令
					
					reCountIntakeSum();
					myChart1.setOption(option1);
					myChart2.setOption(option2);
				}
				refreshChart();
			} else {
				const clearData = () => {
					//先清空之前的食物和运动信息
					dietDataItemList.value = [
						[]
					];
					motionDataItemList.value = [
						[]
					];
					//变为未登录 更新页面信息
					//打卡
					checkItem.value.earlyRising = 0;
					checkItem.value.running = 0;
					checkItem.value.fitness = 0;
					checkItem.value.earlyBedtime = 0;
					//雷达图
					target.dietTarget = [];
					target.motionTarget = '';
					motionData.value = [];
					option1.series[0].data[0].value = [0, 0, 0, 0, 0, 0];
					inkcal.value = 0;
					dietDataList.value[0].data = 0;
					dietDataList.value[1].data = 0;
					dietDataList.value[2].data = 0;
					dietDataList.value[3].data = 0;
					dietDataList.value[4].data = 0;
					dietDataList.value[5].data = 0;
					//仪表图
					option2.series[0].data[0].value = 0;
					outkcal.value = 0;
				};
				
				//获得展示食物信息
				getShowFoodInfo()
					.then(response => {
						dietItemList = response;
						// 调用把数据 push 进渲染运动布局样式的二维数组的函数
						formattedDietData();
					})
					.catch(error => {
						console.error('请求食物信息失败：', error);
					});;
				//获得展示运动信息
				getShowSportInfo()
					.then(response => {
						motionItemList = response;
						formattedMotionData();
					})
					.catch(error => {
						console.error('请求运动信息失败', error);
					})
				
				//刷新统计图
				async function refreshChart() {
					await Promise.all([clearData()]);
					// 函数都执行完毕后，执行别的命令
					myChart1.setOption(option1);
					myChart2.setOption(option2);
				}
				refreshChart();	
			}
		})
	});

	//1是雷达图 2是环形图
	const chartRef1 = ref(null);
	let myChart1 = null;
	const chartRef2 = ref(null);
	let myChart2 = null;
	
	let i = 0;
	//雷达图的样式和数据
	var option1 = {
		textStyle: {
			fontStyle: 'italic',
			fontWeight: 'bold',
		},
		radar: {
			radius: 65,
			startAngle: 0,
			splitNumber: 1,
			nameGap: 4,
			center: ['48%', '50%'],
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
			 name: {
			    textStyle: {
			    	fontSize: 12,
			    	color: '#3D3D3D',
			    },
			},
			indicator: [{
					name: '碳水',
					max: target.dietTarget[0] 
				},
				{
					name: '维生素',
					max: target.dietTarget[1]
				},
				{
					name: '脂肪',
					max: target.dietTarget[2]
				},
				{
					name: '水',
					max: target.dietTarget[3]
				},
				{
					name: '无机盐',
					max: target.dietTarget[4]
				},
				{
					name: '蛋白质',
					max: target.dietTarget[5]
				}
			]
		},

		series: [{
			name: '营养摄入',
			type: 'radar',
			symbolSize: 0,
			data: [{
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
			}, ]
		}]
	};

	//环形图数据
	var option2 = {
		textStyle: {
			fontStyle: 'italic',
			fontFamily: 'Impact',
		},
		series: [{
			type: 'gauge',
			//startAngle: 180,
			//endAngle: 0,
			min: 0,
			max: 240,
			radius: '90%',
			center: ["50%", "55%"],
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
					color: [
						[1, '#EBEDED']
					],
					width: 15
				}
			},
			axisTick: {
				show: false,
				splitNumber: 2,
				lineStyle: {
					width: 2,
					color: '#999'
				}
			},
			splitLine: {
				show: false,
				length: 12,
				lineStyle: {
					width: 3,
					color: '#999'
				}
			},
			axisLabel: {
				axisTick: { //坐标轴刻度
					show: false
				},
				show: false,
				distance: 30,
				color: '#999',
				fontSize: 20
			},
			detail: {
				formatter: function(value) {
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
			data: [{
				value: 0
			}]
		}]
	};

	//模拟各项食物的数据，后面靠后端传
	let dietItemList = [];

	//模拟各项运动的数据，后面靠后端传 
	let motionItemList = [];

	//实时摄入的热量
	const inkcal = ref(0);
	//实时消耗的热量
	const outkcal = ref(0);
	//热量换算
	const conversionkcal = (e) => {
		let unit = 0;
		unit = parseFloat(e / 150).toFixed(1);
		return unit;
	};

	//把食物各项数据存放到这个二维数组，控制页面的实现效果
	let dietDataItemList = ref([
		[]
	]);
	//把运动各项数据存放到这个二维数组，控制页面的实现效果
	let motionDataItemList = ref([
		[]
	]);
	//按照蜂巢样式布局来存放数据
	const formattedDietData = () => {
		const numb = dietItemList.length;
		let item = 0;
		for (let page = 0; item < numb; page++) {
			if (!dietDataItemList.value[page]) {
				dietDataItemList.value[page] = [];
			}
			for (let row = 0; row < 3; row++) {
				let newRow = [];
				for (let j = 0; j < (row % 2 === 0 ? 5 : 4); j++) {
					if (item < numb) {
						newRow.push(dietItemList[item]);
						item++;
					} else {
						break;
					}
				};
				dietDataItemList.value[page].push(newRow);
			}
		}
	};
	//按照蜂巢样式布局来存放数据
	const formattedMotionData = () => {
		const numb = motionItemList.length;
		let item = 0;
		for (let page = 0; item < numb; page++) {
			if (!motionDataItemList.value[page]) {
				motionDataItemList.value[page] = [];
			}
			for (let row = 0; row < 3; row++) {
				let newRow = [];
				for (let j = 0; j < (row % 2 === 0 ? 5 : 4); j++) {
					if (item < numb) {
						newRow.push(motionItemList[item]);
						item++;
					} else {
						break;
					}
				};
				motionDataItemList.value[page].push(newRow);
			}
		}
	};
	//每页的第二行缩进64rpx，交错布局
	const style = (row) => {
		if (row % 2 !== 0)
			return {
				'margin-left': '64rpx',
			}
	};


	//dietData来存放当天饮食的数据 之后接受后端传来的数据
	const dietData = ref([]);
	//dietDataList来存放要渲染在页面上饮食的数据
	let dietDataList = ref([{
			name: '碳水',
			unit: 'g',
			data: 0
		},
		{
			name: '维生素',
			unit: 'mg',
			data: 0
		},
		{
			name: '脂肪',
			unit: 'g',
			data: 0
		},
		{
			name: '水',
			unit: 'L',
			data: 0
		},
		{
			name: '无机盐',
			unit: 'mg',
			data: 0
		},
		{
			name: '蛋白质',
			unit: 'g',
			data: 0
		}
	]);
	//给list添加各项数据
	const setDietDataList = () => {
		dietDataList.value[0].data = dietData.value[0];
		dietDataList.value[1].data = dietData.value[1];
		dietDataList.value[2].data = dietData.value[2];
		dietDataList.value[3].data = dietData.value[3];
		dietDataList.value[4].data = dietData.value[4];
		dietDataList.value[5].data = dietData.value[5];
	}

	//监听滑动区域的左右滑动事件
	const left = ref(false);
	const right = ref(false);
	const up = ref(false);
	const down = ref(false);
	const startX = ref(0);
	const startY = ref(0);
	//存储每次的滑动事件
	const direction = ref('');

	const handleTouchStart = (event) => {
		startX.value = event.touches[0].clientX;
		startY.value = event.touches[0].clientY;
	};

	const handleTouchMove = (event) => {
		const moveX = event.touches[0].clientX;
		const moveY = event.touches[0].clientY;
		const disX = moveX - startX.value;
		const disY = moveY - startY.value;
		if (Math.abs(disX) > Math.abs(disY)) {
			direction.value = disX > 0 ? '向右滑动' : '向左滑动';
		}
		/* else {
	    direction.value = disY > 0 ? '向下滑动' : '向上滑动';
	  } */
	};

	//左右滑动事件控制页面试图
	const handleTouchEnd = (event) => {
		if (direction.value == '向左滑动') {
			left.value = true;
			right.value = false;
			up.value = false;
			down.value = false;
		} else if (direction.value == '向右滑动') {
			left.value = false;
			right.value = true;
			up.value = false;
			down.value = false;
		}
		/* else if(direction.value == '向上滑动'){
					left.value = false;
					right.value = false;
					up.value = true;
					down.value = false;
				}else {
					left.value = false;
					right.value = false;
					up.value = false;
					down.value = true;
				} */
	};
	//标题的点击事件所触发的页面滑动事件
	const leftSlider = () => {
		left.value = false;
		right.value = true;
	};
	const rightSlider = () => {
		left.value = true;
		right.value = false;
	};

	//判断是否打卡的值
	const checkItem = ref({
		"earlyRising": 0,
		"running": 0,
		"fitness": 0,
		"earlyBedtime": 0,
	});
	//点击后触发该事件，安装标签顺序传值，让打卡值取反
	const checkImg = (item) => {
		switch (item) {
			case 1:
				checkItem.value.earlyRising = checkItem.value.earlyRising ? 0 : 1;
				break;
			case 2:
				checkItem.value.running = checkItem.value.running ? 0 : 1;
				break;
			case 3:
				checkItem.value.fitness = checkItem.value.fitness ? 0 : 1;
				break;
			case 4:
				checkItem.value.earlyBedtime = checkItem.value.earlyBedtime ? 0 : 1;
				break;
		}
	};

	//摄入详情弹窗
	const popupDietData =ref(null);
	
	//消耗详情弹窗
	const popupMotionData = ref(null);
	//当日运动信息
	let motionData = ref([]);
	

	//食物弹窗
	const popupDiet = ref(null);
	//弹窗所需要的食物数据
	const dietItem = ref({
		"foodId": null,
		"foodName": "",
		"carbohydrates": null,
		"vitamins": null,
		"fats": null,
		"water": null,
		"minerals": null,
		"proteins": null,
		"foodPriority": null,
		"foodTip": ""
	});
	//点击食物后触发弹窗事件
	const openDiet = (item) => {
		//把当前点击的食物数据传给弹窗所需要的食物数据
		dietItem.value = item;
		dietQualityItem.value.foodId = dietItem.value.foodId;
		dietQualityItem.value.foodName = dietItem.value.foodName;
		//打开弹窗
		moveUpPopup.value = false;
		popupDiet.value.open();
	};
	//点击弹窗中的取消和确认按钮关闭弹窗，参数0为取消，1为确认
	const closeDiet = (e) => {
		//点击确认且重量不为0时添加到已食用列表中
		if (e && dietQualityItem.value.foodWeight != 0) {
			const newItem = {
				...dietQualityItem.value
			};
			dietQualityItemList.value.push(newItem);
		};
		//重新赋值为0
		dietQuality.value = '0';
		dietQualityItem.value.foodWeight = 0;
		dietQualityItem.value.carbohydrates = 0;
		dietQualityItem.value.vitamins = 0;
		dietQualityItem.value.fats = 0;
		dietQualityItem.value.water = 0;
		dietQualityItem.value.minerals = 0;
		dietQualityItem.value.proteins = 0;
		//关闭弹窗
		popupDiet.value.close();
	};
	//输入的食物重量 字符串的类型
	const dietQuality = ref('0');
	//封装好食物的重量和各项营养的重量
	const dietQualityItem = ref({
		"foodId": null,
		"foodName": "",
		"foodWeight": 0,
		"carbohydrates": 0,
		"vitamins": 0,
		"fats": 0,
		"water": 0,
		"minerals": 0,
		"proteins": 0,
	});
	//计算各项食物营养的重量 保留一位小数 并封装到对象里面
	const countDietQuality = () => {
		dietQuality.value = inputRule(dietQuality);
		const scale = dietQuality.value / 100;
		dietQualityItem.value.foodWeight = parseFloat(scale * 100).toFixed(1);
		dietQualityItem.value.carbohydrates = parseFloat(dietItem.value.carbohydrates * scale).toFixed(1);
		dietQualityItem.value.vitamins = parseFloat(dietItem.value.vitamins * scale).toFixed(1);
		dietQualityItem.value.fats = parseFloat(dietItem.value.fats * scale).toFixed(1);
		dietQualityItem.value.water = parseFloat(dietItem.value.water * scale).toFixed(1);
		dietQualityItem.value.minerals = parseFloat(dietItem.value.minerals * scale).toFixed(1);
		dietQualityItem.value.proteins = parseFloat(dietItem.value.proteins * scale).toFixed(1);
	};
	//已食用的食物列表
	const dietQualityItemList = ref([]);
	//移除食物列表中的食物
	const removeDietQualityItem = (e) => {
		dietQualityItemList.value.splice(e, 1);
	};
	//提交食物列表
	const submitDietQualityItemList = () => {
		// 调用 isLogin 函数，并根据登录状态执行相应操作
		isLogin()
			.then(response => {
				if (response) {
					//提交食物摄入
					postFoodConsum(dietQualityItemList.value)
						.then(response => {
							//重新获取食物摄入
							getIntakeSum()
								.then(response => {
									//计算
									countIntakeSum(response);
									reCountIntakeSum();
									//刷新统计图
									myChart1.setOption(option1);
									//清空列表
									dietQualityItemList.value = [];
								})
								.catch(error => {
									console.log(error);
								});
						})
						.catch(error => {
							console.log(error);
						});
				}
			})
			.catch((error) => {
				// 用户未登录或取消登录，执行相应的操作
				console.error(error.message);
			});
	};

	//计算获取到的每日摄入
	const countIntakeSum = (dataList) => {
		option1.series[0].data[0].value[0] = parseFloat(dataList.carbohydrates).toFixed(1);
		option1.series[0].data[0].value[1] = parseFloat(dataList.vitamins).toFixed(1);
		option1.series[0].data[0].value[2] = parseFloat(dataList.fats).toFixed(1);
		option1.series[0].data[0].value[3] = parseFloat(dataList.water).toFixed(1);
		option1.series[0].data[0].value[4] = parseFloat(dataList.minerals).toFixed(1);
		option1.series[0].data[0].value[5] = parseFloat(dataList.proteins).toFixed(1);
		dietData.value = option1.series[0].data[0].value;
		setDietDataList();
		//换算食物热量 直接通过后端数据算  目前使用静态数据计算
		const data = option1.series[0].data[0].value;
		inkcal.value = parseFloat(data[0] * 4 + data[2] * 9 + data[5] * 4).toFixed(0);
	};
	//如果大于目标值，则重新赋值为目标值
	const reCountIntakeSum = () => {
		for(let i = 0;i < 6;i++){
			if(option1.series[0].data[0].value[i] > target.dietTarget[i]){
				option1.series[0].data[0].value[i] = target.dietTarget[i];
			}
		}
	}
	
	//弹窗中的输入框获得焦点后弹窗上移 默认不上移
	const moveUpPopup = ref(false);
	//运动弹窗
	const popupMotion = ref(null);
	//弹窗所需要的运动数据
	let motionItem = ref({
		"sportId": null,
		"sportName": "",
		"sportUnit": "",
		"fmet": null,
		"mmet": null,
		"smet": null,
		"sportPercent": null,
		"sportPriority": null,
		"sportTip": "",
	});
	//点击运动后触发弹窗事件
	const openMotion = (item) => {
		//把当前点击的运动数据传给弹窗所需要的运动数据
		motionItem.value = item;
		motionConsumptionItem.value.sportId = motionItem.value.sportId;
		motionConsumptionItem.value.sportName = motionItem.value.sportName;
		motionConsumptionItem.value.sportUnit = motionItem.value.sportUnit;
		//打开弹窗
		moveUpPopup.value = false;
		popupMotion.value.open();
	};
	//点击弹窗中的取消和确认按钮关闭弹窗，参数0为取消，1为确认 
	const closeMotion = (e) => {
		//点击确认且消耗的热量不为0时添加到已运动列表中
		if (e && motionConsumptionItem.value.sportConsumption != 0) {
			const newItem = {
				...motionConsumptionItem.value
			};
			motionConsumptionItemList.value.push(newItem);
		};
		//重新赋值为0
		motionConsumptionItem.value.sportConsumption = 0;
		motionTime.value = '0';
		motionNumb.value = '0';
		motionWeight.value = '0';
		met.value = 0;
		onFocusIntensity.value = -1;
		//关闭弹窗
		popupMotion.value.close();
	};
	//输入的运动时间 字符串的类型
	const motionTime = ref('0');
	//运动强度按钮
	const intensityButton = ref(['低强度', '中等强度', '高强度']);
	//选中的met值
	const met = ref(0);
	//输入的运动数量
	const motionNumb = ref('0');
	//输入的运动重量
	const motionWeight = ref('0');
	//选中的运动强度 默认不选中
	const onFocusIntensity = ref(-1);
	//给强度按钮绑定的样式进行更改 并给met值赋值 重新计算热量
	const intensityOnFocus = (e) => {
		onFocusIntensity.value = e;
		switch (e) {
			case 0:
				met.value = motionItem.value.smet;
				break;
			case 1:
				met.value = motionItem.value.mmet;
				break;
			case 2:
				met.value = motionItem.value.fmet;
				break;
		}
		
		countMetHeat();
	};
	//封装好运动时间或者数量和消耗的热量 
	const motionConsumptionItem = ref({
		"sportId": null,
		"sportName": "",
		"sportNumber": null,
		"sportConsumption": 0,
		"sportUnit": "",
	});
	//选中不同运动强度和运动时间或者运动数量和重量并计算热量消耗
	const countMotionCal = () => {
		motionTime.value = inputRule(motionTime);
		motionNumb.value = inputRule(motionNumb);
		motionWeight.value = inputRule(motionWeight);
		if (motionItem.value.sportPercent == 0) {
			motionConsumptionItem.value.sportNumber = parseInt(motionTime.value);
			countMetHeat();
		} else {
			motionConsumptionItem.value.sportNumber = parseInt(motionNumb.value);
			countNumbWeightHeat();
		};
	};
	//根据met值来计算热量消耗
	const countMetHeat = () => {
		motionConsumptionItem.value.sportConsumption = parseInt((userInfoStore.weight * met.value * 3.5) / 200 *
			motionTime.value);
	}
	//根据运动数量和重量来计算热量消耗
	const countNumbWeightHeat = () => {
		motionConsumptionItem.value.sportConsumption = parseFloat(userInfoStore.height / 10 * motionItem.value
			.sportPercent * motionNumb.value * motionWeight.value / 4184).toFixed(2);
	}
	//做过的运动列表
	const motionConsumptionItemList = ref([]);
	//移除运动列表中的运动
	const removeMotionConsumptionItem = (e) => {
		motionConsumptionItemList.value.splice(e, 1);
	};
	//提交运动列表
	const submitMotionConsumptionItemList = () => {
		// 调用 isLogin 函数，并根据登录状态执行相应操作
		isLogin()
			.then(response => {
				if (response) {
					//提交运动消耗
					postSportConsum(motionConsumptionItemList.value)
						.then(response => {
							//重新获取运动消耗
							getConsumSum()
								.then(response => {
									option2.series[0].data[0].value = response.sportConsumption;
									outkcal.value = option2.series[0].data[0].value.toFixed(1);
									//刷新统计图
									myChart2.setOption(option2);
									//清空列表
									motionConsumptionItemList.value = [];
								})
								.catch(error => {
									console.log(error);
								});
							//刷新详情数据
							sportConsumptionInfo()
								.then(response => {
									motionData.value = response;
								})
								.catch(error => {
									console.log(error);
								});
						})
						.catch(error => {
							console.log(error);
						});
				}
			})
			.catch((error) => {
				// 用户未登录或取消登录，执行相应的操作
				console.error(error.message);
			});
	};

	//输入框的输入规则，不能出现连续的0，首位不能为0
	const inputRule = (data) => {
		if (data.value.charAt(0) === '0' && data.value.length > 1) {
			nextTick(() => {
				data.value = data.value.slice(1);
			});
		};
		return data.value;
	};
	
	//食物和运动超出布局，进行换行
	const textWrap = (e) => {
		if(e.length >= 4){
			return e.slice(0, 2) + "\n" + e.slice(2);
		}else {
			return e;
		}
		
	}
</script>

<style lang="scss" scoped>
	.title {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		margin-top: 16rpx;
		padding-left: 20rpx;
		padding-right: 20rpx;

		.title-text {
			width: 240rpx;
			height: 60rpx;
		}
	}

	.slider {
		height: 10rpx;
		width: 180rpx;
		margin-left: 50rpx;
		background: #9060df;
		transition: transform 0.5s;
		border-radius: 5rpx;
		transform: translateX(0rpx);

		&.left-slider {
			transform: translateX(470rpx);
			background: #6356de;
		}

		&.right-slider {
			transform: translateX(0rpx);
		}
	}

	.slider-area {
		display: flex;
		flex-direction: column;
		transition: transform 0.5s;
		transform: translateX(0rpx);

		&.left-slider-area {
			transform: translateX(-750rpx);
		}

		&.right-slider-area {
			transform: translateX(0rpx);
		}
	}

	.statistics {
		display: flex;
		flex-direction: row;
		margin-top: 16rpx;
		height: 320rpx;
		width: 1500rpx;

		.echarts-diet {
			display: flex;
			flex-direction: row;
			align-items: center;
			margin-left: 30rpx;
			//margin-top: 30rpx;
			width: 690rpx;
			height: 320rpx;
			//padding-right: 20rpx;
			border-radius: 32rpx;
			opacity: 1;
			background: #FFFFFF;
			box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);

			.chart-radar {
				width: 380rpx;
				height: 320rpx;
			}

			.diet-data {
				width: 310rpx;
				height: 288rpx;

				/* ::-webkit-scrollbar {
					display: none;
				} */

				.diet-data-text {
					display: flex;
					box-sizing: border-box;
					flex-direction: column;
					justify-content: space-around;
					align-items: center;
					width: 294rpx;
					height: 288rpx;
					padding: 24rpx 24rpx 6rpx 24rpx;
					border-radius: 32rpx;
					background: #EBEDED;
				}

				
			}
		}

		.echarts-motion {
			display: flex;
			flex-direction: row;
			align-items: center;
			margin-left: 60rpx;
			//margin-top: 30rpx;
			width: 690rpx;
			height: 320rpx;
			border-radius: 32rpx;
			opacity: 1;
			background: #FFFFFF;
			// border: 5rpx solid #000000;
			box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);

			.chart-bar {
				width: 380rpx;
				height: 320rpx;
			}

			.motion-data {
				width: 310rpx;
				height: 288rpx;

				.motion-data-text {
					display: flex;
					box-sizing: border-box;
					flex-direction: column;
					justify-content: space-around;
					align-items: center;
					width: 294rpx;
					height: 288rpx;
					padding: 24rpx 24rpx 6rpx 24rpx;
					border-radius: 32rpx;
					background: #EBEDED;
				}
			}
		}
	}

	.diet-motion {
		display: flex;
		flex-direction: row;
		margin-top: 30rpx;
		width: 1500rpx;
	}

	.diet {
		display: flex;
		box-sizing: border-box;
		flex-direction: column;
		margin-left: 30rpx;
		padding: 24rpx 24rpx 0rpx 24rpx;
		width: 690rpx;
		border-radius: 32rpx;
		background-color: #E7FCDC;
		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);
	}

	.motion {
		display: flex;
		box-sizing: border-box;
		flex-direction: column;
		margin-left: 60rpx;
		padding: 24rpx 24rpx 0rpx 24rpx;
		width: 690rpx;
		border-radius: 32rpx;
		background-color: #B8FAEA;
		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);
	}

	.add-area {
		display: flex;
		flex-direction: column;
		height: 120rpx;
		border-radius: 40rpx;
		background-color: #ffffff;
		margin-top: 20rpx;
		z-index: 1;

		.submit {
			display: flex;
			justify-content: center;
			align-items: center;
			align-self: flex-end;
			width: 120rpx;
			height: 120rpx;
		}
	}

	.add-foods {
		position: absolute;
		white-space: nowrap;
		margin-left: 16rpx;
		height: 120rpx;
		width: 492rpx;

		::-webkit-scrollbar {
			background-color: transparent;
		}

		.food-item {
			display: inline-block;
			width: 100rpx;
			height: 88rpx;
			margin-top: 16rpx;
			margin-right: 16rpx;
			border-radius: 32rpx;
			background-color: #ADFFC3;

			//font-family: 'YouSheBiaoTiHei';
			.remove {
				margin-top: -95rpx;
				margin-left: 75rpx;
			}
		}
	}

	.add-sports {
		position: absolute;
		white-space: nowrap;
		margin-left: 16rpx;
		height: 120rpx;
		width: 492rpx;

		::-webkit-scrollbar {
			background-color: transparent;
		}

		.sport-item {
			display: inline-block;
			width: 100rpx;
			height: 88rpx;
			margin-top: 16rpx;
			margin-right: 16rpx;
			border-radius: 32rpx;
			background-color: #95F3F9;

			//font-family: 'YouSheBiaoTiHei';
			.remove {
				margin-top: -95rpx;
				margin-left: 75rpx;
			}
		}
	}

	.title-child {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 120rpx;
		height: 50rpx;
		border-radius: 25rpx;
		background-color: #FFFFFF;
		box-shadow: 0rpx 6rpx 12rpx 0rpx rgba(0, 0, 0, 0.2);
	}

	.swiper-area {
		width: 658rpx;
		height: 380rpx;

		//margin-top: 12rpx;
		.view {
			padding-top: 30rpx;
			padding-bottom: 24rpx;

			.row {
				display: flex;
				flex-direction: row;

				//margin-left: 4rpx;
				.item {
					display: flex;
					justify-content: center;
					align-items: center;
					//font-family: 'YouSheBiaoTiHei';
					width: 120rpx;
					height: 120rpx;
					margin-top: -12rpx;
					margin-left: 8rpx;
					border-radius: 28rpx;
					clip-path: polygon(50% 0%, 95% 25%, 95% 75%, 50% 100%, 5% 75%, 5% 25%);
					transition-property: all;
					transition-duration: 0.5s;
					transition-timing-function: ease;
				}

				.item:hover {
					margin-top: -22rpx;
					margin-bottom: -12rpx;
					margin-left: -5rpx;
					margin-right: -12rpx;
					width: 140rpx;
					height: 140rpx;
				}
			}
		}
	}

	.check-in-area {
		display: flex;
		box-sizing: border-box;
		flex-direction: column;
		margin-left: 30rpx;
		margin-top: 30rpx;
		padding: 24rpx;
		width: 690rpx;
		border-radius: 32rpx;
		background-color: #D3F3FD;
		box-shadow: 0rpx 6rpx 20rpx 0rpx rgba(0, 0, 0, 0.2);

		.check-in-data {
			display: flex;
			flex-direction: row;
			justify-content: space-around;
			width: 690rpx;
			margin-left: -24rpx;

			.check-in-item {
				display: flex;
				flex-direction: column;
				margin-top: 8rpx;
				perspective: 240rpx;

				.check-in-img {
					display: flex;
					flex-direction: column;
					justify-content: flex-end;
					align-items: center;
					position: relative;
					width: 120rpx;
					height: 120rpx;
					transition: all 1s;
					filter: grayscale(1);

					&.checkIn {
						transform: rotateY(360deg);
						filter: grayscale(0);
					}
				}

				.check-in-tag {
					display: flex;
					justify-content: center;
					align-items: center;
					width: 120rpx;
					height: 40rpx;
					margin-top: 24rpx;
					border-radius: 30rpx;
					background-color: #FFFFFF;
				}
			}
		}
	}

	.diet-data-list {
		display: flex;
		box-sizing: border-box;
		flex-direction: column;
		justify-content: space-around;
		width: 690rpx;
		padding: 24rpx 32rpx 24rpx 32rpx;
		border-radius: 32rpx;
		font-size: 32rpx;
		background: #EBEDED;
		.list-text {
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			.nutrition-list, .intake-list, .target-list{
				display: flex;
				flex-direction: column;
			}
		}
	}
	
	.motion-data-list {
		display: flex;
		box-sizing: border-box;
		flex-direction: column;
		justify-content: space-around;
		width: 690rpx;
		padding: 24rpx 32rpx 24rpx 32rpx;
		border-radius: 32rpx;
		font-size: 32rpx;
		background: #EBEDED;
		.title {
			margin: 0rpx;
			padding: 0rpx;
		}
		.motion-list {
			margin-top: 16rpx;
			.motion-sports {
				display: flex;
				flex-direction: row;
			}
		}
	}

	.popup-diet {
		display: flex;
		box-sizing: border-box;
		flex-direction: column;
		width: 690rpx;
		margin-left: 30rpx;
		margin-bottom: 30rpx;
		background-color: #E7FCDC;
		border-radius: 32rpx;
		padding: 24rpx;

		&.move-up-popup {
			margin-bottom: 480rpx;
		}

		.food-tip {
			font-size: 28rpx;
			margin-top: 16rpx;
			margin-left: 16rpx;
		}

		.component {
			display: flex;
			flex-direction: row;
			width: 642rpx;
			margin-top: 16rpx;

			.component-area {
				display: flex;
				flex-direction: row;
				justify-content: space-around;
				width: 564rpx;

				.component-item {
					display: flex;
					flex-direction: column;
					justify-content: space-around;
					align-items: center;
					height: 160rpx;

					.component-name {
						display: flex;
						justify-content: center;
						align-items: center;
						width: 100rpx;
						height: 40rpx;
						border-radius: 20rpx;
						background-color: #FFFFFF;
					}

					.component-value {
						font-size: 28rpx;
					}
				}
			}

			.component-every {
				display: flex;
				flex-direction: column;
				justify-content: space-around;
				height: 100rpx;
				margin-top: 60rpx;
			}
		}
	}

	.popup-motion {
		display: flex;
		box-sizing: border-box;
		flex-direction: column;
		width: 690rpx;
		margin-left: 30rpx;
		margin-bottom: 30rpx;
		background-color: #B8FAEA;
		border-radius: 32rpx;
		padding: 24rpx;

		&.move-up-popup {
			margin-bottom: 480rpx;
		}

		.motion-tip {
			font-size: 28rpx;
			margin-top: 16rpx;
			margin-left: 16rpx;
		}

		.motion-data-area {
			display: flex;
			flex-direction: column;
			width: 642rpx;
			margin-top: 16rpx;

			.intensity-area {
				display: flex;
				flex-direction: row;
				justify-content: space-around;
				width: 540rpx;

				.intensity {
					display: flex;
					justify-content: center;
					align-items: center;
					width: 150rpx;
					height: 60rpx;
					border-radius: 30rpx;
					background-color: #FFFFFF;

					&.on-focus {
						border: 2px solid #000000;
						box-shadow: 0rpx 6rpx 12rpx 0rpx rgba(0, 0, 0, 0.2);
					}
				}
			}

			.numb-weight {
				display: flex;
				flex-direction: row;
				align-items: center;
				justify-content: space-around;
				width: 540rpx;

				.data-area-input {
					display: flex;
					flex-direction: row;
					align-items: center;
					padding-left: 16rpx;
					padding-right: 16rpx;
					height: 60rpx;
					border-radius: 35rpx;
					background-color: #FFFFFF;
					box-shadow: 0rpx 6rpx 12rpx 0rpx rgba(0, 0, 0, 0.2);
					font-size: 40rpx;
				}
			}

			.motion-consumption {
				text-align: right;
				margin-top: 16rpx;
			}
		}
	}

	.popup-title {
		display: flex;
		flex-direction: column;

		.title-head {
			display: flex;
			flex-direction: row;
			justify-content: space-between;

			.title-name {
				display: flex;
				justify-content: center;
				align-items: center;
				padding-left: 16rpx;
				padding-right: 16rpx;
				height: 60rpx;
				border-radius: 35rpx;
				background-color: #FFFFFF;
				box-shadow: 0rpx 6rpx 12rpx 0rpx rgba(0, 0, 0, 0.2);
				font-size: 40rpx;
			}

			.title-input {
				display: flex;
				flex-direction: row;
				align-items: center;
				padding-left: 16rpx;
				padding-right: 16rpx;
				height: 60rpx;
				border-radius: 35rpx;
				background-color: #FFFFFF;
				box-shadow: 0rpx 6rpx 12rpx 0rpx rgba(0, 0, 0, 0.2);
				font-size: 40rpx;
			}
		}
	}

	.input-box {
		width: 120rpx;
		text-align: right;
		//margin-top: 8rpx;
	}

	.button {
		display: flex;
		flex-direction: row;
		align-self: flex-end;
		margin-top: 16rpx;

		.cancel {
			display: flex;
			justify-content: center;
			align-items: center;
			width: 140rpx;
			height: 70rpx;
			border-radius: 35rpx;
			background-color: #FFFFFF;
			box-shadow: 0rpx 6rpx 12rpx 0rpx rgba(0, 0, 0, 0.2);
			font-size: 40rpx;
		}

		.confirm {
			display: flex;
			justify-content: center;
			align-items: center;
			margin-left: 24rpx;
			width: 140rpx;
			height: 70rpx;
			border-radius: 35rpx;
			background-color: #FFFFFF;
			box-shadow: 0rpx 6rpx 12rpx 0rpx rgba(0, 0, 0, 0.2);
			font-size: 40rpx;
		}
	}
</style>