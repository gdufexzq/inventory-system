package com.cdc.inventorysystem.service;

import com.cdc.inventorysystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 * @author yangjinchao
 * @since 2019-08-02
 */
public interface UserService extends IService<User> {
	
//	public User selectUserById(int id);
	
	public int updateUser(User user);

}
