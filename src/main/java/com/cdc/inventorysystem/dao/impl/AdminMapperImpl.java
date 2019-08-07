package com.cdc.inventorysystem.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdc.inventorysystem.dao.AdminMapper;
import com.cdc.inventorysystem.entity.Admin;

@Mapper
public class AdminMapperImpl implements AdminMapper {

	@Override
	public int delete(Wrapper<Admin> arg0) {
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
	public int insert(Admin entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Admin> selectBatchIds(Collection<? extends Serializable> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Admin selectById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Admin> selectByMap(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer selectCount(Wrapper<Admin> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Admin> selectList(Wrapper<Admin> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> selectMaps(Wrapper<Admin> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPage<Map<String, Object>> selectMapsPage(IPage<Admin> arg0, Wrapper<Admin> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> selectObjs(Wrapper<Admin> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Admin selectOne(Wrapper<Admin> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPage<Admin> selectPage(IPage<Admin> arg0, Wrapper<Admin> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Admin arg0, Wrapper<Admin> arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateById(Admin arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void shieldUser(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shieldMission(int missionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMsg(int userId) {
		// TODO Auto-generated method stub
		
	}


}
