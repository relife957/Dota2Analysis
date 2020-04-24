package com.wangyi.dotaapi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangyi
 * @create 2019-10-22 5:53 PM
 **/
@Data
@ApiModel("英雄详细信息")
public class HeroInfo {

    private Integer id ;
    @ApiModelProperty("英雄id")
    private Integer heroId;
    @ApiModelProperty("英雄中文名字")
    private String cnName;
    @ApiModelProperty("英雄英文名字")
    private String name ;
    @ApiModelProperty("英雄定位")
    private String cnRoles;
    @ApiModelProperty("英雄图像")
    private String img ;
    @ApiModelProperty("英雄类别")
    private String cnAttr ;



}
