package com.slsale.service;

import com.slsale.pojo.DataDictionary;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/4/20
 * @Description:com.slsale.service
 * @Version:1.0
 */
public interface DataDictionaryService {

    //根据类型代码 获取该类型下的所有对象
    public List<DataDictionary> getDataDictionaryList(DataDictionary dataDictionary) throws Exception;

    //根据typeCode进行分组
    public List<DataDictionary> getDataDictionaryByCategory() throws Exception;

    //
    public int modifyDataDictionary(DataDictionary dataDictionary) throws Exception;

    public int typeCodeOrValueNameIsExist(DataDictionary dataDictionary) throws Exception;

    public int delDataDictionary(DataDictionary dataDictionary) throws Exception;

    public int getMaxValueId(DataDictionary dataDictionary) throws Exception;

    public int addDataDictionary(DataDictionary dataDictionary) throws Exception;

    public List<DataDictionary> getDataDictionaryListTypeCodeNotIn(DataDictionary dataDictionary) throws Exception;

    public int modifyDataDictionaryByTypeCode(DataDictionary dataDictionary) throws Exception;
}
