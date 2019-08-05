package com.cdc.inventorysystem.controller;


import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.inventorysystem.entity.Mission;
import com.cdc.inventorysystem.entity.QueryVo;
import com.cdc.inventorysystem.service.MissionService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangjinchao
 * @since 2019-07-31
 */
@Controller
@RequestMapping("/mission")
public class MissionController {
	
	@Autowired
	private MissionService missionService;
		
	/**
	 * 分页查询信息表，
	 * @param map 参数：userId npage每页显示条数   dpage查第几页  
	 * @return
	 */
	 @RequestMapping(value = "/selectMission",method = {RequestMethod.GET,RequestMethod.POST})
	 @ResponseBody
     public Object queryToPage(@RequestBody Map<String, Object> map) {
		 System.out.println(map);
		 Integer dpage = (Integer)map.get("dpage");//第几页
		 Integer npage = (Integer)map.get("npage");//每页显示多少条数据
		 Integer userId = (Integer)map.get("userId");//查询哪个用户的信息
		 QueryVo vo = new QueryVo();
		 vo.setDpage(dpage);
		 vo.setNpage(npage);
		 vo.setUserId(userId);
		 return missionService.selectMission(vo);
	 }
	 
	 /**
	  * 删除信息记录
	  * @param map
	  * @return
	  */
	@RequestMapping(value="/deleteMission")
	@ResponseBody
	public  Object delete(@RequestBody Map<String, Object> map){
		Integer id = (Integer)map.get("id");
		int state = missionService.deleteMissionById(id);
		Map<String, Object> resultMap = new HashedMap();
		if (state>0) {
			resultMap.put("msg", "删除成功！");
		}else {
			resultMap.put("msg", "删除失败！");
		}
		return resultMap;
	}
	 
	/**
	 * 修改信息记录  只能修改title、content、schoolId
	 * @param map
	 * @return
	 */
	 @RequestMapping(value = "/updateMission",method = {RequestMethod.GET,RequestMethod.POST})
	 @ResponseBody
     public Object updateMission(@RequestBody Map<String, Object> map) {
		 Integer id = (Integer)map.get("id");
		 String title = (String)map.get("title");
		 String content = (String)map.get("content");
		 Integer schoolId = (Integer)map.get("schoolId");
		 Mission mission = new Mission();
		 mission.setId(id);
		 mission.setTitle(title);
		 mission.setContent(content);
		 mission.setSchoolId(schoolId);
		 int state = missionService.updateMission(mission);
		 Map<String, Object> resultMap = new HashedMap();
		if (state>0) {
			resultMap.put("msg", "更新成功！");
		}else {
			resultMap.put("msg", "更新失败！");
		}
		return resultMap;
	 }
	 

}

