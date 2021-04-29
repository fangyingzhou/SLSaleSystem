package com.slsale.controller;

import com.slsale.commons.Constant;
import com.slsale.pojo.Function;
import com.slsale.pojo.Role;
import com.slsale.pojo.RoleFunctions;
import com.slsale.service.FunctionService;
import com.slsale.service.RoleService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther:
 * @Date:2021/4/26
 * @Description:com.slsale.controller
 * @Version:1.0
 */
@Controller
public class AuthorityController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private FunctionService functionService;

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

    @RequestMapping(value="/backend/functions.html",produces={"text/html;charset=utf-8"})
    @ResponseBody
    public Object functions(){
        Function function = new Function();
        String cjson = "nodata";
        try {
            List<RoleFunctions> rList = new ArrayList<>();

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
}
