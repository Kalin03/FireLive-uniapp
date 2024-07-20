package com.kalin.service.impl;

import com.google.gson.Gson;
import com.kalin.mapper.UserMapper;
import com.kalin.pojo.dto.*;
import com.kalin.pojo.vo.WeChatSessionModel;
import com.kalin.pojo.vo.WeekFoodIntake;
import com.kalin.pojo.vo.WeekSportConsume;
import com.kalin.service.UserService;
import com.kalin.utils.ChatGLMResponse;
import com.kalin.utils.GetTargetValue;
import com.kalin.utils.JwtUtil;
import com.kalin.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${APPID}")
    private String appid;

    @Value("${APPSECRET}")
    private String appsecret;

    @Autowired
    private RestTemplate restTemplate;

    // 用于存储用户信息和token
    Map<String, Object> map = new HashMap<>();

    @Override
    public Map<String, Object> checkLogin(String code) {
        //请求微信服务器url
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid
                + "&secret=" + appsecret
                + "&js_code=" + code
                + "&grant_type=authorization_code";
        String errmsg = "";
        String errcode = "";
        String session_key = "";
        String openid = "";
        WeChatSessionModel weChatSessionModel;
        //向发送请求F
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        //判断请求是否成功
        if (responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            //获取主页内容
            String sessionData = responseEntity.getBody();
            Gson gson = new Gson();
            //将json字符串转换为实体类
            weChatSessionModel = gson.fromJson(sessionData, WeChatSessionModel.class);
            //获取用户唯一标识openid
            openid = weChatSessionModel.getOpenid();
            //获取错误码
            errcode = weChatSessionModel.getErrcode();
            //获取错误信息
            errmsg = weChatSessionModel.getErrmsg();
            //获取会话校验
            session_key = weChatSessionModel.getSession_key();
        } else {
            //在map里面添加错误信息
            map.put("errmsg", errmsg);
            //此时返回 map.size() == 1 所以在UserController内判断为1即为错误
            return map;
        }
        //判断是否成功获取到openid
        if ("".equals(openid) || openid == null) {
            //同上
            map.put("errmsg", errmsg);
            return map;
        } else {
            //用于存储需要验证的值
            Map<String, Object> claims = new HashMap<>();
            //判断用户是否存在，查询数据库
            User user = findUser(openid);
            //不存在 加入数据表
            if (user == null) {
                //数据写入数据库
                User newUser = new User();
                newUser.setOpenid(openid);
                //先写入user
                userMapper.addUser(newUser);
                //再写入UserData
                UserData userData = new UserData();
                userData.setOpenid(openid);
                userData.setAvatar("https://health-user-avatar.oss-cn-beijing.aliyuncs.com/user/avatar/default.jpg");
                userData.setNickName("微信用户");
                //添加新用户信息
                userMapper.addUserData(userData);
                //初始化用户数据库数据(优先级推荐)
                initPriority(openid);
                //用户信息加入map返回
                map.put("userData", userData);
                //用Map存储我们需要验证的值
                claims.put("openid", newUser.getOpenid());
                //将claims放入到ThreadLocalUtil
                ThreadLocalUtil.set(claims);
                //调用JwtUtil 创建token
                String token = JwtUtil.genToken(claims);
                //添加token
                map.put("token", token);
                return map;
            } else {
                //用户存在 查询出用户信息加入map返回
                UserData userData = userMapper.findUserData(user.getOpenid());
                map.put("userData", userData);
                //用Map存储我们需要验证的值
                claims.put("openid", user.getOpenid());
                //将claims放入到ThreadLocalUtil
                ThreadLocalUtil.set(claims);
                String token = JwtUtil.genToken(claims);
                map.put("token", token);
                return map;
            }
        }
    }

    //初始化食物和运动优先级
    public void initPriority(String openid) {
        //查询食物表总记录数
        int foodSum = userMapper.foodSum();
        for (int i = 1; i <= foodSum; i++) {
            FoodPriority foodPriority = new FoodPriority();
            foodPriority.setOpenid(openid);
            foodPriority.setFoodId(i);
            foodPriority.setPriority(1);
            foodPriority.setUpdateTime(LocalDateTime.now());
            foodPriority.setCreateTime(LocalDateTime.now());
            //插入记录
            userMapper.addFoodPriority(foodPriority);
        }
        //查询运动表总记录数
        int sportSum = userMapper.sportSum();
        for (int i = 1; i <= sportSum; i++) {
            SportPriority sportPriority = new SportPriority();
            sportPriority.setOpenid(openid);
            sportPriority.setSportId(i);
            sportPriority.setPriority(1);
            sportPriority.setUpdateTime(LocalDateTime.now());
            sportPriority.setCreateTime(LocalDateTime.now());
            //插入记录
            userMapper.addSportPriority(sportPriority);
        }

    }

    //查询数据库 获取一周摄入总值
    @Override
    public WeekFoodIntake getWeekFoodIntake(LocalDate startTime, LocalDate endTime) {
        Map<String, Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");

        int startYear = startTime.getYear();
        int endYear = endTime.getYear();
        int startMonth = startTime.getMonthValue();
        int endMonth = endTime.getMonthValue();
        int startDay = startTime.getDayOfMonth();
        int endDay = endTime.getDayOfMonth();

        WeekFoodIntake weekFoodIntake = new WeekFoodIntake();
        List<Intake> intakeList = userMapper.getIntakeList(openid, startYear, startMonth, startDay, endYear, endMonth, endDay);

        double carbohydrates = 0;
        double vitamins = 0;
        double fats = 0;
        double water = 0;
        double minerals = 0;
        double proteins = 0;

        double kcalList[] = new double[7];

        for (Intake intake : intakeList) {
            carbohydrates += intake.getCarbohydrates();
            vitamins += intake.getVitamins();
            fats += intake.getFats();
            water += intake.getWater();
            minerals += intake.getMinerals();
            proteins += intake.getProteins();
            //热量 = (碳水*4)+(蛋白质*4)+(脂肪*9) 单位克
            double kcal = intake.getCarbohydrates() * 4 + intake.getProteins() * 4 + intake.getFats() * 9;
            int dayOfWeek = intake.getCreateTime().getDayOfWeek().getValue();
            kcalList[dayOfWeek - 1] += kcal;
        }
        weekFoodIntake.setCarbohydrates(carbohydrates);
        weekFoodIntake.setVitamins(vitamins);
        weekFoodIntake.setFats(fats);
        weekFoodIntake.setMinerals(minerals);
        weekFoodIntake.setWater(water);
        weekFoodIntake.setProteins(proteins);
        weekFoodIntake.setKcalList(kcalList);
        return weekFoodIntake;
    }

    //查询数据库 获取一周消耗总值
    @Override
    public WeekSportConsume getWeekSportConsume(LocalDate startTime, LocalDate endTime) {
        Map<String, Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");

        int startYear = startTime.getYear();
        int endYear = endTime.getYear();
        int startMonth = startTime.getMonthValue();
        int endMonth = endTime.getMonthValue();
        int startDay = startTime.getDayOfMonth();
        int endDay = endTime.getDayOfMonth();

        WeekSportConsume weekSportConsume = new WeekSportConsume();
        List<Consume> consumeList = userMapper.findConsumeList(openid, startYear, startMonth, startDay, endYear, endMonth, endDay);

        double sportConsumptionSum = 0;

        double kcalList[] = new double[7];

        for (Consume consume : consumeList) {
            sportConsumptionSum += consume.getSportConsumption();
            int dayOfWeek = consume.getCreateTime().getDayOfWeek().getValue();
            kcalList[dayOfWeek - 1] += consume.getSportConsumption();
        }

        weekSportConsume.setSportConsumptionSum(sportConsumptionSum);
        weekSportConsume.setKcalList(kcalList);

        return weekSportConsume;
    }

    //查询用户收藏的文章
    @Override
    public List<Article> findMyCollect() {
        Map<String, Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        List<Article> articleList = userMapper.findMyCollect(openid);
        return articleList;
    }

    //查询用户所有Bmi值
    @Override
    public List<Bmi> geiAllBmi() {
        Map<String, Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        List<Bmi> bmiList = userMapper.findAllBmi(openid);
        return bmiList;
    }

    //根据openid查询用户
    @Override
    public User findUser(String openid) {
        User user;
        user = userMapper.findUser(openid);
        return user;
    }

    //根据openid查询用户信息
    @Override
    public UserData findUserData() {
        Map<String, Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        UserData userData = userMapper.findUserData(openid);
        return userData;
    }

    //更新用户
    @Override
    public void updateUserData(UserData userData) throws Exception {
        Map<String, Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        System.out.println(openid);
        String sex = userData.getSex();
        double weight = userData.getWeight();
        double height = userData.getHeight();
        int age = LocalDate.now().getYear() - userData.getBirthday().getYear();
        String target = userData.getTarget();
        //添加目标值
        //查询是否存在数据库 存在update 反之insert
        TargetValue targetValue = GetTargetValue.getTarget(height, weight, age, sex, target);
        targetValue.setOpenid(openid);
        if (userMapper.findTargetValue(openid) != null) {
            userMapper.updateTargetValue(targetValue);
        } else {
            userMapper.insertTargetValue(targetValue);
        }
        //记录用户体重更改 计算BMI值
        //bim=weight/height^2(体重单位kg,身高单位m)
        double bmi = userData.getWeight() / Math.pow(userData.getHeight() / 100.0, 2);
        userMapper.insertBmi(openid, bmi);
        //获得ai健康分析信息
        String str = ChatGLMResponse.getHealthAnalyse(sex,height,weight,age);
        userData.setHealthAnalyse(str);
        System.out.println(str);
        System.out.println(userData.getOpenid());
        //修改用户信息
        userMapper.updateUserData(userData);
    }

    //查询打卡记录
    @Override
    public List<CheckIn> findCheckIn() {
        Map<String,Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        List<CheckIn> checkInList = userMapper.findCheckIn(openid);
        return checkInList;
    }

    //插入用户反馈信息
    @Override
    public void insertFeedback(Feedback feedback) {
        userMapper.insertFeedback(feedback);
    }

    //查询用户食品摄入记录
    @Override
    public List<Intake> findFoodConsumption(LocalDate findTime) {
        Map<String, Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        int year = findTime.getYear();
        int month = findTime.getMonthValue();
        int day = findTime.getDayOfMonth();
        List<Intake> intakeList = userMapper.findFoodConsumption(openid, year, month, day);
        return intakeList;
    }

    //查询用户运动消耗记录
    @Override
    public List<Consume> findSportConsumption(LocalDate findTime) {
        Map<String, Object> userMap = ThreadLocalUtil.get();
        String openid = (String) userMap.get("openid");
        int year = findTime.getYear();
        int month = findTime.getMonthValue();
        int day = findTime.getDayOfMonth();
        List<Consume> consumeList = userMapper.findSportConsumption(openid, year, month, day);
        return consumeList;
    }
}
