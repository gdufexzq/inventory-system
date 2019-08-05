package com.cdc.inventorysystem.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdc.inventorysystem.entity.School;

@Mapper
public interface SchoolMapper extends BaseMapper<School>  {
	int add(String schoolName);
}
