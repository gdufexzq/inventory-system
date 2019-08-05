package com.cdc.inventorysystem.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdc.inventorysystem.entity.Message;
import com.cdc.inventorysystem.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zzix
 * @since 2019-08-01
 */
@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 获取用户所有消息
     *
     * @param id 用户Id
     * @return
     */
    @RequestMapping(value = "/getMessages", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Page<Message> getMessages(@RequestParam Integer id, @RequestParam(defaultValue = "1") String current, @RequestParam(defaultValue = "10") String size) {
        if (id == null) {
            return null;
        }
        Page<Message> messages = messageService.getMessages(id, current, size);
        return messages;
    }

    /**
     * 管理员发布消息
     *
     * @param title   消息标题
     * @param content 消息内容
     * @param userId  消息对象[0：全体对象；其他：用户id]
     * @return
     */
    @RequestMapping(value = "/addMessage", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public HashMap addMessage(String title, String content, String userId) {
        String msg = null;
        Boolean result = messageService.addMessage(title, content, Integer.parseInt(userId));
        if (result) {
            msg = "消息发布成功！";
        } else {
            msg = "消息发布失败！";
        }
        HashMap<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("result", result);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 根据消息Id获取消息
     *
     * @param msgId
     * @return
     */
    @RequestMapping(value = "/msgDetail", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Message getMessageById(String msgId) {
        return messageService.getMessageById(Integer.parseInt(msgId));
    }

    /**
     * 根据消息Id删除消息
     *
     * @param msgId
     * @return
     */
    @RequestMapping(value = "delMsg", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public HashMap delMsgById(String msgId) {
        String msg = null;
        Boolean result = messageService.delMsgById(Integer.parseInt(msgId));
        if (result) {
            msg = "消息删除成功！";
        } else {
            msg = "消息删除失败！";
        }
        HashMap<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("result", result);
        resultMap.put("msg", msg);
        return resultMap;
    }
}

