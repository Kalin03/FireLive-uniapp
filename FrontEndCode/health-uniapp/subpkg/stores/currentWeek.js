import {defineStore} from "pinia"
import {ref, watch} from "vue"

export const currentWeekStore = defineStore('currentWeek',() => {
	const week = ref('');
	const year = ref('');
	const weekArray = ref([]);
	const startTime = ref('');
	const endTime = ref('');
	watch([week,year] , () => {
		weekArray.value = week.value.match(/\d+/g).map(str => str.length < 2 ? '0' + str : str);
		if(weekArray.value[0] === '12' && weekArray.value[2] === '1'){
			startTime.value = year.value + '-' + weekArray.value[0] + '-' + weekArray.value[1];
			endTime.value = (parseInt(year.value) +1 ) +'-' + weekArray.value[2] + '-' + weekArray.value[3];
			
		}else {
			startTime.value = year.value + '-' + weekArray.value[0] + '-' + weekArray.value[1];
			endTime.value = year.value +'-' + weekArray.value[2] + '-' + weekArray.value[3];
		}
	})
	return {
		week,
		year,
		weekArray,
	    startTime,
	    endTime
	};
})