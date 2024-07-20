package com.kalin.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

/**
 * 食物表
 */

@Data
public class Food {
    @NotNull
    private Integer foodId;
    @NotNull
    private String foodName;
    @NotNull
    private double carbohydrates; //碳水数值
    @NotNull
    private double vitamins;//维生素数值
    @NotNull
    private double fats;//脂肪数值
    @NotNull
    private double water;//水分数值
    @NotNull
    private double minerals;//无机盐数值
    @NotNull
    private double proteins;//蛋白质数值
    @NotNull
    private Integer foodPriority;//食物推荐优先级
    @NotNull
    private String foodTip;//温馨提示
}
