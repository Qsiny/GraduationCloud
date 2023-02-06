package com.qsiny.controller;

import com.qsiny.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Qin
 * @date 2023/2/4 14:02
 */
@RestController
@RequestMapping("/demo")
public class TestController {

    @GetMapping("/test/{count}")
    private String getCount(@PathVariable("count") Integer count){
        System.out.println(count);
        return "OKK";
    }
}
