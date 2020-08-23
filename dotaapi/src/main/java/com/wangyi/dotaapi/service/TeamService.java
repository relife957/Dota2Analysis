package com.wangyi.dotaapi.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangyi.dotaapi.dao.RecordDao;
import com.wangyi.dotaapi.dao.TeamDao;
import com.wangyi.dotaapi.domain.HeroInfo;
import com.wangyi.dotaapi.domain.Record;
import com.wangyi.dotaapi.domain.Team;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wangyi
 * @create 2019-02-01 8:41 PM
 **/

@Service
public class TeamService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //    private static HashMap<Integer,String> id_name_map ;
    private static HashMap<String,Integer> name_id_map ;


    private TeamDao teamDao ;
    private HeroInfoService heroInfoService;
    private RecordDao recordDao;

    @Autowired
    public TeamService(TeamDao teamDao,HeroInfoService heroInfoService,RecordDao recordDao) {
        this.teamDao = teamDao;
        this.heroInfoService = heroInfoService;
        this.recordDao = recordDao;
        init();
    }

    public List<HeroInfo> getByTeam(String teams){
        List<String> allGroup = new ArrayList<>(24);
        teams = trans(teams);
        permutation(allGroup,teams);
        Team result = null ;
        for (String s : allGroup) {
            result = teamDao.getByTeam(s);
            if(result != null){
                break;
            }
        }
        if(result == null){
            logger.info("推荐集中不包含{}",teams);
            return null;
        }

        Record record = new Record();
        record.setInput(teams);
        record.setOutput(result.getRecommend().trim());
        record.setAddTime(Date.valueOf(LocalDate.now()));
        recordDao.insert(record);

        String[] ids = result.getRecommend().trim().split(" ");
        List<HeroInfo> res = new ArrayList<>();

        for (String id : ids){
            int _id = Integer.parseInt(id);
            HeroInfo hero = heroInfoService.getHeroByHeroId(_id);
            if (hero == null){
                logger.info("不包含该{}的英雄",_id);
            }else{
                res.add(hero);
            }
        }
        return res ;
    }

    private String trans(String teams) {
        String[] names = teams.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String name : names){
            if (name_id_map.containsKey(name)){
                sb.append(name_id_map.get(name)).append(" ");
            }
        }
        return sb.toString().trim();
    }

//    public static String trans2cn(int id){
//        if (id_name_map.containsKey(id)){
//            return id_name_map.get(id);
//        }
//        return "";
//    }

    public static String ReadFile(String path){

        File file = null;
        try {
            file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX +path);
        } catch (FileNotFoundException e) {
        }
        BufferedReader reader = null;
        StringBuilder laststr = new StringBuilder();
        try {
            //System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            //一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                //显示行号
                laststr.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return laststr.toString();
    }

    private static void permutation(List<String> allGroup,String teams){
        String[] teamArray = teams.split(" ");
        permutate(teamArray,0,teamArray.length,allGroup);
    }

    private static void permutate(String[] teamArray, int k, int length,List<String> allGroup) {
        if (k == length){
            StringBuilder t = new StringBuilder();
            for (String s : teamArray){
                t.append(s).append(" ");
            }
            allGroup.add(t.toString().trim()) ;
        }
        for (int i = k ; i < length ; i++){
            String temp = teamArray[i] ;
            teamArray[i] = teamArray[k] ;
            teamArray[k] = temp ;
            permutate(teamArray,k+1,length,allGroup);
            teamArray[k] = teamArray[i] ;
            teamArray[i] = temp ;
        }
    }

    private void init(){
//        id_name_map = new HashMap<>(121);
        name_id_map = new HashMap<>(121);
        String path = "cn_heroes_name.json";
        String res = ReadFile(path);
        JSONObject jsonObject = JSONObject.parseObject(res);
        JSONArray teamData = jsonObject.getJSONArray("data");
        for (Object o : teamData){
            JSONObject hero = (JSONObject)o;
//            id_name_map.put(hero.getInteger("hero_id"),hero.getString("cn_name"));
            name_id_map.put(hero.getString("cn_name"),hero.getInteger("hero_id"));
        }
    }

}
