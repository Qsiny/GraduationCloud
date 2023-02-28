package com.qsiny.service;

import com.qsiny.dto.DepartmentRequestDto;
import com.qsiny.entity.Department;
import com.qsiny.po.PageResult;

import java.util.List;

public interface DepartmentService {

    Integer addDepartment(Department department);

    List<String> searchDeanByDeanName(String deanName);

    List<String> searchCounselors(String counselorName);

    List<Department> searchDepartmentList();

    PageResult<Department> searchDepartmentList(DepartmentRequestDto departmentRequestDto);

}
