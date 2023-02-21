package com.qsiny.service.impl;

import com.qsiny.entity.Class;
import com.qsiny.mapper.ClassMapper;
import com.qsiny.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class ClassServiceImpl implements ClassService {

    @Resource
    private ClassMapper classMapper;
    @Override
    public void addClass(Class classInfo) {
        classInfo.setUpdateData(new Date());
        classMapper.insert(classInfo);
        log.info("插入成功");
    }
}
