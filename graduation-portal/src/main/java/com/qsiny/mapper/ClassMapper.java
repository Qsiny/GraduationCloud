package com.qsiny.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qsiny.entity.Class;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author qinshouyuan
* @description 针对表【sys_class(班级表)】的数据库操作Mapper
* @createDate 2023-02-21 19:12:19
* @Entity .entity.Class
*/

@Mapper
public interface ClassMapper extends BaseMapper<Class> {

    List<Class> searchClassByDepartmentAndMajorAndGrade(@Param("departmentCode") String departmentCode,@Param("majorCode")  String majorCode, @Param("gradeCode") String gradeCode);
}




