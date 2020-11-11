package com.tiangou.info_service.netty.basic.core;

import com.tiangou.info_service.netty.basic.data.kv.KvEntry;
import com.tiangou.info_service.netty.basic.session.SessionMsgType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 凌战 on 2020/8/26
 */
public class BasicTelemetryUploadRequest extends BasicRequest implements TelemetryUploadRequest {

  private static final long serialVersionUID = 1L;

  private final Map<Long, List<KvEntry>> data;

  public BasicTelemetryUploadRequest() {
    this(DEFAULT_REQUEST_ID);
  }

  public void add(long ts,KvEntry entry){
    List<KvEntry> tsEntries = data.get(ts);
    if (tsEntries == null) {
      tsEntries = new ArrayList<>();
      data.put(ts, tsEntries);
    }
    tsEntries.add(entry);
  }

  public BasicTelemetryUploadRequest(Integer requestId) {
    super(requestId);
    this.data=new HashMap<>();
  }

  @Override
  public Map<Long, List<KvEntry>> getData() {
    return data;
  }

  @Override
  public SessionMsgType getMsgType() {
    return SessionMsgType.POST_TELEMETRY_REQUEST;
  }
}
