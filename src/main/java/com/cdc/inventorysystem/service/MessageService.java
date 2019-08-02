package com.cdc.inventorysystem.service;

import com.cdc.inventorysystem.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzix
 * @since 2019-08-01
 */
public interface MessageService extends IService<Message> {

    /**
     * 根据用户id获取用户系统信息和个人信息
     * @param userId 用户id
     * @return
     */
    List<Message> getMessages(Integer userId);

    /**
     * 添加消息
     *      1. id不等于0，推送到个体
     *      2. id等于0，推送到全体
     * @param title
     * @param content
     * @param id
     * @return
     */
    Boolean addMessage(String title, String content, Integer id);

    /**
     * 根据消息Id获取消息
     * @param msgId 消息id
     * @return
     */
    Message getMessageById(int msgId);

    //Boolean
}
