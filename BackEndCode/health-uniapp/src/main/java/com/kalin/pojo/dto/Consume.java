package com.kalin.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 运动消耗记录表
 */

@Data
public class Consume {
    @JsonIgnore
    private Integer recordId; //记录号
    @JsonIgnore
    private String openid;//用户id
    @NotNull
    private Integer sportId;//运动id
    @NotNull
    private String sportName;//运动名称
    @NotNull
    private double sportNumber;//运动数量
    @NotNull
    private String sportUnit;//运动单位
    @NotNull
    private double sportConsumption;//运动卡路里消耗值
    private LocalDateTime updateTime;//修改时间
    private LocalDateTime createTime;//创建时间

}
