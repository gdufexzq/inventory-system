package com.cdc.inventorysystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdc.inventorysystem.common.exceptions.ParameterException;
import com.cdc.inventorysystem.common.util.AESUtils;
import com.cdc.inventorysystem.common.util.CookieUtils;
import com.cdc.inventorysystem.common.util.RSAUtils;
import com.cdc.inventorysystem.dao.UserMapperDemo;
import com.cdc.inventorysystem.entity.User;
import com.cdc.inventorysystem.service.UserServiceDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-05-15
 */
@Service
public class UserServiceImplDemo extends ServiceImpl<UserMapperDemo, User> implements UserServiceDemo {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserServiceDemo userService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void register(String username, String password) {

        //判断用户名是否唯一
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        List list = userService.list(queryWrapper);
        if(list != null && list.size() != 0) {
            throw new ParameterException("用户名已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
//        user.setCreateTime(System.currentTimeMillis());
//        user.setModifyTime(System.currentTimeMillis());
        userService.save(user);
    }

    @Override
    public boolean validate(String username, String password) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        List list = userService.list(queryWrapper);
        if(list == null || list.size() == 0) {
            return false;
        }
        User user = (User) list.get(0);
        if(password.equals(user.getPassword())){
            return true;
        }
        return false;
    }

    @Override
    public String login(String username, String password,
                        HttpServletRequest request, HttpServletResponse response) {
        String uname = null;
        String pswd = null;
        try {
            uname = AESUtils.desEncrypt(username, AESUtils.KEY, AESUtils.IV).trim();
            pswd = AESUtils.desEncrypt(password, AESUtils.KEY, AESUtils.IV).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", uname);
        List list = userService.list(queryWrapper);
        if(list == null || list.size() == 0) {
            throw new ParameterException("用户名或密码错误");
        }
        User user = (User) list.get(0);
        if(pswd.equals(user.getPassword())){
            //保存用户信息到cookie
//            httpSession.setAttribute("user", user);
            // 创建cookie并将成功登陆的用户保存在里面并且在redis中做缓存
            try {

                String sign = RSAUtils.encryptByPubKey(user.getUsername()
                        + ":" + user.getPassword());
                CookieUtils.writeCookie(response, "sign", sign, 0);
                //缓存失效时间设置跟cookie失效时间是一样的
                redisTemplate.opsForValue().set(sign, sign, 5 * 60, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "登录成功";
        }else {
            throw new ParameterException("用户名或密码错误");
        }
    }

    @Override
    public void test() {
        System.out.println("test...");
    }


    @Override
    public void logout(HttpServletResponse response) {
        System.out.println("logout...");
        CookieUtils.writeCookie(response, "sign", "", 0);
    }

}
