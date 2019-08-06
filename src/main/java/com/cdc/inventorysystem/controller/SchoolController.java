package com.cdc.inventorysystem.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.inventorysystem.entity.School;
import com.cdc.inventorysystem.service.SchoolService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangjinchao
 * @since 2019-07-31
 */
@Controller
@RequestMapping("/school")
@CrossOrigin
public class SchoolController {
	
	@Autowired
	private SchoolService schoolservice;
	
	/**
	 * 查询所有学校
	 * @return
	 */
	@RequestMapping(value = "/selectSchool",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
   public Object querySchool() {
		 List<School> data = schoolservice.selectSchool();
		 Map<String, Object> dataMap = new HashMap<String, Object>();
		 if(data.size() != 0) {
			 dataMap.put("msg", "查询成功！");
			 dataMap.put("data", data);
		 }else {
			 dataMap.put("msg", "查询失败！");
			 dataMap.put("data", data);
		 }
		 return dataMap;
	 }

}

