package com.qsiny.mapper;

import com.qsiny.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author qinshouyuan
* @description 针对表【sys_department(学院表)】的数据库操作Mapper
* @createDate 2023-02-14 20:45:04
* @Entity com.qsiny.entity.Department
*/
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

}




