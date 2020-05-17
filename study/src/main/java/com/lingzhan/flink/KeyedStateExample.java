package com.lingzhan.flink;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

/**
 * Created by 凌战 on 2019/7/8
 */
public class KeyedStateExample {


    private static class CountWithKeyedState extends RichFlatMapFunction<Tuple2<Long,Long>, Tuple2<Long,Long>>{


        private transient ValueState<Tuple2<Long,Long>> sum;


        @Override
        public void flatMap(Tuple2<Long, Long> value, Collector<Tuple2<Long, Long>> out) throws Exception {
            Tuple2<Long,Long> currentSum=sum.value();

            if (null==currentSum){
                currentSum=Tuple2.of(0L,0L);
            }

            currentSum.f0+=1;
            currentSum.f1+=value.f1;

            sum.update(currentSum);
            if (currentSum.f0>=3){
                out.collect(Tuple2.of(value.f0,currentSum.f1/currentSum.f0));
                sum.clear();
            }


        }

        @Override
        public void open(Configuration parameters) throws Exception {


            StateTtlConfig ttlConfig=StateTtlConfig
                    .newBuilder(Time.seconds(1))
                    .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
                    .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
                    .build();

            ValueStateDescriptor<String> stateDescriptor=new ValueStateDescriptor<String>("text state",String.class);
            stateDescriptor.enableTimeToLive(ttlConfig);



        }
    }


    public static void main(String[] args) {


    }

}
