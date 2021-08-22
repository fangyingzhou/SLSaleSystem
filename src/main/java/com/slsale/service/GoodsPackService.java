package com.slsale.service;

import com.slsale.pojo.GoodsPack;
import com.slsale.pojo.GoodsPackAffiliated;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/5/14
 * @Description:com.slsale.service
 * @Version:1.0
 */
public interface GoodsPackService {

    public List<GoodsPack> getGoodsPackListByPage(GoodsPack goodsPack) throws Exception;

    public int getTotalCount(GoodsPack goodsPack) throws Exception;

    public GoodsPack getGoodsPackById(GoodsPack goodsPack) throws Exception;

    public int modifyGoodsPack(GoodsPack goodsPack) throws Exception;

    public boolean hl_modifyGoodsPack(GoodsPack goodsPack, List<GoodsPackAffiliated> gpaList) throws Exception;
}
