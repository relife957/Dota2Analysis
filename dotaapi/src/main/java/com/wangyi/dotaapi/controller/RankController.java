package com.wangyi.dotaapi.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.wangyi.dotaapi.Exception.InvalidParamException;
import com.wangyi.dotaapi.Exception.ServerException;
import com.wangyi.dotaapi.domain.RankInfo;
import com.wangyi.dotaapi.service.RankService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: wangyi
 * @description:
 * @date: 2020-04-25 11:06
 **/

@Api(tags = "排行榜", value = "排行榜接口")
@RestController
@RequestMapping("/rank")
public class RankController {


    private RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping("/dateRank")
    public ResponseEntity<List<RankInfo>> dateRank(@RequestParam("start_date") String startDate,
                                             @RequestParam("end_date") String endDate, @RequestParam("top") int top){

        if (Strings.isNullOrEmpty(startDate) || Strings.isNullOrEmpty(endDate)){
            throw new InvalidParamException();
        }

        List<RankInfo> rankInfoList = rankService.getRankList(startDate, endDate, top);

        if (rankInfoList == null){
            throw new ServerException();
        }
        return new ResponseEntity<>(rankInfoList, HttpStatus.OK);
    }

}
