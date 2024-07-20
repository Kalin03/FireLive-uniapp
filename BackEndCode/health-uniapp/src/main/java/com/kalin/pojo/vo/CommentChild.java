package com.kalin.pojo.vo;

import com.kalin.pojo.dto.UserData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 子评论实体
 */

@Data
@NoArgsConstructor
public class CommentChild {
    private Integer recordId;//记录号
    private Integer articleId;//文章id
    /**
     * 评论用户信息
     */
    private UserData userData;
    /**
     * 回复用户信息
     */
    private UserData toUserData;
    private Integer rootId;//根评论id
    private String commentContent;//评论内容
    private Integer type;//类型 0为根评论 1子评论
    private LocalDateTime createTime;//创建时间
}