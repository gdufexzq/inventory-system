package com.cdc.inventorysystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdc.inventorysystem.entity.Message;
import com.cdc.inventorysystem.dao.MessageMapper;
import com.cdc.inventorysystem.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzix
 * @since 2019-08-01
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Autowired
    private MessageService messageService;

    @Override
    public List<Message> getMessages(Integer userId) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        //userId = 0 代表当前消息为全体消息
        queryWrapper.eq("userId", 0);
        queryWrapper.or(true);
        queryWrapper.eq("userId", userId);
        return messageService.list(queryWrapper);
    }

    @Override
    public Boolean addMessage(String title, String content, Integer id) {
        Message message = new Message();
        message.setTitle(title).setContent(content).setUserId(id);
        return messageService.saveOrUpdate(message);
    }

    @Override
    public Message getMessageById(int msgId) {
        return messageService.getById(msgId);
    }
}
