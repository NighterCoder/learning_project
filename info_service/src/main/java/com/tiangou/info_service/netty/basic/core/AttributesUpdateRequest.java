package com.tiangou.info_service.netty.basic.core;

import com.tiangou.info_service.netty.basic.data.kv.AttributeKvEntry;
import com.tiangou.info_service.netty.basic.session.FromDeviceRequestMsg;
import java.util.Set;

/**
 * Created by 凌战 on 2020/8/26
 */
public interface AttributesUpdateRequest extends FromDeviceRequestMsg {

  Set<AttributeKvEntry> getAttributes();

}
