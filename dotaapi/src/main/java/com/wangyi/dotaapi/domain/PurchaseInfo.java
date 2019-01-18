package com.wangyi.dotaapi.domain;


/**
 * @author wangyi
 * @create 2018-12-20 10:15 PM
 **/
public class PurchaseInfo {

    private int id ;

    private int heroId;

    private String item ;

    private int kind ;

    private int count ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getheroId() {
        return heroId;
    }

    public void setheroId(int heroId) {
        this.heroId = heroId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
