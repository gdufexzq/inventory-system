package com.cdc.inventorysystem.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdc.inventorysystem.entity.UserDetail;
import com.cdc.inventorysystem.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-01
 */
@Controller
@RequestMapping("/userDetail")
@CrossOrigin(origins = "http://127.0.0.1:5500",
		maxAge = 3600, allowCredentials = "true",
		methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.OPTIONS},
		allowedHeaders = "*")
public class UserDetailController {
	@Autowired
	private UserDetailService userDetailService;
	
	@RequestMapping(value ="/selectUserDetail", method = RequestMethod.GET)
	@ResponseBody
    public IPage<UserDetail> selectUserDetails(String username, Integer current, Integer size) {		
		return userDetailService.selectUserDetails(username, current, size);
    }
	
	@RequestMapping(value ="/deleteUserById", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> deleteUserById(Integer id) {
		return userDetailService.deleteUserById(id);
	}
}

