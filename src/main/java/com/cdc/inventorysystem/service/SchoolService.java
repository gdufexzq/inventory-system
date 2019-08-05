package com.cdc.inventorysystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cdc.inventorysystem.entity.School;


public interface SchoolService extends IService<School>  {
	int add(String SchoolName);
}
