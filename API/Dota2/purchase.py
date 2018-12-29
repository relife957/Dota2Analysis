# -*- coding: utf-8 -*-
"""
-------------------------------------------------
   File Name：     purchase
   Description :
   Author :       wangyi
   date：          11/24/18
-------------------------------------------------
   Change Activity:
                   11/24/18:
-------------------------------------------------
"""
from datetime import datetime
import time
import csv

__author__ = 'wangyi'

import requests

keys = {
  "funDota":{
    "API_KEY" : "81fda66a981246a",
    "API_SECRET" : "0649e000682e4aeeb4cbd45058bc689"
  },
  "openDota":{
    "API_KEY" : "B6F03F91CD8355AD6856E6DF03854370"
  }
}

openDota_param = {
    'api_key':"B6F03F91CD8355AD6856E6DF03854370"
}



def get_data(url,param=None,headers=None):
    """

    :param url:目标url
    :param param: 传入参数 默认none
    :param headers: 传入的头信息 默认none
    :return:
    """
    res = None
    try:
        res = requests.get(url=url,
                           params=param,
                           headers=headers)
    except:
        return "访问失败"
    if res.status_code == 200:
        return res.json()
    else:
        return "错误: "+ str(res.status_code)



def get_purchase_data(match_data,radiant_win):
    """
    获取match比赛中的玩家购买信息
    :param match_data:比赛信息
    :param radiant_win:天辉是否胜利
    :return:
    """
    hero_purchase = []
    for player in match_data['players']:
        player_slot = player['player_slot']
        if radiant_win and player_slot < 5:
            hero_purchase.append(
                (player['hero_id'],refactor_str(player['purchase_log'])))

        elif not radiant_win and player_slot >4 :
            hero_purchase.append(
                (player['hero_id'], refactor_str(player['purchase_log']))
            )
        # print(type(player['purchase_log']))
    return hero_purchase

def refactor_str(purchase_log):
    """
    重构一个英雄的购买信息
    :return:
    """
    refactor = []
    for purchase in purchase_log:
        refactor.append((purchase['time'],purchase['key']))
    return refactor

def get_match_data(match_id):
    """
    :param match_id: 比赛id
    :return:
    """
    url = "https://api.opendota.com/api/matches/{0}".format(match_id)
    match_data = get_data(url,openDota_param)
    if isinstance(match_data,str):
        print(match_data)
        return None
    return match_data

def refactor_data(match_data):
    for key in match_data.keys():
        print(key)

def get_match_list(less_match_id):
    """
    获得职业比赛比赛id和胜利方
    :param less_match_id:
    :return:
    """
    url = "https://api.opendota.com/api/proMatches"
    params = {
        'api_key': keys["openDota"],
        'less_than_match_id':less_match_id
    }
    match_list = get_data(url,params)
    res_list = []
    for match in match_list:
        if type(match)==dict:
            try:
                temp_dict = (match['match_id'],match['radiant_win'])
                res_list.append(temp_dict)
            except:
                continue
    return res_list

def find_min_match_id(match_list):
    """
    寻找比赛列表中最小的比赛id
    :param match_list:
    :return:
    """
    min_num = match_list[0][0]
    for tuples in match_list:
        if tuples[0] < min_num:
            min_num = tuples[0]
    return min_num

def write_csv(id,f_csv,radiant_win):
    a = datetime.now()
    match_data = get_match_data(id)
    print((datetime.now()-a).microseconds)
    b = datetime.now()
    hero_purchase = []
    if match_data:
        hero_purchase= get_purchase_data(match_data,radiant_win)
    for purchase in hero_purchase:
        hero_id = purchase[0]
        for items in purchase[1]:
            temp = [hero_id, items[0], items[1]]
            f_csv.writerow(temp)
    print((datetime.now()-b).microseconds)
def main():
    # max_num = 4276046111
    # with open('purchase.csv','w') as f:
    #     f_csv = csv.writer(f)
    #     f_csv.writerow(['hero_id','time','item'])
    #     for i in range(50):
    #         match_list = get_match_list(max_num)
    #         for match in match_list:
    #             try:
    #                 write_csv(match[0],f_csv,match[1])
    #                 time.sleep(0.5)
    #             except:
    #                 print(match[0])
    #                 continue
    #         max_num = find_min_match_id(match_list)


    a = datetime.now()
    id = 4197659256
    get_match_data(id)
    print((datetime.now()-a).microseconds)
    # refactor_data(match_data)
    # if match_data :
    #     get_purchase_data(match_data, True)
    # with open('temp.csv','w') as f:
    #     f_csv = csv.writer(f)
    #     f_csv.writerow(['hero_id','time','item'])
    #     a = datetime.now()
    #     write_csv(id,f_csv,True)
    #     b = datetime.now()
    #     print((b-a).microseconds)


if __name__ == '__main__':
    main()