package com.kalin.utils;

import com.kalin.pojo.dto.TargetValue;

public class GetTargetValue {

    public static TargetValue getTarget(double height, double weight, int age, String sex, String goal) {

        TargetValue targetValue = new TargetValue();

        // 计算基础代谢率（BMR）
        double bmr = calculateBMR(height, weight, age, sex);

        // 根据目标调整营养摄入和运动消耗
        double calories = 0;
        double carbs = 0;
        double fat = 0;
        double protein = 0;
        double water = 0;
        double exerciseCalories = 0;

        switch (goal) {
            case "健身":
                calories = bmr * 1.4; // 增加能量摄入
                carbs = calories * 0.55; // 碳水化合物占比55%
                fat = calories * 0.30 / 9; // 脂肪占比25%
                protein = weight * 1.2; // 蛋白质体重×0.8至1.2
                water = weight * 0.03; // 每日饮水体重3%
                exerciseCalories = bmr * 0.3; // 每日运动消耗30%的BMR
                break;
            case "减肥":
                calories = bmr * 0.8; // 降低能量摄入
                carbs = calories * 0.4;
                fat = calories * 0.20 / 9;
                protein = weight * 0.8;
                water = weight * 0.03;
                exerciseCalories = bmr * 0.4; // 增加运动消耗40%的BMR
                break;
            case "保持":
                calories = bmr; // 保持能量摄入
                carbs = calories * 0.5;
                fat = calories * 0.25 / 9;
                protein = weight * 1;
                water = weight * 0.03;
                exerciseCalories = bmr * 0.3; // 每日运动消耗30%的BMR
                break;
            case "增重":
                calories = bmr * 1.2; // 增加能量摄入
                carbs = calories * 0.6;
                fat = calories * 0.35 / 9;
                protein = weight * 1.2;
                water = weight * 0.03;
                exerciseCalories = bmr * 0.25; // 每日运动消耗25%的BMR
                break;
            default:
                System.out.println("无效的目标，请重新输入。");
                return null;
        }
        targetValue.setCarbohydrates(carbs / 2);
        targetValue.setFats(fat);
        targetValue.setProteins(protein);
        targetValue.setWater(water);
        targetValue.setVitamins(220);
        targetValue.setMinerals(4865);
        targetValue.setSportConsumption(exerciseCalories);
        return targetValue;
    }

    // 使用哈里斯-贝内迪克特公式计算基础代谢率（BMR）
    private static double calculateBMR(double height, double weight, int age, String gender) {
        double bmr = 0;
        if (gender.equalsIgnoreCase("男")) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else if (gender.equalsIgnoreCase("女")) {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        } else {
            System.out.println("无效的性别，请重新输入。");
            System.exit(1);
        }
        return bmr;
    }
}
