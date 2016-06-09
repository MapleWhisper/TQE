package com.tqe.service;

import com.tqe.po.BaseConfig;
import org.springframework.stereotype.Service;

@Service
public class BaseConfigServiceImpl extends BaseService<BaseConfig>{

    public BaseConfig getByKey(String key){
        return baseConfigDao.getByKey(key);
    }

    public String getConfigValue(String key){
        return baseConfigDao.getConfigValue(key);
    }


}
