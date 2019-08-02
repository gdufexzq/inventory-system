package com.cdc.inventorysystem.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdc.inventorysystem.dao.MissionMapper;
import com.cdc.inventorysystem.entity.Mission;
import com.cdc.inventorysystem.entity.QueryVo;
import com.cdc.inventorysystem.service.MissionService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjinchao
 * @since 2019-07-31
 */
@Service
public class MissionServiceImpl extends ServiceImpl<MissionMapper, Mission> implements MissionService {
	
	@Autowired
	private MissionMapper missionMapper;

	@Override
	public Page<Mission> selectMission(QueryVo vo){
        Map<String,Object> m = new HashMap<>();
        m.put("userId",vo.getUserId());
        Page<Mission> page = new Page<>(vo.getDpage(),vo.getNpage());
        Page<Mission> setRecords = page.setRecords(missionMapper.selectpage(m,page));
        return setRecords;
    }

	@Override
	public int deleteMissionById(int id) {
		
		return missionMapper.deleteById(id);
	}


	@Override
	public int updateMission(Mission mission) {
		
		return missionMapper.updateById(mission);
	}
	
	
	
}
