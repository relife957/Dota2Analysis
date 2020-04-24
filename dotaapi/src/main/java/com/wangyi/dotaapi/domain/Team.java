package com.wangyi.dotaapi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 阵容推荐阵容实体类
 *
 * @author wangyi
 * @create 2019-02-01 8:36 PM
 **/
@ApiModel("阵容推荐实体")
@Data
public class Team {
    private Integer id ;
    @ApiModelProperty("原始阵容")
    private String team ;
    @ApiModelProperty("推荐阵容")
    private String recommend ;


}
