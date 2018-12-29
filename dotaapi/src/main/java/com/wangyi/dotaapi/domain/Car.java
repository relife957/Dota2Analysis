package com.wangyi.dotaapi.domain;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author wangyi
 * @create 2018-12-12 5:37 PM
 **/
@Component
public class Car {

    private String name ;
    private int price ;
    private Date factoryTime ;

    public Car() {
    }

    public Car(String name, int price, Date factoryTime) {
        this.name = name;
        this.price = price;
        this.factoryTime = factoryTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getFactoryTime() {
        return factoryTime;
    }

    public void setFactoryTime(Date factoryTime) {
        this.factoryTime = factoryTime;
    }
}
