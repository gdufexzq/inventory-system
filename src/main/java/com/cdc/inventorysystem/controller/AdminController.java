package com.cdc.inventorysystem.controller;


import com.alibaba.fastjson.JSONObject;
import com.cdc.inventorysystem.common.enums.ResponseStatusEnum;
import com.cdc.inventorysystem.entity.vo.ResponseVO;
import com.cdc.inventorysystem.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
@CrossOrigin(origins = "http://127.0.0.1:5500",
        maxAge = 3600, allowCredentials = "true",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.OPTIONS},
        allowedHeaders = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(String username, String password,
                            HttpServletRequest request, HttpServletResponse response) {
//      response.setHeader("Access-Control-Allow-Origin", "*");
    	String result = adminService.login(username, password, request, response);
    	Map<String,Object> dataMap = new HashMap<String, Object>();
    	dataMap.put("msg", result);
        return dataMap;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        adminService.logout(request, response);
    	Map<String,Object> dataMap = new HashMap<String, Object>();
    	dataMap.put("msg", "退出成功！");
        return dataMap;
    }

}


