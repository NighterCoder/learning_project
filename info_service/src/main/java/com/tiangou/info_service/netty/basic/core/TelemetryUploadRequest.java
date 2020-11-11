package com.tiangou.info_service.netty.basic.core;

import com.tiangou.info_service.netty.basic.data.kv.KvEntry;
import com.tiangou.info_service.netty.basic.session.FromDeviceRequestMsg;
import java.util.List;
import java.util.Map;

/**
 * Created by 凌战 on 2020/8/26
 */
public interface TelemetryUploadRequest extends FromDeviceRequestMsg {
  Map<Long, List<KvEntry>> getData();
}
