package state;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by 凌战 on 2019/2/27
 */
public class KeyedStateMain {

    public static void main(String argsp[]) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // 设置checkpoint
        env.enableCheckpointing(60000);
        CheckpointConfig checkpointConf = env.getCheckpointConfig();
        checkpointConf.setMinPauseBetweenCheckpoints(30000L);
        checkpointConf.setCheckpointTimeout(10000L);
        checkpointConf.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);


        SingleOutputStreamOperator<Tuple2<Long, Long>> flow=env.fromElements(Tuple2.of(1L, 3L), Tuple2.of(1L, 5L), Tuple2.of(1L, 7L), Tuple2.of(1L, 4L), Tuple2.of(1L, 2L))
                .keyBy(0)
                .flatMap(new CountWithKeyedState());

        flow.print();
        env.execute("CountOnlyState");
    }

}
