package com.kalin.service;

import com.kalin.pojo.dto.*;
import com.kalin.pojo.vo.WeekFoodIntake;
import com.kalin.pojo.vo.WeekSportConsume;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface UserService {

    //检查是否注册
    public Map<String,Object> checkLogin(String code);

    //根据openid查询用户
    public User findUser(String openid);

    UserData findUserData();

    void updateUserData(UserData userData) throws Exception;

    List<CheckIn> findCheckIn();

    void insertFeedback(Feedback feedback);

    List<Intake> findFoodConsumption(LocalDate findTime);

    List<Consume> findSportConsumption(LocalDate findTime);

    void initPriority(String openid);

    WeekFoodIntake getWeekFoodIntake(LocalDate startTime, LocalDate endTime);

    List<Article> findMyCollect();

    List<Bmi> geiAllBmi();

    WeekSportConsume getWeekSportConsume(LocalDate startTime, LocalDate endTime);
}
