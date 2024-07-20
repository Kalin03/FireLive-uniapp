package com.kalin.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 目标值表
 */
@Data
public class TargetValue {
    @JsonIgnore
    @NotNull
    private String openid;
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
    private double sportConsumption;//运动项消耗数值
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
