package com.lingzhan.elastic_job.model;

/**
 * Created by 凌战 on 2020/8/30
 */
public class Order {

  private Integer orderId;
  // 0:未处理 1:已处理
  private Integer status;

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Order{" +
        "orderId=" + orderId +
        ", status=" + status +
        '}';
  }
}
