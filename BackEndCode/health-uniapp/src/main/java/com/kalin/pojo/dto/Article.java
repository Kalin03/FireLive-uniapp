package com.kalin.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章表
 */

@Data
public class Article {
    @NotNull
    private Integer articleId;
    @NotNull
    private String openid;//发布人id
    @NotNull
    private String articleImage;//文章图片
    @NotNull
    private String articleTitle;//文章标题
    @NotNull
    private String articleContent;//文章内容
    private Integer likesNumber;//点赞数量
    private Integer commentsNumber;//评论数
    private Integer collectNumber;//收藏数
    private LocalDateTime updateTime;//修改时间
    private LocalDateTime createTime;//创建时间

}
