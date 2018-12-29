package com.wangyi.dotaapi.domain;

import java.util.Date;

/**
 * @author wangyi
 * @create 2018-12-12 6:11 PM
 **/
public class OriginalData {
    private int id ;
    private String sno ;
    private int balance ;
    private String toaccount ;
    private Date effectdate ;
    private int poscode ;
    private int tranamt ;
    private int hour ;

    public OriginalData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getToaccount() {
        return toaccount;
    }

    public void setToaccount(String toaccount) {
        this.toaccount = toaccount;
    }

    public Date getEffectdate() {
        return effectdate;
    }

    public void setEffectdate(Date effectdate) {
        this.effectdate = effectdate;
    }

    public int getPoscode() {
        return poscode;
    }

    public void setPoscode(int poscode) {
        this.poscode = poscode;
    }

    public int getTranamt() {
        return tranamt;
    }

    public void setTranamt(int tranamt) {
        this.tranamt = tranamt;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
