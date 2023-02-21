package com.qsiny.service;

import com.qsiny.entity.Major;

import java.util.List;

public interface MajorService {

    void addMajor(Major major);

    List<Major> searchMajor(String majorName);
}
