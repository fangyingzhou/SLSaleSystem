package com.slsale.service.impl;

import com.slsale.mapper.datadictionary.DataDictionaryMapper;
import com.slsale.pojo.DataDictionary;
import com.slsale.service.DataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther:
 * @Date:2021/4/20
 * @Description:com.slsale.service.impl
 * @Version:1.0
 */
@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {

    @Autowired
    private DataDictionaryMapper dataDictionaryMapper;

    /**
     * 功能描述：select * from data_dictionary where typeCode = #{typeCode}
     */
    @Override
    public List<DataDictionary> getDataDictionaryList(DataDictionary dataDictionary) throws Exception {
        return dataDictionaryMapper.selectDataDictionaryList(dataDictionary);
    }

    /**
     * 功能描述：select * from data_dictionary group by typeCode order by id asc
     */
    @Override
    public List<DataDictionary> getDataDictionaryByCategory() throws Exception {
        return dataDictionaryMapper.selectDataDictionaryByCategory();
    }

    /**
     * 功能描述：
     */
    @Override
    public int modifyDataDictionary(DataDictionary dataDictionary) throws Exception {
        return dataDictionaryMapper.updateDataDictionary(dataDictionary);
    }

    /**
     * 功能描述：
     */
    @Override
    public int typeCodeOrValueNameIsExist(DataDictionary dataDictionary) throws Exception {
        return dataDictionaryMapper.typeCodeOrValueNameIsExist(dataDictionary);
    }

    /**
     * 功能描述：
     */
    @Override
    public int delDataDictionary(DataDictionary dataDictionary) throws Exception {
        return dataDictionaryMapper.deleteDataDictionary(dataDictionary);
    }

    /**
     * 功能描述：select max(valueId) from data_dictionary where typeCode = #{typeCode}
     */
    @Override
    public int getMaxValueId(DataDictionary dataDictionary) throws Exception {
        return dataDictionaryMapper.selectMaxValueId(dataDictionary);
    }

    /**
     * 功能描述：
     */
    @Override
    public int addDataDictionary(DataDictionary dataDictionary) throws Exception {
        return dataDictionaryMapper.insertDataDictionary(dataDictionary);
    }

    /**
     * 功能描述：
     */
    @Override
    public List<DataDictionary> getDataDictionaryListTypeCodeNotIn(DataDictionary dataDictionary) throws Exception {
        return dataDictionaryMapper.selectDataDictionaryListTypeCodeNotIn(dataDictionary);
    }

    @Override
    public int modifyDataDictionaryByTypeCode(DataDictionary dataDictionary) throws Exception {
        return dataDictionaryMapper.updateDataDictionaryByTypeCode(dataDictionary);
    }
}
