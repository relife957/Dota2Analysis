package com.wangyi.dotaapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author wangyi
 */

@SpringBootApplication
@MapperScan("com.wangyi.dotaapi.dao")
@ServletComponentScan("com.wangyi.dotaapi.configuration")
public class DotaapiApplication  {
    public static void main(String[] args) {
        SpringApplication.run(DotaapiApplication.class, args);
    }
}
