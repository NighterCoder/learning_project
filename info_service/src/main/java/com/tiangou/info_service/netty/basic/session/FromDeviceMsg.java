package com.tiangou.info_service.netty.basic.session;

import java.io.Serializable;

/**
 * Created by 凌战 on 2020/8/26
 */
public interface FromDeviceMsg extends Serializable {

  SessionMsgType getMsgType();

}
