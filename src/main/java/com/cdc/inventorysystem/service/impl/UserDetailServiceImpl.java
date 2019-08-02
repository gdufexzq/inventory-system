package com.cdc.inventorysystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdc.inventorysystem.dao.UserDetailMapper;
import com.cdc.inventorysystem.entity.UserDetail;
import com.cdc.inventorysystem.service.MissionService;
import com.cdc.inventorysystem.service.UserDetailService;

@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail> implements UserDetailService {
	@Autowired
	private UserDetailService userDetailService;
	@Autowired
	private MissionService missionService;
	
	@Override
	public List<UserDetail> selectUserDetails(String username) {
		List<UserDetail> userDetails = null;
		
		if(username != null && username != "") {
			QueryWrapper<UserDetail> queryWrapper = new QueryWrapper<UserDetail>();
			queryWrapper.like("username", username);
			userDetails = userDetailService.list(queryWrapper);
		} else {
			userDetails = userDetailService.list();
		}
		
		if(userDetails != null && userDetails.size() > 0) {
			for(UserDetail userDetail : userDetails) {
				QueryWrapper queryWrapper1 = new QueryWrapper();
				queryWrapper1.eq("userId", userDetail.getId());
				userDetail.setReleaseNum(missionService.count(queryWrapper1));
				QueryWrapper queryWrapper2 = new QueryWrapper();
				queryWrapper2.eq("recUserId", userDetail.getId());
				userDetail.setAcceptNum(missionService.count(queryWrapper2));
			}
		}
		return userDetails;
	}


	@Override
	public boolean deleteUserById(Integer id) {
		// TODO Auto-generated method stub
		
		return userDetailService.removeById(id);
	}

}
