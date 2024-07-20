package com.kalin.service;

import com.kalin.pojo.dto.*;
import com.kalin.pojo.vo.ConsumeSum;
import com.kalin.pojo.vo.IntakeSum;
import com.kalin.pojo.vo.WeekFoodIntake;

import java.time.LocalDate;
import java.util.List;

public interface IndexService {
    TargetValue findTarget();

    List<Food> finAllFood();

    List<Sport> finAllSport();

    void checkIn(CheckIn checkIn);

    void addIntake(List<Intake> intake);

    void addConsume(List<Consume> consume);

    void addSportPriority(List<Integer> sportIds);

    CheckIn findCheckIn(LocalDate date);

    IntakeSum getIntakeSum();

    ConsumeSum getConsumeSum();

    List<Food> getAllFood();

    List<Sport> getAllSport();
}
