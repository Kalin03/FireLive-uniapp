package com.kalin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kalin.mapper.CommunityMapper;
import com.kalin.mapper.UserMapper;
import com.kalin.pojo.ao.PageBean;
import com.kalin.pojo.dto.Article;
import com.kalin.pojo.dto.Comment;
import com.kalin.pojo.dto.SavesTable;
import com.kalin.pojo.dto.UserData;
import com.kalin.pojo.vo.ArticleList;
import com.kalin.pojo.vo.CommentChild;
import com.kalin.pojo.vo.CommentRoot;
import com.kalin.service.CommunityService;
import com.kalin.utils.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private UserMapper userMapper;

    //根据文章id查询文章
    @Override
    public Article findArticle(Integer articleId) {
        Article article = communityMapper.findArticle(articleId);
        return article;
    }

    //根据文章id查询作者
    @Override
    public UserData findAuthor(String openid) {
        UserData userData = communityMapper.findAuthor(openid);
        return userData;
    }

    //当前登录用户收藏文章
    @Override
    public void addSaves(Integer articleId) {
        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        communityMapper.addSaves(openid,articleId);
        //查询收藏数量
        int collectNumber = communityMapper.findCollectNumber(articleId);
        //修改收藏数量
        communityMapper.updateArticleCollectNumber(collectNumber,articleId);
    }

    //取消收藏文章
    @Override
    public void delSaves(Integer articleId) {
        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        communityMapper.delSaves(openid,articleId);
        //查询收藏数量
        int collectNumber = communityMapper.findCollectNumber(articleId);
        //修改收藏数量
        communityMapper.updateArticleCollectNumber(collectNumber,articleId);
    }

    //分页查询
    @Override
    public PageBean<ArticleList> findArticleList(Integer pageNum, Integer pageSize) {
        //创建PageBean对象
        PageBean<ArticleList> articlePageBean = new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //数据库查询
        List<ArticleList> articleList = communityMapper.findArticleList();
        //Page中提供了方法，可以获取PageHelper分页查询后，得到记录条数和当前页数据
        Page<ArticleList> page = (Page<ArticleList>) articleList;
        //对PageBean对象进行填充
        //总记录数
        articlePageBean.setTotal(page.getTotal());
        //数据
        articlePageBean.setItems(page.getResult());
        return articlePageBean;
    }

    //点赞数量
    @Override
    public void addLikeNumber(Integer articleId) {
        communityMapper.addLikeNumber(articleId);
    }

    //取消点赞
    @Override
    public void lessLikeNumber(Integer articleId) {
        communityMapper.lessLikeNumber(articleId);
    }

    @Override
    public PageBean<Article> searchArticle(String searchStr, Integer pageNum, Integer pageSize) {
        PageBean<Article> articlePageBean = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        searchStr = '%' + searchStr + '%';
        List<Article> articleList = communityMapper.searchArticleList(searchStr);
        Page<Article> articlePage = (Page<Article>) articleList;
        articlePageBean.setTotal(articlePage.getTotal());
        articlePageBean.setItems(articlePage.getResult());
        return articlePageBean;
    }

    //文章排行榜
    @Override
    public List<Article> findArticleTop() {
        List<Article> articleList = communityMapper.findArticleTop();
        return articleList;
    }

    //添加根评论
    @Override
    public void addRootComment(Comment comment) {
        comment.setType(0);//标识为根评论
        comment.setCreateTime(LocalDateTime.now());
        communityMapper.addRootComment(comment);
        //查询评论数量
        int commentNumber = communityMapper.findCommentNumber(comment.getArticleId());
        //修改评论数量
        communityMapper.updateCommentNumber(commentNumber,comment.getArticleId());
    }

    //回复别人评论
    @Override
    public void addChildComment(Comment comment) {
        comment.setType(1);//标识为子评论
        comment.setCreateTime(LocalDateTime.now());
        communityMapper.addChildComment(comment);
        //查询评论数量
        int commentNumber = communityMapper.findCommentNumber(comment.getArticleId());
        //修改评论数量
        communityMapper.updateCommentNumber(commentNumber, comment.getArticleId());
    }

    //删除评论
    @Override
    public void delComment(Integer recordId) {
        //查询评论信息
        Comment comment = communityMapper.findCommentInfo(recordId);
        Integer type = communityMapper.findCommentType(recordId);
        //如果是子评论 只需删除本身 反之删除本身评论和所有子评论
        if (type == 1){
            communityMapper.delRootComment(recordId);
        } else if (type == 0) {
            communityMapper.delRootComment(recordId);
            communityMapper.delChildComment(recordId);
        }
        //查询剩余评论数量
        int commentNumber = communityMapper.findCommentNumber(comment.getArticleId());
        //修改评论数量
        communityMapper.updateArticleCommentNumber(commentNumber,comment.getArticleId());
    }

    //查询文章下所有评论
    @Override
    public List<CommentRoot> findArticleRootComment(Integer articleId) {
        //查询出该帖子所有评论
        List<Comment> commentList = communityMapper.findAllComment(articleId);
        //如果为空就返回空列表
        if(commentList == null){
            return new ArrayList<>();
        }
        //统计用户id 并进行去重 set
        Set<String> openidSet = new HashSet<>();
        for (Comment comment:commentList){
            openidSet.add(comment.getOpenid());
        }
        //查询出所有用户信息
        List<String> openidList = new ArrayList<>(openidSet);
        Map<String,UserData> userDataMap = new HashMap<>();
        for (String openid:openidList){
            UserData userData = userMapper.findUserData(openid);
            userDataMap.put(openid,userData);
        }
        //创建根评论列表
        List<CommentRoot> commentRootList = new ArrayList<>();
        //创建关联map,key为根id,值为子评论集合
        Map<Integer,List<CommentChild>> commentChildMap = new HashMap<>(10);
        //存储根评论和对应评论
        Map<CommentRoot,Comment> commentRootCommentMap = new HashMap<>(10);
        for (Comment comment:commentList){
            //倒叙存入根评论，正序存入回复
            if (comment.getType() == 0){//评论为根评论
                CommentRoot commentRoot = new CommentRoot();
                //将comment属性拷贝到commentRoot
                BeanUtils.copyProperties(comment,commentRoot);
                //设置用户信息
                commentRoot.setUserData(userDataMap.get(comment.getOpenid()));
                //从头部插入根评论，并创建map
                commentRootList.add(0,commentRoot);
                //以自己(根评论的id)作为键
                commentChildMap.put(comment.getRecordId(),new ArrayList<>());
                commentRootCommentMap.put(commentRoot,comment);
            } else {
                //时间排序的集合可以保证已经创建了根评论的map
                List<CommentChild> commentChildList = commentChildMap.get(comment.getRootId());
                CommentChild commentChild = new CommentChild();
                //将comment属性拷贝到commentChild
                BeanUtils.copyProperties(comment,commentChild);
                //设置用户信息和回复用户信息
                commentChild.setUserData(userDataMap.get(comment.getOpenid()));
                commentChild.setToUserData(userDataMap.get(comment.getToUserId()));
                commentChildList.add(commentChild);
            }
        }
        //第二次遍历将子评论复制到根评论
        for (Map.Entry<CommentRoot,Comment> entry:commentRootCommentMap.entrySet()){
            entry.getKey().setChildCommentList(commentChildMap.get(entry.getValue().getRecordId()));
        }
        return commentRootList;
    }

    //添加文章
    @Override
    public void addArticle(Article article) {
        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        article.setOpenid(openid);
        communityMapper.addArticle(article);
    }

    //修改文章
    @Override
    public void updateArticle(Article article) {
        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        communityMapper.updateArticle(article,openid);
    }

    //删除文章
    @Override
    public void delArticle(Integer articleId) {
        Map<String ,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        communityMapper.delArticle(articleId,openid);
        communityMapper.delAllComment(articleId);
    }

    //判断用户是否收藏本篇文章
    @Override
    public boolean isCollect(Integer articleId, String openid) {
        boolean flag = false;
        SavesTable savesTable = communityMapper.findCollect(articleId,openid);
        if(savesTable != null)
            flag = true;
        return flag;
    }

}
