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

    @Override
    public List<DataDictionary> getDataDictionaryList(DataDictionary dataDictionary) throws Exception {
        return dataDictionaryMapper.selectDataDictionaryList(dataDictionary);
    }
}
