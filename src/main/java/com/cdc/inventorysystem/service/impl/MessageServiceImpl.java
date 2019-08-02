package com.cdc.inventorysystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdc.inventorysystem.entity.Message;
import com.cdc.inventorysystem.dao.MessageMapper;
import com.cdc.inventorysystem.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Override
    public Boolean acceptTaskMsg(Integer pubId,String title, String name, Date acceptTime) {
        //格式化时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(acceptTime);
        //生成消息对象，其中消息时间由数据库自动生成
        Message message = new Message();
        message.setTitle("任务【"+title+"】已被接受");
        message.setContent("您发布的任务【"+title+"】已被用户【"+name+"】于【"+dateStr+"】接受，请留意任务进度。");
        //消息对象
        message.setUserId(pubId);
        return messageService.saveOrUpdate(message);
    }

    @Override
    public Boolean compTaskMsg(Integer pubId,String title, String name) {
        Message message = new Message();
        message.setTitle("任务【"+title+"】已完成");
        message.setContent("您发布的任务【"+title+"】已被用户【"+name+"】完成，请及时验收任务结果。");
        message.setUserId(pubId);
        return messageService.saveOrUpdate(message);
    }

    @Override
    public Boolean verifyMsg(Integer recId,String title, String name, int state) {
        Message message = new Message();
        /**
         * state:任务状态，流转到此只能有以下两个状态
         *      3：验收通过（任务完成）
         *      4：验收不通过（任务失败）
         */
        if (state == 3){
            message.setTitle("任务【"+title+"】验收通过");
            message.setContent("您完成的任务【"+title+"】符合其发布用户【"+name+"】要求，已被验收通过，您将获得该任务积分奖励，请留意。");
        }else if(state == 4){
            message.setTitle("任务【"+title+"】验收不通过");
            message.setContent("您完成的任务【"+title+"】不符合其发布用户【"+name+"】要求，验收不通过，您无法获得该任务积分奖励，请留意任务要求。");
        }else{
            return false;
        }
        message.setUserId(recId);
        return messageService.saveOrUpdate(message);
    }
}
