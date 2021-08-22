package com.slsale.controller;

import com.slsale.commons.Constant;
import com.slsale.pojo.DataDictionary;
import com.slsale.service.DataDictionaryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Auther:
 * @Date:2021/4/29
 * @Description:com.slsale.controller
 * @Version:1.0
 */
@Controller
public class DataDictionaryController {

    @Autowired
    private DataDictionaryService dataDictionaryService;

    /**
     * 功能描述：根据typeCode进行分组 将分组后的结果回显到前台页面
     * select * from data_dictionary group by typeCod order by id asc
     *
     * 请求链接地址只有子菜单才拥有 一级主菜单是没有的 第一次登录成功的时候 存放到redis中 后续从redis中获取
     */
    @RequestMapping(value="/backend/dicmanage.html")
    public Object showDataDictionary(HttpSession session, Model model){

        //当前Map中存储了当前登录用户 以及 当前用户所拥有的菜单集合
        Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constant.SESSION_BASE_MODE);
        if(baseModel == null){
            return "redirect:/";
        }else{
            List<DataDictionary> dataList = null;
            try {
                dataList = dataDictionaryService.getDataDictionaryByCategory();
            } catch (Exception e) {
                e.printStackTrace();
                dataList = null;
            }
            model.addAttribute("dataList",dataList);
            model.addAllAttributes(baseModel);
        }
        return "/backend/dicmanage";
    }

    /**
     * 功能描述：根据typeCode 获取该类型下所有的证件类型 并将该集合回显到前台页面中
     */
    @RequestMapping(value="/backend/getJsonDic.html",produces={"text/html;charset=utf-8"})
    @ResponseBody
    public Object getDicJsonDic(@RequestParam(value="typeCode",required = false)String typeCode){
        if (typeCode == null || typeCode.equals("")){
            return "nodata";
        }else{
            List<DataDictionary> dataList = null;
            try {
                //根据typeCode 获取该类型下的所有数据
                DataDictionary dataDictionary = new DataDictionary();
                dataDictionary.setTypeCode(typeCode);
                dataList =  dataDictionaryService.getDataDictionaryList(dataDictionary);
                if (dataList != null){
                    JSONArray jsonArray = JSONArray.fromObject(dataList);
                    String jsonString = jsonArray.toString();
                    return jsonString;
                }else{
                    return "nodata";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }
    }

    /**
     * 功能描述：修改证件对象 本质就是修改typeCode下的valueName
     * 同一类型代码下的 数据ID不能重复
     * select count(1) from data_dictionary where typeCode = #{typeCode} and typeName = #{typeName} and id != #{id}
     */
    @RequestMapping(value = "/backend/modifydic.html")
    @ResponseBody
    public Object modifyDic(@RequestParam(value = "dicJson",required = false)String dicJson){
        if(dicJson == null || dicJson ==""){
            return "nodata";
        }else{
            try {
                JSONObject jsonObject = JSONObject.fromObject(dicJson);
                DataDictionary dataDictionary = (DataDictionary) JSONObject.toBean(jsonObject,DataDictionary.class);
                //同一类型代码下的 数值ID不能够重复
                int index = dataDictionaryService.typeCodeOrValueNameIsExist(dataDictionary);
                if(index > 0){
                    return "rename";
                }
                dataDictionaryService.modifyDataDictionary(dataDictionary);
                return "success";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }
    }

    @RequestMapping(value = "/backend/deldic.html")
    @ResponseBody
    public Object delDic(@RequestParam(value="id",required = false)Integer id){
        if(id ==null || id <0){
            return "nodata";
        }else{
            try {
                DataDictionary dataDictionary = new DataDictionary();
                dataDictionary.setId(id);
                dataDictionaryService.delDataDictionary(dataDictionary);
                return "success";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }
    }

    @RequestMapping(value = "/backend/addSubDic.html",produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public Object addDic(@RequestParam(value = "jsonDic",required = false)String jsonDic){
        if(jsonDic ==null || jsonDic.equals("")){
            return  "nodata";
        }else{
            try {
                JSONObject jsonObject = JSONObject.fromObject(jsonDic);
                DataDictionary dataDictionary =(DataDictionary) JSONObject.toBean(jsonObject,DataDictionary.class);
                int index = dataDictionaryService.typeCodeOrValueNameIsExist(dataDictionary);
                if(index > 0){
                   return "rename";
                }
                Integer valueId =  dataDictionaryService.getMaxValueId(dataDictionary)+1;
                dataDictionary.setValueId(valueId);
                dataDictionaryService.addDataDictionary(dataDictionary);
                return "success";

            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }
    }

    /**
     * 功能描述：
     */
    @RequestMapping(value = "/backend/typecodeisexist.html")
    @ResponseBody
    public Object typeCodeIsExist(@RequestParam(value = "jsonDic",required = false)String jsonDic){
        if(jsonDic == null || jsonDic.equals("")){
            return "nodata";
        }else{
            try {
                JSONObject jsonObject = JSONObject.fromObject(jsonDic);
                DataDictionary dataDictionary =(DataDictionary) JSONObject.toBean(jsonObject,DataDictionary.class);
                int index = dataDictionaryService.typeCodeOrValueNameIsExist(dataDictionary);
                if(index >0){
                    return "rename";
                }
                return "only";

            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }
    }
    /**
     * 功能描述：
     */
    @RequestMapping(value = "/backend/modifymaindic.html")
    @ResponseBody
    public Object modifyMainDic(@RequestParam(value = "oldDic",required = false)String oldDic,
                                @RequestParam(value = "newDic",required = false)String newDic){
        if(oldDic == null || oldDic.equals("") || newDic == null || newDic.equals("")){
            return "nodata";
        }else{
            List<DataDictionary> dataList = null;
            try {
                JSONObject oldDicJson = JSONObject.fromObject(oldDic);
                JSONObject newDicJson = JSONObject.fromObject(newDic);

                DataDictionary oldDataDictionary = (DataDictionary) JSONObject.toBean(oldDicJson,DataDictionary.class);
                DataDictionary newDataDictionary = (DataDictionary) JSONObject.toBean(newDicJson,DataDictionary.class);

                DataDictionary _dataDictionary = new DataDictionary();

                //typeCode为新传递过来的typeCode 并且新传递过来的typeCode 不在原数据库中
                _dataDictionary.setTypeName(newDataDictionary.getTypeCode());
                _dataDictionary.setTypeCode(oldDataDictionary.getTypeCode());

                //
                dataList = dataDictionaryService.getDataDictionaryListTypeCodeNotIn(_dataDictionary);

                if(dataList != null && dataList.size() >0){
                    return "rename";
                }else{
                    newDataDictionary.setValueName(oldDataDictionary.getTypeCode());
                    dataDictionaryService.modifyDataDictionaryByTypeCode(newDataDictionary);
                    return "success";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }
    }
}
