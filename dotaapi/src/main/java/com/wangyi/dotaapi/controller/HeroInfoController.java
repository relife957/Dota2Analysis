package com.wangyi.dotaapi.controller;

import com.wangyi.dotaapi.Exception.Error;
import com.wangyi.dotaapi.Exception.InvalidParamException;
import com.wangyi.dotaapi.Exception.NotFoundException;
import com.wangyi.dotaapi.domain.HeroInfo;
import com.wangyi.dotaapi.service.HeroInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangyi
 * @create 2019-10-22 6:07 PM
 **/
@RestController
@RequestMapping("/heroinfo")
public class HeroInfoController {
    private final HeroInfoService heroInfoService;

    @Autowired
    public HeroInfoController(HeroInfoService heroInfoService) {
        this.heroInfoService = heroInfoService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> informationNotFound(NotFoundException e){
        String keyname = (String) e.getInput();
        Error error = new Error(400 , "this keyname "+keyname+"'s information not found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidParamException.class)
    public ResponseEntity<Error> invalidInput(InvalidParamException e){
        Error error = new Error(400,"Invalid input , please check your input") ;
        return new ResponseEntity<>(error, HttpStatus.INSUFFICIENT_STORAGE) ;
    }

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
