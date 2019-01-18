package com.wangyi.dotaapi.Exception;

/**
 * @author wangyi
 * @create 2019-01-18 4:06 PM
 **/
public class NotFoundException extends RuntimeException{
    private int heroId ;

    public NotFoundException(int heroId) {
        this.heroId = heroId;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }
}
