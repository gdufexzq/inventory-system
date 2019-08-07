package com.cdc.inventorysystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdc.inventorysystem.common.enums.ResponseStatusEnum;
import com.cdc.inventorysystem.entity.User;
import com.cdc.inventorysystem.entity.vo.ResponseVO;
import com.cdc.inventorysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @author lweiq
 * @since 2019-08-07
 */
@Controller
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 查询用户信息，前端传参为如{"id":1}
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/selectUser",method = {RequestMethod.GET,RequestMethod.POST})
	 @ResponseBody
    public Object queryUser(@RequestBody Map<String, Object> map) {
		 Integer id = (Integer)map.get("id");
		 Map<String, Object> dataMap = new HashMap<String, Object>();
		 User data = userService.selectUserById(id);
		 if (data != null) {
			dataMap.put("msg","查询成功！");
			dataMap.put("data", data);
		 }else {
			dataMap.put("msg","查询失败！");
			dataMap.put("data", data);
		}
		 return dataMap;
	 }
	
	
	/**
	 * 更新用户信息
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/updateUser",method = {RequestMethod.GET,RequestMethod.POST})
	 @ResponseBody
   public Object updateUser(@RequestBody Map<String, Object> map) {
		 Integer id = (Integer)map.get("id");
		 String username = (String)map.get("username");
		 String password = (String)map.get("password");
		 Integer schoolId = (Integer)map.get("schoolId");
		 User user = new User();
		 user.setUsername(username);
		 user.setPassword(password);
		 user.setSchoolId(schoolId);
		 user.setId(id);
		 int data = userService.updateUser(user);
		 Map<String,Object> dataMap = new HashMap<String, Object>();
		 if(data>0) {
			 dataMap.put("msg", "修改成功！");
		 }else {
			 dataMap.put("msg", "修改失败！");
		 }
		 return dataMap;
	 }

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

