package analysis.channel.stream.task;

import analysis.channel.entity.ChannelHot;
import analysis.channel.entity.KafkaMessageSchema;
import analysis.channel.entity.KafkaMessageWatermarks;
import analysis.channel.stream.map.ChannelHotMapper;
import analysis.channel.stream.reduce.ChannelHotReducer;
import com.tiangou.info_service.entity.KafkaMessage;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

/**
 * 频道热点统计任务
 * Created by 凌战 on 2019/2/15
 */
public class ChannelHotTask {
    public static void main(String[] args) {
        args = new String[]{"--input-topic", "flinkTest1", "--output-topic", "output", "--bootstrap.servers", "192.168.106.130:9092",
                "--zookeeper.connect", "192.168.106.130:2181", "--group.id", "myConsumer", "--windows.size", "10", "--windows.slide", "5"};

        //加载参数
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        //参数集合的数目小于5
        if (parameterTool.getNumberOfParameters() < 5) {
            System.out.println("参数不足");
            return;
        }

        //加载流处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //屏蔽日志打印
        env.getConfig().disableSysoutLogging();
        //设置重启次数为4次，间隔10秒钟
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
        //每5秒创建一个checkpoint
        env.enableCheckpointing(5000);
        //设置时间模型
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);



        //env.readFile()


        //Kafka中有但是res这里就没有了
        //添加自定义数据源并操作
        DataStream<KafkaMessage> res = env.addSource(new FlinkKafkaConsumer011<KafkaMessage>(
                parameterTool.getRequired("input-topic"),
                new KafkaMessageSchema(),
                parameterTool.getProperties()
        ));

        //res.print();


        DataStream<ChannelHot> res1=res.assignTimestampsAndWatermarks(new KafkaMessageWatermarks()) //添加水印
                .map(new ChannelHotMapper())  //
                .keyBy("channelId")
                //指定滑动计数窗口的窗口长度和滑动长度 即每隔5个数计算最近10个数的count和
                .countWindow(Long.valueOf(parameterTool.getRequired("windows.size")), Long.valueOf(parameterTool.getRequired("windows.slide")))
                .reduce(new ChannelHotReducer());

        //保存
        /*res.addSink(new SinkFunction<ChannelHot>() {
            public void invoke(ChannelHot value) throws Exception {
               long count=value.getCount();
               long channelId=value.getChannelId();
               //保存到数据库中
            }
        });
        */
        System.out.println("数据来了：");

        res1.print();
        try {
            env.execute("Channel Hot Begin");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
