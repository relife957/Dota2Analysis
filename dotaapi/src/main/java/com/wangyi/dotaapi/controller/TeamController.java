package com.wangyi.dotaapi.controller;

import com.wangyi.dotaapi.Exception.InvalidParamException;
import com.wangyi.dotaapi.domain.HeroInfo;
import com.wangyi.dotaapi.service.TeamService;
import com.wangyi.dotaapi.util.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyi
 * @create 2019-02-01 8:45 PM
 **/
@Api(tags = "阵容推荐",value = "提供阵容推荐的api")
@RestController
@RequestMapping("/team")
public class TeamController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private TeamService teamService ;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }


    @ApiOperation("通过查询阵容返回推荐阵容的英雄详细信息")
    @GetMapping("/recommend")
    public ResponseEntity<List<HeroInfo>> getRecommend(HttpServletRequest request, @RequestParam String teams){
        String ipAddress = IpUtil.getIpAddr(request);
        logger.info("访客: {}",ipAddress);
        if("".equals(teams)){
            throw  new InvalidParamException();
        }

        List<HeroInfo> team = teamService.getByTeam(teams) ;
        logger.info("输入阵容: {} ",teams);

        if (team == null){
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(team,HttpStatus.OK) ;

    }




}
