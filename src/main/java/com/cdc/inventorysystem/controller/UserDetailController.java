package com.cdc.inventorysystem.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.inventorysystem.common.enums.ResponseStatusEnum;
import com.cdc.inventorysystem.entity.UserDetail;
import com.cdc.inventorysystem.entity.vo.ResponseVO;
import com.cdc.inventorysystem.service.UserDetailService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
public class UserDetailController {
	@Autowired
	private UserDetailService userDetailService;
	
	@RequestMapping(value =
            "/selectDetail", method = RequestMethod.GET)
	@ResponseBody
    public List<UserDetail> register(String username) {
		
		return userDetailService.selectUserDetails(username);
    }
}

