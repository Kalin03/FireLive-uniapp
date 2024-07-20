package com.kalin.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 食物摄入记录表
 */

@Data
public class Intake {
    @JsonIgnore
    private Integer recordId;//记录号
    @JsonIgnore
    @NotNull
    private String openid;//用户id
    @NotNull
    private Integer foodId;
    @NotNull
    private String foodName;//食物名称
    @NotNull
    private double foodWeight;//食物重量
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
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
