package com.wangyi.dotaapi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author: wangyi
 * @description:
 * @date: 2020-04-25 11:12
 **/
@Data
@ApiModel("排行榜")
@Builder
public class RankInfo {
    @ApiModelProperty("排名")
    private int rankNumber ;
    @ApiModelProperty("查询阵容")
    private List<HeroInfo> queryTeam ;
    @ApiModelProperty("推荐阵容")
    private List<HeroInfo> recommendTeam ;
    @ApiModelProperty("查询次数")
    private long count ;
}
