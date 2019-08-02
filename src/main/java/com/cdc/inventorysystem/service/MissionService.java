package com.cdc.inventorysystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cdc.inventorysystem.entity.Mission;
import com.cdc.inventorysystem.entity.QueryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjinchao
 * @since 2019-07-31
 */
public interface MissionService extends IService<Mission> {
	
	/**
	 * 根据发布者id查找全部任务信息
	 * @param mission
	 * @return
	 */
	
	public Page<Mission> selectMission(QueryVo vo);
	
	public int deleteMissionById(int id);
	
	public int updateMission(Mission mission);
	
}
