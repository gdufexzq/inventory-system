package com.cdc.inventorysystem.dao;

import com.cdc.inventorysystem.entity.Mission;
import com.cdc.inventorysystem.entity.vo.MissionDTO;

import java.util.List;

import com.cdc.inventorysystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.Map;
import org.apache.ibatis.annotations.*;
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
	public List<Mission> getAll();
	/**
	 * 根据用户id查询该用户的所有信息
	 * @return
	 */
//	public List<Mission> listMissionByUserId(Mission mission);
//
//	public List<Mission> getMissionByUserId(Mission mission);

	@Select("SELECT\r\n" +
			"		   m.Id Id,m.title title,m.content content,m.score score,m.userId userId,\r\n" +
			"		   DATE_FORMAT(m.pubTime,'%Y-%m-%d %H:%i:%s') pubTime,\r\n" +
			"		   m.schoolId schoolId,m.recUserId recUserId,\r\n" +
			"		   DATE_FORMAT(m.recTime,'%Y-%m-%d %H:%i:%s') recTime,\r\n" +
			"		   m.state state,m.display display,\r\n" +
			"		 `school`.id,\r\n" +
			"		 `school`.name schoolName,\r\n" +
			"		 user.Id,\r\n" +
			"		 user.username pubuser\r\n" +
			"		 \r\n" +
			"		 FROM\r\n" +
			"		 	mission m,school,user\r\n" +
			"		 where (m.userId=#{m.userId} or m.recUserId=#{m.userId}) and m.schoolId = `school`.id and m.userId = user.Id order by pubTime desc")
	List<MissionDTO> selectpage(Map<String,Object> m, Page<MissionDTO> page);

	@Select("SELECT\r\n" +
			"		   m.Id Id,m.title title,m.content content,m.score score,m.userId userId,\r\n" +
			"		   DATE_FORMAT(m.pubTime,'%Y-%m-%d %H:%i:%s') pubTime,\r\n" +
			"		   m.schoolId schoolId,m.recUserId recUserId,\r\n" +
			"		   DATE_FORMAT(m.recTime,'%Y-%m-%d %H:%i:%s') recTime,\r\n" +
			"		   m.state state,m.display display,\r\n" +
			"		   `school`.id,\r\n" +
			"		  `school`.name schoolName,\r\n" +
			"		   user.Id,\r\n" +
			"		   user.username pubuser\r\n" +
			"		   \r\n" +
			"		   FROM\r\n" +
			"				mission m,school,user\r\n" +
			"		   where m.state=0 and m.schoolId = `school`.id and m.userId = user.Id order by pubTime desc")
	List<MissionDTO> selectpageBystate(Page<MissionDTO> page);

//	public void updateMissionById(Mission mission);
//
//	public int updateM();
//
//	public int deleteMissionById(int id);

}
