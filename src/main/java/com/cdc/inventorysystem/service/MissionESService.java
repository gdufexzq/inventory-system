package com.cdc.inventorysystem.service;

import com.cdc.inventorysystem.entity.Mission;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-01
 */
public interface MissionESService {

	// 根据id删除记录
	public void deleteDocumentById(int id);

	public List<Mission> findAll();

}
