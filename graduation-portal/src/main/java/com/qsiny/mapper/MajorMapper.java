package com.qsiny.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qsiny.dto.GradeVo;
import com.qsiny.entity.Major;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author qinshouyuan
* @description 针对表【sys_major(部门表)】的数据库操作Mapper
* @createDate 2023-02-20 20:11:42
* @Entity generator.domain.com.qsiny.entity.Major
*/
@Mapper
public interface MajorMapper extends BaseMapper<Major> {

    List<GradeVo> searchGradeByDepartmentAndMajor(@Param("departmentCode") String departmentCode, @Param("majorCode") String majorCode);
}




