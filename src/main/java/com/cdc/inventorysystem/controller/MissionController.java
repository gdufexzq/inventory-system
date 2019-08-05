package com.cdc.inventorysystem.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdc.inventorysystem.entity.Mission;
import com.cdc.inventorysystem.entity.User;
import com.cdc.inventorysystem.service.MessageService;
import com.cdc.inventorysystem.service.MissionService;
import com.cdc.inventorysystem.service.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.cdc.inventorysystem.entity.QueryVo;

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
	MissionService missionService;
	@Autowired
	UserService userService;
	@Autowired
	MessageService messageService;
	
	
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
			return dataMap.put("msg", "失败");
		}
		/**
		 * 判断积分是否足够
		 * 只有发布任务时才判断积分，接收任务时不需要判断积分
		 */
		User user = userService.getById(mission.getUserId());
		if(mission.getState()==0){
			if(user.getScore()<mission.getScore()){
				dataMap.put("msg", "失败");
				dataMap.put("data", "积分不足，不能发布任务");
				return dataMap;
			}
		}
		
		
		int i = missionService.saveMissionOrUpdate(mission);
		if(i==1){
			/**
			 * 根据任务状态推送信息通知
			 */
			if(mission.getState()==1){//任务被接收
				messageService.acceptTaskMsg(mission.getUserId(), mission.getTitle(), user.getUsername(), new Date());
			}else if(mission.getState()==2){//任务完成后待验证
				messageService.compTaskMsg(mission.getUserId(), mission.getTitle(), user.getUsername());
			}else if(mission.getState()==3||mission.getState()==4){//任务验证完成
				/**
				 * 任务验证完成后就更新对应积分
				 */
				User recUser = userService.getById( mission.getRecUserId());
				user.setScore(user.getScore()-mission.getScore());
				recUser.setScore(recUser.getScore()+mission.getScore());
				boolean flag1 = userService.updateById(user);
				boolean flag2 = userService.updateById(recUser);
				if(!(flag1||flag2)){
					dataMap.put("msg", "失败");
					dataMap.put("data", "积分更新失败");
				}
				messageService.verifyMsg(mission.getRecUserId(), mission.getTitle(), user.getUsername(), mission.getState());
			}
			
			dataMap.put("msg", "成功");
			dataMap.put("data", "更新任务成功");
		}else{
			dataMap.put("msg", "失败");
			dataMap.put("data", "更新任务失败");
		}
		return dataMap;
	}
	
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
	 * 分页查询信息表，
	 * @param map 参数：userId npage每页显示条数   dpage查第几页
	 * @return
	 */
	@RequestMapping(value = "/selectMission",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object queryToPage(@RequestBody Map<String, Object> map) {
		Integer dpage = (Integer)map.get("dpage");//第几页
		Integer npage = (Integer)map.get("npage");//每页显示多少条数据
		Integer userId = (Integer)map.get("userId");//查询哪个用户的信息
		QueryVo vo = new QueryVo();
		vo.setDpage(dpage);
		vo.setNpage(npage);
		vo.setUserId(userId);
		Page<Mission> data = missionService.selectMission(vo);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(data.getRecords().size() != 0) {
			dataMap.put("msg", "查询成功");
			dataMap.put("data",data );
		}else {
			dataMap.put("msg", "查询失败，该用户无任务数据！");
			dataMap.put("data",data );
		}
		return dataMap;
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
		int data = missionService.deleteMissionById(id);
		Map<String, Object> delMap = new HashMap<String, Object>();
		if(data > 0) {
			delMap.put("msg", "删除成功！");
		}else {
			delMap.put("msg", "删除失败！");
		}
		return delMap;
	}

	/**
	 * 修改信息记录  只能修改title、content、schoolId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/updateMission",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object updateMission(@RequestBody Map<String, Object> map) {
		System.out.println(map);
		Integer id = (Integer)map.get("id");
		String title = (String)map.get("title");
		String content = (String)map.get("content");
		Integer schoolId = (Integer)map.get("schoolId");
		Mission mission = new Mission();
		mission.setId(id);
		mission.setTitle(title);
		mission.setContent(content);
		mission.setSchoolId(schoolId);
		int data = missionService.updateMission(mission);
		Map<String, Object> delMap = new HashMap<String, Object>();
		if(data > 0) {
			delMap.put("msg", "修改成功！");
		}else {
			delMap.put("msg", "修改失败！");
		}
		return delMap;
	}

}

