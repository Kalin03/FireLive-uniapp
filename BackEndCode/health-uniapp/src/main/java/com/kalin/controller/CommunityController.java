package com.kalin.controller;

import com.kalin.pojo.ao.PageBean;
import com.kalin.pojo.ao.Result;
import com.kalin.pojo.dto.Article;
import com.kalin.pojo.dto.Comment;
import com.kalin.pojo.dto.UserData;
import com.kalin.pojo.vo.ArticleList;
import com.kalin.pojo.vo.CommentRoot;
import com.kalin.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    //分页查询
    @GetMapping("/loadCategory")
    public Result<PageBean<ArticleList>> findArticle(@RequestParam @Validated Integer pageNum, Integer pageSize){
        PageBean<ArticleList> articlePageBean = communityService.findArticleList(pageNum,pageSize);
        return Result.success(articlePageBean);
    }

    //根据文章id查询
    @PostMapping("/getArticleInfo")
    public Result<Article> getArticleInfo(@RequestParam @Validated Integer articleId){
        Article article = communityService.findArticle(articleId);
        return Result.success(article);
    }

    //根据openid查询作者
    @PostMapping("/getArticleAuthor")
    public Result<UserData> getArticleAuthor(@RequestParam @Validated String openid){
        UserData userData = communityService.findAuthor(openid);
        return Result.success(userData);
    }

    //收藏文章
    @PostMapping("/addCollect")
    public Result addCollectArticle(@RequestParam @Validated Integer articleId){
        communityService.addSaves(articleId);
        return Result.success();
    }

    //取消收藏文章
    @DeleteMapping("/cancelCollect")
    public Result delCollectArticle(@RequestParam @Validated Integer articleId){
        communityService.delSaves(articleId);
        return Result.success();
    }

    //点赞文章
    @PostMapping("/addLike")
    public Result addLikeNumber(@RequestParam @Validated Integer articleId){
        communityService.addLikeNumber(articleId);
        return Result.success();
    }

    //取消点赞
    @PostMapping("/cancelLike")
    public Result lessLikeNumber(@RequestParam @Validated Integer articleId){
        communityService.lessLikeNumber(articleId);
        return Result.success();
    }

    //模糊搜索
    @GetMapping("/searchArticle")
    public Result<PageBean<Article>> getSearchArticle(@RequestParam @Validated String searchStr,Integer pageNum,Integer pageSize){
        PageBean<Article> articlePageBean = communityService.searchArticle(searchStr,pageNum,pageSize);
        return Result.success(articlePageBean);
    }

    //排行榜
    @PostMapping("/articleTop")
    public Result<List<Article>> getArticleTop(){
        List<Article> articleList = communityService.findArticleTop();
        return Result.success(articleList);
    }

    //添加根评论(评论文章)
    @PostMapping("/fatherComment")
    public Result addFatherComment(@RequestBody Comment comment){
        System.out.println(comment);
        communityService.addRootComment(comment);
        return Result.success();
    }

    //添加回复他人评论(回复别人评论)
    @PostMapping("/childComment")
    public Result addChildComment(@RequestBody Comment comment){
        System.out.println(comment);
        communityService.addChildComment(comment);
        return Result.success();
    }

    //删除评论
    @DeleteMapping("/delComment")
    public Result delComment(@RequestParam Integer recordId){
        communityService.delComment(recordId);
        return Result.success();
    }

    //查询文章所有评论
    @PostMapping("/findArticleComment")
    public Result<List<CommentRoot>> getArticleComment(@RequestParam Integer articleId){
        List<CommentRoot> commentRootList = communityService.findArticleRootComment(articleId);
        return Result.success(commentRootList);
    }

    //发布文章（添加）
    @PostMapping("/uploadArticle")
    public Result uploadArticle(@RequestBody Article article){
        communityService.addArticle(article);
        return Result.success("添加成功");
    }

    //修改文章
    @PostMapping("/updateArticle")
    public Result updateArticle(@RequestBody Article article){
        communityService.updateArticle(article);
        return Result.success("修改成功");
    }

    //删除文章
    @DeleteMapping("/delArticle")
    public Result delArticle(@RequestParam Integer articleId){
        communityService.delArticle(articleId);
        return Result.success();
    }

    //判断用户是否收藏本篇文章
    @PostMapping("/isCollect")
    public Result<Boolean> isCollect(@RequestParam Integer articleId, String openid){
        boolean flag = communityService.isCollect(articleId,openid);
        return Result.success(flag);
    }

}
