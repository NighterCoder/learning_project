package com.lingzhan.dashboard;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ContinuousProcessingTimeTrigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 凌战 on 2019/11/18
 */
public class StateProcessDemo {

    public static void main(String[] args) throws Exception {
        String hostName = "localhost";
        Integer port = Integer.parseInt("8801");

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //从指定socket获取输入数据
        DataStream<String> text = env.socketTextStream(hostName, port);

        text.flatMap(new LineSplitter())
                .keyBy(0)
                .window(TumblingProcessingTimeWindows.of(Time.seconds(120)))
                .process(new ProcessWindowFunction<Tuple2<String, Integer>, Tuple2<String,Integer>, Tuple, TimeWindow>() {

                    private MapState<String,Integer> res;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        super.open(parameters);
                    }

                    @Override
                    public void process(Tuple tuple, Context context, Iterable<Tuple2<String, Integer>> iterable, Collector<Tuple2<String, Integer>> collector) throws Exception {



                    }
                })
                .map(new ContinueTriggerDemo.TimestampAdd())
                .print();

        env.execute("start demo!");
    }

    public static final class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> out) throws Exception {
            String[] tokens = s.toLowerCase().split("\\W+");

            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<>(token, 1));
                }
            }
        }
    }


    public static final class TimestampAdd implements MapFunction<Tuple2<String, Integer>, Tuple3<String, String, Integer>> {

        @Override
        public Tuple3<String, String, Integer> map(Tuple2<String, Integer> value) throws Exception {

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String s = format.format(new Date());

            return new Tuple3<>(value.f0, s, value.f1);
        }
    }

}
