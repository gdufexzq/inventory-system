package com.cdc.inventorysystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdc.inventorysystem.entity.UserDetail;
import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-01
 */
public interface UserDetailService extends IService<UserDetail> {
	public List<UserDetail> selectUserDetails(String username);
	
	public boolean deleteUserById(Integer id);
}
