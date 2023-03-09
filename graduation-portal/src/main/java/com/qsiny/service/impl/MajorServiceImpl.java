package com.qsiny.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qsiny.constant.ResponseStatusCode;
import com.qsiny.dto.GradeVo;
import com.qsiny.dto.MajorRequestDto;
import com.qsiny.entity.Major;
import com.qsiny.entity.ResponseResult;
import com.qsiny.mapper.MajorMapper;
import com.qsiny.po.PageResult;
import com.qsiny.service.MajorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MajorServiceImpl implements MajorService {

    @Resource
    private MajorMapper majorMapper;

    @Override
    public void addMajor(Major major) {
        major.setCreateData(new Date());
        major.setUpdateData(new Date());
        major.setUpdateId(major.getCreateId());
        majorMapper.insert(major);



    }

    @Override
    public List<Major> searchMajor(String majorName) {
        List<Major> majors = majorMapper.selectList(new QueryWrapper<Major>().lambda()
                .like(Major::getMajorName, majorName));

        return majors;
    }

    @Override
    public PageResult<Major> searchMajorList(MajorRequestDto majorRequestDto) {
        PageResult<Major> result = new PageResult<>();
        Page<Major> page = new Page<>(majorRequestDto.getPage(), majorRequestDto.getSize(), true);
        Page<Major> selectPage = new Page<>();
        if (StringUtils.hasText(majorRequestDto.getMajorName())) {
            selectPage = majorMapper.selectPage(page, new QueryWrapper<Major>()
                    .lambda()
                    .eq(Major::getMajorName, majorRequestDto.getMajorName()));
        }else{
            selectPage = majorMapper.selectPage(page,null);
        }
        result.setResult(selectPage.getRecords());
        result.setTotal(selectPage.getTotal());
        return result;
    }

    @Override
    public ResponseResult<List<Major>> searchMajorByDepartmentCode(String departmentCode) {
        List<Major> majors = majorMapper.selectList(new QueryWrapper<Major>().lambda().eq(Major::getCode, departmentCode));
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功",majors);
    }

    @Override
    public ResponseResult<List<GradeVo>> searchGradeByDepartmentAndMajor(String departmentCode, String majorCode) {

        List<GradeVo> list = majorMapper.searchGradeByDepartmentAndMajor(departmentCode,majorCode);
        return null;
    }
}
