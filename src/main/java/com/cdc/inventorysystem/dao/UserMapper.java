package com.cdc.inventorysystem.dao;

import org.springframework.data.repository.query.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdc.inventorysystem.entity.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-05-15
 */

public interface UserMapper extends BaseMapper<User> {
	public void register(String username, String password, String schoolName);
	public void login(String username, String password);
	public void logout();
	public String selectPassByName(@Param("username") String username);
}
