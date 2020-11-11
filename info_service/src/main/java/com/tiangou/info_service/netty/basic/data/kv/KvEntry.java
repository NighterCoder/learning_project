package com.tiangou.info_service.netty.basic.data.kv;

import com.tiangou.info_service.netty.DataType;
import java.io.Serializable;
import java.util.Optional;

/**
 * Created by 凌战 on 2020/8/26
 */
public interface KvEntry extends Serializable {

  String getKey();

  DataType getDataType();

  Optional<String> getStrValue();

  Optional<Long> getLongValue();

  Optional<Boolean> getBooleanValue();

  Optional<Double> getDoubleValue();

  Optional<String> getJsonValue();

  String getValueAsString();

  Object getValue();

}
