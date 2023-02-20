package com.qsiny.filter;


import com.qsiny.entity.ResponseResult;
import com.qsiny.feign.SecurityFeign;
import com.qsiny.utils.ContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 * @author Qin
 * @date 2023/2/4 21:39
 */
@Component
@Slf4j
public class AllFilter implements GlobalFilter, Ordered {

    public static final  String ATTRIBUTE_IGNORE_FILTER = "ignoreFilter";

    @Resource
    SecurityFeign securityFeign;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (Boolean.FALSE.equals(exchange.getAttribute(ATTRIBUTE_IGNORE_FILTER))) {
            ContextUtils.setToken(exchange.getRequest().getHeaders().getFirst("Authorization"));
            //这里调用security的权限校验接口
            CompletableFuture<ResponseResult<Boolean>> completableFuture = CompletableFuture.supplyAsync
                    (()-> securityFeign.PermissionAuthentication(request.getPath().toString()));

            try {
                ResponseResult<Boolean> result = completableFuture.get();
                if(!result.getData()){
                    log.info("当前url访问无权限:{}");
                    //set response 403
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                if(e.getCause().toString().contains("302")){
                    exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                    return exchange.getResponse().setComplete();
                }
                throw new RuntimeException(e);
            }
            log.info("测试。。");

        }
        return chain.filter(exchange);


    }

    @Override
    public int getOrder() {
        return 10;
    }
}
