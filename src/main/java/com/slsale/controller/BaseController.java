package com.slsale.controller;

import com.slsale.commons.Constant;
import com.slsale.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Auther:
 * @Date:2021/4/15
 * @Description:com.slsale.controller
 * @Version:1.0
 */
@Controller
public class BaseController {
    private Logger logger = Logger.getLogger(BaseController.class);

    private User currentUser;

    public User getCurrentUser(){
        if(this.currentUser == null){

            //RequestContextHolder 持有上下文的Request容器
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

            //默认为true 表示没有找到session的时候自动创建session 为false表示 不会自动创建
            HttpSession session = request.getSession(false);

            if(session !=null){
                currentUser =(User)session.getAttribute(Constant.SESSION_USER);
            }else{
                currentUser = null;
            }
        }
        return currentUser;
    }
}
