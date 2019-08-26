package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.City;

import java.util.List;

/**
 * @description: 地址相关
 * @author: hejiafei
 * @date: 2019/08/26 14:27
 */
public interface CityService {

    List<City> findCitysByParentId(Integer parentId);
}