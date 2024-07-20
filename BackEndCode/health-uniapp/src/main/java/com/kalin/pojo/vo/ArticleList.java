package com.kalin.pojo.vo;

import lombok.Data;

@Data
public class ArticleList {
    private Integer articleId;
    private String openid;
    private String articleImage;
    private String articleTitle;
    private Integer likesNumber;
    private String avatar;
    private String nickName;
}
