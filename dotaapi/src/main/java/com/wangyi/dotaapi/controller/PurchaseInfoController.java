package com.wangyi.dotaapi.controller;

import com.wangyi.dotaapi.Exception.InvalidParamException;
import com.wangyi.dotaapi.Exception.NotFoundException;
import com.wangyi.dotaapi.domain.PurchaseInfo;
import com.wangyi.dotaapi.service.PurchaseInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wangyi.dotaapi.Exception.Error ;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author wangyi
 * @create 2018-12-20 10:29 PM
 **/
@ApiIgnore
@RestController
@RequestMapping("/purchase")
public class PurchaseInfoController {

    private PurchaseInfoService purchaseInfoService ;

    @Autowired
    public PurchaseInfoController(PurchaseInfoService purchaseInfoService) {
        this.purchaseInfoService = purchaseInfoService;
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<PurchaseInfo>> getByIdAndKind(
//            @Size(min = 1,max = 121) @RequestParam("id") int id, @Size(min = 1,max = 4) @RequestParam("kind") int kind){
            @RequestParam("id") int id,@RequestParam("kind") int kind){
        if(id < 0 || kind < 0 ){
            throw new InvalidParamException() ;
        }
        List<PurchaseInfo> purchaseInfos = purchaseInfoService.getByIdAndKind(id,kind);
        HttpStatus status = purchaseInfos.size()!=0 ? HttpStatus.OK : HttpStatus.NOT_FOUND ;
        if(purchaseInfos.size() == 0){
            throw new NotFoundException(id) ;
        }
        return new ResponseEntity<>(purchaseInfos,status) ;
    }


}
