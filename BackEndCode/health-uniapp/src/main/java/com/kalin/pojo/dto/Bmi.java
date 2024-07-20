package com.kalin.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Bmi {
    @NotNull
    @JsonIgnore
    private Integer recordId;
    @NotNull
    @JsonIgnore
    private String openid;
    @NotNull
    private double BMI;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
