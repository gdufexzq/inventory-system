package com.cdc.inventorysystem.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdc.inventorysystem.entity.Admin;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwq
 * @since 2019-08-01
 */

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
	public void shieldUser(int userId); //屏蔽某用户
	public void shieldMission(int missionId); //屏蔽某任务消息
	public void sendMsg(int userId);   //发系统消息，userId为0时表示全体
}
