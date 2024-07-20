<template>
	<!-- 周历滚动插件 -->
	<view class="statistic_week">
		<!-- 周历顶部信息 -->
		<view class="week_info">
			<picker mode="date" :value="selectedDate" fields="month" @change="bindDateChange">
				<text class="uni-week__header-text week__header-year">{{ MondayOfYear }}</text>
				<text class="uni-week__header-text week__header-month">{{ weekDateRange }}</text>
			</picker>
			<text v-show="showBackToTodayBtn" class="backToToday" :style="{color: dateActiveColor}"
				@tap="goToCurrentWeek()">回到本周</text>
		</view>
		<!-- 周历轮播 -->
		<view class="week_swiper">
			<swiper key="normalSwiper" circular :style="{height: swiperHeight}" :current="current" :duration="duration"
				:skip-hidden-item-layout="true" @change="e => current = e.detail.current" @transition="swiperTransition"
				@animationfinish="swiperTransitionFinish">
				<swiper-item v-for="(swiper, swiperIndex) in 3" :key="swiperIndex" class="swiper-item">
					<weekBox :weekOfMonth="weekOfMonthCount" :cellHeight="cellHeight" :selectedWeek="selectedWeek"
						@chooseDate="chooseDate">
					</weekBox>
				</swiper-item>
			</swiper>
		</view>
	</view>
</template>

<script>
	import {
		parseTime,
		deepClone
	} from './js/utils.js'
	import weekBox from './weekBox.vue'
	import { ref } from 'vue';
	import {currentWeekStore} from "@/subpkg/stores/currentWeek.js";
	export default {
		name: 'statistic-week',
		components: {
			weekBox
		},
		props: {
			duration: { // 轮播图动画时长
				type: Number,
				default: 300
			},
			cellHeight: { // 一列的高度
				type: Number,
				default: 75
			},
			dateActiveColor: { // 日期选中颜色
				type: String,
				default: '#6356de'
			},
			defaultSelectedDate: { // 默认选中日期
				type: String | null,
				default: null
			},
		},
		data() {
			return {
				today: parseTime(new Date(), '{y}-{m}-{d}'), // 今天日期
				current: 1, // 当前轮播默认显示索引
				selectedDate: null, // 选中日期
				selectedWeek: 0, //选中的周数
				weekOfMonthCount: 0, //周数
				MondayOfYear: 0, //每周周一所在的年份
				weekDateRange: "", //选中周的日期范围
				todayMonth: "",
				todayYear: "",
				todayWeek: "",

			}
		},
		
		computed: {
			/* 获取指定日期信息
			isToday: 是否获取当天的信息还是选中日期的信息
			index: 0 表示年份 1 表示月份 2 表示日期 */
			getAssignDateInfo() {
				return (isToday, index) => {
					return (isToday ? this.today : this.selectedDate).split('-')[index] * 1
				}
			},
			// 是否显示回到本周按钮
			showBackToTodayBtn() {
				return this.getAssignDateInfo(false, 0) !== this.todayYear || this.getAssignDateInfo(false, 1) !== this
					.todayMonth || this.selectedWeek !== this.todayWeek;
			},
			// 返回轮播图高度
			swiperHeight() {
				const shrinkHeight = this.cellHeight + 20 + 'rpx'
				return shrinkHeight;
			}
		},
		created() {
			this.init() // 初始化数据
			
			
		},

		watch: {
			// 轮播切换
			current(newV, oldV) {
				if (newV === 0 && oldV === 2) { // 右滑
					this.swiperChange(1)
					return
				}
				if (newV === 2 && oldV === 0) { // 左滑
					this.swiperChange(-1)
					return
				}
				if (newV > oldV) { // 右滑
					this.swiperChange(1)
				} else { // 左滑
					this.swiperChange(-1)
				}
			},
		},
		methods: {
			// 初始化数据
			init() {
				if (this.selectedDate === null) { // 默认选中日期为当天
					this.selectedDate = this.defaultSelectedDate || this.today
				}
				this.getCurrentWeek(this.getAssignDateInfo(true, 0), this.getAssignDateInfo(true, 1), this
					.getAssignDateInfo(true, 2));
				this.getWeeks(this.getAssignDateInfo(false, 0), this.getAssignDateInfo(false, 1))
				this.emitDate();
				
				this.setCurrentWeek()
			},
			//向pinia中存储当前选择的周
			setCurrentWeek() {
				currentWeekStore().week = this.weekDateRange;
				currentWeekStore().year = this.MondayOfYear;
			},
			/* picker选择年月 */
			bindDateChange(e) {
				const value = e.detail.value
				this.selectedDate = value;
				this.getWeeks(this.getAssignDateInfo(false, 0), this.getAssignDateInfo(false, 1))
				this.selectedWeek = 1;
				this.emitDate()
				
				this.setCurrentWeek()
			},
			/* 选中日期事件 */
			chooseDate(e) {
				this.selectedWeek = e;
				this.emitDate()
				
				this.setCurrentWeek()
				//console.log('点击完成后', this.weekDateRange);
				
			},
			// 轮播图切换结束
			swiperChange(e) {
				this.getPrevOrNextDate(e)
			},
			//轮播滑动中触发，把selectedWeek当前选中的周重置为0
			swiperTransition(e) {
				if (e.detail.dx != 0) {
					this.selectedWeek = 0;
				}
			},
			//轮播结束时触发，默认选中每个月的第一周
			swiperTransitionFinish(e) {
				if (this.selectedWeek == 0) {
					this.selectedWeek = 1;
					this.emitDate()
				}
				this.setCurrentWeek()
				//console.log('轮播完成后', this.weekDateRange);
				
			},
			// 获取月的一号日期/周的第一天
			getPrevOrNextDate(type) {
				if (type == 1) {
					let currentMonth = this.getAssignDateInfo(false, 1) + type === 13 ? 1 : this.getAssignDateInfo(false,
						1) + type;
					let currentYear = this.getAssignDateInfo(false, 1) + type === 13 ? this.getAssignDateInfo(false, 0) +
						1 : this.getAssignDateInfo(false, 0);
					this.selectedDate = parseTime(new Date(currentYear, currentMonth - 1), '{y}-{m}');
				} else if (type == -1) {
					let currentMonth = this.getAssignDateInfo(false, 1) + type === 0 ? 12 : this.getAssignDateInfo(false,
						1) + type;
					let currentYear = this.getAssignDateInfo(false, 1) + type === 0 ? this.getAssignDateInfo(false, 0) -
						1 : this.getAssignDateInfo(false, 0);
					this.selectedDate = parseTime(new Date(currentYear, currentMonth - 1), '{y}-{m}');
				}
				this.getWeeks(this.getAssignDateInfo(false, 0), this.getAssignDateInfo(false, 1))
			},
			/* 获取每月第几周的周一的日期 */
			getMondayTime(year, month, weekday) {
				let newDate = new Date();
				newDate.setFullYear(year, month - 1, 1); //该月第一天
				let week = newDate.getDay(); //该月的第一天是星期几
				if (week == 0) week = 7; //如果该月的第一天是0 那么就是周日
				let monday, newMonth; //第几周的周一的日期
				if (week != 1) { //如果该月的第一天不是星期一 .....
					newDate.setFullYear(year, month - 1, 2 - week); //那么就向上个月去找第一周的周一
					var lastDate = newDate.getDate(); //获取第一周的周一的日期
					newDate.setFullYear(year, month - 1, 0); //周一的上个月的总天数
					var lastDateCount = newDate.getDate(); //周一的上个月的总天数
					monday = (lastDate + (weekday - 1) * 7) % lastDateCount == 0 ? lastDateCount : (lastDate + (weekday -
						1) * 7) % lastDateCount; //计算出每个月第几周的周一的日期
					if (weekday == 1) {
						newMonth = month - 1 == 0 ? 12 : month - 1;
					} else {
						newMonth = month;
					}
				} else { //如果该月的第一天是星期一 .....
					monday = 1 + (weekday - 1) * 7; //那么直接计算出每个月第几周的周一的日期
					newMonth = month;
				}
				this.MondayOfYear = month < newMonth ? year - 1 : year
				return newMonth + "." + monday;
			},
			/* 获取每月第几周的周日的日期 */
			getSundayTime(year, month, weekday) {
				let newDate = new Date();
				let monDay = this.getMondayTime(year, month, weekday); //获取第几周的周一
				newDate.setFullYear(year, monDay.split(".")[0], 0); //取第几周的月总天数
				var monthCount = newDate.getDate(); //该月总天数
				var sunDay = (Number(monDay.split(".")[1]) + 6) % monthCount == 0 ? monthCount : (Number(monDay.split(".")[
					1]) + 6) % monthCount; //第几周的周日的日
				return month + "." + sunDay;
			},
			/* 获取每月有几周 */
			getWeeks(year, month) {
				let newDate = new Date();
				newDate.setFullYear(year, month, 0); //该月的最后一天，也就是该月天数
				let currentLastDay = newDate.getDate(); // 该月的最后一天
				let week = newDate.getDay(); //该月的最后一天是星期几
				let week_count = Math.ceil((currentLastDay - week) / 7); //该月最后一周的周日日期
				this.weekOfMonthCount = week_count;
			},
			/* 获得当前日期在当月第几周 */
			getCurrentWeek(year, month, day) {
				let currentDate = new Date(year, month - 1, day);
				let currentWeek = currentDate.getDay(); //当前日期是星期几
				if (currentWeek == 0) currentWeek = 7; //如果当前日期是0 那么就是周日
				let currentDay = currentDate.getDate(); //当前日期是几号
				currentDate.setFullYear(year, month, 0); //当前月的最后一天
				let currentMonthDays = currentDate.getDate(); //当前月总天数
				let currentWeekLastDay = (currentDay + 7 - currentWeek) % currentMonthDays == 0 ? currentMonthDays : (
					currentDay + 7 - currentWeek) % currentMonthDays; //当前日期所在的周的最后一天是几号
				let currentMonth = month + Math.floor((currentDay + 7 - currentWeek) / currentMonthDays == 1 ? 0 : (
					currentDay + 7 - currentWeek) / currentMonthDays); //计算出当前日期的所在的周的月份
				currentMonth = currentMonth % 12 == 0 ? currentMonth : currentMonth % 12; //如果月份超过12月进行处理
				let currentYear = currentMonth < month ? year + 1 : year; //计算出当前日期的所在的周的年份
				this.selectedDate = parseTime(new Date(currentYear, currentMonth - 1, currentWeekLastDay), '{y}-{m}-{d}');
				this.selectedWeek = Math.ceil(currentWeekLastDay / 7); //当前日期的周日在本月的第几周
				this.weekDateRange =
					`${this.getMondayTime(this.getAssignDateInfo(false, 0), this.getAssignDateInfo(false, 1), this.selectedWeek) }-${ this.getSundayTime(this.getAssignDateInfo(false, 0), this.getAssignDateInfo(false, 1), this.selectedWeek)}`
				this.todayYear = this.getAssignDateInfo(false, 0); //当前日期所在的年份
				this.todayMonth = this.getAssignDateInfo(false, 1); //当前日期所在的月份
				this.todayWeek = this.selectedWeek; //当前日期所在的周数
				
			},
			// 向父组件传递当前选中数据
			emitDate() {
				this.weekDateRange =
					`${this.getMondayTime(this.getAssignDateInfo(false, 0), this.getAssignDateInfo(false, 1), this.selectedWeek) }-${ this.getSundayTime(this.getAssignDateInfo(false, 0), this.getAssignDateInfo(false, 1), this.selectedWeek)}`
				const e = {
					selectedDate: parseTime(new Date(this.getAssignDateInfo(false, 0), this.getAssignDateInfo(false,
						1) - 1), '{y}-{m}'),
					selectedWeek: this.selectedWeek
				}
				this.$emit('change', e)
			},
			// 回到本周事件
			goToCurrentWeek() {
				this.getCurrentWeek(this.getAssignDateInfo(true, 0), this.getAssignDateInfo(true, 1), this
					.getAssignDateInfo(true, 2));
				this.getWeeks(this.getAssignDateInfo(false, 0), this.getAssignDateInfo(false, 1))
				this.emitDate();
				this.setCurrentWeek()
			},
		}
	}
</script>

<style>
	.statistic_week {
		width: 100%;
		padding: 20rpx 0;
		box-sizing: border-box;
		background-color: #fff;
	}

	.week_swiper {
		border-bottom: 1px solid #F2F2F2;
	}

	/* 周历顶部信息 */
	.week_info {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 0 20rpx;
	}

	.week_info .backToToday {
		margin-left: auto;
		font-size: 24rpx;
	}

	/* 周历顶部信息 */

	/* piker选择年月 */

	.uni-week__header-text {
		font-size: 28rpx;
		color: #333333;
		font-weight: bold;
	}

	.week__header-year {
		padding-right: 8rpx;
		border-right: 1px solid #F2F2F2;
	}

	.week__header-month {
		padding-left: 8rpx;
	}

	/* piker选择年月 */
</style>