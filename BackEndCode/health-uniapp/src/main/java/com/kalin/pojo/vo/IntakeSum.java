package com.kalin.pojo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IntakeSum {
    @NotNull
    private double carbohydrates; //记录碳水数值
    @NotNull
    private double vitamins;//记录维生素数值
    @NotNull
    private double fats;//记录脂肪数值
    @NotNull
    private double water;//记录水分数值
    @NotNull
    private double minerals;//记录无机盐数值
    @NotNull
    private double proteins;//记录蛋白质数值
}
