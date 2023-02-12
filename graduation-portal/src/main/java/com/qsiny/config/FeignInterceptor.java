
package com.qsiny.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

/**
* 调用前从 MDC中获取上一步骤设置的traceId，放置到 header中，供下一个服务从header中获取traceId
*/
@Configuration
public class FeignInterceptor implements RequestInterceptor {
    private static final String TRACE_ID = "traceId";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(TRACE_ID,  MDC.get(TRACE_ID));
    }
}
