package com.cdc.inventorysystem.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdc.inventorysystem.dao.UserMapper;
import com.cdc.inventorysystem.entity.User;

@Mapper
public class UserMapperImpl implements UserMapper {

	@Override
	public int delete(Wrapper<User> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBatchIds(Collection<? extends Serializable> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByMap(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(User entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> selectBatchIds(Collection<? extends Serializable> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User selectById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> selectByMap(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer selectCount(Wrapper<User> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> selectList(Wrapper<User> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectMaps(Wrapper<User> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPage<Map<String, Object>> selectMapsPage(IPage<User> arg0, Wrapper<User> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> selectObjs(Wrapper<User> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User selectOne(Wrapper<User> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPage<User> selectPage(IPage<User> arg0, Wrapper<User> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(User arg0, Wrapper<User> arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateById(User arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void register(String username, String password, String schoolName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void login(String username, String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}

	@Override
	public String selectPassByName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User selectUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
