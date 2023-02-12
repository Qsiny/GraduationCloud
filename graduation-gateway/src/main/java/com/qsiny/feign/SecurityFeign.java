package com.qsiny.feign;

import com.qsiny.entity.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name="graduation-security")
public interface SecurityFeign {

    @PostMapping(value = "security/authentication/PermissionAuthentication")
    ResponseResult<Void> PermissionAuthentication(@RequestBody String requestUrl);
}
