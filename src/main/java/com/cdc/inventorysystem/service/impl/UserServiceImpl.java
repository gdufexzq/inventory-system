package com.cdc.inventorysystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdc.inventorysystem.common.exceptions.AccountLockException;
import com.cdc.inventorysystem.common.exceptions.ParameterException;
import com.cdc.inventorysystem.common.util.AESUtils;
import com.cdc.inventorysystem.common.util.CookieUtils;
import com.cdc.inventorysystem.common.util.RSAUtils;
import com.cdc.inventorysystem.dao.UserMapper;
import com.cdc.inventorysystem.entity.User;
import com.cdc.inventorysystem.service.AdminService;
import com.cdc.inventorysystem.service.SchoolService;
import com.cdc.inventorysystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwq
 * @since 2019-07-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	@Autowired
	private UserMapper userMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private AdminService adminService;

    @Autowired
    private RedisTemplate redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public User selectUserById(int id) {

		return userMapper.selectUserById(id);
	}

	@Override
	public int updateUser(User user) {
		return userMapper.updateById(user);
	}


    @Override
    public String register(String username, String password, String schoolName) {
    	if(username == null || username.length() < 2 || username.length() > 10) {
    		return "用户名长度必须为2到10位";
    	}
    	if(password == null || password.length() < 6 || username.length() > 10) {
    		return "密码长度必须为6到10位";
    	}
    	if(schoolName == null || schoolName.length() > 20) {
    		return "高校名称长度必须少于20";
    	}
        //判断用户名是否唯一
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        List list = userService.list(queryWrapper);
        if(list != null && list.size() != 0) {
        	return "用户名已存在";
        }
        User user = new User();
        user.setUsername(username);
        String pass = null;
        try {
        	pass = new String(password.getBytes(),"UTF-8");
        	pass = AESUtils.encrypt(password);
        	if(pass.length() > 20) {   //user表的password字段定义了varchar(20)
        		pass = pass.substring(0, 20);
        	}
        } catch (Exception e) {
			e.printStackTrace();
			return "注册失败";
		}
        user.setPassword(pass);
        Date now = new Date();
        user.setRegTime(now);
        user.setCredit(10); //信用分初始为10分
        user.setScore(100);   //积分初始为100
        int schoolId = schoolService.add(schoolName); //把学校名称添加到'学校表'
        user.setSchoolId(schoolId);
        user.setDeadline(now);
        userService.save(user);
        return "注册成功";
    }

    private boolean validate(String username, String password,HttpServletResponse response) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        List list = userService.list(queryWrapper);
        if(list == null || list.size() == 0) {
            return false;
        }
        User user = (User) list.get(0);
        try {
        	password = new String(password.getBytes(),"UTF-8");
        	password = AESUtils.encrypt(password);
        	if(password.length() > 20) {
        		password = password.substring(0, 20);
        	}
        	//将用户信息写入cookie（无奈之举 π_π）
        	CookieUtils.writeCookie(response,"userId",String.valueOf(user.getId()),0);
        	CookieUtils.writeCookie(response,"username",String.valueOf(user.getUsername()),0);
        	CookieUtils.writeCookie(response,"schoolId",String.valueOf(user.getSchoolId()),0);
        } catch (Exception e) {
			e.printStackTrace();
		}

        if(!password.equals(user.getPassword())){
            return false;
        }

        return true;
    }

    //用户账户是否被屏蔽
    private boolean isLock(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        List list = userService.list(queryWrapper);
        if(list == null || list.size() == 0) {
            return true;
        }
        User user = (User) list.get(0);
        Date now = new Date();
        Date deadline = user.getDeadline();
        return now.before(deadline);
	}

    @Override
    public String login(String username, String password,
                        HttpServletRequest request, HttpServletResponse response) {
    	if(isLock(username)) {
    		return "账号不存在或被屏蔽";
    	}
        if(validate(username, password,response)){
            //保存用户信息到cookie
            request.getSession().setAttribute("username", username);
            // 创建cookie并将成功登陆的用户保存在里面并且在redis中做缓存
            try {
                String sign = RSAUtils.encryptByPubKey(username + ":" + password);
                CookieUtils.writeCookie(response, "sign", sign, 0);
                //缓存失效时间设置跟cookie失效时间是一样的
                redisTemplate.opsForValue().set(sign, sign, 5 * 60, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "登录成功";
        }else {
            return "用户名或密码错误";
        }
    }

    @Override
    public void test() {
        System.out.println("test...");
    }


    @Override
    public String logout(HttpServletRequest request, HttpServletResponse response) {
    	request.getSession().setAttribute("username", null);
        System.out.println("logout...");
        CookieUtils.writeCookie(response, "sign", "", 0); //cookie过期时间设为0，即删除cookie
        CookieUtils.writeCookie(response, "userId", "", 0);
        CookieUtils.writeCookie(response, "username", "", 0);
        CookieUtils.writeCookie(response, "schoolId", "", 0);
        return "退出成功";
    }

}
