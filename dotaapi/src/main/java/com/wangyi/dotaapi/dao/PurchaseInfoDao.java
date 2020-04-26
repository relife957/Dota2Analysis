package com.wangyi.dotaapi.dao;

import com.wangyi.dotaapi.domain.PurchaseInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wangyi
 * @create 2018-12-20 10:19 PM
 **/
public interface PurchaseInfoDao {

    @Select("select * from api.purchase where hero_id=#{hero_id} and kind=#{kind} order by count DESC limit 8")
    List<PurchaseInfo> getByIdAndKind(int hero_id , int kind);

}
