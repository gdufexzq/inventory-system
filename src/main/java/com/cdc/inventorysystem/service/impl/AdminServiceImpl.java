package com.cdc.inventorysystem.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdc.inventorysystem.common.exceptions.ParameterException;
import com.cdc.inventorysystem.common.util.AESUtils;
import com.cdc.inventorysystem.common.util.CookieUtils;
import com.cdc.inventorysystem.common.util.RSAUtils;
import com.cdc.inventorysystem.dao.AdminMapper;
import com.cdc.inventorysystem.entity.Admin;
import com.cdc.inventorysystem.entity.User;
import com.cdc.inventorysystem.service.AdminService;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>  implements AdminService {

	@Autowired
	private AdminService adminService;
	
	@Override
	public String login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
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
        List list = adminService.list(queryWrapper);
        if(list == null || list.size() == 0) {
            throw new ParameterException("用户名或密码错误");
        }
        Admin admin = (Admin) list.get(0);
        if(pswd.equals(admin.getPassword())){
            //保存用户信息到cookie
//            httpSession.setAttribute("admin", admin);
            // 创建cookie并将成功登陆的用户保存在里面并且在redis中做缓存
            try {

                String sign = RSAUtils.encryptByPubKey(admin.getUsername() + ":" + admin.getPassword());
                CookieUtils.writeCookie(response, "sign", sign, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "登录成功";
        }else {
            throw new ParameterException("用户名或密码错误");
        }
	}

	@Override
	public void logout(HttpServletResponse response) {
        System.out.println("logout...");
        CookieUtils.writeCookie(response, "sign", "", 0);		
	}

}
