package com.tanhua.dubbo.api;

import com.tanhua.domain.db.Settings;

public interface SettingsApi {
    //根据用户id查询通知配置
    Settings findByUserId(Long userId);
}