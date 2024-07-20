import { defineStore } from 'pinia'
import { ref } from 'vue'

export const targetStore = defineStore('target', () => {
	const dietTarget = ref([]);
	const motionTarget = ref(0);
	/* 
	target.dietTarget[0] = carbohydrates;
	target.dietTarget[1] = vitamins;
	target.dietTarget[2] = fats;
	target.dietTarget[3] = water;
	target.dietTarget[4] = minerals;
	target.dietTarget[5] = proteins;
	target.motionTarget = sportConsumption; 
	*/
	return {
		dietTarget,
		motionTarget
	}
})