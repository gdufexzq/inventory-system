package com.cdc.inventorysystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdc.inventorysystem.entity.User;
import com.cdc.inventorysystem.entity.UserDetail;
import com.cdc.inventorysystem.service.MissionService;
import com.cdc.inventorysystem.service.UserDetailService;
import com.cdc.inventorysystem.service.UserService;

@Service
public class UserDetailServiceImpl implements UserDetailService{
	@Autowired
	private UserService userService;
	@Autowired
	private MissionService missionService;
	@Override
	public List<UserDetail> selectUserDetails(String username) {
		List<User> users = null;
		
		if(username != null && username != "") {
			QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
			queryWrapper.like("username", username);
			users = userService.list(queryWrapper);
		} else {
			users = userService.list();
		}
		
		List<UserDetail> userDetails = new ArrayList<UserDetail>();
		if(users != null && users.size() > 0) {
			for(User user : users) {
				UserDetail userDetail = new UserDetail();
				userDetail.setId(user.getId());
				userDetail.setUser(user);
				QueryWrapper queryWrapper1 = new QueryWrapper();
				queryWrapper1.eq("userId", user.getId());
				userDetail.setReleaseNum(missionService.count(queryWrapper1));
				QueryWrapper queryWrapper2 = new QueryWrapper();
				queryWrapper2.eq("recUserId", user.getId());
				userDetail.setAcceptNum(missionService.count(queryWrapper2));
				
				userDetails.add(userDetail);
			}
		}
		return userDetails;
	}

	public static void main(String[] args) {
		UserDetailService userDetailService = new UserDetailServiceImpl();
		System.out.print(userDetailService.selectUserDetails("a"));;
	}
}
