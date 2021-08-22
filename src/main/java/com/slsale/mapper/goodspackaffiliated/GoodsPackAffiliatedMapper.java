package com.slsale.mapper.goodspackaffiliated;

import com.slsale.pojo.GoodsPackAffiliated;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/5/15
 * @Description:com.slsale.mapper.goodspackaffiliated
 * @Version:1.0
 */
public interface GoodsPackAffiliatedMapper {

    public List<GoodsPackAffiliated> selectGoodsPackAffiliatedListByPackId(GoodsPackAffiliated goodsPackAffiliated);

    public int deleteGoodsPackAffiliated(GoodsPackAffiliated goodsPackAffiliated);

    public int addGoodsPackAffiliated(GoodsPackAffiliated goodsPackAffiliated);
}
