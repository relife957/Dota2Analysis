package com.wangyi.dotaapi.controller;

import com.wangyi.dotaapi.domain.OriginalData;
import com.wangyi.dotaapi.service.OriginalDataService;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author wangyi
 * @create 2018-12-12 6:54 PM
 **/
@ApiIgnore
@RestController
@RequestMapping("/original")
public class OriginalDataController {

    private OriginalDataService service ;

    @Autowired
    public void setService(OriginalDataService service) {
        this.service = service;
    }

    @GetMapping
    public List<OriginalData> getAll(){
        return service.getAllData() ;
    }

    @GetMapping("/{sno}")
    public OriginalData getOneBySno(@PathVariable String sno){

        return service.getOneBySno(sno);
    }


}
