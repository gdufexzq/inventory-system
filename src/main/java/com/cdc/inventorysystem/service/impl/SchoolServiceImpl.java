package com.cdc.inventorysystem.service.impl;

import com.cdc.inventorysystem.entity.School;
import com.cdc.inventorysystem.dao.SchoolMapper;
import com.cdc.inventorysystem.service.SchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjinchao
 * @since 2019-07-31
 */
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {
	
	@Autowired
	private SchoolMapper schoolMapper;

	@Override
	public List<School> selectSchool() {

		return schoolMapper.selectList(null);
	}

}
