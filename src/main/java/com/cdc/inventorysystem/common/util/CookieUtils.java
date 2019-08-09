package com.cdc.inventorysystem.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xzquan
 * @version V1.0
 * @Description: ${TODO}
 * @date 2019/6/17 10:44
 */
public class CookieUtils {
    public static String getCookie(HttpServletRequest request, String cookieName) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 设置cookie失效时间,单位为秒
     * 如果设置为负值的话，则为浏览器进程Cookie(内存中保存)，
     * 关闭浏览器就失效；如果设置为0，则立即删除该Cookie。
     * @param response
     * @param cookieName
     * @param second
     * @return
     */
    public static String setCookieTime(HttpServletResponse response, String cookieName, int second) {

//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(cookieName)) {
//                    System.out.println(cookie.getName() + ":" + cookie.getValue() + ":" + second);
//                    cookie.setMaxAge(second);
//
//                }
//
//            }
//        }
        Cookie cookie = new Cookie(cookieName, "");
//        cookie.setPath("/");
//        cookie.setMaxAge(60 * 60 * 24); //一小时过期
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return null;
    }

    /**设置cookie失效时间,单位为秒
     * 如果设置为负值的话，则为浏览器进程Cookie(内存中保存)，
     * 关闭浏览器就失效；如果设置为0，则立即删除该Cookie。
     * 设置cookie存活时间
     * @param response
     * @param cookieName
     * @param value
     * @param second
     */
    public static void writeCookie(HttpServletResponse response, String cookieName, String value, int second) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setPath("/");
//        cookie.setMaxAge(60 * 60 * 24); //一小时过期
        if(second != 0) {
            cookie.setMaxAge(second);
        }

        response.addCookie(cookie);

    }

}
