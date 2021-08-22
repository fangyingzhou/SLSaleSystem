package com.slsale.controller;

import com.slsale.pojo.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import com.slsale.commons.Constant;
import com.slsale.commons.PageSupport;
import com.slsale.pojo.DataDictionary;
import com.slsale.pojo.GoodsPack;
import com.slsale.pojo.GoodsPackAffiliated;
import com.slsale.service.DataDictionaryService;
import com.slsale.service.GoodsPackAffiliatedService;
import com.slsale.service.GoodsPackService;
import net.sf.json.JSONSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther:
 * @Date:2021/5/14
 * @Description:com.slsale.controller
 * @Version:1.0
 */
@Controller
public class GoodsPackController {

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Autowired
    private GoodsPackService goodsPackService;

    @Autowired
    private GoodsPackAffiliatedService goodsPackAffiliatedService;

    @RequestMapping(value = "/backend/goodspacklist.html")
    public Object showGoodsPack(HttpSession session, Model model,
                                @RequestParam(value="s_goodsPackName",required = false)String s_goodsPackName,
                                @RequestParam(value = "s_typeId",required = false)String s_typeId,
                                @RequestParam(value = "s_state",required = false)String s_state,
                                @RequestParam(value = "currentpage",required = false)Integer currentpage
                                ){
        Map<String,Object> baseModel =(Map<String, Object>)session.getAttribute(Constant.SESSION_BASE_MODE);
        if(baseModel == null){
            return "redirect:/";
        }else{
            DataDictionary dataDictionary = new DataDictionary();
            dataDictionary.setTypeCode("PACK_TYPE");
            List<DataDictionary> packTypeList = null;
            PageSupport page = new PageSupport();
            GoodsPack goodsPack = new GoodsPack();

            if(!StringUtils.isEmpty(s_goodsPackName)){
                goodsPack.setGoodsPackName(s_goodsPackName);
            }
            if(!StringUtils.isEmpty(s_typeId)){
                goodsPack.setTypeId(Integer.valueOf(s_typeId));
            }
            if (!StringUtils.isEmpty(s_state)){
                goodsPack.setState(Integer.valueOf(s_state));
            }
            try {
                packTypeList = dataDictionaryService.getDataDictionaryList(dataDictionary);
                int totalCount = goodsPackService.getTotalCount(goodsPack);

                if(totalCount > 0){

                    page.setTotalCount(totalCount);

                    if(currentpage != null){
                        page.setPageNo(currentpage);
                    }

                    if(page.getPageNo() <= 0){
                        page.setPageNo(1);
                    }

                    if(page.getPageNo() > page.getPageCount()){
                        page.setPageNo(page.getPageCount());
                    }
                    goodsPack.setStarNum((page.getPageNo()-1)*page.getPageSize());
                    goodsPack.setPageSize(page.getPageSize());

                    List<GoodsPack> goodsPackList = goodsPackService.getGoodsPackListByPage(goodsPack);
                    page.setItems(goodsPackList);
                }else{
                    page.setItems(null);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAllAttributes(baseModel);
            model.addAttribute("packTypeList",packTypeList);
            model.addAttribute("page",page);
            model.addAttribute("s_goodsPackName",s_goodsPackName);
            model.addAttribute("s_typeId",s_typeId);
            model.addAttribute("s_state",s_state);

            return "/backend/goodspacklist";
        }
    }

    @RequestMapping(value = "/backend/modifygoodspack.html")
    public Object modifyGoodsPack(HttpSession session,Model model,
                                  @RequestParam(value = "id",required = false)String id){

        Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constant.SESSION_BASE_MODE);
        if(baseModel == null){
            return "redirect:/";
        }else{
            if(id=="" || id.equals("")){
                return "redirect:/backend/goodspacklist.html";
            }
            DataDictionary dataDictionary = new DataDictionary();
            dataDictionary.setTypeCode("PACK_TYPE");
            List<DataDictionary> dataList = null;

            GoodsPackAffiliated goodsPackAffiliated = new GoodsPackAffiliated();
            goodsPackAffiliated.setGoodsPackId(Integer.valueOf(id));
            List<GoodsPackAffiliated> gpaList = null;

            //根据传递过来的Id获取该套餐信息
            GoodsPack goodsPack = new GoodsPack();
            goodsPack.setId(Integer.valueOf(id));
            try {
                dataList = dataDictionaryService.getDataDictionaryList(dataDictionary);
                gpaList = goodsPackAffiliatedService.getGoodsPackAffiliatedByPackId(goodsPackAffiliated);
                goodsPack = goodsPackService.getGoodsPackById(goodsPack);
            } catch (Exception e) {
                e.printStackTrace();
            }

            model.addAllAttributes(baseModel);
            model.addAttribute("packTypeList",dataList);
            model.addAttribute("gpaList",gpaList);
            model.addAttribute("goodsPack",goodsPack);
            return "/backend/modifygoodspack";
        }
    }

    /**
     * 功能描述：将JSON对象转换成集合
     */
    private <T> List<T> getJavaCollection(T clazz, String jsons) {
        List<T> objs=null;
        JSONArray jsonArray=(JSONArray)JSONSerializer.toJSON(jsons);
        if(jsonArray.size() > 1){
            objs=new ArrayList<T>();
            List list=(List)JSONSerializer.toJava(jsonArray);
            for(int i = 0; i < list.size()-1; i++){
                JSONObject jsonObject=JSONObject.fromObject(list.get(i));
                T obj=(T)JSONObject.toBean(jsonObject, clazz.getClass());
                objs.add(obj);
            }
        }
        return objs;
    }

    @RequestMapping(value="/backend/savemodifygoodspack.html")
    public Object modifyGoodsPack(HttpSession session,GoodsPack goodsPack){
        Map<String,Object> baseModel =(Map<String, Object>) session.getAttribute(Constant.SESSION_BASE_MODE);
        User user =(User) session.getAttribute(Constant.SESSION_USER);
        System.out.println("=="+goodsPack.getGoodsJson());
        if(baseModel == null){
            return "redirect:/";
        }
        try {
            List<GoodsPackAffiliated> gpaList = this.getJavaCollection(new GoodsPackAffiliated(), goodsPack.getGoodsJson());
            goodsPack.setLastUpdateTime(new Date());
            goodsPackService.hl_modifyGoodsPack(goodsPack,gpaList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/backend/goodspacklist.html";
    }

    @RequestMapping(value = "/backend/modifystate.html")
    @ResponseBody
    public Object modifyState(@RequestParam(value="jsonState",required = false)String jsonState){
        System.out.println("========"+jsonState);
        if(StringUtils.isEmpty(jsonState)){
            return "nodata";
        }else{
            try {
                JSONObject jsonObject = JSONObject.fromObject(jsonState);
                GoodsPack _goodsPack = (GoodsPack) JSONObject.toBean(jsonObject,GoodsPack.class);
                goodsPackService.modifyGoodsPack(_goodsPack);
                return "success";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }
    }

    @RequestMapping(value = "/backend/addgoodspack.html")
    public Object addGoodsPack(HttpSession session,Model model){

       Map<String,Object> baseModel = (Map<String, Object>) session.getAttribute(Constant.SESSION_BASE_MODE);
       if(baseModel == null){
           return "redirect:/";
       }else{
           DataDictionary dataDictionary = new DataDictionary();
           dataDictionary.setTypeCode("PACK_TYPE");
           List<DataDictionary> packTypeList = null;
           try {
               packTypeList = dataDictionaryService.getDataDictionaryList(dataDictionary);
           } catch (Exception e) {
               e.printStackTrace();
           }
           model.addAttribute("packTypeList",packTypeList);
           model.addAllAttributes(baseModel);
           return "/backend/addgoodspack";
       }
    }
}
