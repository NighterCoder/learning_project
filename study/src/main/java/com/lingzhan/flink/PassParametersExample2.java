package com.lingzhan.flink;

import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.configuration.Configuration;

/**
 * Created by 凌战 on 2019/7/26
 */
public class PassParametersExample2 {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Integer> toFilter=env.fromElements(1,2,3);

        //1.定义一个Configuration类,定义limit为2
        Configuration config=new Configuration();
        config.setInteger("limit",2);

        toFilter.filter(new RichFilterFunction<Integer>() {

            private int limit;

            @Override
            public void open(Configuration parameters) throws Exception {
                //获取配置类中的参数
                limit=parameters.getInteger("limit",0);
            }

            @Override
            public boolean filter(Integer value) throws Exception {
                return value>limit;
            }
        }).withParameters(config).print();  //2.使用withParameters传入配置类



    }


}
