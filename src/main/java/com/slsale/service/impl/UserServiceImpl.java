package com.slsale.service.impl;

import com.slsale.mapper.user.UserMapper;
import com.slsale.pojo.User;
import com.slsale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/4/13
 * @Description:com.slsale.service.impl
 * @Version:1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 功能描述：既可以判断用户名是否存在 也可以判断用户名是否重复
     */
    @Override
    public int loginCodeIsExit(User user) throws Exception {
        return userMapper.loginCodeIsExit(user);
    }

    @Override
    public User login(User user) throws Exception {
        return userMapper.selectByLoginCodeAndPwd(user);
    }
    /**
     * 功能描述：获取用户总数量
     */
    @Override
    public int getTotalCount(User user) throws Exception {
        return userMapper.selectTotalCount(user);
    }
    /**
     * 功能描述：分页显示
     */
    @Override
    public List<User> getUserListByPage(User user) throws Exception {
        return userMapper.selectUserListByPage(user);
    }

    @Override
    public User getUserById(User user) throws Exception {
        return userMapper.selectUserById(user);
    }
}
