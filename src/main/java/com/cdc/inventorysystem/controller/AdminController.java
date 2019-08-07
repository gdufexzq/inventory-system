package com.cdc.inventorysystem.controller;


import com.alibaba.fastjson.JSONObject;
import com.cdc.inventorysystem.common.enums.ResponseStatusEnum;
import com.cdc.inventorysystem.entity.vo.ResponseVO;
import com.cdc.inventorysystem.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lwq
 * @since 2019-08-01
 */
//跨域处理
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseVO login(@RequestBody JSONObject json,
                            HttpServletRequest request, HttpServletResponse response) {
//      response.setHeader("Access-Control-Allow-Origin", "*");
    	String username = json.getString("username");
    	String password = json.getString("password");
    	String result = adminService.login(username, password, request, response);
        return new ResponseVO(ResponseStatusEnum.SUCCESS, result);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseVO logout(HttpServletRequest request, HttpServletResponse response) {
        adminService.logout(request, response);
        return new ResponseVO(ResponseStatusEnum.SUCCESS, "");
    }

}


