package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.CityMapper;
import com.xiupeilian.carpart.model.City;
import com.xiupeilian.carpart.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 地址接口实现类
 * @author: hejiafei
 * @date: 2019/08/26 14:27
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityMapper cityMapper;

    @Override
    public List<City> findCitysByParentId(Integer parentId) {
        return cityMapper.findCitysByParentId(parentId);
    }
}
