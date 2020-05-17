package com.tiangou.info_service.entity;

import lombok.Data;

/**
 * 用户浏览记录
 * Created by 凌战 on 2019/2/15
 */
@Data
public class UserLog {

    private Long channelId;
    private Long categoryId;
    private Long productId;
    private Long userId;
    private String country;
    private String province;
    private String city;
    private String network;
    private String source;
    private String browserType;
    private Long startTime;
    private Long endTime;

}
