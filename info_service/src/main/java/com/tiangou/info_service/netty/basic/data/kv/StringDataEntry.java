package com.tiangou.info_service.netty.basic.data.kv;

import com.tiangou.info_service.netty.DataType;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by 凌战 on 2020/8/26
 */
public class StringDataEntry extends BasicKvEntry {

  private static final long  serialVersionUID = 1L;
  private final String value;

  public StringDataEntry(String key, String value){
    super(key);
    this.value = value;
  }

  @Override
  public Optional<String> getStrValue() {
    return Optional.of(value);
  }

  @Override
  public DataType getDataType() {
    return DataType.STRING;
  }

  @Override
  public String getValueAsString() {
    return value;
  }

  @Override
  public Object getValue() {
    return value;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StringDataEntry)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    StringDataEntry that = (StringDataEntry) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), value);
  }

  @Override
  public String toString() {
    return "StringDataEntry{" + "value='" + value + '\'' + "} " + super.toString();
  }


}
