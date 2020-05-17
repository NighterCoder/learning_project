package kafka

import org.apache.flink.api.common.restartstrategy.RestartStrategies
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer011, FlinkKafkaProducer011}

/**
  *
  * Created by 凌战 on 2019/2/11.
  */
object Kafka011Example {
  def main(args: Array[String]): Unit = {
    val args = Array("--input-topic", "input", "--output-topic", "output", "--bootstrap.servers", "192.168.106.130:9092",
      "--zookeeper.connect", "192.168.106.130:2181", "--group.id", "myConsumer")

    val parameterPool = ParameterTool.fromArgs(args)
    if (parameterPool.getNumberOfParameters < 5) {
      println("参数不全")
      return
    }

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //设置环境变量
    //设置该属性可以屏蔽掉日志打印
    env.getConfig.disableSysoutLogging()
    //设置重启策略
    env.getConfig.setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000))

    //env.getConfig.setRestartStrategy(RestartStrategies.failureRateRestart())
    //每隔5秒创建一个检查点
    env.enableCheckpointing(5000)
    env.getConfig.setGlobalJobParameters(parameterPool)
    //设置时间模型
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    //添加自定义数据源
    val res: DataStream[KafkaEvent] = env.addSource(
      //Kafka Consumer
      new FlinkKafkaConsumer011(
        parameterPool.getRequired("input-topic"),
        new KafkaEventSchema(),
        parameterPool.getProperties
      ).assignTimestampsAndWatermarks(new CustomWatermarkExtractor)
    ).keyBy(_.getWord).map(new RollingAdditionMapper)

    //res.writeAsText("C:\\log.txt")
    //println("ooooooooooooooooooooooooooooooooooooooooooo")
    res.print()

    //添加sink
    res.addSink(
      //Kafka Producer
      new FlinkKafkaProducer011(
        parameterPool.getRequired("output-topic"),
        new KafkaEventSchema,
        parameterPool.getProperties
      )
    )

    //开始执行job
    env.execute("Kafka 0.11 Example")


  }
}
