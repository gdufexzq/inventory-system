package com.cdc.inventorysystem.service.impl;

import java.util.List;
//import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdc.inventorysystem.common.exceptions.ParameterException;
import com.cdc.inventorysystem.common.util.AESUtils;
//import com.cdc.inventorysystem.common.util.CookieUtils;
//import com.cdc.inventorysystem.common.util.RSAUtils;
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
        if(validate(username, password)){
            //保存信息到cookie
        	request.getSession().setAttribute("admin", username);
//            // 创建cookie并将成功登陆的用户保存在里面并且在redis中做缓存
//            try {
//                String sign = RSAUtils.encryptByPubKey(username + ":" + password);
//                CookieUtils.writeCookie(response, "admin_sign", sign, 0);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            return "登录成功";
        }else {
            return "管理员账号或密码错误";
        }
	}

	@Override
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("admin", null);
        System.out.println("logout...");
        //CookieUtils.writeCookie(response, "admin_sign", "", 0);
        return "退出成功";
	}
	
    private boolean validate(String username, String password) {
    	if(username == null || username.length() < 5 || username.length() > 10) {
    		throw new ParameterException("管理员账户长度必须在5到10位");
    	}
    	if(password == null || password.length() < 5 || password.length() > 10) {
    		throw new ParameterException("密码长度必须在5到10位");
    	}
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        List list = adminService.list(queryWrapper);
        if(list == null || list.size() == 0) {
            return false;
        }
        Admin admin = (Admin) list.get(0);
        try {
        	password = new String(password.getBytes(),"UTF-8");
        	password = AESUtils.encrypt(password);
        	if(password.length() > 20) {
        		password = password.substring(0, 20);
        	}
        } catch (Exception e) {
			e.printStackTrace();
		}
        
        if(password.equals(admin.getPassword())){
            return true;
        }
        return false;
    }

}
