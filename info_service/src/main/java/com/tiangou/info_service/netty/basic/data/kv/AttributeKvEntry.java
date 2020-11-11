package com.tiangou.info_service.netty.basic.data.kv;

/**
 * Created by 凌战 on 2020/8/26
 */
public interface AttributeKvEntry extends KvEntry {
  long getLastUpdateTs();
}
