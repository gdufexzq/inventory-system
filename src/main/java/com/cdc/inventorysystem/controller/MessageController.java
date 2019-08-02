package com.cdc.inventorysystem.controller;


import com.cdc.inventorysystem.entity.Message;
import com.cdc.inventorysystem.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
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
     * @param session
     * @return
     */
    @PostMapping("/getMessages")
    @ResponseBody
    public List<Message> getMessages(HttpSession session){
        User user = (User) session.getAttribute("user");
        Integer userId = user.getId();
        //Integer userId = 1;
        List<Message> messages = messageService.getMessages(userId);
        return messages;
    }

    /**
     * 管理员发布消息
     * @param title     消息标题
     * @param content   消息内容
     * @param userId    消息对象[0：全体对象；其他：用户id]
     * @return
     */
    @PostMapping("/addMessage")
    @ResponseBody
    public HashMap addMessage(String title, String content, String userId){
        String msg = null;
        Boolean result = messageService.addMessage(title,content,Integer.parseInt(userId));
        if (result) {
            msg = "消息发布成功！";
        }else{
            msg = "消息发布失败！";
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result",result);
        resultMap.put("msg",msg);
        return resultMap;
    }

    /**
     * 根据消息Id获取消息
     * @param msgId
     * @return
     */
    @PostMapping("/msgDetail")
    @ResponseBody
    public Message getMessageById(String msgId){
        return messageService.getMessageById(Integer.parseInt(msgId));
    }
}

