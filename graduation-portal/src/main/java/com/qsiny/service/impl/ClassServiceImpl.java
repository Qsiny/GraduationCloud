package com.qsiny.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qsiny.dto.ClassRequestDto;
import com.qsiny.entity.Class;
import com.qsiny.entity.ResponseResult;
import com.qsiny.mapper.ClassMapper;
import com.qsiny.po.PageResult;
import com.qsiny.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    @Override
    public PageResult<Class> searchClassList(ClassRequestDto classRequestDto) {
        PageResult<Class> result = new PageResult<>();
        Page<Class> page = new Page<>(classRequestDto.getPage(), classRequestDto.getSize(), true);
        Page<Class> selectPage = new Page<>();
        if (StringUtils.hasText(classRequestDto.getClassName())) {
            selectPage = classMapper.selectPage(page, new QueryWrapper<Class>()
                    .lambda()
                    .eq(Class::getClassName, classRequestDto.getClassName()));
        }else{
            selectPage = classMapper.selectPage(page,null);
        }
        result.setResult(selectPage.getRecords());
        result.setTotal(selectPage.getTotal());
        return result;
    }

    @Override
    public ResponseResult<List<Class>> searchClassByDepartmentAndMajorAndGrade(String departmentCode, String majorCode, String gradeCode) {
        List<Class> classList = classMapper.searchClassByDepartmentAndMajorAndGrade(departmentCode,majorCode,gradeCode);
        return null;
    }
}
