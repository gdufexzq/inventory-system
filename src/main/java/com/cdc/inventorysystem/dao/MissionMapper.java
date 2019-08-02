package com.cdc.inventorysystem.dao;

import com.cdc.inventorysystem.entity.Mission;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangjinchao
 * @since 2019-07-31
 */
@Mapper
public interface MissionMapper extends BaseMapper<Mission> {
	
	/**
	 * 根据用户id查询该用户的所有信息
	 * @return
	 */
	public List<Mission> listMissionByUserId(Mission mission);
	
	public List<Mission> getAll();
	
	public List<Mission> getMissionByUserId(Mission mission);
	
	@Select("SELECT\r\n" + 
			"		   m.Id Id,m.title title,m.content content,m.score score,m.userId userId,\r\n" + 
			"		   DATE_FORMAT(m.pubTime,'%Y-%m-%d %H:%i:%s') pubTime,\r\n" + 
			"		   m.schoolId schoolId,m.recUserId recUserId,\r\n" + 
			"		   DATE_FORMAT(m.recTime,'%Y-%m-%d %H:%i:%s') recTime,\r\n" + 
			"		   m.state state,m.display display,\r\n" + 
			"		 `school`.id,\r\n" + 
			"		 `school`.name schoolName,\r\n" + 
			"		 admin.Id,\r\n" + 
			"		 admin.username recuser\r\n" + 
			"		 \r\n" + 
			"		 FROM\r\n" + 
			"		 	mission m,school,admin\r\n" + 
			"		 where m.userId=#{m.userId} and m.schoolId = `school`.id and m.recUserId = admin.Id ")
    List<Mission> selectpage(Map<String,Object> m, Page<Mission> page);
	
	public void updateMissionById(Mission mission);
	
	public int updateM();
	
	public int deleteMissionById(int id);

}
