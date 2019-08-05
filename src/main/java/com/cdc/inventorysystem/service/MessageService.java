package com.cdc.inventorysystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cdc.inventorysystem.entity.Message;

import java.util.Date;
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
    Page<Message> getMessages(Integer userId, String current, String size);

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
    Message getMessageById(Integer msgId);

    /**
     * 任务被接受时推送消息给任务发布者
     *
     * @param pubId 任务发布者Id
     * @param title 任务标题
     * @param name 接受者name
     * @param acceptTime 接受时间
     * @return boolean 消息发布成功true，失败false
     */
    public Boolean acceptTaskMsg(Integer pubId,String title, String name, Date acceptTime);

    /**
     * 任务完成时推送信息给任务发布者
     * @param pubId 任务发布者Id
     * @param title 任务标题
     * @param name 接受者name
     * @return Boolean 消息发布成功true，失败false
     */
    public Boolean compTaskMsg(Integer pubId,String title,String name);

    /**
     * 任务验收完成时通知任务接受者
     * @param recId 任务接受者Id
     * @param title 任务标题
     * @param name 发布者name
     * @param state 任务状态
     *              - 3:验证通过(任务完成)
     *              - 4:验证不通过(任务失败)
     * @return boolean 消息发布成功true，失败false
     */
    public Boolean verifyMsg(Integer recId, String title, String name, int state);

    /**
     * 根据消息Id 删除消息
     * @param msgId 消息Id
     * @return
     */
    Boolean delMsgById(Integer msgId);
}
