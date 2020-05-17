package com.tiangou.info_service.data;

import com.alibaba.fastjson.JSONObject;
import com.tiangou.info_service.entity.UserLog;
import com.tiangou.info_service.utils.MessageSendUtil;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 模拟电商网站日志生成
 * Created by 凌战 on 2019/2/15
 */
public class LogProducer {


    //频道id 类别id 产品id 用户id 打开时间 离开时间 地区 网络方式 来源方式 浏览器
    private static Long[] channelIds = new Long[]{1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l};//频道id集合
    private static Long[] categoryIds = new Long[]{1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l};//产品类别id集合
    private static Long[] productIds = new Long[]{1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l};//产品id集合
    private static Long[] userIds = new Long[]{1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l};//用户id集合

    /**
     * 地区
     */
    private static String[] countries = new String[]{"America", "China"};//地区-国家集合
    private static String[] provinces = new String[]{"NewYork", "LiaoNing"};//地区-省集合
    private static String[] citys = new String[]{"NewYork", "DaLian"};//地区-市集合

    /**
     * 网络方式
     */
    private static String[] networks = new String[]{"电信", "移动", "联通"};

    /**
     * 来源方式
     */
    private static String[] sources = new String[]{"直接输入", "百度跳转", "360搜索跳转", "必应跳转"};

    /**
     * 浏览器
     */
    private static String[] browsers = new String[]{"火狐", "qq浏览器", "360浏览器", "谷歌浏览器"};


    private static List<Long[]> userTimeLog=LogProducer.produceTimes();

    private static List<Long[]>  produceTimes(){
        List<Long[]> timeLog=new ArrayList<>();
        for (int i=0;i<10;i++){
            Long[] timesArray=getTimes("2018-02-09 12:45:45:014");
            timeLog.add(timesArray);
        }
        return timeLog;
    }

    //产生时间
    private static Long [] getTimes(String time){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
        try {
            Date date = dateFormat.parse(time);
            long timeStamp = date.getTime();
            Random random = new Random();
            int randomInt = random.nextInt(10);
            long startTime = timeStamp - randomInt*3600*1000;
            long endTime = timeStamp;
            return new Long[]{startTime,endTime};
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Long[]{0l,0l};
    }


    //生成模拟的电商浏览记录
    public static void main(String[] args) {
        Random random=new Random();
        while (true) {
            for (int i = 0; i < 1; i++) {
                UserLog userLog = new UserLog();

                userLog.setChannelId(1l);
                userLog.setCategoryId(categoryIds[random.nextInt(categoryIds.length)]);
                userLog.setProductId(productIds[random.nextInt(productIds.length)]);
                userLog.setUserId(userIds[random.nextInt(userIds.length)]);
                userLog.setCountry(countries[random.nextInt(countries.length)]);
                userLog.setProvince(provinces[random.nextInt(provinces.length)]);
                userLog.setCity(citys[random.nextInt(citys.length)]);

                userLog.setNetwork(networks[random.nextInt(networks.length)]);
                userLog.setSource(sources[random.nextInt(sources.length)]);
                userLog.setBrowserType(browsers[random.nextInt(browsers.length)]);

                Long[] times = userTimeLog.get(random.nextInt(userTimeLog.size()));
                userLog.setStartTime(times[0]);
                userLog.setEndTime(times[1]);

                //将对象转成json串
                String jsonStr = JSONObject.toJSONString(userLog);
                //将消息发送到收集信息的接口
                MessageSendUtil.sendMessage("http://localhost:6097/infoSearch/webInfo", jsonStr);


            }

        }



    }







}
