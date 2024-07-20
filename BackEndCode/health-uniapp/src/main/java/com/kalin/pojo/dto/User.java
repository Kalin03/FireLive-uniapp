package com.kalin.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户表
 */

@Data
public class User {

    @NotNull
    private String openid;

    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
