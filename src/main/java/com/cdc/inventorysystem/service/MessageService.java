package com.cdc.inventorysystem.service;

import com.cdc.inventorysystem.entity.Message;

import java.util.Date;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-02
 */
public interface MessageService extends IService<Message> {
	/**
	 * 任务被接受时推送消息给任务发布者
	 * state=1
	 */
	public boolean acceptTaskMsg(Integer pubId,String title, String name, Date acceptTime);
	
	/**
	 * 任务完成时推送信息给任务发布者
	 * state=2
	 */
	public boolean compTaskMsg(Integer pubId,String title,String name);
	
	/**
	 * 任务验收完成时通知任务接受者
	 * state=3
	 */
	public boolean verifyMsg(Integer recId, String title, String name, int state);
	
}
