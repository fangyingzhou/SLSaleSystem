package com.slsale.mapper.goodsinfo;

import com.slsale.pojo.GoodsInfo;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/5/12
 * @Description:com.slsale.mapper
 * @Version:1.0
 */
public interface GoodsInfoMapper {

    public List<GoodsInfo> selectGoodsInfoListByPage(GoodsInfo goodsInfo);

    public int selectTotalCount(GoodsInfo goodsInfo);

    public GoodsInfo selectGoodsInfoById(GoodsInfo goodsInfo);

    public int goodsSNIsExist(GoodsInfo goodsInfo);

    public int updateGoodsInfo(GoodsInfo goodsInfo);

    public int updateGoodsInfoState(GoodsInfo goodsInfo);
}
