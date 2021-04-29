package com.slsale.mapper.role;

import com.slsale.pojo.Role;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/4/19
 * @Description:com.slsale.mapper.role
 * @Version:1.0
 */
public interface RoleMapper {

    public List<Role> selectRoleNameAndId();
}
