package com.tanhua.server.interceptor;

import com.tanhua.domain.db.User;

/**
 * 登陆用户信息持有者
 * 通过ThreadLocal的形式，存储登陆用户的数据
 */
public class UserHolder {
    private static ThreadLocal<User> userThreadLocal=new ThreadLocal<>();
    /**
     * 向当前线程存入用户数据
     */
    public static void setUser(User user){
        userThreadLocal.set(user);

    }
    /**
     * 从当前线程获得用户数据
     */
public static User getUser(){
    return userThreadLocal.get();
}
/**
 * 获得用户的ID
 *
 */
public static Long getUserID(){
    return userThreadLocal.get().getId();
}

}
