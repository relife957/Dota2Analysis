package com.wangyi.dotaapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.lang.reflect.Method;

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

    /**
     * key生成器
     */
    @Bean("myKeyGeneration")
    public KeyGenerator keyGenerator(){
        return (target, method, params) -> params[0]+" "+params[1];
    }

}
