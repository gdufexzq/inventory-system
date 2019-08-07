package com.cdc.inventorysystem.controller;


import com.cdc.inventorysystem.common.enums.ResponseStatusEnum;
import com.cdc.inventorysystem.entity.vo.ResponseVO;
import com.cdc.inventorysystem.service.UserServiceDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-05-15
 */
//跨域处理
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserControllerDemo {

    @Autowired
    private UserServiceDemo userService;


    @RequestMapping(value =
            "/register", method = RequestMethod.GET)
    public ResponseVO register(String username, @RequestParam String password) {
        userService.register(username, password);
        return new ResponseVO(ResponseStatusEnum.SUCCESS, "");

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseVO login(String username, String password,
                            HttpServletRequest request, HttpServletResponse response) {
//      response.setHeader("Access-Control-Allow-Origin", "*");
        return new ResponseVO(ResponseStatusEnum.SUCCESS, userService.login(username, password, request, response));
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseVO test() {
        userService.test();
        return new ResponseVO(ResponseStatusEnum.SUCCESS, "");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseVO logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(response);
        return new ResponseVO(ResponseStatusEnum.SUCCESS, "");
    }

}

