package com.slsale.interceptor;

import com.slsale.commons.Constant;
import com.slsale.commons.RedisAPI;
import com.slsale.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Auther:
 * @Date:2021/6/23
 * @Description:com.slsale.interceptor
 * @Version:1.0
 */
public class SysInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisAPI redisAPI;

    /**
     * 功能描述：在进入控制器之前执行 可用于登录检测
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        //如果用户已经登录 用户信息肯定存储到session中
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.SESSION_USER);

        //如果获取user为null 则说明未登录
        if(user == null){
            response.sendRedirect("/");
            return false;
        }else{
            //获取当前用户请求的uri
            String urlPath = request.getRequestURI();

            //根据key从redis中获取当前用户可以请求的资源路径
            String urlKey = "Role-"+user.getRoleId()+"-urlList";

            //redis中存储的是拼接后的资源路径 字符串
            String urlValue = "url:"+redisAPI.get(urlKey);

            if(urlValue != null && !urlValue.equals("") && urlValue.indexOf(urlPath) > 0){
                return true;
            }else{
                response.sendRedirect("/401.html");
                return false;
            }
        }

    }
    /**
     * 功能描述：进入jsp之前执行的方法
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView model) throws Exception {
        //可以根据model中的key获取 model中存储的value值
        Map<String,Object> map = model.getModel();
        String value = map.get("").toString();

        //视图名称
        String viewName = model.getViewName();
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
