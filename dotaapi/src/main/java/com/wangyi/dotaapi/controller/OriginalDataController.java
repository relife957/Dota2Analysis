package com.wangyi.dotaapi.controller;

import com.wangyi.dotaapi.Exception.ApiResult;
import com.wangyi.dotaapi.domain.OriginalData;
import com.wangyi.dotaapi.service.OriginalDataService;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangyi
 * @create 2018-12-12 6:54 PM
 **/
@RestController
@RequestMapping("/original")
public class OriginalDataController {

    OriginalDataService service ;

    @Autowired
    public void setService(OriginalDataService service) {
        this.service = service;
    }

    @GetMapping
    public List<OriginalData> getAll(){
        return service.getAllData() ;
    }


    @ExceptionHandler(TooManyResultsException.class)//处理所有异常
    @ResponseBody
    public ApiResult defaultErrorHandler(RuntimeException  ex) {
        // 打印异常信息：
        return new ApiResult(ex.getMessage());
    }

    @GetMapping("/{sno}")
    public OriginalData getOneBySno(@PathVariable String sno){

        return service.getOneBySno(sno);
    }


}
