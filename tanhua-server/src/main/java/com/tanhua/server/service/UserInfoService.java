package com.tanhua.server.service;


import com.tanhua.commons.exception.TanHuaException;
import com.tanhua.domain.db.User;
import com.tanhua.domain.db.UserInfo;
import com.tanhua.domain.vo.ErrorResult;
import com.tanhua.domain.vo.UserInfoVo;
import com.tanhua.dubbo.api.UserInfoApi;
import com.tanhua.server.interceptor.UserHolder;
import com.tanhua.server.utils.GetAgeUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
@Service
public class UserInfoService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Reference
    private UserInfoApi userInfoApi;

    @Value("${tanhua.tokenKey}")
    private String tokenKey;

    @Autowired
    private UserService userService;

    /**
     * 查询用户信息
     *
     * @param userID
     * @param huanxinID
     * @param token
     * @return
     */
    public UserInfoVo queryUserInfo(Long userID, Long huanxinID, String token) {
        //定义返回的userinfovo
        UserInfoVo userInfoVo = new UserInfoVo();
//        User user = userService.getUserByToken(token);
//        if (null == user) {
//            throw new TanHuaException();
//        }
//        Long userId = user.getId();
        Long userId = UserHolder.getUserID();
        //b.调用服务提供者：根据当前用户id查询tb_userInfo数据
        UserInfo userInfo = userInfoApi.queUserInfo(userId);
        //c.得到UserInfo后将数据copy到UserInfoVO中
        BeanUtils.copyProperties(userInfo,userInfoVo);
        //单独设置年龄
    if(!StringUtils.isEmpty(userInfo.getAge())){
        userInfoVo.setAge(String.valueOf(userInfo.getAge()));
    }
    else{
        //通过调用工具类 将生日转成年龄
        userInfoVo.setAge(String.valueOf(GetAgeUtil.getAge(userInfo.getBirthday())));

    }
        //d.返回UserInfoVo
        return userInfoVo;
    }

    /**
     * 更改用户信息
     * @param userInfoVo
     * @param token
     */

    public void updateUserInfo(UserInfoVo userInfoVo, String token) {
        //判断token是否存在（判断是否登录）
//        User user=userService.getUserByToken(token);
//        if(user==null){
//            throw new TanHuaException(
//                    ErrorResult.loginError()
//            );
//        }
//        Long userId=user.getId();
        Long userId = UserHolder.getUserID();
        //b,调用服务提供者，根据当前用户Id，更新用户信息
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoVo,userInfo);
        if(!StringUtils.isEmpty(userInfoVo.getAge())){
            userInfoVo.setAge(String.valueOf(userInfoVo.getBirthday()));
        }
        //设置当前用户Id
        userInfo.setId(userId);
        userInfoApi.update(userInfo);

    }
}
