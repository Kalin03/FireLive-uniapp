<template>
	<!-- 日期显示 -->
	<view class="week">
		<view class="week-box" :style="{backgroundColor: selectedWeek == dateInfo ? dateActiveColor : ''}" v-for="(dateInfo, dateIndex) in weekOfMonth" :key="dateIndex">
			<view class="week-box-date" @tap="chooseDate(dateInfo)">
				<view class="week-box-date-text" :style="{color: selectedWeek == dateInfo ? textActiveColor : '',}">第{{ numberToChinese(dateInfo) }}周</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			weekOfMonth: {
				type: [Number, String],
				default: 4
			},
			dateActiveColor: {  //日期选中背景颜色
				type: String,
				default: '#9060df'
			},
			textActiveColor: { // 日期选中文字颜色
				type: String,
				default: '#FFFFFF'
			},
			selectedWeek: {	   //选中的周
				type: [Number, String],
				default: 1
			},
		},
		computed: {
			
		},
		methods: {
			/* 数字转中文处理 */
			numberToChinese(number){
				let chinese = ["一", "二", "三", "四", "五", "六"];
				return chinese[number - 1];
			},
			chooseDate(dateInfo) {
				this.$emit('chooseDate', dateInfo)
			}
		}
	}
</script>

<style lang="scss">
	.week {
		display: flex;
		flex-wrap: wrap;
		justify-content: space-around;
		
		
		&-box{
			padding: 8rpx 24rpx;
			border-radius: 20px;
			display: flex;
			align-items: center;
			margin: 12px 0;
			
			&-date{
				
				&-text{
					font-size: 28rpx;
					color: #333333;
					font-weight: bold;
				}
				
			}
		}
	}
</style>