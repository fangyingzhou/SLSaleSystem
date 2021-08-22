package com.slsale.service.impl;

import com.slsale.mapper.goodsinfo.GoodsInfoMapper;
import com.slsale.pojo.GoodsInfo;
import com.slsale.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/5/12
 * @Description:com.slsale.service.impl
 * @Version:1.0
 */
@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public List<GoodsInfo> getGoodsInfoListByPage(GoodsInfo goodsInfo) throws Exception {
        return goodsInfoMapper.selectGoodsInfoListByPage(goodsInfo);
    }

    @Override
    public int getTotalCount(GoodsInfo goodsInfo) throws Exception {
        return goodsInfoMapper.selectTotalCount(goodsInfo);
    }

    @Override
    public GoodsInfo getGoodsInfoById(GoodsInfo goodsInfo) throws Exception {
        return goodsInfoMapper.selectGoodsInfoById(goodsInfo);
    }

    @Override
    public int goodsSNIsExist(GoodsInfo goodsInfo) throws Exception {
        return goodsInfoMapper.goodsSNIsExist(goodsInfo);
    }

    @Override
    public int modifyGoodsInfo(GoodsInfo goodsInfo) throws Exception {
        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }

    @Override
    public int modifyGoodsInfoState(GoodsInfo goodsInfo) throws Exception {
        return goodsInfoMapper.updateGoodsInfoState(goodsInfo);
    }
}
