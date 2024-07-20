package com.kalin.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 收藏表
 */

@Data
public class SavesTable {
    private Integer recordId;
    @NotNull
    private Integer articleId;//文章id
    @NotNull
    private String openid;//收藏人id
    private LocalDateTime updateTime;//修改时间
    private LocalDateTime createTime;//创建时间
}
