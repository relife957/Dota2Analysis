package com.wangyi.dotaapi.service;

import com.wangyi.dotaapi.dao.RecordDao;
import com.wangyi.dotaapi.domain.HeroInfo;
import com.wangyi.dotaapi.domain.Record;
import com.wangyi.dotaapi.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: wangyi
 * @description:
 * @date: 2020-04-20 09:06
 **/
@Service
@Slf4j
public class HistoryRecordService {

    public static final int LIMIT = 200 ;

    private RecordDao recordDao;
    private HeroInfoService heroInfoService;

    public HistoryRecordService(RecordDao recordDao, HeroInfoService heroInfoService) {
        this.recordDao = recordDao;
        this.heroInfoService = heroInfoService;
    }

    public List<Record> getRecordList(String startDate, String endDate) {
        List<Record> recordList ;
        if (DateUtils.compareDate(startDate,endDate)){
            return new ArrayList<>();
        }
        try {
            recordList = recordDao.findRecordByAddTime(DateUtils.string2Date(startDate),DateUtils.string2Date(endDate),LIMIT);
        }catch (Exception e){
            log.error("cannot query data from db , caused by {}",e.toString());
            return new ArrayList<>();
        }
        recordList.forEach(record -> {
            record.setInput(trans(record.getInput()));
            record.setOutput(trans(record.getOutput()));
        });
        recordList = recordList.stream().filter(Record::validate).collect(Collectors.toList());
        return recordList;
    }

    private String trans(String ids){
        StringBuilder sb = new StringBuilder();
        String[] ids_ = ids.trim().split(" ");
        for (String id : ids_){
            int _id = Integer.parseInt(id);
            HeroInfo hero = heroInfoService.getHeroByHeroId(_id);
            if (hero == null){
                log.info("不包含该{}的英雄",_id);
                return null;
            }else{
                sb.append(hero.getCnName()).append(" ");
            }
        }
        return sb.toString().trim();
    }

}
