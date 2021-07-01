package com.tanhua.dubbo.api;

import com.tanhua.domain.db.UserInfo;
import com.tanhua.dubbo.mapper.UserInfoMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserInfoApiImpl implements UserInfoApi {

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 保存用户基本信息
     * @param userInfo
     */
    @Override
    public void save(UserInfo userInfo) {

        userInfoMapper.insert(userInfo);
    }

    /**
     * 通过id更新用户基本信息
     * @param userInfo
     */
    @Override
    public void update(UserInfo userInfo) {

        userInfoMapper.updateById(userInfo);
    }
    /**
     * 通过id查询用户基本信息
     * @param userId
     */
    @Override
    public UserInfo queUserInfo(Long userId) {
        return userInfoMapper.selectById(userId);
    }


}
