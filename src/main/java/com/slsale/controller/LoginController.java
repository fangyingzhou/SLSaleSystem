package com.slsale.controller;

import com.slsale.commons.Constant;
import com.slsale.commons.RedisAPI;
import com.slsale.pojo.Authority;
import com.slsale.pojo.Function;
import com.slsale.pojo.Menu;
import com.slsale.pojo.User;
import com.slsale.service.FunctionService;
import com.slsale.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.*;

/**
 * @Auther:
 * @Date:2021/4/13
 * @Description:com.slsale.controller
 * @Version:1.0
 */
@Controller
public class LoginController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private FunctionService functionService;

   @Resource(name = "redisAPI")
    private RedisAPI redisAPI;

    //@RequestMapping的属性值设置为"/"时，表示除jsp之外 所有的请求都会被控制器拦截 都会走控制器
    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }

    @RequestMapping(value="/login.html")
    @ResponseBody
    public Object login(@RequestParam String user, HttpSession session){

        try {
            if(user == null){
                return "nodata";
            }else{
                JSONObject jsonObject = JSONObject.fromObject(user);
                User _user = (User)JSONObject.toBean(jsonObject,User.class);

                //即可以判断用户民是否存在 也可以判断用户名是否重复
                int index = userService.loginCodeIsExit(_user);

                if(index==0){
                    return "nologincode";
                }else{
                    //根据用户名和密码查询
                    User loginUser = userService.login(_user);
                    if (loginUser == null){
                        return "pwderror";
                    }else{
                        session.setAttribute(Constant.SESSION_USER,loginUser);
                        loginUser.setLastLoginTime(new Date());
                        return "success";
                    }
                }
            }
        } catch (Exception e) {
           return "failed";
        }
    }

    @RequestMapping(value="/main.html")
    public String main(HttpSession session, Model model){
        User user = this.getCurrentUser();
        List<Menu> mList = null;

        //user 不为null 说明用户已经登录
        if(user != null){
            Map<String,Object> baseModel = new HashMap<>();
            baseModel.put("user",user);

            String key = "menuList-"+user.getRoleId();

            //如果redis中存在key
            if(redisAPI.exists(key)){
                String value = redisAPI.get(key);
                if(value !=null && !"".equals(value)){
                    baseModel.put("mList",value);
                }else{
                    //redis中存在key 但是key对应的value为null;
                    return "redirect:/";
                }
            }else{
                //从数据库中读取数据
                mList = this.getFunctionListByCurrentUser(user.getRoleId());
                if(mList != null){
                    //将集合List转换成JSONArray数组
                    JSONArray jsonArray = JSONArray.fromObject(mList);
                    String jsonString = jsonArray.toString();
                    baseModel.put("mList",jsonString);
                    redisAPI.set(key,jsonString);
                }
            }

            //将子菜单的url写入到redis中
            String roleKey = "Role-"+user.getRoleId()+"-UrlList";
            try {
                //如果redis中 不存在key
                if(!redisAPI.exists(roleKey)){
                    Authority authority = new Authority();
                    authority.setRoleId(user.getRoleId());
                    List<Function> fList = functionService.getFunctionListByRoleId(authority);

                    if (fList != null){
                        //StringBuffer是线程安全的 StringBuilder是线程不安全的
                        StringBuffer sb = new StringBuffer();

                        for(Function function:fList){
                            sb.append(function.getFuncUrl());
                        }
                        redisAPI.set(roleKey,sb.toString());
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            session.setAttribute(Constant.SESSION_BASE_MODE,baseModel);
            model.addAllAttributes(baseModel);
            return "main";

        }else{
            return "redirect:/"; //跳转到登录页面
        }

    }

    //根据当前登录者的roleId 查询该用户所拥有的权限
    protected List<Menu> getFunctionListByCurrentUser(int roleId){
        List<Menu> menuList = new ArrayList<>();
        Authority authority = new Authority();
        authority.setRoleId(roleId);
        try {
            List<Function> functionList = functionService.getMainFunctionList(authority);
            //循环遍历一级主菜单 传递父菜单ID 查询的时候需要同时传递父菜单ID(parentId)和角色ID(roleId) 因此需要在父function中设置roleId
            for(Function function:functionList){
                Menu menu = new Menu();
                function.setRoleId(roleId);
                menu.setMainMenu(function); //一级主菜单
                List<Function> subFunctionLList = functionService.getFunctionListByPid(function);
                if(subFunctionLList != null){
                    menu.setSubMenus(subFunctionLList); //子菜单
                    menuList.add(menu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuList;
    }
}
