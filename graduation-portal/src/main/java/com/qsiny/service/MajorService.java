package com.qsiny.service;

import com.qsiny.dto.GradeVo;
import com.qsiny.dto.MajorRequestDto;
import com.qsiny.entity.Major;
import com.qsiny.entity.ResponseResult;
import com.qsiny.po.PageResult;

import java.util.List;

public interface MajorService {

    void addMajor(Major major);

    List<Major> searchMajor(String majorName);

    PageResult<Major> searchMajorList(MajorRequestDto majorRequestDto);

    ResponseResult<List<Major>> searchMajorByDepartmentCode(String departmentCode);

    ResponseResult<List<GradeVo>> searchGradeByDepartmentAndMajor(String departmentCode, String majorCode);
}
