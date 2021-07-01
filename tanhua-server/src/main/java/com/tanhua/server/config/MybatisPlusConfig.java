package com.tanhua.server.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

public class MybatisPlusConfig {
    /**
     * 启用分页插件
     */
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
