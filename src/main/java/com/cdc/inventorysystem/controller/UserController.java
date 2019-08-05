package com.cdc.inventorysystem.controller;


import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
		 return userService.selectUserById(id);
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
		 int state = userService.updateUser(user);
		 Map<String, Object> resultMap = new HashedMap();
		if (state>0) {
			resultMap.put("msg", "更新成功！");
		}else {
			resultMap.put("msg", "更新失败！");
		}
		return resultMap;
	 }

}

