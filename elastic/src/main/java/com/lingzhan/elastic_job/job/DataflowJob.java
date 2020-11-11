package com.lingzhan.elastic_job.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.lingzhan.elastic_job.model.Order;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于处理流式作业
 * 分为数据抓取 和 数据处理
 * 1.定时任务根据规则触发 2.数据抓取 3.处理完数据后,再次抓取 4.若数据存在,继续处理;若不存在,则本次任务结束
 * <p>
 * DataflowJob的泛型,规定了抓取数据的返回类型
 * <p>
 * 适用于不间歇的数据处理,比如:第三方订单的抓取
 * Created by 凌战 on 2020/8/29
 */
public class DataflowJob implements com.dangdang.ddframe.job.api.dataflow.DataflowJob<Order> {


  private List<Order> orders = new ArrayList<Order>();


  {
    for (int i = 0; i < 100; i++) {
      Order order = new Order();
      order.setStatus(0);
      order.setOrderId(i + 1);
      orders.add(order);
    }
  }


  @Override
  public List<Order> fetchData(final ShardingContext shardingContext) {

    //订单号%分片总数 == 当前分片项
    List<Order> orderList =
        orders.stream().filter(o -> o.getStatus() == 0)
            .filter(o -> o.getOrderId() % shardingContext.getShardingTotalCount() ==
                shardingContext.getShardingItem())
            .collect(Collectors.toList());

    List<Order> subList = null;

    // 模拟每次只处理10条
    if (orderList.size() > 0) {
      subList = orderList.subList(0, 10);
    }

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    LocalTime time = LocalTime.now();
    System.out.println(time + ",我是分片项:" + shardingContext.getShardingItem() + ",我抓取的数据:" + subList);
    return subList;

  }

  @Override
  public void processData(ShardingContext shardingContext, List<Order> data) {
    data.forEach(o -> o.setStatus(1));
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    LocalTime time = LocalTime.now();
    System.out.println(time + ",我是分片项:" + shardingContext.getShardingItem() + ",我正在处理数据!");
  }


}
