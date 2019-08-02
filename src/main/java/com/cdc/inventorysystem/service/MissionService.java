package com.cdc.inventorysystem.service;

import com.cdc.inventorysystem.entity.Mission;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-01
 */
public interface MissionService extends IService<Mission> {
	public List<Mission> getAll();
	/**
	 * 发布任务，领取任务
	 * @return 
	 */
	public int saveMissionOrUpdate(Mission mission); 
}
