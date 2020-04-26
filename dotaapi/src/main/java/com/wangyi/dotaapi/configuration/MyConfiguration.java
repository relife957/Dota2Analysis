package com.wangyi.dotaapi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author wangyi
 * @create 2018-12-12 5:39 PM
 **/

@Configuration
public class MyConfiguration implements WebMvcConfigurer {

    /**
     * 同源跨域问题解决
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");

    }


}
