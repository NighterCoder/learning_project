package com.tiangou.info_service.netty.basic.data.kv;

import com.tiangou.info_service.netty.DataType;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by 凌战 on 2020/8/26
 */
public class LongDataEntry extends BasicKvEntry {

  private final Long value;

  public LongDataEntry(String key, Long value) {
    super(key);
    this.value = value;
  }

  @Override
  public DataType getDataType() {
    return DataType.LONG;
  }

  @Override
  public String getValueAsString() {
    return Long.toString(value);
  }

  @Override
  public Object getValue() {
    return value;
  }

  @Override
  public Optional<Long> getLongValue() {
    return Optional.of(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LongDataEntry)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    LongDataEntry that = (LongDataEntry) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), value);
  }

  @Override
  public String toString() {
    return "LongDataEntry{" +
        "value=" + value +
        "} " + super.toString();
  }


}
