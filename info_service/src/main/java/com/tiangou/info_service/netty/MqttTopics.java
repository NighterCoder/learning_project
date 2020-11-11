package com.tiangou.info_service.netty;

/**
 * Created by 凌战 on 2020/8/26
 */
public class MqttTopics {
  public static final String BASE_DEVICE_API_TOPIC = "v1/devices/me";
  public static final String DEVICE_TELEMETRY_TOPIC = BASE_DEVICE_API_TOPIC + "/telemetry";
  public static final String DEVICE_ATTRIBUTES_TOPIC = BASE_DEVICE_API_TOPIC + "/attributes";
  public static final String DEVICE_ATTRIBUTES_REQUEST_TOPIC_PREFIX = BASE_DEVICE_API_TOPIC + "/attributes/request/";

  public static final String DEVICE_ATTRIBUTES_RESPONSE_TOPIC_PREFIX = BASE_DEVICE_API_TOPIC + "/attributes/response/";
  public static final String DEVICE_ATTRIBUTES_RESPONSES_TOPIC = DEVICE_ATTRIBUTES_RESPONSE_TOPIC_PREFIX + "+";

}
