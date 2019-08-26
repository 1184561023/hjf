package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Brand;
import com.xiupeilian.carpart.model.Parts;
import com.xiupeilian.carpart.model.Prime;

import java.util.List;

/**
 * @description: 汽车配件等
 * @author: hejiafei
 * @date: 2019/08/26 14:52
 */
public interface BrandService {
    List<Brand> findBrandAll();

    List<Parts> findPartsAll();

    List<Prime> findPrimeAll();
}