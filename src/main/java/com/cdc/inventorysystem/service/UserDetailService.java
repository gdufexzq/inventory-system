package com.cdc.inventorysystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cdc.inventorysystem.entity.UserDetail;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-01
 */
public interface UserDetailService extends IService<UserDetail> {
	public IPage<UserDetail> selectUserDetails(String username, Integer current, Integer size);
	
	public Map<String, String> deleteUserById(Integer id);
}
