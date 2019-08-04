package com.cdc.inventorysystem.controller;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	
}

