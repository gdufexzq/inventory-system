package com.cdc.inventorysystem.aop;

import com.cdc.inventorysystem.common.exceptions.NoAuthException;
import com.cdc.inventorysystem.common.util.CookieUtils;
import com.cdc.inventorysystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UserLoginInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("UserLoginInterceptor preHandle...");
        String sign = null;
        //管理员与用户公共接口权限验证
        if (request.getRequestURI().contains("/message/addMessage")) {
            sign = CookieUtils.getCookie(request, "admin_sign");
            if (sign == null) {
                sign = CookieUtils.getCookie(request, "sign");
            }
        } else {
            sign = CookieUtils.getCookie(request, "sign");
        }
        if (sign != null && "".equals(sign)) {
            // 使用redis对cookie做校验,username和password作为key:value
            Boolean isSign = redisTemplate.hasKey(sign);
            if (isSign) {
                return true;
            }
        }

        //每一个项目对于登陆的实现逻辑都有所区别，我这里使用最简单的Session提取username来验证登陆。
        HttpSession session = request.getSession();
        //这里的username是登陆时放入session的
        String username = (String) session.getAttribute("username");
        //如果session中没有username，表示没登陆
        if (username == null) {
            //这个方法返回false表示忽略当前请求，如果一个用户调用了需要登陆才能使用的接口，如果他没有登陆这里会直接忽略掉
            //当然你可以利用response给用户返回一些提示信息，告诉他没登陆
            throw new NoAuthException("用户未登陆...");
        } else {
            return true;    //如果session里有username，表示该用户已经登陆，放行，用户即可继续调用自己需要的接口
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
