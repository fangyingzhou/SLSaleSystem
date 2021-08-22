package com.slsale.service.impl;

import com.slsale.mapper.goodspackaffiliated.GoodsPackAffiliatedMapper;
import com.slsale.pojo.GoodsPackAffiliated;
import com.slsale.service.GoodsPackAffiliatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/5/16
 * @Description:com.slsale.service.impl
 * @Version:1.0
 */
@Service
public class GoodsPackAffiliatedServiceImpl implements GoodsPackAffiliatedService {

    @Autowired
    private GoodsPackAffiliatedMapper goodsPackAffiliatedMapper;

    @Override
    public List<GoodsPackAffiliated> getGoodsPackAffiliatedByPackId(GoodsPackAffiliated goodsPackAffiliated) throws Exception {
        return goodsPackAffiliatedMapper.selectGoodsPackAffiliatedListByPackId(goodsPackAffiliated);
    }
}
