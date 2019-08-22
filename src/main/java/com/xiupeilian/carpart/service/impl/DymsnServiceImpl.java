package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.DymsnMapper;
import com.xiupeilian.carpart.mapper.NoticeMapper;
import com.xiupeilian.carpart.model.Dymsn;
import com.xiupeilian.carpart.model.Notice;
import com.xiupeilian.carpart.service.DymsnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 动态消息实现类
 * @author: hejiafei
 * @date: 2019/08/21 20:54
 */

@Service
public class DymsnServiceImpl implements DymsnService {
    @Autowired
    DymsnMapper dymsnMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public List<Dymsn> findDymsns() {
        return dymsnMapper.findDymsns();
    }

    @Override
    public List<Notice> findNotice() {
        return noticeMapper.findNotice();
    }
}
