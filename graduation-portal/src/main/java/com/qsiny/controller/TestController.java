package com.qsiny.controller;

import com.qsiny.api.ApiFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author Qin
 * @date 2023/2/4 10:40
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @Resource
    private ApiFeign feignController;


    @GetMapping("/test1")
    public String getTest(){
        Random random = new Random(10);
        int i = random.nextInt();
        feignController.getCount(i);
        return "成功";
    }


}
