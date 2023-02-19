package com.qsiny.controller;

import com.qsiny.constant.ResponseStatusCode;
import com.qsiny.entity.Department;
import com.qsiny.entity.ResponseResult;
import com.qsiny.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/collage")
@Slf4j
public class CollageController {

    @Resource
    private DepartmentService departmentService;

    @PostMapping("/searchDean")
    public ResponseResult<List<String>> searchDean(String deanName){
        log.info("搜索院长：参数：{}",deanName);
        if(!StringUtils.hasText(deanName)){
            return ResponseResult.build(ResponseStatusCode.SERVER_ERROR,"院长名称为空");
        }
        List<String> deans = departmentService.searchDeanByDeanName(deanName);
        log.info("搜索院长，结果:{}",deans);
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功",deans);
    }

    @PostMapping("/addDepartment")
    public ResponseResult<Void> addDepartment(@RequestBody Department department){
        log.info("添加学院{}",department);
        departmentService.addDepartment(department);
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功");
    }
}
