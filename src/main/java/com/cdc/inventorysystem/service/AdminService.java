package com.cdc.inventorysystem.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdc.inventorysystem.entity.Admin;

public interface AdminService extends IService<Admin> {
	
    public String login(String username, String password,
                 HttpServletRequest request, HttpServletResponse response);

    public String logout(HttpServletRequest request, HttpServletResponse response);
}
