package com.tiangou.info_service.netty.basic.data.kv;

import java.util.Objects;
import java.util.Optional;

/**
 *
 * key的类型是确定的,String类型
 * 但是value类型是不确定的
 *
 * Created by 凌战 on 2020/8/26
 */
public abstract class BasicKvEntry implements KvEntry {

  private final String key;

  protected BasicKvEntry(String key){
    this.key=key;
  }

  @Override
  public String getKey() {
    return key;
  }


  @Override
  public Optional<String> getStrValue() {
    return Optional.ofNullable(null);
  }

  @Override
  public Optional<Long> getLongValue() {
    return Optional.ofNullable(null);
  }

  @Override
  public Optional<Boolean> getBooleanValue() {
    return Optional.ofNullable(null);
  }

  @Override
  public Optional<Double> getDoubleValue() {
    return Optional.ofNullable(null);
  }

  @Override
  public Optional<String> getJsonValue() {
    return Optional.ofNullable(null);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BasicKvEntry)) {
      return false;
    }
    BasicKvEntry that = (BasicKvEntry) o;
    return Objects.equals(key, that.key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key);
  }

  @Override
  public String toString() {
    return "BasicKvEntry{" +
        "key='" + key + '\'' +
        '}';
  }


}
