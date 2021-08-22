package com.slsale.service.impl;

import com.slsale.mapper.goodspack.GoodsPackMapper;
import com.slsale.mapper.goodspackaffiliated.GoodsPackAffiliatedMapper;
import com.slsale.pojo.GoodsPack;
import com.slsale.pojo.GoodsPackAffiliated;
import com.slsale.service.GoodsPackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/5/14
 * @Description:com.slsale.service.impl
 * @Version:1.0
 */
@Service
public class GoodsPackServiceImpl implements GoodsPackService {

    @Autowired
    private GoodsPackMapper goodsPackMapper;

    @Autowired
    private GoodsPackAffiliatedMapper goodsPackAffiliatedMapper;

    @Override
    public List<GoodsPack> getGoodsPackListByPage(GoodsPack goodsPack) {
        return goodsPackMapper.selectGoodsPackListByPage(goodsPack);
    }

    @Override
    public int getTotalCount(GoodsPack goodsPack) {

        return goodsPackMapper.selectTotalCount(goodsPack);
    }

    @Override
    public GoodsPack getGoodsPackById(GoodsPack goodsPack) throws Exception {
        return goodsPackMapper.selectGoodsPackById(goodsPack);
    }

    @Override
    public int modifyGoodsPack(GoodsPack goodsPack) throws Exception {
        return goodsPackMapper.updateGoodsPack(goodsPack);
    }

    @Override
    public boolean hl_modifyGoodsPack(GoodsPack goodsPack, List<GoodsPackAffiliated> gpaList) throws Exception {
        goodsPackMapper.updateGoodsPack(goodsPack);
        int goodsPackId = goodsPack.getId();
        GoodsPackAffiliated goodsPackAffiliated = new GoodsPackAffiliated();
        goodsPackAffiliated.setGoodsPackId(goodsPackId);
        //根据套餐ID 删除套餐_商品表中的所有商品  类似于 根据角色ID 删除权限_功能表中的所有功能
        goodsPackAffiliatedMapper.deleteGoodsPackAffiliated(goodsPackAffiliated);
        if(gpaList != null){
            for(int i=0;i<gpaList.size();i++){
                if(gpaList.get(i) != null){
                    gpaList.get(i).setGoodsPackId(goodsPackId);
                    //传递过来的GoodsPackAffiliated已经包含了goodsInfoId 和 goodsNum
                    goodsPackAffiliatedMapper.addGoodsPackAffiliated(gpaList.get(i));
                }
            }
        }
        return true;
    }
}
