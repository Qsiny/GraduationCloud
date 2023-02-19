package com.qsiny.service;

import com.qsiny.entity.Department;

import java.util.List;

public interface DepartmentService {

    Integer addDepartment(Department department);

    List<String> searchDeanByDeanName(String deanName);
}
