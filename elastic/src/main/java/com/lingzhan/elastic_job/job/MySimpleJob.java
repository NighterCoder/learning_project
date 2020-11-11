package com.lingzhan.elastic_job.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * Simple作业,意思是定时任务的简单实现 只需要实现execute方法
 * 提供了弹性扩容和分片功能
 *
 * Created by 凌战 on 2020/5/26
 */
public class MySimpleJob implements SimpleJob {

  @Override
  public void execute(ShardingContext shardingContext) {
    System.out.println("我是分片项："+shardingContext.getShardingItem()+
        ",总分片项："+shardingContext.getShardingTotalCount());
  }


}
