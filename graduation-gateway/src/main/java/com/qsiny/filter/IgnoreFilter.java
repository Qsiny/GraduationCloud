package com.qsiny.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class IgnoreFilter implements GlobalFilter, Ordered {



    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ignore = exchange.getRequest().getHeaders().getFirst("ignore");
        if("Y".equals(ignore)){
            exchange.getAttributes().put(AllFilter.ATTRIBUTE_IGNORE_FILTER, true);
        }else{
            exchange.getAttributes().put(AllFilter.ATTRIBUTE_IGNORE_FILTER, false);
        }
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
