package com.slsale.service;

import com.slsale.pojo.Role;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/4/19
 * @Description:com.slsale.service
 * @Version:1.0
 */
public interface RoleService {

    public List<Role> getRoleNameAndId() throws Exception;
}
