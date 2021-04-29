package com.slsale.service;

import com.slsale.pojo.Authority;
import com.slsale.pojo.Function;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/4/14
 * @Description:com.slsale.service.impl
 * @Version:1.0
 */
public interface FunctionService {

    //获取一级主菜单
    public List<Function> getMainFunctionList(Authority authority) throws Exception;

    //获取一级菜单下的子菜单
    public List<Function> getFunctionListByPid(Function function) throws Exception;

    //获取一级主菜单下的子菜单 即parentId > 0 包含二级菜单 以及 三级菜单
    public List<Function> getFunctionListByRoleId(Authority authority) throws Exception;

    //
    public List<Function> getSubFunctionList(Function function) throws Exception;
}
