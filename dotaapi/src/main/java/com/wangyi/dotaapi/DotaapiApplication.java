package com.wangyi.dotaapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author wangyi
 */
@SpringBootApplication
@MapperScan("com.wangyi.dotaapi.dao")
public class DotaapiApplication  {


    public static void main(String[] args) {
        SpringApplication.run(DotaapiApplication.class, args);
    }
}
