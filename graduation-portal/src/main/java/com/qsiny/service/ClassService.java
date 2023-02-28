package com.qsiny.service;

import com.qsiny.dto.ClassRequestDto;
import com.qsiny.entity.Class;
import com.qsiny.po.PageResult;

public interface ClassService {
     void addClass(Class classInfo);

    PageResult<Class> searchClassList(ClassRequestDto classRequestDto);
}
