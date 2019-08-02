package com.cdc.inventorysystem.service.impl;

import com.cdc.inventorysystem.entity.User;
import com.cdc.inventorysystem.dao.UserMapper;
import com.cdc.inventorysystem.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjinchao
 * @since 2019-08-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User selectUserById(int id) {
		
		return userMapper.selectUserById(id);
	}

	@Override
	public int updateUser(User user) {
		return userMapper.updateById(user);
	}

}
