package com.qsiny.controller;

import com.qsiny.constant.ResponseStatusCode;
import com.qsiny.dto.ClassRequestDto;
import com.qsiny.dto.DepartmentRequestDto;
import com.qsiny.dto.MajorRequestDto;
import com.qsiny.entity.Class;
import com.qsiny.entity.Department;
import com.qsiny.entity.Major;
import com.qsiny.entity.ResponseResult;
import com.qsiny.po.PageResult;
import com.qsiny.service.ClassService;
import com.qsiny.service.DepartmentService;
import com.qsiny.service.MajorService;
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

    @Resource
    private MajorService majorService;

    @Resource
    private ClassService classService;

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

    @PostMapping("/searchCounselor")
    public ResponseResult<List<String>> searchCounselor(String counselorName){
        log.info("搜索辅导员：参数：{}",counselorName);
        if(!StringUtils.hasText(counselorName)){
            return ResponseResult.build(ResponseStatusCode.SERVER_ERROR,"辅导员名称为空");
        }
        List<String> counselors = departmentService.searchCounselors(counselorName);
        log.info("搜索辅导员，结果:{}",counselors);
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功",counselors);
    }

    @PostMapping("/addMajor")
    public ResponseResult<Void> addMajor(@RequestBody Major major){
        log.info("添加学院{}",major);
        majorService.addMajor(major);
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功");
    }

    @PostMapping("/searchDepartmentList")
    public ResponseResult<List<Department>> searchDepartmentList(){
        List<Department> departments = departmentService.searchDepartmentList();
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功",departments);
    }

    @PostMapping("/searchMajor")
    public ResponseResult<List<Major>> searchMajor(String majorName){
        log.info("搜索专业：参数：{}",majorName);
        if(!StringUtils.hasText(majorName)){
            return ResponseResult.build(ResponseStatusCode.SERVER_ERROR,"辅导员名称为空");
        }
        List<Major> majors = majorService.searchMajor(majorName);
        log.info("搜索辅导员，结果:{}",majors);
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功",majors);
    }

    @PostMapping("/addClass")
    public ResponseResult<Void> addClass(@RequestBody Class classInfo){
        if(!StringUtils.hasText(classInfo.getClassName())){
            return ResponseResult.build(ResponseStatusCode.SERVER_ERROR,"班级名称为空");
        }

        classService.addClass(classInfo);
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功");
    }


    @PostMapping(value = "/searchDepartment")
    public ResponseResult<PageResult<Department>> searchDepartment(DepartmentRequestDto departmentRequestDto){

        if(StringUtils.hasText(departmentRequestDto.getDepartmentName())){
            log.info("当前查询院校信息需要名称：{}",departmentRequestDto.getDepartmentName());
        }
        PageResult<Department> pageResult = departmentService.searchDepartmentList(departmentRequestDto);

        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功",pageResult);
    }

    @PostMapping(value = "/searchMajorList")
    public ResponseResult<PageResult<Major>> searchMajorList(MajorRequestDto majorRequestDto){

        if(StringUtils.hasText(majorRequestDto.getMajorName())){
            log.info("当前查询院校信息需要名称：{}",majorRequestDto.getMajorName());
        }
        PageResult<Major> pageResult = majorService.searchMajorList(majorRequestDto);

        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功",pageResult);
    }

    @PostMapping(value = "/searchClassList")
    public ResponseResult<PageResult<Class>> searchClassList(ClassRequestDto classRequestDto){

        if(StringUtils.hasText(classRequestDto.getClassName())){
            log.info("当前查询院校信息需要名称：{}",classRequestDto.getClassName());
        }
        PageResult<Class> pageResult = classService.searchClassList(classRequestDto);

        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功",pageResult);
    }



}
