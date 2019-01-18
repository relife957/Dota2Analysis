package com.wangyi.dotaapi.service;

import com.wangyi.dotaapi.dao.PurchaseInfoDao;
import com.wangyi.dotaapi.domain.PurchaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangyi
 * @create 2018-12-20 10:24 PM
 **/
@Service
public class PurchaseInfoService {

    PurchaseInfoDao purchaseInfoDao ;

    @Autowired
    public PurchaseInfoService(PurchaseInfoDao purchaseInfoDao) {
        this.purchaseInfoDao = purchaseInfoDao;
    }

    @Cacheable(cacheNames = "purchase",key = "myKeyGeneration")
    public List<PurchaseInfo> getByIdAndKind(int id, int kind){
        return purchaseInfoDao.getByIdAndKind(id,kind) ;
    }
}
