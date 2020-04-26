package com.wangyi.dotaapi.controller;

import com.wangyi.dotaapi.Exception.NotFoundException;
import com.wangyi.dotaapi.domain.HeroInfo;
import com.wangyi.dotaapi.service.HeroInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangyi
 * @create 2019-10-22 6:07 PM
 **/
@RestController
@Api(tags = "英雄信息接口", value = "提供英雄详细信息的接口")
@RequestMapping("/heroinfo")
public class HeroInfoController {
    private final HeroInfoService heroInfoService;

    public HeroInfoController(HeroInfoService heroInfoService) {
        this.heroInfoService = heroInfoService;
    }

    @ApiOperation("通过关键字模糊匹配英雄详细信息列表")
    @GetMapping("/query")
    public ResponseEntity<List<HeroInfo>> getByIdAndKind(
            @RequestParam("keyname") String keyName){

        List<HeroInfo> heroInfos = heroInfoService.getHeroesByKeyName(keyName);
        HttpStatus status = heroInfos.size()!=0 ? HttpStatus.OK : HttpStatus.NOT_FOUND ;
        if(heroInfos.size() == 0){
            throw new NotFoundException(keyName) ;
        }
        return new ResponseEntity<>(heroInfos,status) ;
    }
}
