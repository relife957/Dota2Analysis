package com.wangyi.dotaapi;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author wangyi
 * @create 2018-12-12 10:07 PM
 **/

@Ignore
public class DotaTest {

    String deal(String attr){
        return attr.substring(1,attr.length()-1).replace("\"","")
                .replace(" ","");
    }

    @Test
    public void test(){
        String s = "[\"核心\", \"逃生\", \"爆发\"]";
        System.out.println(deal(s));
    }
}
