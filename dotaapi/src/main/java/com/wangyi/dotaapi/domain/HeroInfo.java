package com.wangyi.dotaapi.domain;

/**
 * @author wangyi
 * @create 2019-10-22 5:53 PM
 **/
public class HeroInfo {

    private Integer id ;
    private Integer heroId;
    private String cnName;
    private String name ;
    private String cnRoles;
    private String img ;
    private String cnAttr ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHeroId() {
        return heroId;
    }

    public void setHeroId(Integer heroId) {
        this.heroId = heroId;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnRoles() {
        return cnRoles;
    }

    public void setCnRoles(String cnRoles) {
        this.cnRoles = cnRoles;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCnAttr() {
        return cnAttr;
    }

    public void setCnAttr(String cnAttr) {
        this.cnAttr = cnAttr;
    }
}
