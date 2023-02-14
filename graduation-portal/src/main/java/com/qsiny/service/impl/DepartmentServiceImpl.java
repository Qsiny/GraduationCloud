package com.qsiny.service.impl;

import com.qsiny.entity.Department;
import com.qsiny.mapper.DepartmentMapper;
import com.qsiny.mapper.UserMapper;
import com.qsiny.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private UserMapper userMapper;
    @Override
    public Integer addDepartment(Department department) {

//        departmentMapper.insert(department);
        return 1;
    }
}
