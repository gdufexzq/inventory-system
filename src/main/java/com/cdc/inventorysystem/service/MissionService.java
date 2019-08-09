package com.cdc.inventorysystem.service;

import com.cdc.inventorysystem.entity.Mission;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdc.inventorysystem.entity.QueryVo;
import com.cdc.inventorysystem.entity.vo.MissionDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangjinchao
 * @since 2019-07-31
 */
public interface MissionService extends IService<Mission> {
	public List<Mission> getAll();
	/**
	 * 发布任务，领取任务
	 * @return 
	 */
	public int saveMissionOrUpdate(Mission mission);
	
	/**
	 * 根据发布者id查找全部任务信息
	 * @param mission
	 * @return
	 */
	public Page<MissionDTO> selectMission(QueryVo vo);
	
	public int deleteMissionById(int id);
	
	public int updateMission(Mission mission);
	
	//public Mission getAll(int id);
	
	public int updateState(Mission mission) ;

	public Page<MissionDTO> selectMissionAll(Integer dpage,Integer npage);
}
