package com.slsale.service.impl;

import com.slsale.mapper.role.RoleMapper;
import com.slsale.pojo.Role;
import com.slsale.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/4/19
 * @Description:com.slsale.service.impl
 * @Version:1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    /**
     * 功能描述：获取角色名称roleName 与 角色ID roleId
     */
    @Override
    public List<Role> getRoleNameAndId() throws Exception {
        return roleMapper.selectRoleNameAndId();
    }
}
