package com.wangyi.dotaapi.controller;

import com.google.common.base.Strings;
import com.wangyi.dotaapi.Exception.Error;
import com.wangyi.dotaapi.Exception.InvalidParamException;
import com.wangyi.dotaapi.Exception.NotFoundException;
import com.wangyi.dotaapi.Exception.ResultEmptyException;
import com.wangyi.dotaapi.domain.HeroInfo;
import com.wangyi.dotaapi.domain.Record;
import com.wangyi.dotaapi.service.HistoryRecordService;
import com.wangyi.dotaapi.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: wangyi
 * @description:
 * @date: 2020-04-20 09:05
 **/
@Api(tags = "历史查询列表",value = "提供对历史查询信息的查询")
@RestController
@RequestMapping("history")
public class HistoryRecordController {

    HistoryRecordService historyRecordService ;

    public HistoryRecordController(HistoryRecordService historyRecordService){
        this.historyRecordService = historyRecordService;
    }

//    @ExceptionHandler(ResultEmptyException.class)
//    public ResponseEntity<Error> invalidInput(ResultEmptyException e){
//        Error error = new Error(401,"the query result is empty!") ;
//        return new ResponseEntity<>(error, HttpStatus.INSUFFICIENT_STORAGE) ;
//    }

    @ApiOperation("通过开始日期和结束日期返回历史查询列表")
    @GetMapping("/query")
    public ResponseEntity<List<Record>> getRecord(
            @RequestParam("start_date") String startDate, @RequestParam("end_date") String endDate){
        if (Strings.isNullOrEmpty(startDate)){
            startDate = DateUtils.today();
        }
        if (Strings.isNullOrEmpty(endDate)){
            endDate = DateUtils.today();
        }
        List<Record> recordList = historyRecordService.getRecordList(startDate,endDate);
        if (recordList == null){
            throw new NotFoundException(startDate +  " - " + endDate);
        }
        return new ResponseEntity<>(recordList,HttpStatus.OK) ;
    }


}
