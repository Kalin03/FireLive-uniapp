package com.kalin.controller;

import com.kalin.pojo.ao.Result;
import com.kalin.pojo.dto.*;
import com.kalin.pojo.vo.WeChatModel;
import com.kalin.pojo.vo.WeekFoodIntake;
import com.kalin.pojo.vo.WeekSportConsume;
import com.kalin.service.UserService;
import com.kalin.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/my")
public class UserController {

    @Autowired
    private UserService userService;

    //用于测试
    @GetMapping("/test")
    public Result<UserData> hello(){
        UserData userData = new UserData();
        userData.setOpenid("openidxxxx");
        userData.setAvatar("static/user/默认头像.png");
        userData.setNickName("微信用户");
        return Result.success(userData);
    }

    //微信一键登录
    @PostMapping("/login")
    public Result<UserData> login(@RequestBody WeChatModel weChatModel, HttpServletResponse response){
        //检查登录
        Map<String,Object> resultMap = userService.checkLogin(weChatModel.getCode());
        //resultMap大于1为通过
        if(resultMap.size() > 1){
            //将token添加入响应头以及返回用户信息
            response.setHeader("Authorization",resultMap.get("token").toString());
            return Result.success((UserData) resultMap.get("userData"));
        }else{
            //当size为1 即为报错信息
            return Result.error(resultMap.get("errmsg").toString());
        }
    }

    //获取用户信息
    @GetMapping("/getUserInfo")
    public Result<UserData> findUserData(){
        UserData userData = userService.findUserData();
        return Result.success(userData);
    }

    //更新用户信息
    @PutMapping("/updateUserInfo")
    public Result updateUserData(@RequestBody @Validated UserData userData) throws Exception {
        userService.updateUserData(userData);
        return Result.success("添加成功");
    }

    //获取用户食物摄入记录
    @PostMapping("/foodConsumptionInfo")
    public Result<List<Intake>> getFoodConsumptionInfo(@RequestParam @Validated LocalDate findTime){
        List<Intake> intakeList = userService.findFoodConsumption(findTime);
        if(intakeList != null)
            return Result.success(intakeList);
        else
            return Result.error("未查询到");
    }

    //获取用户运动消耗记录
    @PostMapping("/sportConsumptionInfo")
    public Result<List<Consume>> getSportConsumptionInfo(@RequestParam @Validated LocalDate findTime){
        List<Consume> consumeList = userService.findSportConsumption(findTime);
        if(consumeList != null)
            return Result.success(consumeList);
        else
            return Result.error("未查询到");
    }

    //获取用户打卡记录
    @GetMapping("/checkInRecord")
    public Result<List<CheckIn>> getCheckIn(){
        List<CheckIn> checkInList = userService.findCheckIn();
        return Result.success(checkInList);
    }

    //提交用户反馈信息
    @PostMapping("/sendFeedback")
    public Result userFeedback(@RequestParam @Validated String feedbackContent){
        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        Feedback feedback = new Feedback();
        feedback.setFeedbackContent(feedbackContent);
        feedback.setOpenid(openid);
        feedback.setCreateTime(LocalDateTime.now());
        userService.insertFeedback(feedback);
        return Result.success("反馈成功");
    }

    //获取一周摄入总值
    @PostMapping("/weekFood")
    public Result<WeekFoodIntake> getWeekFood(@RequestParam @Validated LocalDate startTime, LocalDate endTime){
        WeekFoodIntake weekFoodIntake = userService.getWeekFoodIntake(startTime,endTime);
        return Result.success(weekFoodIntake);
    }

    //获取一周消耗总值
    @PostMapping("/weekSport")
    public Result<WeekSportConsume> getWeekSport(@RequestParam @Validated LocalDate startTime, LocalDate endTime){
        WeekSportConsume weekSportConsume = userService.getWeekSportConsume(startTime,endTime);
        return Result.success(weekSportConsume);
    }

    //查询我的收藏
    @GetMapping("/myCollect")
    public Result<List<Article>> getMyCollect(){
        List<Article> articleList = userService.findMyCollect();
        return Result.success(articleList);
    }

    //查询用户所有bmi记录
    @GetMapping("/getUserBmi")
    public Result<List<Bmi>> getUserBmi(){
        List<Bmi> bmiList = userService.geiAllBmi();
        return Result.success(bmiList);
    }
}
