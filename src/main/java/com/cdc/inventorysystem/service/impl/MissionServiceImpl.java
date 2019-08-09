package com.cdc.inventorysystem.service.impl;

import com.cdc.inventorysystem.entity.Mission;
import com.cdc.inventorysystem.dao.MissionMapper;
import com.cdc.inventorysystem.service.MissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.cdc.inventorysystem.entity.QueryVo;
import com.cdc.inventorysystem.entity.vo.MissionDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>
 *  服务实现类
 * </p>
 * @author yangjinchao
 * @since 2019-07-31
 */
@Service
public class MissionServiceImpl extends ServiceImpl<MissionMapper, Mission> implements MissionService {
	@Autowired
	MissionMapper missionMapper;
	
	@Override
	public List<Mission> getAll() {
		return missionMapper.getAll();
	}

	@Override
	public int saveMissionOrUpdate(Mission mission) {
		int i = 0;
		if(mission.getId()!=null){
			if(mission.getState()==1){
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String recTime = sdf.format(date);
				mission.setRecTime(recTime);
			}
			 i = missionMapper.updateById(mission);
		}else{
			i = missionMapper.insert(mission);
		}
		return i;
		
	}
	
	@Override
	public int updateState(Mission mission) {
		int  i = missionMapper.updateById(mission);
		return i;

	}
	@Override
	public Page<MissionDTO> selectMission(QueryVo vo){
		Map<String,Object> m = new HashMap<>();
		m.put("userId",vo.getUserId());
		Page<MissionDTO> page = new Page<>(vo.getDpage(),vo.getNpage());
		Page<MissionDTO> setRecords = page.setRecords(missionMapper.selectpage(m,page));
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


	//public Mission getAll(int id) {
	//	return missionMapper.getAll(id);
	//}

	@Override
	public Page<MissionDTO> selectMissionAll(Integer dpage, Integer npage) {
		Page<MissionDTO> page = new Page<>(dpage,npage);
		Page<MissionDTO> setRecords = page.setRecords(missionMapper.selectpageBystate(page));
		return setRecords;
	}
}
