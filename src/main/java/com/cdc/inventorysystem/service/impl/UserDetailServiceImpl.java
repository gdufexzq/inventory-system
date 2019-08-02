package com.cdc.inventorysystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
	private UserDetailMapper userDetailMapper;
	@Autowired
	private MissionService missionService;
	
	@Override
	public IPage<UserDetail> selectUserDetails(String username, Integer current, Integer size) {
		QueryWrapper<UserDetail> queryWrapper = new QueryWrapper<UserDetail>();
		if(username != null && username != "") {
			queryWrapper.like("username", username);
		}
		if(current == null || size == null) {
			current = 1;
			size = 10;
		}
		Page<UserDetail> page = new Page<UserDetail>(current, size);
		IPage<UserDetail> userDetailPage = userDetailMapper.selectPage(page, queryWrapper);
		//查询发任务和接任务的数量
		if(userDetailPage.getRecords() != null && userDetailPage.getRecords().size() > 0) {
			for(UserDetail userDetail : userDetailPage.getRecords()) {
				QueryWrapper queryWrapper1 = new QueryWrapper();
				queryWrapper1.eq("userId", userDetail.getId());
				userDetail.setReleaseNum(missionService.count(queryWrapper1));
				QueryWrapper queryWrapper2 = new QueryWrapper();
				queryWrapper2.eq("recUserId", userDetail.getId());
				userDetail.setAcceptNum(missionService.count(queryWrapper2));
			}
		}
		return userDetailPage;
	}


	@Override
	public boolean deleteUserById(Integer id) {
		// TODO Auto-generated method stub
		
		return userDetailService.removeById(id);
	}

}
