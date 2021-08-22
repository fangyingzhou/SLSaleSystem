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

    public List<DataDictionary> selectDataDictionaryByCategory();

    public int updateDataDictionary(DataDictionary dataDictionary);

    public int typeCodeOrValueNameIsExist(DataDictionary dataDictionary);

    public int deleteDataDictionary(DataDictionary dataDictionary);

    public int selectMaxValueId(DataDictionary dataDictionary);

    public int insertDataDictionary(DataDictionary dataDictionary);

    public List<DataDictionary> selectDataDictionaryListTypeCodeNotIn(DataDictionary dataDictionary);

    public int updateDataDictionaryByTypeCode(DataDictionary dataDictionary);
}
