package com.slsale.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther:
 * @Date:2021/7/11
 * @Description:com.slsale.controller
 * @Version:1.0
 */
@Controller
public class DemoCookie {

    @RequestMapping(value="/createCookie")
    public String createCookie(HttpServletResponse response){

        //服务器端产生Cookie
        Cookie cookie = new Cookie("key","value");

        //Cookie的默认存活时间与HttpSession相同 设置Cookie的存活时间
        //cookie.setMaxAge();

        //Cookie的默认存储路径为path=/ 表示只要是该路径下的资源 即根路径下的所有资源均可以访问cookie
        //即控制哪个路径下的资源可以防卫到cookie

        cookie.setPath("/"); //表示 根路径下的资源可以访问到cookie
        //cookie.setPath("/demo"); //表示根路径下的demo下的资源可以访问到cookie

        //域名和当前项目的域名一致

        //跳转方式必须是重定向 第一次请求控制器产生cookie并将cookie设置到response中响应给浏览器，第二次请求的时候就会携带所有有效Cookie
        response.addCookie(cookie);

        return "redirect:/getCookie";
    }
    @RequestMapping(value="/getCookie")
    public String getCookie(HttpServletRequest request, Model model) throws IOException {

        Cookie[] cookies = request.getCookies();

        //第一次请求服务器的时候 是不存在cookie的 即cookie数组为null
        if(cookies != null){
            for(Cookie cookie:cookies){
                request.setAttribute("key",cookie.getName());
                request.setAttribute("value",cookie.getValue());
                //model.addAttribute("key1",cookie.getName());
                //model.addAttribute("value1",cookie.getValue());
            }
        }else{
            request.setAttribute("error","不存在cookie");
        }
        return "cookie/cookie";
    }
}
