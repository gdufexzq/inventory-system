package com.cdc.inventorysystem.service.impl;

import com.cdc.inventorysystem.entity.Mission;
import com.cdc.inventorysystem.dao.MissionMapper;
import com.cdc.inventorysystem.service.MissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-01
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

}
