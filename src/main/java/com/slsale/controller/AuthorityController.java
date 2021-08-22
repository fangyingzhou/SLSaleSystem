package com.slsale.controller;

import com.slsale.commons.Constant;
import com.slsale.commons.RedisAPI;
import com.slsale.pojo.*;
import com.slsale.service.AuthorityService;
import com.slsale.service.FunctionService;
import com.slsale.service.RoleService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class AuthorityController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private FunctionService functionService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private LoginController  loginController;

    @Resource(name="redisAPI")
    private RedisAPI redisAPI;

    @RequestMapping(value="/backend/authoritymanage.html")
    public Object authorityManager(HttpSession session, Model model){
        Map<String,Object> baseModel =(Map<String,Object>) session.getAttribute(Constant.SESSION_BASE_MODE);
        if(baseModel == null){
            return "redirect:/";
        }
        List<Role> roleList = null;
        try {
            roleList = roleService.getRoleNameAndId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAllAttributes(baseModel);
        model.addAttribute("roleList",roleList);
        return "/backend/authoritymanage";
    }
    /**
     * 功能描述：根据父菜单ID 获取系统中所有的菜单功能对象
     * select * from au_function where parentId = #{id}
     */
    @RequestMapping(value="/backend/functions.html",produces={"text/html;charset=utf-8"})
    @ResponseBody
    public Object functions(){
        Function function = new Function();
        String cjson = "nodata";
        try {
            //存储了整个菜单
            List<RoleFunctions> rList = new ArrayList<>();
            //首先获取所有的一级菜单功能
            function.setId(0);
            List<Function> funcList  = functionService.getSubFunctionList(function);
            if(funcList != null){
                for(Function func:funcList){
                    RoleFunctions roleFunctions = new RoleFunctions();
                    roleFunctions.setMainFunction(func);
                    List<Function> subFuncList = functionService.getSubFunctionList(func);
                    roleFunctions.setSubFunctions(subFuncList);
                    rList.add(roleFunctions);
                }
                cjson = JSONArray.fromObject(rList).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            cjson =  "failed";
        }
        return cjson;
    }
    /**
     * 功能描述：根据角色ID 以及 菜单ID 确定一个权限对象
     * /backend/getDefaultAuthority.html
     */
    @RequestMapping(value = "/backend/getDefaultAuthority.html",produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public Object getAuthority(@RequestParam(value = "roleId",required = false)Integer roleId,
                               @RequestParam(value="functionId",required=false)Integer functionId){
        String result = "nodata";
        try {
            Authority authority = new Authority();
            //根据角色ID 以及 菜单ID 确定一个权限对象
            authority.setRoleId(roleId);
            authority.setFunctionId(functionId);
            Authority _authority = authorityService.getAuthority(authority);
            if(_authority != null){
                result = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value="/backend/modifyAuthority.html")
    @ResponseBody
    public Object modifyAuthority(HttpSession session,
                                  @RequestParam(value = "ids",required=false)String ids){
        String result= "nodata";
        System.out.println("ids========"+ids);
        try {
            if(!StringUtils.isEmpty(ids)){
                String[] idsArray = ids.split("-");
                if(idsArray != null && idsArray.length>0){
                    User user = (User) session.getAttribute(Constant.SESSION_USER);
                    authorityService.hl_addAuthority(idsArray,user.getLoginCode());
                    List<Menu> mList = null;
                    //当前角色的权限被修改之后 需要重新获取当前角色的权限 并更新redis中的缓存
                    mList = loginController.getFunctionListByCurrentUser(Integer.valueOf(idsArray[0]));
                    JSONArray jsonArray = JSONArray.fromObject(mList);
                    redisAPI.set("menuList-"+idsArray[0],jsonArray.toString());

                    Authority authority = new Authority();
                    authority.setRoleId(Integer.valueOf(idsArray[0]));

                    //select * from au_function where parentId > 0 and id in (select functionId from au_authority where roleId = #{roleId})
                    List<Function> functionUrlList = functionService.getFunctionListByRoleId(authority);
                    if(functionUrlList != null && functionUrlList.size() >=0){
                        StringBuffer sb = new StringBuffer();
                        for(Function func:functionUrlList){
                            sb.append(func.getFuncUrl());
                        }
                        redisAPI.set("Role-"+idsArray[0]+"-UrlList",sb.toString());
                    }
                    result = "success";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String gitHub(){
        String result ="push github";
        return result;
    }

}
