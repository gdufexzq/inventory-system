package com.cdc.inventorysystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.inventorysystem.entity.User;
import com.cdc.inventorysystem.service.UserService;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangjinchao
 * @since 2019-08-02
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

}

