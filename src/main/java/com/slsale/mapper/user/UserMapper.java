package com.slsale.mapper.user;

import com.slsale.pojo.User;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/4/13
 * @Description:com.slsale.mapper
 * @Version:1.0
 */
public interface UserMapper {

    public int loginCodeIsExit(User user);

    public User selectByLoginCodeAndPwd(User user);

    public int selectTotalCount(User user);

    public List<User> selectUserListByPage(User user);

    public User selectUserById(User user);
}
