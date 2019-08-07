package com.cdc.inventorysystem.controller;


import com.alibaba.fastjson.JSONObject;
import com.cdc.inventorysystem.common.enums.ResponseStatusEnum;
import com.cdc.inventorysystem.entity.vo.ResponseVO;
import com.cdc.inventorysystem.service.UserService;
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
 * @since 2019-07-31
 */
//跨域处理
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseVO register(@RequestBody JSONObject json) {
    	String username = json.getString("username");
    	String password = json.getString("password");
    	String schoolName = json.getString("schoolName");
    	String result = userService.register(username, password, schoolName);
        return new ResponseVO(ResponseStatusEnum.SUCCESS, result);

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseVO login(@RequestBody JSONObject json,
                            HttpServletRequest request, HttpServletResponse response) {
//      response.setHeader("Access-Control-Allow-Origin", "*");
    	String username = json.getString("username");
    	String password = json.getString("password");
    	String result = userService.login(username, password, request, response);
        return new ResponseVO(ResponseStatusEnum.SUCCESS, result);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseVO test() {
        userService.test();
        return new ResponseVO(ResponseStatusEnum.SUCCESS, "测试");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseVO logout(HttpServletRequest request, HttpServletResponse response) {
    	String result = userService.logout(request, response);
        return new ResponseVO(ResponseStatusEnum.SUCCESS, result);
    }

}

