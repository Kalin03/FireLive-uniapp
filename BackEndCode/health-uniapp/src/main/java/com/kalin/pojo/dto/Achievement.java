package com.kalin.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 成就表
 */

@Data
public class Achievement {
    @NotNull
    private String openid;//用户id
    @NotNull
    private String achievementName;//成就名称
    @NotNull
    private Integer classification;//成就分类
    @NotNull
    private Integer state;//激活状态 1激活 0未激活
    @NotNull
    private String  achievementTip;//成就提示
    private LocalDateTime activateTime;//激活时间
    private LocalDateTime createTime;//创建时间
}
