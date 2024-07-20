package com.kalin.pojo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConsumeSum {
    @NotNull
    private double sportConsumption;//运动卡路里消耗值
}
