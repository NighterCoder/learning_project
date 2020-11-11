package com.tiangou.info_service.netty.basic.core;

/**
 * Created by 凌战 on 2020/8/26
 */
public class BasicRequest {

  public static final Integer DEFAULT_REQUEST_ID = 0;

  private final Integer requestId;

  public BasicRequest(Integer requestId){
    this.requestId = requestId;
  }

  public Integer getRequestId() {
    return requestId;
  }


}
