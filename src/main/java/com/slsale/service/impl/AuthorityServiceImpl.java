package com.slsale.service.impl;

import com.slsale.mapper.authority.AuthorityMapper;
import com.slsale.mapper.function.FunctionMapper;
import com.slsale.pojo.Authority;
import com.slsale.pojo.Function;
import com.slsale.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther:
 * @Date:2021/5/4
 * @Description:com.slsale.service.impl
 * @Version:1.0
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private FunctionMapper functionMapper;
    /**
     * 功能描述：
     */
    @Override
    public Authority getAuthority(Authority authority) throws Exception {
        return authorityMapper.selectAuthority(authority);
    }

    /**
     * 功能描述：传递过来的是菜单功能的ID
     */
    @Override
    public Boolean hl_addAuthority(String[] ids,String createdBy) throws Exception {
        Authority authority = new Authority();
        authority.setRoleId(Integer.valueOf(ids[0]));

        //1.首先根据角色ID从权限表中删除该角色所拥有的所有权限
        authorityMapper.deleteAuthority(authority);

        //2.将数组转换成字符串
        String idsStr = "";
        for(int i=1;i<ids.length;i++){
            idsStr += ids[i]+",";
        }
        System.out.println("idsStr=========="+idsStr);
        if(idsStr !=null && idsStr.contains(",")){
            idsStr = idsStr.substring(0,idsStr.lastIndexOf(","));
            //根据id获取菜单对象
            List<Function> fList = functionMapper.selectFunctionListByIdIn(idsStr);
            if (fList != null && fList.size() > 0){
                for(Function func:fList){
                    authority.setFunctionId(func.getId());
                    authority.setCreatedBy(createdBy);
                    authority.setCreationTime(new Date());
                    authorityMapper.insertAuthority(authority);
                }
            }
        }
        return true;
    }


}
