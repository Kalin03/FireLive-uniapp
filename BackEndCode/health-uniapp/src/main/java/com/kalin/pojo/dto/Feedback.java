package com.kalin.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 反馈表
 */

@Data
public class Feedback {

    private Integer recordId; //记录号
    @NotNull
    private String openid;//反馈人id
    @NotNull
    private String feedbackContent;//反馈内容
    private LocalDateTime createTime;//创建时间
}
