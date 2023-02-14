package com.qsiny.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Qin
 * @date 2023/2/4 13:59
 */
@Component
@FeignClient(name = "graduation-api")
public interface ApiFeign {

    @GetMapping("/api/demo/test/{count}")
    String getCount(@PathVariable("count") Integer count);

    @GetMapping("/api/demo/test1")
     String demo(String demo);

}
