package com.cdc.inventorysystem.service.impl;

import com.cdc.inventorysystem.entity.Message;
import com.cdc.inventorysystem.dao.MessageMapper;
import com.cdc.inventorysystem.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-02
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

	@Override
	public boolean acceptTaskMsg(Integer pubId, String title, String name, Date acceptTime) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean compTaskMsg(Integer pubId, String title, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean verifyMsg(Integer recId, String title, String name, int state) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
