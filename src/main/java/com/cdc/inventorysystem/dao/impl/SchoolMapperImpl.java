package com.cdc.inventorysystem.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdc.inventorysystem.dao.SchoolMapper;
import com.cdc.inventorysystem.entity.School;

@Mapper
public class SchoolMapperImpl implements SchoolMapper {

	@Override
	public int delete(Wrapper<School> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBatchIds(Collection<? extends Serializable> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Serializable id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByMap(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(School entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<School> selectBatchIds(Collection<? extends Serializable> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public School selectById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<School> selectByMap(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer selectCount(Wrapper<School> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<School> selectList(Wrapper<School> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectMaps(Wrapper<School> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPage<Map<String, Object>> selectMapsPage(IPage<School> arg0, Wrapper<School> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> selectObjs(Wrapper<School> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public School selectOne(Wrapper<School> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPage<School> selectPage(IPage<School> arg0, Wrapper<School> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(School arg0, Wrapper<School> arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateById(School arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int add(String schoolName) {
		// TODO Auto-generated method stub
		return 0;
	}

}
