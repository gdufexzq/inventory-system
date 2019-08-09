package com.cdc.inventorysystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdc.inventorysystem.entity.Mission;
import com.cdc.inventorysystem.entity.QueryVo;
import com.cdc.inventorysystem.entity.User;
import com.cdc.inventorysystem.entity.vo.MissionDTO;
import com.cdc.inventorysystem.service.MessageService;
import com.cdc.inventorysystem.service.MissionService;
import com.cdc.inventorysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@CrossOrigin(origins = "http://127.0.0.1:5500",
		maxAge = 3600, allowCredentials = "true",
		methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.OPTIONS},
		allowedHeaders = "*")
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
    public Object saveOrUpdate(Mission mission) {
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
			}else{
				user.setScore(user.getScore()-mission.getScore());
			}
		}

		int i = missionService.saveMissionOrUpdate(mission);
		if(i==1){
			dataMap.put("msg", "成功");
			dataMap.put("data", "发布任务成功");
		}else{
			dataMap.put("msg", "失败");
			dataMap.put("data", "发布任务失败");
		}
		return dataMap;
	}
	
	/**
	 * 领取任务
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/updateState", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
    public Object updateState(@RequestParam("id") String id,@RequestParam("recUserId") String recUserId, @RequestParam("state") String state) {
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if(id == null){
			dataMap.put("msg", "失败");
			return dataMap.put("data", "操作失败");
		}

		//需要更新的任务
		//todo 这里进行修改，原来为getAll(Id)
		Mission dataMission = missionService.getById(Integer.parseInt(id));
		//需要更新任务的字段，领取任务的用户id，和领取时间,和任务状态
		dataMission.setRecUserId(Integer.parseInt(recUserId));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String recTime = sdf.format(date);
		dataMission.setRecTime(recTime);
		dataMission.setState(Integer.parseInt(state));
		//执行更新操作
		int i = missionService.updateState(dataMission);

		//获取发布人
		User user = userService.getById(Integer.parseInt(recUserId));
		//判断是否更新成功
		if(i==1) {//更新成功则提示信息
			/**
			 * 根据任务状态推送信息通知
			 */
			if(dataMission.getState()==1){//任务被接收
				messageService.acceptTaskMsg(dataMission.getUserId(), dataMission.getTitle(), user.getUsername(), new Date());
			}else if(dataMission.getState()==2){//任务完成后待验证
				messageService.compTaskMsg(dataMission.getUserId(), dataMission.getTitle(), user.getUsername());
			}else if(dataMission.getState()==3||dataMission.getState()==4){//任务验证
				/**
				 * 任务验证完成后就更新对应积分
				 */
				boolean flag1 = true, flag2 = true;
				//验证通过
				if (dataMission.getState() == 3) {
					User recUser = userService.getById(dataMission.getRecUserId());
					recUser.setScore(recUser.getScore() + dataMission.getScore());
					flag2 = userService.updateById(recUser);
				}else{
					user.setScore(user.getScore() + dataMission.getScore());
				 	flag1 = userService.updateById(user);
				}

				if (!(flag1 && flag2)) {
					dataMap.put("msg", "失败");
					dataMap.put("data", "积分更新失败");
					return dataMap;
				}
				messageService.verifyMsg(dataMission.getRecUserId(), dataMission.getTitle(), user.getUsername(), dataMission.getState());
			}
			messageService.acceptTaskMsg(dataMission.getUserId(), dataMission.getTitle(),user.getUsername(), new Date());
			dataMap.put("msg", "成功");
			dataMap.put("data", "操作成功");
		}else {
			dataMap.put("msg", "失败");
			dataMap.put("data", "操作失败");
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
	public Object queryToPage(Integer dpage,Integer npage,Integer userId) {
		QueryVo vo = new QueryVo();
		vo.setDpage(dpage);
		vo.setNpage(npage);
		vo.setUserId(userId);
		Page<MissionDTO> data = missionService.selectMission(vo);
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
	 * 分页查询为接受所有信息表，
	 * @param map 参数： npage每页显示条数   dpage查第几页
	 * @return
	 */
	@RequestMapping(value = "/selectMissionAll",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object queryPage(Integer dpage,Integer npage) {
		//Integer dpage = (Integer)map.get("dpage");//第几页
		//Integer npage = (Integer)map.get("npage");//每页显示多少条数据
		Page<MissionDTO> data = missionService.selectMissionAll(dpage, npage);
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
	public  Object delete(Integer id){
		Mission mission = missionService.getById(id);
		User user = userService.getById(mission.getUserId());
		//删除任务退回积分
		user.setScore(mission.getScore()+user.getScore());
		userService.updateUser(user);
		int data = missionService.deleteMissionById(id);

		Map<String, Object> delMap = new HashMap<String, Object>(2);
		if(data > 0) {
			delMap.put("code",200);
			delMap.put("msg", "删除成功！");
		}else {
			delMap.put("code",-1);
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

