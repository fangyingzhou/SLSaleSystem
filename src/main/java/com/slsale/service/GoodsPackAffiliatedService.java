package com.slsale.service;

import com.slsale.pojo.GoodsPackAffiliated;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/5/16
 * @Description:com.slsale.service
 * @Version:1.0
 */
public interface GoodsPackAffiliatedService {

    public List<GoodsPackAffiliated> getGoodsPackAffiliatedByPackId(GoodsPackAffiliated goodsPackAffiliated) throws Exception;
}
