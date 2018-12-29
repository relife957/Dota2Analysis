package com.wangyi.dotaapi.service;

import com.wangyi.dotaapi.dao.OriginalDataDao;
import com.wangyi.dotaapi.domain.OriginalData;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangyi
 * @create 2018-12-12 6:51 PM
 **/
@Service
public class OriginalDataService {

    @Autowired
    OriginalDataDao originalDataDao ;

    public List<OriginalData> getAllData(){
        return originalDataDao.getAll();
    }

    public OriginalData getOneBySno(String sno){

        OriginalData res ;
        try {
            res = originalDataDao.getOneBySno(sno) ;
        }catch (TooManyResultsException e){
            res = originalDataDao.getSomeBySno(sno).get(0) ;
        }
        return res ;
    }
    public List<OriginalData> getSomeBySno(String sno){
        return originalDataDao.getSomeBySno(sno) ;
    }
}