package com.kalin.mapper;

import com.kalin.pojo.dto.Article;
import com.kalin.pojo.dto.Comment;
import com.kalin.pojo.dto.SavesTable;
import com.kalin.pojo.dto.UserData;
import com.kalin.pojo.vo.ArticleList;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommunityMapper {

    //根据id查询文章
    @Select("select * from article_table where article_id = #{articleId}")
    Article findArticle(Integer articleId);

    //根据文章id查询作者
    @Select("select avatar,nick_name from user_data where openid = #{openid} ")
    UserData findAuthor(String openid);

    //新增收藏
    @Insert("insert into saves_table(article_id,openid,update_time,create_time) values (#{articleId},#{openid},now(),now())")
    void addSaves(String openid, Integer articleId);

    //取消收藏
    @Delete("delete from saves_table where article_id = #{articleId} and openid = #{openid}")
    void delSaves(String openid,Integer articleId);

    //查询文章列表
    @Select("select a.article_id,a.openid,a.article_image,a.article_title,a.likes_number,b.avatar,b.nick_name " +
            "from article_table as a,user_data as b where a.openid = b.openid order by a.create_time DESC ")
    List<ArticleList> findArticleList();

    //点赞文章
    @Update("update article_table set likes_number = likes_number + 1 where article_id = #{articleId}")
    void addLikeNumber(Integer articleId);

    //取消点赞文章
    @Update("update article_table set likes_number = likes_number - 1 where article_id = #{articleId}")
    void lessLikeNumber(Integer articleId);

    //模糊查询相关文章
    @Select("select * from article_table where article_title LIKE #{searchStr} or article_content LIKE #{searchStr}")
    List<Article> searchArticleList(String searchStr);

    //查询文章点赞数量前十
    @Select("select * from article_table order by likes_number DESC LIMIT 10 ")
    List<Article> findArticleTop();

    //添加根评论
    @Insert("insert into comment_table(article_id,openid,comment_content,type,create_time)" +
            "values (#{articleId},#{openid},#{commentContent},#{type},#{createTime})")
    void addRootComment(Comment comment);

    //添加子评论
    @Insert("insert into comment_table(article_id,openid,to_user_id,root_id,comment_content,type,create_time)" +
            "values (#{articleId},#{openid},#{toUserId},#{rootId},#{commentContent},#{type},#{createTime})")
    void addChildComment(Comment comment);

    //查询评论类型
    @Select("select type from comment_table where record_id = #{recordId}")
    Integer findCommentType(Integer recordId);

    //删除根评论
    @Delete("delete from comment_table where record_id = #{recordId}")
    void delRootComment(Integer recordId);

    //删除子评论
    @Delete("delete from comment_table where root_id = #{recordId}")
    void delChildComment(Integer recordId);

    //查询文章所有评论
    @Select("select * from comment_table where article_id = #{articleId} order by create_time ")
    List<Comment> findAllComment(Integer articleId);

    //添加文章
    @Insert("insert into article_table(openid,article_image,article_title,article_content,update_time,create_time) " +
            "values (#{openid},#{articleImage},#{articleTitle},#{articleContent},now(),now())")
    void addArticle(Article article);

    //修改文章
    @Update("update article_table set article_image = #{article.articleImage},article_title = #{article.articleTitle},article_content = #{article.articleContent} " +
            "where openid = #{openid} and article_id = #{article.articleId}")
    void updateArticle(Article article, String openid);

    //删除文章
    @Delete("delete from article_table where article_id = #{articleId} and openid = #{openid}")
    void delArticle(Integer articleId, String openid);

    //修改评论数量
    @Update("update article_table set comments_number = #{commentNumber} where article_id = #{articleId}")
    void updateCommentNumber(Integer commentNumber,Integer articleId);

    //查询评论信息
    @Select("select * from comment_table where record_id = #{recordId}")
    Comment findCommentInfo(Integer recordId);

    //查询评论数量
    @Select("select count(*) from comment_table where article_id = #{articleId}")
    int findCommentNumber(Integer articleId);

    //修改文章评论数量
    @Update("update article_table set comments_number = #{commentNumber} where article_id = #{articleId}")
    void updateArticleCommentNumber(int commentNumber, Integer articleId);

    //查询收藏数量
    @Select("select count(*) from saves_table where article_id = #{articleId}")
    int findCollectNumber(Integer articleId);

    //修改收藏数量
    @Update("update article_table set collect_number = #{collectNumber} where article_id = #{articleId}")
    void updateArticleCollectNumber(int collectNumber, Integer articleId);

    //查找用户是否收藏文章
    @Select("select * from saves_table where article_id = #{articleId} and openid = #{openid}")
    SavesTable findCollect(Integer articleId, String openid);

    //删除文章所有评论
    @Delete("delete from comment_table where article_id = #{articleId}")
    void delAllComment(Integer articleId);
}
