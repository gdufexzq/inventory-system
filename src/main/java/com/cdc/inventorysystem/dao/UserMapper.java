package com.cdc.inventorysystem.dao;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdc.inventorysystem.entity.User;

public interface UserMapper extends BaseMapper<User> {
	public User selectUserById(int id);
	public void register(String username, String password, String schoolName);
	public void login(String username, String password);
	public void logout();
	public String selectPassByName(@Param("username") String username);
}
