package com.slsale.mapper.datadictionary;

import com.slsale.pojo.DataDictionary;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/4/20
 * @Description:com.slsale.mapper
 * @Version:1.0
 */
public interface DataDictionaryMapper {

    public List<DataDictionary> selectDataDictionaryList(DataDictionary dataDictionary);
}
