package com.qsiny.filter;


import com.qsiny.entity.CustomizeException;
import com.qsiny.entity.LoginUser;
import com.qsiny.enums.UrlRoleEnum;
import com.qsiny.utils.JwtUtil;
import com.qsiny.utils.RedisCache;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Qin
 * @date 2023/2/4 21:39
 */
@Component
@Slf4j
public class TestFilter implements GlobalFilter, Ordered {

    @Resource
    private RedisCache redisCache;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String requestPath = request.getPath().toString();
        if(!UrlRoleEnum.needCheck(requestPath)){
            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst("authorization");
        if(!StringUtils.hasText(token)){
            //401 这里就是需要权限但是没有token，则需要登录给一个token
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //解析token
        Claims claims;
        try {
             claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            throw new CustomizeException("token解析异常");
        }
        String userId = claims.getId();
        if(userId == null){
            throw new CustomizeException("从token中无法解析用户ID");
        }
        //从redis拿到userInfo
        LoginUser loginUser = redisCache.getCacheObject(userId);
        List<String> permissions = loginUser.getPermissions();

        //拿到这个url对应的role
        String role = UrlRoleEnum.valueOf(requestPath).getRole();
        //role是否在该用户的权限列表之中
        if(!UrlRoleEnum.hasRole(role,permissions)){
            //返回错误的状态码 403 权限不足，无法访问
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
