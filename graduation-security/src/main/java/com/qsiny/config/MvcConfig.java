package com.qsiny.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Qin
 * @date 2023/2/5 10:08
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${apiPathProperties.prefix}")
    private String prefix;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer
                .addPathPrefix(prefix,c -> c.isAnnotationPresent(Controller.class) || c.isAnnotationPresent(RestController.class));
    }

}
