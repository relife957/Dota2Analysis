package com.wangyi.dotaapi.domain;

/**
 * 阵容推荐阵容实体类
 *
 * @author wangyi
 * @create 2019-02-01 8:36 PM
 **/
public class Team {
    private Integer id ;
    private String team ;
    private String recommend ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }
}
