package com.cdc.inventorysystem.service;

import com.cdc.inventorysystem.entity.School;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjinchao
 * @since 2019-07-31
 */
public interface SchoolService extends IService<School> {
	
	public List<School> selectSchool();

}
