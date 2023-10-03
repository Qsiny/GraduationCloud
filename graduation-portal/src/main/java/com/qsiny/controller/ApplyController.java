package com.qsiny.controller;

import com.qsiny.constant.ResponseStatusCode;
import com.qsiny.entity.ResponseResult;
import com.qsiny.entity.UserApplyInfo;
import com.qsiny.service.ApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/apply")
public class ApplyController {

    @Resource
    private ApplyService applyService;

    @PostMapping("/addApply")
    public ResponseResult<Void> addDepartment(@RequestBody UserApplyInfo userApplyInfo){
        log.info("用户发起申请{}",userApplyInfo);
        applyService.addUserApply(userApplyInfo);
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功");
    }

    @PostMapping("/decideApply")
    public ResponseResult<Void> decideApply(@RequestBody UserApplyInfo userApplyInfo){
        log.info("决定用户申请{}",userApplyInfo);
        applyService.decideApply(userApplyInfo);
        return ResponseResult.build(ResponseStatusCode.SUCCESS_CODE,"成功");
    }
}
