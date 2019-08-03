package com.cdc.inventorysystem.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdc.inventorysystem.dao.SchoolMapper;
import com.cdc.inventorysystem.entity.School;
import com.cdc.inventorysystem.entity.User;
import com.cdc.inventorysystem.service.SchoolService;

@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {
	
	@Autowired
    private SchoolService schoolService;
	@Autowired
	private SchoolMapper schoolMapper;
	/*
	 * add(schoolName)把schoolName添加到school表，并返回其主键id
	 * */
	@Override
	public int add(String schoolName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", schoolName);
        List list = schoolService.list(queryWrapper);
        School school = null;
        if(list == null || list.size() == 0) {
        	school = new School();
        	school.setName(schoolName);
            schoolMapper.insert(school);
            System.out.println("schoolName:"+school.getName());
            System.out.println("id:"+school.getId());
        	return school.getId();
        }
        //否则返回已存在的id
        school = (School) list.get(0);
		return school.getId();
	}

}
