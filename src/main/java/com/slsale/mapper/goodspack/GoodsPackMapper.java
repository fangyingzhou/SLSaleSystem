package com.slsale.mapper.goodspack;

import com.slsale.pojo.GoodsPack;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/5/14
 * @Description:com.slsale.mapper.goodspack
 * @Version:1.0
 */
public interface GoodsPackMapper {

    public List<GoodsPack> selectGoodsPackListByPage(GoodsPack goodsPack);

    public int selectTotalCount(GoodsPack goodsPack);

    public GoodsPack selectGoodsPackById(GoodsPack goodsPack);

    public int updateGoodsPack(GoodsPack goodsPack);

}
