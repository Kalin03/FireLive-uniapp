package com.kalin.service;

import com.kalin.pojo.ao.PageBean;
import com.kalin.pojo.dto.Article;
import com.kalin.pojo.dto.Comment;
import com.kalin.pojo.dto.UserData;
import com.kalin.pojo.vo.ArticleList;
import com.kalin.pojo.vo.CommentRoot;

import java.util.List;

public interface CommunityService {
    Article findArticle(Integer articleId);

    UserData findAuthor(String articleId);

    void addSaves(Integer articleId);

    void delSaves(Integer articleId);

    PageBean<ArticleList> findArticleList(Integer pageNum, Integer pageSize);

    void addLikeNumber(Integer articleId);

    void lessLikeNumber(Integer articleId);

    PageBean<Article> searchArticle(String searchStr, Integer pageNum, Integer pageSize);

    List<Article> findArticleTop();

    void addRootComment(Comment comment);

    void addChildComment(Comment comment);

    void delComment(Integer recordId);

    List<CommentRoot> findArticleRootComment(Integer articleId);

    void addArticle(Article article);

    void updateArticle(Article article);

    void delArticle(Integer articleId);

    boolean isCollect(Integer articleId, String openid);
}
