package com.slsale.mapper.authority;

import com.slsale.pojo.Authority;

/**
 * @Auther:
 * @Date:2021/5/4
 * @Description:com.slsale.mapper
 * @Version:1.0
 */
public interface AuthorityMapper {

    public Authority selectAuthority(Authority authority);

    public int insertAuthority(Authority authority);

    public int deleteAuthority(Authority authority);
}
