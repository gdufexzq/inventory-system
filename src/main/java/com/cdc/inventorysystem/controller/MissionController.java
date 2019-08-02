package com.cdc.inventorysystem.controller;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdc.inventorysystem.entity.Mission;
import com.cdc.inventorysystem.service.MessageService;
import com.cdc.inventorysystem.service.MissionService;
import com.cdc.inventorysystem.service.UserService;

import java.util.Date;
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
 * @author xuzhiquan
 * @since 2019-08-01
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
			return dataMap.put("msg", "操作失败");
		}
		
		/**
		 * 判断积分是否足够
		 */
		Integer userId = mission.getUserId();
		User user = userService.getById(userId);
		if(user.getScore()<mission.getScore()){
			dataMap.put("error", "操作失败");
			dataMap.put("msg", "积分不足，不能发布任务");
			return dataMap;
		}
		
		int i = missionService.saveMissionOrUpdate(mission);
		if(i==1){
			/**
			 * 发布成功，更新用户积分
			 */
			user.setScore(user.getScore()-mission.getScore());
			boolean flag = userService.updateById(user);
			if(!flag){
				dataMap.put("error", "操作失败");
				dataMap.put("msg", "发布任务时更新积分失败");
				return dataMap;
			}
			
			/**
			 * 根据任务状态推送信息通知
			 */
			if(mission.getState()==1){//任务被接收
				messageService.acceptTaskMsg(mission.getUserId(), mission.getTitle(), user.getUsername(), new Date());
			}else if(mission.getState()==2){//任务完成后待验证
				messageService.compTaskMsg(mission.getUserId(), mission.getTitle(), user.getUsername());
			}else if(mission.getState()==3||mission.getState()==4){//任务验证完成
				messageService.verifyMsg(mission.getRecUserId(), mission.getTitle(), user.getUsername(), mission.getState());
			}
			
			dataMap.put("success", "操作成功");
			dataMap.put("msg", "发布任务成功");
		}else{
			dataMap.put("error", "操作失败");
			dataMap.put("msg", "发布任务失败");
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
	
	
}

