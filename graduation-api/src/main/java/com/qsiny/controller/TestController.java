package com.qsiny.controller;

import com.qsiny.entity.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Qin
 * @date 2023/2/4 14:02
 */
@RestController
@Slf4j
@RequestMapping("/demo")
public class TestController {

    @GetMapping("/test/{count}")
    private String getCount(@PathVariable("count") Integer count) {
        log.info("当前count：{}",count);
        System.out.println(count);
        return "OKK";
    }

    @GetMapping("/test1")
    public ResponseResult<String> demo(String demo){
        System.out.println(demo);
        return ResponseResult.build(200,"ad","aaa");
    }
}
