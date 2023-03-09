package com.qsiny.service;

import com.qsiny.dto.ClassRequestDto;
import com.qsiny.entity.Class;
import com.qsiny.entity.ResponseResult;
import com.qsiny.po.PageResult;

import java.util.List;

public interface ClassService {
     void addClass(Class classInfo);

    PageResult<Class> searchClassList(ClassRequestDto classRequestDto);

    ResponseResult<List<Class>> searchClassByDepartmentAndMajorAndGrade(String departmentCode, String majorCode, String gradeCode);
}
