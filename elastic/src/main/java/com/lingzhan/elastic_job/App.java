package com.lingzhan.elastic_job;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.lingzhan.elastic_job.job.DataflowJob;
import com.lingzhan.elastic_job.job.MySimpleJob;

/**
 * Created by 凌战 on 2020/5/26
 */
public class App {

  public static void main(String[] args) {
    System.out.println("Hello World");
    new JobScheduler(zkCenter(),configurationScript()).init();
  }


  /**
   * zookeeper注册中心
   */
  public static CoordinatorRegistryCenter zkCenter() {
    ZookeeperConfiguration zc = new ZookeeperConfiguration("localhost:2181", "java-simple-job");
    CoordinatorRegistryCenter crc = new ZookeeperRegistryCenter(zc);
    crc.init();
    return crc;
  }


  /**
   * Job 配置
   */
  public static LiteJobConfiguration configuration() {
    // 1.job核心配置
    JobCoreConfiguration jcc = JobCoreConfiguration
        .newBuilder("mySimpleJob", "0/10 * * * * ?", 2)
        .build();
    // 2.job类型配置
    JobTypeConfiguration jtc = new SimpleJobConfiguration(jcc,
        MySimpleJob.class.getCanonicalName());
    // 3.job根的配置
    LiteJobConfiguration ljc = LiteJobConfiguration
        .newBuilder(jtc)
        // 如果不加overwrite,那么对job的一些配置,比如说定时时间的修改,不会生效
        .overwrite(true)
        .build();
    return ljc;

  }


  /**
   * Job 配置
   */
  public static LiteJobConfiguration configurationDataflow() {
    // 1.job核心配置
    JobCoreConfiguration jcc = JobCoreConfiguration
        .newBuilder("myDataflowJob", "0/10 * * * * ?", 2)
        .build();

    // 2.job类型配置(如果第三个参数设置为false,那么数据抓取和数据处理只会执行1次)
    JobTypeConfiguration jtc = new DataflowJobConfiguration(jcc,
        DataflowJob.class.getCanonicalName(),true);

    // 3.job根的配置
    LiteJobConfiguration ljc = LiteJobConfiguration
        .newBuilder(jtc)
        // 如果不加overwrite,那么对job的一些配置,比如说定时时间的修改,不会生效
        .overwrite(true)
        .build();
    return ljc;

  }

  /**
   * Job 配置
   */
  public static LiteJobConfiguration configurationScript() {
    // 1.job核心配置
    JobCoreConfiguration jcc = JobCoreConfiguration
        .newBuilder("myScriptJob", "0/10 * * * * ?", 2)
        .build();

    // 2.job类型配置(如果第三个参数设置为false,那么数据抓取和数据处理只会执行1次)
    JobTypeConfiguration jtc = new ScriptJobConfiguration(jcc,
        "C:\\resources\\test.cmd");

    // 3.job根的配置
    LiteJobConfiguration ljc = LiteJobConfiguration
        .newBuilder(jtc)
        // 如果不加overwrite,那么对job的一些配置,比如说定时时间的修改,不会生效
        .overwrite(true)
        .build();
    return ljc;

  }

}
