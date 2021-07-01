package com.tanhua.server.interceptor;

import com.tanhua.domain.db.User;
import com.tanhua.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("进入前置处理器");
        log.info("进入前置拦截器");
        String token = request.getHeader("Authorization");
        if(StringUtils.isEmpty(token)){
            User user=userService.getUserByToken(token);
            if(user==null){
                response.setStatus(401);
                return false;
            }
            UserHolder.setUser(user);
            return true;
            }
        response.setStatus(401);
        return false;

    }
}
