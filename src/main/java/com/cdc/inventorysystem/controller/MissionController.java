package com.cdc.inventorysystem.controller;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.inventorysystem.common.enums.ResponseStatusEnum;
import com.cdc.inventorysystem.common.util.Map2Object;
import com.cdc.inventorysystem.entity.Mission;
import com.cdc.inventorysystem.entity.vo.ResponseVO;
import com.cdc.inventorysystem.service.MissionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-01
 */
@Controller
@RequestMapping("/mission")
public class MissionController {
	@Autowired
	MissionService missionService;
	
	/**
	 * 获取列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
    public Object list(@RequestParam Map<String,String> map) {
		List<Mission> list = missionService.getAll();
		for(Mission m:list){
			System.out.println(m);
		}
		return list;
	}
	/**
	 * 发布任务，领取任务
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
    public Object saveOrUpdate(@RequestBody Mission mission) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if(mission == null){
			return dataMap.put("msg", "操作失败");
		}
		int i = missionService.saveMissionOrUpdate(mission);
		if(i==1){
			dataMap.put("msg", "操作成功");
		}else{
			dataMap.put("msg", "操作失败");
		}
		return dataMap;
	}
	
	
}

