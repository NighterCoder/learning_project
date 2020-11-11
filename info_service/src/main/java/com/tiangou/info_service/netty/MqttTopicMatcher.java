package com.tiangou.info_service.netty;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * https://www.cnblogs.com/jason1990/p/11589297.html
 * Created by 凌战 on 2020/8/26
 */
public class MqttTopicMatcher {

  private final String topic;
  private final Pattern topicRegex;

  MqttTopicMatcher(String topic){
    if (topic == null){
      throw new NullPointerException("topic");
    }
    this.topic = topic;
    // +代表单层通配符,所以后面不能出现/,否则代表层级
    // #代表多层级通配符,所以用.+,可匹配任意字符
    // 最后加上$
    // (有些存在特殊字符的,如/以及$,需要转义处理)
    this.topicRegex = Pattern.compile(topic.replace("+", "[^/]+").replace("#", ".+") + "$");
  }

  public String getTopic() {
    return topic;
  }

  public boolean matches(String topic){
    return this.topicRegex.matcher(topic).matches();
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MqttTopicMatcher that = (MqttTopicMatcher) o;
    return topic.equals(that.topic);
  }

  @Override
  public int hashCode() {
    return topic.hashCode();
  }
}
