package com.cdc.inventorysystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdc.inventorysystem.common.enums.ResponseStatusEnum;
import com.cdc.inventorysystem.common.exceptions.ParameterException;
import com.cdc.inventorysystem.common.util.AESUtils;
import com.cdc.inventorysystem.common.util.CookieUtils;
import com.cdc.inventorysystem.entity.User;
import com.cdc.inventorysystem.entity.vo.ResponseVO;
import com.cdc.inventorysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yangjinchao
 * @since 2019-08-02
 */
@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "http://127.0.0.1:5500",
        maxAge = 3600, allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS},
        allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户信息，前端传参为如{"id":1}
     *
     * @return
     */
    @RequestMapping(value = "/selectUser", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object queryUser(String id) {
        Integer idInt = Integer.parseInt(id);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        User data = userService.getById(idInt);
        if (data != null) {
            dataMap.put("msg", "查询成功！");
            dataMap.put("data", data);
        } else {
            dataMap.put("msg", "查询失败！");
            dataMap.put("data", data);
        }
        return dataMap;
    }


    /**
     * 更新用户信息
     *
     * @return
     */
    @RequestMapping(value = "/updateUser", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object updateUser(Integer id, String username, String password, Integer schoolId, HttpServletResponse response) {

        if (username == null || username.length() < 2 || username.length() > 10) {
            if (!"".equals(username)) {
                throw new ParameterException("用户名长度必须为2到10位");
            }
        }
        if (password != null && !"".equals(password) && (password.length() < 6 || username.length() > 10)) {
            throw new ParameterException("密码长度必须为5到10位");
        }

        //判断用户名是否唯一
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        queryWrapper.ne("id", id);
        List list = userService.list(queryWrapper);
        if (list != null && list.size() != 0) {
            throw new ParameterException("用户名已存在");
        }
        String pass = null;
        User user = new User();
        if (password != null && !"".equals(password)) {
            try {
                pass = new String(password.getBytes(), "UTF-8");
                pass = AESUtils.encrypt(password);
                if (pass.length() > 20) {   //user表的password字段定义了varchar(20)
                    pass = pass.substring(0, 20);
                }
                user.setPassword(pass);
            } catch (Exception e) {
                e.printStackTrace();
                return new HashMap<String, Object>(1).put("msg", "修改失败！");
            }
        }

        user.setUsername(username);
        user.setSchoolId(schoolId);
        user.setId(id);
        int data = userService.updateUser(user);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        if (data > 0) {
            dataMap.put("msg", "修改成功！");
            CookieUtils.writeCookie(response, "username", user.getUsername(), 0);
            CookieUtils.writeCookie(response, "schoolId", String.valueOf(user.getId()), 0);
        } else {
            dataMap.put("msg", "修改失败！");
        }
        return dataMap;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO register(String username, String password, String schoolName) {
        String result = userService.register(username, password, schoolName);
        return new ResponseVO(ResponseStatusEnum.SUCCESS, result);

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVO login(String username, String password,
                            HttpServletRequest request, HttpServletResponse response) {
//      response.setHeader("Access-Control-Allow-Origin", "*");
//    	String username = json.getString("username");
//    	String password = json.getString("password");
        String result = userService.login(username, password, request, response);
        if (result.equals("登录成功")) {
            return new ResponseVO(ResponseStatusEnum.SUCCESS, result);
        }
        return new ResponseVO(ResponseStatusEnum.PARAMETER_ERROR, result);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO test() {
        userService.test();
        return new ResponseVO(ResponseStatusEnum.SUCCESS, "测试");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO logout(HttpServletRequest request, HttpServletResponse response) {
        String result = userService.logout(request, response);
        return new ResponseVO(ResponseStatusEnum.SUCCESS, result);
    }

}

