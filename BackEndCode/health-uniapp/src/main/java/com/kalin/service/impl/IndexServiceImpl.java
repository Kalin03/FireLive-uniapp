package com.kalin.service.impl;

import com.kalin.mapper.IndexMapper;
import com.kalin.pojo.dto.*;
import com.kalin.pojo.vo.ConsumeSum;
import com.kalin.pojo.vo.IntakeSum;
import com.kalin.service.IndexService;
import com.kalin.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IndexMapper indexMapper;

    //查询用户目标值
    @Override
    public TargetValue findTarget() {
        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        TargetValue targetValue = indexMapper.findTarget(openid);
        return targetValue;
    }

    //查询所有食品信息
    @Override
    public List<Food> finAllFood() {
        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        List<Food> foodList = indexMapper.findAllFood(openid);
        return foodList;
    }

    //查询所有运动信息
    @Override
    public List<Sport> finAllSport() {
        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        List<Sport> foodSport = indexMapper.findAllSport(openid);
        return foodSport;
    }

    //提交打卡
    @Override
    public void checkIn(CheckIn checkIn) {
        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        checkIn.setOpenid(openid);
        checkIn.setCreateTime(LocalDateTime.now());
        checkIn.setUpdateTime(LocalDateTime.now());
        //如果今日打卡记录不存在 那么就插入 反之修改
        if(indexMapper.findCheckIn(openid, LocalDate.now()) != null){
            indexMapper.updateCheckIn(checkIn,LocalDate.now());
        }else {
            indexMapper.insertCheckIn(checkIn);
        }
    }

    //食物消耗提交添加进数据库
    @Override
    public void addIntake(List<Intake> intakeList) {
        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        for(Intake intake:intakeList){
            //食物消耗记录添加进数据库
            intake.setOpenid(openid);
            indexMapper.addIntake(intake);
            //优先级提交
            indexMapper.addFoodPriority(intake.getFoodId(),openid);
        }
    }

    //运动消耗数值添加进数据库
    @Override
    public void addConsume(List<Consume> consumeList) {
        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        for(Consume consume:consumeList){
            //运动消耗记录添加进数据库
            consume.setOpenid(openid);
            indexMapper.addConsume(consume);
            //优先级提交
            indexMapper.addSportPriority(consume.getSportId(),openid);
        }
    }

    //增加运动优先级
    @Override
    public void addSportPriority(List<Integer> sportIds) {
        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        for(Integer sportId:sportIds){
            indexMapper.addSportPriority(sportId,openid);
        }
    }

    //查询打卡记录
    @Override
    public CheckIn findCheckIn(LocalDate date) {
        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        CheckIn checkIn = indexMapper.findCheckIn(openid,date);
        return checkIn;
    }

    //单日摄入总值
    @Override
    public IntakeSum getIntakeSum() {
        IntakeSum intakeSum = new IntakeSum();
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        List<Intake> intakeList = indexMapper.findIntakeNow(openid,year,month,day);
        for (Intake intake:intakeList){
            intakeSum.setCarbohydrates(intakeSum.getCarbohydrates() + intake.getCarbohydrates());
            intakeSum.setVitamins(intakeSum.getVitamins() + intake.getVitamins());
            intakeSum.setFats(intakeSum.getFats() + intake.getFats());
            intakeSum.setWater(intakeSum.getWater() + intake.getWater());
            intakeSum.setMinerals(intakeSum.getMinerals() + intake.getMinerals());
            intakeSum.setProteins(intakeSum.getProteins() + intake.getProteins());
        }

        intakeSum.setWater(intakeSum.getWater() / 1000);

        return intakeSum;
    }

    //单日消耗总值
    @Override
    public ConsumeSum getConsumeSum() {
        ConsumeSum consumeSum = new ConsumeSum();
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        List<Consume> consumeList = indexMapper.findConsumeNow(openid,year,month,day);
        for (Consume consume:consumeList){
            consumeSum.setSportConsumption(consumeSum.getSportConsumption() + consume.getSportConsumption());
        }

        return consumeSum;
    }

    //获取食品展示信息
    @Override
    public List<Food> getAllFood() {
        List<Food> foodList = indexMapper.getAllFood();
        return foodList;
    }

    //获取运动展示信息
    @Override
    public List<Sport> getAllSport() {
        List<Sport> sportList = indexMapper.getAllSport();
        return sportList;
    }
}
