package com.qsiny.service;

import com.qsiny.entity.UserApplyInfo;

public interface ApplyService {
    void addUserApply(UserApplyInfo userApplyInfo);

    void decideApply(UserApplyInfo userApplyInfo);
}
