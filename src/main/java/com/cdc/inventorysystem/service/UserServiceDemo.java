package com.cdc.inventorysystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdc.inventorysystem.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-05-15
 */
public interface UserServiceDemo extends IService<User> {


    /**
     * 注册
     * @param username
     * @param password
     */
    void register(String username, String password);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password,
                 HttpServletRequest request, HttpServletResponse response);

    void test();

    void logout(HttpServletResponse response);

    public boolean validate(String username, String password);
}

