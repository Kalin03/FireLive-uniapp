package com.kalin.interceptors;

import com.kalin.utils.JwtUtil;
import com.kalin.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptors implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //得到令牌
        String token = request.getHeader("Authorization");
        //判断token是否存在
        if(token == null || token.equals("")){
            return false;
        }
        try {
            Map<String,Object> claims = JwtUtil.parseToken(token);

            //将claims放入到ThreadLocalUtil
            ThreadLocalUtil.set(claims);

            return true;
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
