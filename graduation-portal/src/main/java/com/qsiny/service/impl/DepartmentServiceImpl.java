package com.qsiny.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qsiny.constant.ResponseStatusCode;
import com.qsiny.dto.DepartmentRequestDto;
import com.qsiny.entity.Department;
import com.qsiny.entity.ResponseResult;
import com.qsiny.entity.User;
import com.qsiny.mapper.DepartmentMapper;
import com.qsiny.mapper.RoleMapper;
import com.qsiny.mapper.UserMapper;
import com.qsiny.po.PageResult;
import com.qsiny.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;
    @Override
    public Integer addDepartment(Department department) {
        departmentMapper.insert(department);
        return 1;
    }

    @Override
    public List<String> searchDeanByDeanName(String deanName) {
        List<String> result = new ArrayList<>();
        List<Long> deanIds = roleMapper.searchUserIdByRole("dean");
        List<User> users = userMapper.selectList(new QueryWrapper<User>()
                .lambda()
                .like(User::getUserName, deanName)
                .in(User::getUserId,deanIds)
        );
        for (User user : users) {
            result.add(user.getUserName()+"-"+user.getUserId());
        }
        return result;
    }

    @Override
    public List<String> searchCounselors(String counselorName) {
        List<String> result = new ArrayList<>();
        List<Long> counselorIds = roleMapper.searchUserIdByRole("counselor");
        List<User> users = userMapper.selectList(new QueryWrapper<User>()
                .lambda()
                .like(User::getUserName, counselorName)
                .in(User::getUserId,counselorIds)
        );
        for (User user : users) {
            result.add(user.getUserName()+"-"+user.getUserId());
        }
        return result;
    }

    @Override
    public List<Department> searchDepartmentList() {

        List<Department> departments = departmentMapper.selectList(null);
        return departments;
    }

    @Override
    public PageResult<Department> searchDepartmentList(DepartmentRequestDto departmentRequestDto) {

        PageResult<Department> result = new PageResult<>();
        Page<Department> departmentPage;
        Page<Department> page = new Page<>(departmentRequestDto.getPage(), departmentRequestDto.getSize(),true);
        if(StringUtils.hasText(departmentRequestDto.getDepartmentName())){
            departmentPage = departmentMapper.selectPage(page, new QueryWrapper<Department>()
                    .lambda()
                    .likeRight(Department::getDepartmentName, departmentRequestDto.getDepartmentName()));

        }else{
            departmentPage = departmentMapper.selectPage(page,null);
        }
        result.setTotal(departmentPage.getTotal());
        result.setResult(departmentPage.getRecords());
        return result;
    }

    @Override
    public ResponseResult<List<Department>> searchMajorByDepartmentCode() {

        List<Department> departments = departmentMapper.selectList(null);
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功",departments);
    }
}
