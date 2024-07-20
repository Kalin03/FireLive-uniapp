package com.kalin.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 食物优先级表
 */
@Data
public class FoodPriority {
    @NotNull
    private Integer foodId;//食物id
    @NotNull
    private String openid;//用户id
    @NotNull
    private Integer priority;//优先级
    private LocalDateTime updateTime;//修改时间
    private LocalDateTime createTime;//创建时间
}
