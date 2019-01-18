package com.wangyi.dotaapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author wangyi
 */
@SpringBootApplication
@MapperScan("com.wangyi.dotaapi.dao")
@EnableCaching
public class DotaapiApplication  {


    public static void main(String[] args) {
        SpringApplication.run(DotaapiApplication.class, args);
    }
}
