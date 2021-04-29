package com.slsale.service.impl;

import com.slsale.mapper.function.FunctionMapper;
import com.slsale.pojo.Authority;
import com.slsale.pojo.Function;
import com.slsale.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/4/14
 * @Description:com.slsale.service.impl
 * @Version:1.0
 */
@Service
public class FunctionServiceImpl implements FunctionService {
    @Autowired
    private FunctionMapper functionMapper;

    /**
     * 功能描述：通过查询au_authority获取一级主菜单 au_authority是 角色表(au_role) 与 功能表(au_function) 之间的维护表
     */
    @Override
    public List<Function> getMainFunctionList(Authority authority) throws Exception {
        return functionMapper.selectMainFunctionList(authority);
    }

    /**
     * 功能描述：根据一级主菜单 获取主菜单下的子菜单 传递的是父菜单的ID
     */
    @Override
    public List<Function> getFunctionListByPid(Function function) throws Exception{
        return functionMapper.selectFunctionListByPid(function);
    }

    /**
     * 功能描述：获取主菜单下的子菜单 传递的是父菜单的ID>0
     */
    @Override
    public List<Function> getFunctionListByRoleId(Authority authority) throws Exception {
        return functionMapper.selectFunctionListByRoleId(authority);
    }

    /**
     * 功能描述：
     */
    @Override
    public List<Function> getSubFunctionList(Function function) throws Exception {
        return functionMapper.selectSubFunctionList(function);
    }
}
