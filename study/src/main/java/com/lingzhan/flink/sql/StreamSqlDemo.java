package com.lingzhan.flink.sql;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;

/**
 * Created by 凌战 on 2020/2/24
 */
public class StreamSqlDemo {

    public static void main(String[] args) {

        // 获取运行环境
        StreamExecutionEnvironment env=StreamExecutionEnvironment.getExecutionEnvironment();

        // 创建一个TableEnvironment(流处理)
        StreamTableEnvironment streamTableEnv=StreamTableEnvironment.create(env);


        // 读取数据源
        DataStream<String> ds1=env.readTextFile("");

        // 数据转换
        DataStream<Tuple2<String,String>> ds2=ds1.map(new MapFunction<String, Tuple2<String,String>>() {
            @Override
            public Tuple2<String, String> map(String s) throws Exception {
                String[] split=s.split(",");
                return new Tuple2<>(split[0],split[1]);
            }
        });

        // 将DataStream转换为Table
        /*Table table=streamTableEnv.fromDataStream(ds2,"uid,name");
        // 注册为一个表
        streamTableEnv.registerTable("user",table);*/


        //或者
        streamTableEnv.createTemporaryView("user",ds2,"uid,name");
        Table table=streamTableEnv.sqlQuery("select name from user");

        // 最后将table转换成DataStream

        DataStream<String> res=streamTableEnv.toAppendStream(table,String.class);
        res.print();

    }

}
