package com.kalin.mapper;

import com.kalin.pojo.dto.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface IndexMapper {

    //根据id查询目标值
    @Select("select * from target_value where openid = #{openid}")
    TargetValue findTarget(String openid);

    //查询所有食品信息
    @Select("select a.food_id,a.food_name,a.carbohydrates,a.vitamins,a.fats,a.water,a.minerals,a.proteins,b.priority" +
            ",a.food_tip from food_table as a ,food_priority as b where a.food_id = b.food_id and b.openid = #{openid}" +
            " order by b.priority DESC ")
    List<Food> findAllFood(String openid);

    //查询所有运动信息
    @Select("select a.sport_id,a.sport_name,a.sport_unit,a.f_met,a.m_met,a.s_met,a.sport_percent,b.priority" +
            ",a.sport_tip from sport_table as a,sport_priority as b where a.sport_id = b.sport_id and b.openid = #{openid}" +
            " order by b.priority DESC")
    List<Sport> findAllSport(String openid);

    //插入打卡记录
    @Insert("insert into check_in_table (openid,early_rising,running,fitness,early_bedtime,update_time,create_time)" +
            "values (#{openid},#{earlyRising},#{running},#{fitness},#{earlyBedtime},#{updateTime},#{createTime})")
    void insertCheckIn(CheckIn checkIn);

    //添加食物消耗记录
    @Insert("insert into intake_table (openid,food_id,food_name,food_weight,carbohydrates,vitamins,fats,water,minerals,proteins,update_time,create_time)" +
            "values (#{openid},#{foodId},#{foodName},#{foodWeight},#{carbohydrates},#{vitamins},#{fats},#{water},#{minerals},#{proteins},now(),now())")
    void addIntake(Intake intake);

    //添加运动消耗记录
    @Insert("insert into consume_table (openid,sport_id,sport_name,sport_number,sport_unit,sport_consumption,update_time,create_time)" +
            "values (#{openid},#{sportId},#{sportName},#{sportNumber},#{sportUnit},#{sportConsumption},now(),now())")
    void addConsume(Consume consume);

    //食物优先级增加
    @Update("update food_priority set priority = priority + 1 where openid = #{openid} and food_id = #{foodId}")
    void addFoodPriority(Integer foodId, String openid);

    //食物优先级增加
    @Update("update sport_priority set priority = priority + 1 where openid = #{openid} and sport_id = #{sportId}")
    void addSportPriority(Integer sportId, String openid);

    //查询打卡记录
    @Select("select * from check_in_table where openid = #{openid} and YEAR(create_time) = YEAR(#{now}) and MONTH(create_time) = MONTH(#{now})" +
            " and DAY(create_time) = DAY(#{now})")
    CheckIn findCheckIn(String openid, LocalDate now);

    //更新打卡记录
    @Update("update check_in_table set early_rising = #{checkIn.earlyRising}, running = #{checkIn.running}, fitness = #{checkIn.fitness}, early_bedtime = #{checkIn.earlyBedtime} " +
            "where openid = #{checkIn.openid} and YEAR(create_time) = YEAR(#{now}) and MONTH(create_time) = MONTH(#{now}) and DAY(create_time) = DAY(#{now})")
    void updateCheckIn(CheckIn checkIn, LocalDate now);

    //查询今日提交所有食物消耗记录
    @Select("select * from intake_table where YEAR(create_time) = #{year} and MONTH(create_time) = #{month} and DAY(create_time) = #{day} and openid = #{openid}")
    List<Intake> findIntakeNow(String openid, int year, int month, int day);

    //查询今日提交所有食物消耗记录
    @Select("select * from consume_table where YEAR(create_time) = #{year} and MONTH(create_time) = #{month} and DAY(create_time) = #{day} and openid = #{openid}")
    List<Consume> findConsumeNow(String openid, int year, int month, int day);

    //查询所有食物信息 用于页面展示
    @Select("select * from food_table")
    List<Food> getAllFood();

    //查询所有运动信息 用于页面展示
    @Select("select * from sport_table")
    List<Sport> getAllSport();
}
