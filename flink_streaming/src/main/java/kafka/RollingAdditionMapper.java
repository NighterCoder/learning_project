package kafka;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;

/**
 *
 * 自定义 Keyed State
 * Created by 凌战 on 2019/2/12.
 */

public class RollingAdditionMapper extends RichMapFunction<KafkaEvent, KafkaEvent> {

    //状态
    private transient ValueState<Integer> currentTotalCount;


    @Override
    public void open(Configuration parameters) throws Exception {
        ValueStateDescriptor descriptor=new ValueStateDescriptor("currentTotalCount",Integer.class);
        currentTotalCount=getRuntimeContext().getState(descriptor);
    }

    public KafkaEvent map(KafkaEvent kafkaEvent) throws Exception {
        //获取当前状态
        Integer totalCount=currentTotalCount.value();
        if (totalCount==null){
            totalCount=0;
        }
        totalCount+=kafkaEvent.getFrequency();
        //更新状态
        currentTotalCount.update(totalCount);
        return new KafkaEvent(kafkaEvent.getWord(),totalCount,kafkaEvent.getTimestamp());
    }



}
