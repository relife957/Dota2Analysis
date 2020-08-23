package com.wangyi.dotaapi;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class DotaapiApplicationTests {

    @Autowired
    DataSource dataSource ;

    @Test
    public void contextLoads() {
        System.out.println(dataSource.getClass());
    }

}
