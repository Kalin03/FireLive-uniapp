package com.kalin.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论表
 */

@Data
public class Comment {

    private Integer recordId;//记录号
    @NotNull
    private Integer articleId;//文章id
    @NotNull
    private String openid;//评论用户id
    private String toUserId;//回复用户的id
    private Integer rootId;//本条评论的根评论
    @NotNull
    private Integer type;//评论类型
    @NotNull
    private String commentContent;//评论内容
    private LocalDateTime createTime;//创建时间
}
