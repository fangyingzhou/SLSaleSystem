package com.slsale.service;

import com.slsale.pojo.GoodsInfo;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/5/12
 * @Description:com.slsale.service.impl
 * @Version:1.0
 */
public interface GoodsInfoService {

    public List<GoodsInfo> getGoodsInfoListByPage(GoodsInfo goodsInfo) throws Exception;

    public int getTotalCount(GoodsInfo goodsInfo) throws Exception;

    public GoodsInfo getGoodsInfoById(GoodsInfo goodsInfo) throws Exception;

    public int goodsSNIsExist(GoodsInfo goodsInfo) throws Exception;

    public int modifyGoodsInfo(GoodsInfo goodsInfo) throws Exception;

    public int modifyGoodsInfoState(GoodsInfo goodsInfo) throws Exception;
 }
