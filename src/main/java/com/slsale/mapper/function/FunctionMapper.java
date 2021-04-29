package com.slsale.mapper.function;

import com.slsale.pojo.Authority;
import com.slsale.pojo.Function;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/4/14
 * @Description:com.slsale.mapper.function
 * @Version:1.0
 */
public interface FunctionMapper {

    //获取所有的一级主菜单 select * from au_function where parentId = 0 and id in
    // (select functionId from au_authority where  roleId = #{roleId})
    public List<Function> selectMainFunctionList(Authority authority);

    //获取一级菜单下的子菜单 select * from au_function where parentId = #{id} and id in
    //(select functionId from au_authority where roleId = #{roleId})
    public List<Function> selectFunctionListByPid(Function function);

    //根据角色ID 获取一级菜单下的子菜单 该角色下所有子菜单包括二级菜单以及三级菜单
    public List<Function> selectFunctionListByRoleId(Authority authority);

    //不限于角色 查询所有的一级主菜单 以及二级菜单
    public List<Function> selectSubFunctionList(Function function);

}
