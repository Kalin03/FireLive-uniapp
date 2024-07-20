package com.kalin.controller;

import com.kalin.pojo.ao.Result;
import com.kalin.pojo.dto.*;
import com.kalin.pojo.vo.ConsumeSum;
import com.kalin.pojo.vo.IntakeSum;
import com.kalin.pojo.vo.WeekFoodIntake;
import com.kalin.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    //查询食物摄入及运动消耗值目标值
    @GetMapping("/target")
    public Result<TargetValue> getTarget(){
        TargetValue targetValue =  indexService.findTarget();
        return Result.success(targetValue);
    }

    //获取所有食品信息
    @GetMapping("/food")
    public Result<List<Food>> allFoodInfo(){
        List<Food> foodList = indexService.finAllFood();
        return Result.success(foodList);
    }

    //获取所有运动信息
    @GetMapping("/sport")
    public Result<List<Sport>> allSportInfo(){
        List<Sport> sportList = indexService.finAllSport();
        return Result.success(sportList);
    }

    //提交打卡
    @PostMapping("/checkIn")
    public Result checkIn(@RequestBody @Validated CheckIn checkIn){
        indexService.checkIn(checkIn);
        return Result.success("打卡成功");
    }

    //查询打卡记录
    @PostMapping("/findCheckIn")
    public Result<CheckIn> findCheckIn(@RequestParam LocalDate findTime){
        CheckIn checkIn = indexService.findCheckIn(findTime);
        return  Result.success(checkIn);
    }

    //食物消耗提交
    @PostMapping("/foodConsumption")
    public Result foodConsumeSubmit(@RequestBody @Validated List<Intake> intakeList){
        indexService.addIntake(intakeList);
        return Result.success("提交成功");
    }

    //运动消耗提交
    @PostMapping("/sportConsumption")
    public Result sportConsumeSubmit(@RequestBody @Validated List<Consume> consumeList){
        indexService.addConsume(consumeList);
        return Result.success("提交成功");
    }

    //单日摄入总值查询
    @GetMapping("/intakeSum")
    public Result<IntakeSum> getIntakeSum(){
        IntakeSum intakeSum = indexService.getIntakeSum();
        return Result.success(intakeSum);
    }

    //单日消耗总值查询
    @GetMapping("/consumeSum")
    public Result<ConsumeSum> getConsumeSum(){
        ConsumeSum consumeSum = indexService.getConsumeSum();
        return Result.success(consumeSum);
    }

    //无需登录查询食物信息
    @GetMapping("/showFood")
    public Result<List<Food>> getShowFood(){
        List<Food> foodList = indexService.getAllFood();
        return Result.success(foodList);
    }

    //无需登录查询运动信息
    @GetMapping("/showSport")
    public Result<List<Sport>> getShowSport(){
        List<Sport> sportList = indexService.getAllSport();
        return Result.success(sportList);
    }

}
