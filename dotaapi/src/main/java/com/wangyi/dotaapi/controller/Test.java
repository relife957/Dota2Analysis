package com.wangyi.dotaapi.controller;

import com.wangyi.dotaapi.domain.Car;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author wangyi
 * @create 2018-12-12 5:45 PM
 **/

@RestController
@RequestMapping("/cars")
public class Test {

    @GetMapping
    public List getCar(){
        List<Car> list = new ArrayList<>(1);
        Calendar calendar = Calendar.getInstance();
        list.add(new Car("BMW",1000000,calendar.getTime()));
        return list ;
    }
}
