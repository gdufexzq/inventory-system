package com.cdc.inventorysystem.service;

import com.cdc.inventorysystem.entity.School;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lweiq
 * @since 2019-07-31
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdc.inventorysystem.entity.School;


public interface SchoolService extends IService<School>  {
	public List<School> selectSchool();
	int add(String SchoolName);
}
