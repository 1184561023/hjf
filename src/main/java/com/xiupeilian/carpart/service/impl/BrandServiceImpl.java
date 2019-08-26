package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.BrandMapper;
import com.xiupeilian.carpart.mapper.PartsMapper;
import com.xiupeilian.carpart.mapper.PrimeMapper;
import com.xiupeilian.carpart.model.Brand;
import com.xiupeilian.carpart.model.Parts;
import com.xiupeilian.carpart.model.Prime;
import com.xiupeilian.carpart.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 汽车配件接口实现类
 * @author: hejiafei
 * @date: 2019/08/26 14:52
 */

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandMapper brandMapper;
    @Autowired
    PartsMapper partsMapper;
    @Autowired
    PrimeMapper primeMapper;

    @Override
    public List<Brand> findBrandAll() {
        return brandMapper.findBrandAll();
    }

    @Override
    public List<Parts> findPartsAll() {
        return partsMapper.findPartsAll();
    }

    @Override
    public List<Prime> findPrimeAll() {
        return primeMapper.findPrimeAll();
    }
}
