package com.kalin.mapper;

import com.kalin.pojo.dto.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    //根据openid查询用户
    @Select("select * from user_table where openid = #{openid}")
    User findUser(@Param("openid") String openid);

    //添加新用户
    @Insert("insert into user_table " +
            "values (#{openid},now(),now())")
    void addUser(User user);

    //添加新用户信息
    @Insert("insert into user_data(openid,avatar,nick_name,update_time,create_time) " +
            "values (#{openid},#{avatar},#{nickName},now(),now())")
    void addUserData(UserData userData);

    //查找用户信息
    @Select("select * from user_data where openid = #{openid}")
    UserData findUserData(String openid);

    //更新用户信息
    @Update("update user_data set avatar = #{avatar},nick_name = #{nickName},sex = #{sex}," +
            "height = #{height},weight = #{weight},birthday = #{birthday},signature = #{signature}," +
            "health_analyse = #{healthAnalyse},target = #{target},update_time = now() where openid = #{openid}")
    void updateUserData(UserData userData);

    //查询用户打卡记录
    @Select("select * from check_in_table where openid = #{openid}")
    List<CheckIn> findCheckIn(String openid);

    //插入用户反馈信息
    @Insert("insert into feedback_table(openid,feedback_content,create_time)" +
            " values (#{openid},#{feedbackContent},#{createTime})")
    void insertFeedback(Feedback feedback);

    //查询用户食物摄入记录
    @Select("select * from intake_table where openid = #{openid} and YEAR(create_time) = #{year}" +
            " and MONTH(create_time) = #{month} and DAY(create_time) = #{day}")
    List<Intake> findFoodConsumption(String openid, int year,int month,int day);

    //查询用户运动消耗记录
    @Select("select * from consume_table where openid = #{openid} and YEAR(create_time) = #{year}" +
            " and MONTH(create_time) = #{month} and DAY(create_time) = #{day}")
    List<Consume> findSportConsumption(String openid, int year, int month, int day);

    //查询food_table记录总数
    @Select("select count(*) from food_table")
    int foodSum();

    //插入food_priority记录
    @Insert("insert into food_priority values (#{foodId},#{openid},#{priority},#{updateTime},#{createTime})")
    void addFoodPriority(FoodPriority foodPriority);

    //查询sport_table记录总数
    @Select("select count(*) from sport_table")
    int sportSum();

    //插入sport_priority记录
    @Insert("insert into sport_priority values (#{sportId},#{openid},#{priority},#{updateTime},#{createTime})")
    void addSportPriority(SportPriority sportPriority);

    //查询摄入周报
    @Select("select * from intake_table where openid = #{openid} and YEAR(create_time) >= #{startYear}" +
            " and YEAR(create_time) <= #{endYear} and MONTH(create_time) >= #{startMonth} and MONTH(create_time) <= #{startMonth}" +
            " and DAY(create_time) >= #{startDay} and DAY(create_time) <= #{endDay}")
    List<Intake> getIntakeList(String openid, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay);

    //查询消耗周报
    @Select("select * from consume_table where openid = #{openid} and YEAR(create_time) >= #{startYear}" +
            " and YEAR(create_time) <= #{endYear} and MONTH(create_time) >= #{startMonth} and MONTH(create_time) <= #{startMonth}" +
            " and DAY(create_time) >= #{startDay} and DAY(create_time) <= #{endDay}")
    List<Consume> findConsumeList(String openid, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay);

    //查询我的收藏文章
    @Select("select a.article_id,a.openid,a.article_image,a.article_title,a.article_content,a.likes_number,a.comments_number," +
            "a.collect_number,a.update_time,a.create_time" +
            " from article_table as a,saves_table as b where b.openid = #{openid} and a.article_id = b.article_id")
    List<Article> findMyCollect(String openid);

    //插入bmi
    @Insert("insert into bmi_table(openid,BMI,update_time,create_time)" +
            "values(#{openid},#{bmi},now(),now())")
    void insertBmi(String openid, double bmi);

    //添加用户目标值
    @Insert("insert into target_value values (#{openid},#{carbohydrates},#{vitamins},#{fats},#{water}" +
            ",#{minerals},#{proteins},#{sportConsumption},now(),now())")
    void insertTargetValue(TargetValue targetValue);

    //查询用户目标值
    @Select("select * from target_value where openid = #{openid}")
    TargetValue findTargetValue(String openid);

    //修改用户目标值
    @Update("update target_value set carbohydrates = #{carbohydrates},vitamins = #{vitamins},fats = #{fats}," +
            "water = #{water},minerals = #{minerals},proteins = #{proteins},sport_consumption = #{sportConsumption},update_time = now() " +
            "where openid = #{openid}")
    void updateTargetValue(TargetValue targetValue);

    //查询所有bmi值
    @Select("select * from bmi_table where openid = #{openid}")
    List<Bmi> findAllBmi(String openid);
}
