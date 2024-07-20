package com.kalin.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户数据表
 */

@Data
public class UserData {

    @NotNull
    private String openid;
    @URL
    private String avatar;
    private String nickName;
    private String sex;
    private double height;
    private double weight;
    private LocalDate birthday;
    private String signature;
    private String healthAnalyse;
    private String target;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
