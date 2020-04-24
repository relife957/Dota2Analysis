package com.wangyi.dotaapi.domain;


import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wangyi
 * @create 2018-12-20 10:15 PM
 **/
@Data
public class PurchaseInfo {

    private int id ;

    @NotNull
    private int heroId;

    private String item ;

    @NotNull
    private int kind ;

    private int count ;


}
