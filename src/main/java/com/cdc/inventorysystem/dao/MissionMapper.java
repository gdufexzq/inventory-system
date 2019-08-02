package com.cdc.inventorysystem.dao;

import com.cdc.inventorysystem.entity.Mission;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-01
 */
@Mapper
public interface MissionMapper extends BaseMapper<Mission> {
	public List<Mission> getAll();
}
