package com.slsale.controller;

import com.slsale.commons.Constant;
import com.slsale.commons.PageSupport;
import com.slsale.pojo.GoodsInfo;
import com.slsale.service.GoodsInfoService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther:
 * @Date:2021/5/12
 * @Description:com.slsale.controller
 * @Version:1.0
 */
@Controller
public class GoodsInfoController {
    @Autowired
    private GoodsInfoService goodsInfoService;

    @RequestMapping(value="/backend/goodslist.html")
    public Object goodsList(HttpSession session, Model model,
                            @RequestParam(value="s_goodsName",required = false)String s_goodsName){

        Map<String,Object> baseModel = (Map<String, Object>) session.getAttribute(Constant.SESSION_BASE_MODE);
        if(baseModel == null){
            return "redirect:/";
        }
        GoodsInfo goodsInfo = new GoodsInfo();
        if(!StringUtils.isEmpty(s_goodsName)){
            goodsInfo.setGoodsName(s_goodsName);
        }
        goodsInfo.setState(1); //上架
        goodsInfo.setStarNum(0);
        goodsInfo.setPageSize(1000);
        List<GoodsInfo> goodsInfoList = null;
        try {
            goodsInfoList = goodsInfoService.getGoodsInfoListByPage(goodsInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAllAttributes(baseModel);
        model.addAttribute("goodsInfoList",goodsInfoList);
        model.addAttribute("s_goodsName",s_goodsName);
        return "/backend/goodslist";
    }
    @RequestMapping(value = "/backend/goodsinfolist.html")
    private Object showGoodsInfo(HttpSession session, Model model,
                                 @RequestParam(value="s_goodsName", required = false) String s_goodsName,
                                 @RequestParam(value = "s_state",required = false)Integer s_state,
                                 @RequestParam(value = "currentpage",required = false)Integer currentpage){

        Map<String,Object> baseModel = (Map<String, Object>) session.getAttribute(Constant.SESSION_BASE_MODE);
        if(baseModel == null){
            return "redirect:/";
        }else{
            List<GoodsInfo> goodsInfoList = null;
            GoodsInfo goodsInfo = new GoodsInfo();

            if(!StringUtils.isEmpty(s_goodsName)){
                goodsInfo.setGoodsName(s_goodsName);
            }
            if(s_state != null){
                goodsInfo.setState(s_state);
            }
            PageSupport page = new PageSupport();
            try {
                int totalCount = goodsInfoService.getTotalCount(goodsInfo);

                if(totalCount > 0){

                    page.setTotalCount(totalCount);

                    if(currentpage != null){   //页面传递过来的页码
                        page.setPageNo(currentpage);
                    }

                    if (page.getPageNo() <= 0){
                        page.setPageNo(1);
                    }

                    if(page.getPageNo() > page.getPageCount()){
                        page.setPageNo(page.getPageCount());
                    }
                    goodsInfo.setStarNum((page.getPageNo()-1)*page.getPageSize());
                    goodsInfo.setPageSize(page.getPageSize());

                    goodsInfoList = goodsInfoService.getGoodsInfoListByPage(goodsInfo);
                    page.setItems(goodsInfoList);
                }else{
                    page.setItems(null);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("page",page);
            model.addAttribute("s_goodsName",s_goodsName);
            model.addAttribute("s_state",s_state);
            model.addAllAttributes(baseModel);
            return "/backend/goodsinfolist";
        }
    }
    @RequestMapping(value = "/backend/getgoodsinfo.html",produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public Object getGoodsInfo(@RequestParam(value = "m_id",required = false)String id){
        if(id == null || id.equals("")){
            return "nodata";
        }else{
            GoodsInfo _goodsInfo = null;
            try {
                GoodsInfo goodsInfo = new GoodsInfo();
                goodsInfo.setId(Integer.valueOf(id));
                 _goodsInfo = goodsInfoService.getGoodsInfoById(goodsInfo);
                JSONObject jsonObject = JSONObject.fromObject(_goodsInfo);
                return jsonObject.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }
    }
    @RequestMapping(value = "/backend/goodssnisexist.html",produces={"text/html;charset=utf-8"})
    @ResponseBody
    public Object goodsSNisExist(@RequestParam(value = "goodsSN",required = false)String goodsSN,
                                  @RequestParam(value="id",required=false)String id){
        String result = "";
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setGoodsSN(goodsSN);
        if(!id.equals(-1)){
            try {
                goodsInfo.setId(Integer.valueOf(id));
                int index = goodsInfoService.goodsSNIsExist(goodsInfo);
                if(index ==0){
                    result = "only";
                }else{
                    result = "rename";
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = "failed";
            }
        }
        return result;
    }

    @RequestMapping(value="/backend/modifygoodsinfo.html")
    public Object modifyGoodsInfo(GoodsInfo goodsInfo){
        try {
            goodsInfo.setLastUpdateTime(new Date());
            int index = goodsInfoService.modifyGoodsInfo(goodsInfo);
            if(index >0){
                return "redirect:/backend/goodsinfolist.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @RequestMapping(value="/backend/modifystate.html",produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public Object modifyState(@RequestParam(value ="goodsInfo",required = false)String goodsInfo){
        if(goodsInfo == null || goodsInfo.equals("")){
            return "nodata";
        }else{
            try {
                JSONObject jsonObject = JSONObject.fromObject(goodsInfo);
                GoodsInfo _goodsInfo = (GoodsInfo) JSONObject.toBean(jsonObject,GoodsInfo.class);
                int index = goodsInfoService.modifyGoodsInfoState(_goodsInfo);
                if(index >0){
                    return "success";
                }else{
                    return "failed";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

    }
}
