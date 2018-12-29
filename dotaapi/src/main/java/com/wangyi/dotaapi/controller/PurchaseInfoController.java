package com.wangyi.dotaapi.controller;

import com.wangyi.dotaapi.domain.OriginalData;
import com.wangyi.dotaapi.domain.PurchaseInfo;
import com.wangyi.dotaapi.service.PurchaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangyi
 * @create 2018-12-20 10:29 PM
 **/

@RestController
@RequestMapping("/purchase")
public class PurchaseInfoController {

    PurchaseInfoService purchaseInfoService ;

    @Autowired
    public PurchaseInfoController(PurchaseInfoService purchaseInfoService) {
        this.purchaseInfoService = purchaseInfoService;
    }

    @GetMapping("/recommand")
    public List<PurchaseInfo> getByIdAndKind(
            @RequestParam("id") int id, @RequestParam("kind") int kind){

        return purchaseInfoService.getByIdAndKind(id,kind);
    }


}
