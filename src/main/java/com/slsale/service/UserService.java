package com.slsale.service;

import com.slsale.pojo.User;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/4/13
 * @Description:com.slsale.service
 * @Version:1.0
 */
public interface UserService {

    public int loginCodeIsExit(User user) throws Exception;

    public User login(User user) throws Exception;

    public int getTotalCount(User user) throws Exception;

    public List<User> getUserListByPage(User user) throws Exception;

    public User getUserById(User user) throws Exception;

}
