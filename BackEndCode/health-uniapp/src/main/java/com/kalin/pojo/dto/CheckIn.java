package com.kalin.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 打卡表
 */

@Data
public class CheckIn {
    @JsonIgnore
    private Integer checkInId; //记录号
    @JsonIgnore
    private String openid;
    private Integer earlyRising;
    private Integer running;
    private Integer fitness;
    private Integer earlyBedtime;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
