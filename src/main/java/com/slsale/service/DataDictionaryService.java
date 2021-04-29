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

    public List<DataDictionary> getDataDictionaryList(DataDictionary dataDictionary) throws Exception;
}
