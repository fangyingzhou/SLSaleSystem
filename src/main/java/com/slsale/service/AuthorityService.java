package com.slsale.service;

import com.slsale.pojo.Authority;

/**
 * @Auther:
 * @Date:2021/5/4
 * @Description:com.slsale.service
 * @Version:1.0
 */
public interface AuthorityService {

    //根据角色ID 以及 功能菜单ID 确定一个权限对象
    public Authority getAuthority(Authority authority) throws Exception;

    //
    public Boolean hl_addAuthority(String[] ids,String createdBy) throws Exception;
}
