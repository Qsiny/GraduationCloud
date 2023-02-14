package com.qsiny.controller;

import com.qsiny.constant.ResponseStatusCode;
import com.qsiny.entity.Department;
import com.qsiny.entity.ResponseResult;
import com.qsiny.service.DepartmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;


    @PostMapping("/addDepartment")
    public ResponseResult<Void> addDepartment(@RequestBody Department department){
        System.out.println(department);
        departmentService.addDepartment(department);
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功");
    }


}
