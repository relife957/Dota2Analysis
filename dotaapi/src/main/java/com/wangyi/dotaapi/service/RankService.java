package com.wangyi.dotaapi.service;

import com.wangyi.dotaapi.dao.RecordDao;
import com.wangyi.dotaapi.domain.RankInfo;
import com.wangyi.dotaapi.domain.Record;
import com.wangyi.dotaapi.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: wangyi
 * @description:
 * @date: 2020-04-25 11:28
 **/
@Slf4j
@Service
public class RankService {

    private RecordDao recordDao;
    private HeroInfoService heroInfoService;

    public RankService(RecordDao recordDao, HeroInfoService heroInfoService) {
        this.recordDao = recordDao;
        this.heroInfoService = heroInfoService;
    }

    public List<RankInfo> getRankList(String startDate, String endDate, int top) {
        List<RankInfo> rankInfoList;
        if (DateUtils.compareDate(startDate,endDate)){
            log.warn("请检查入参： start_date : {} , end_date: {}",startDate,endDate);
            return new ArrayList<>();
        }
        List<Record> recordList ;
        try {
            recordList = recordDao.findRecordByAddTimeNoLimit(DateUtils.string2Date(startDate),DateUtils.string2Date(endDate));
        }catch (Exception e){
            log.error("cannot query data from db , caused by {}",e.toString());
            return null ;
        }
        if (recordList.isEmpty()){
            log.info("record list is empty!");
            return new ArrayList<>();
        }
        Map<Record,Long> groupResult = recordList.stream().collect(
                Collectors.groupingBy(
                        Function.identity(),Collectors.counting()
                )
        );

        rankInfoList = recordList.stream().distinct().map(record -> RankInfo.builder()
                .queryTeam(heroInfoService.getHeroInfoList(record.getInput()))
                .recommendTeam(heroInfoService.getHeroInfoList(record.getOutput()))
                .count(groupResult.get(record)).build())
                .sorted(Comparator.comparing(RankInfo::getCount).reversed())
                .limit(top)
                .collect(Collectors.toList());

        for (int i = 0 ; i < rankInfoList.size();i++){
            rankInfoList.get(i).setRankNumber(i+1);
        }

        return rankInfoList;
    }

}
