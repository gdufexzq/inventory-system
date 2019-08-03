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
 * @author lwq
 * @since 2019-08-03
 */


public interface UserService extends IService<User> {
    /**
     * 注册
     * @param username
     * @param password
     * @param schoolName
     */
    public String register(String username, String password, String SchoolName);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password,
                 HttpServletRequest request, HttpServletResponse response);

    public void test();

    public String logout(HttpServletRequest request, HttpServletResponse response);

    public boolean validate(String username, String password);
}

