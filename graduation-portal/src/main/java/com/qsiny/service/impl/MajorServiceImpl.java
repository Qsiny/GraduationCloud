package com.qsiny.service.impl;

import com.qsiny.entity.Major;
import com.qsiny.mapper.MajorMapper;
import com.qsiny.service.MajorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class MajorServiceImpl implements MajorService {

    @Resource
    private MajorMapper majorMapper;

    @Override
    public void addMajor(Major major) {
        major.setCreateData(new Date());
        major.setUpdateData(new Date());
        major.setUpdateId(major.getCreateId());
        majorMapper.insert(major);



    }
}
