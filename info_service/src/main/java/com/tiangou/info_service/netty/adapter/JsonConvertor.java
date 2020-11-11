package com.tiangou.info_service.netty.adapter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.tiangou.info_service.netty.basic.core.AttributesUpdateRequest;
import com.tiangou.info_service.netty.basic.core.BasicAttributesUpdateRequest;
import com.tiangou.info_service.netty.basic.data.kv.BooleanDataEntry;
import com.tiangou.info_service.netty.basic.data.kv.DoubleDataEntry;
import com.tiangou.info_service.netty.basic.data.kv.JsonDataEntry;
import com.tiangou.info_service.netty.basic.data.kv.KvEntry;
import com.tiangou.info_service.netty.basic.data.kv.LongDataEntry;
import com.tiangou.info_service.netty.basic.data.kv.StringDataEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 凌战 on 2020/8/26
 */
public class JsonConvertor {

  private static final Gson GSON=new Gson();
  public static final String CAN_T_PARSE_VALUE="Can't parse value:";









/*  public static AttributesUpdateRequest convertToAttributes(JsonElement element,int requestId){
    // 符合Json格式
    if(element.isJsonObject()){
      BasicAttributesUpdateRequest request=new BasicAttributesUpdateRequest(requestId);
      long ts=System.currentTimeMillis();
      request.add();
    }
  }
  */


  public static List<KvEntry> parseValues(JsonObject valuesObject){
    List<KvEntry> result=new ArrayList<>();
    for (Map.Entry<String,JsonElement> valueEntry:valuesObject.entrySet()){
      JsonElement element=valueEntry.getValue();
      if (element.isJsonPrimitive()){
        JsonPrimitive value=element.getAsJsonPrimitive();
        if (value.isString()){
          result.add(new StringDataEntry(valueEntry.getKey(),value.getAsString()));
        }else if(value.isBoolean()){
          result.add(new BooleanDataEntry(valueEntry.getKey(),value.getAsBoolean()));
        }else if(value.isNumber()){
          parseNumericValue(result,valueEntry,value);
        }else{
          throw new JsonSyntaxException(CAN_T_PARSE_VALUE + value);
        }

      }else if(element.isJsonObject()||element.isJsonArray()){
        result.add(new JsonDataEntry(valueEntry.getKey(), element.toString()));
      }else{
        throw new JsonSyntaxException(CAN_T_PARSE_VALUE + element);
      }
    }
    return result;
  }


  /**
   *
   * @param result
   * @param valueEntry
   * @param value
   */
  private static void parseNumericValue(List<KvEntry> result, Map.Entry<String, JsonElement> valueEntry, JsonPrimitive value) {
    if (value.getAsString().contains(".")) {
      result.add(new DoubleDataEntry(valueEntry.getKey(), value.getAsDouble()));
    } else {
      try {
        long longValue = Long.parseLong(value.getAsString());
        result.add(new LongDataEntry(valueEntry.getKey(), longValue));
      } catch (NumberFormatException e) {
        throw new JsonSyntaxException("Big integer values are not supported!");
      }
    }
  }






}
