package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Dymsn;
import com.xiupeilian.carpart.model.Notice;

import java.util.List;

/**
 * @description: 动态消息
 * @author: hejiafei
 * @date: 2019/08/21 20:53
 */
public interface DymsnService {
    public List<Dymsn> findDymsns();

    List<Notice> findNotice();
}