package com.kalin.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

/**
 * 运动表
 */

@Data
public class Sport {

    @NotNull
    private Integer sportId;
    @NotNull
    private String sportName;
    @NotNull
    private String sportUnit;//运动单位
    @NotNull
    private Integer fMet;//高强度
    @NotNull
    private Integer mMet;//中强度
    @NotNull
    private Integer sMet;//低强度
    @NotNull
    private double sportPercent;//运动距离
    @NotNull
    private Integer sportPriority;//运动项推荐优先级
    @NotNull
    private String sportTip;//运动项温馨提示
}
