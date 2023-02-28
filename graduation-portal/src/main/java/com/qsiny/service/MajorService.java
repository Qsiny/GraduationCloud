package com.qsiny.service;

import com.qsiny.dto.MajorRequestDto;
import com.qsiny.entity.Major;
import com.qsiny.po.PageResult;

import java.util.List;

public interface MajorService {

    void addMajor(Major major);

    List<Major> searchMajor(String majorName);

    PageResult<Major> searchMajorList(MajorRequestDto majorRequestDto);
}
