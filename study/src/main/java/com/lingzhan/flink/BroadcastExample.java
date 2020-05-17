package com.lingzhan.flink;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.configuration.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Flink中广播变量的使用
 * Created by 凌战 on 2019/7/26
 */
public class BroadcastExample {

    public static void main(String[] args) throws Exception {

        ExecutionEnvironment env=ExecutionEnvironment.getExecutionEnvironment();
        DataSet<Integer> broadCast=env.fromElements(1,2,3);
        DataSet<String> data=env.fromElements("a","b");

        data.map(new RichMapFunction<String, String>() {

            private List list=new ArrayList();

            //执行RichMapFunction最先调用
            @Override
            public void open(Configuration parameters) throws Exception {
                //3.获取广播变量数据
                Collection<Integer> broadcastSet= getRuntimeContext().getBroadcastVariable("number");
                list.addAll(broadcastSet);
            }

            @Override
            public String map(String value) throws Exception {
                return value+": "+list;
            }


        }).withBroadcastSet(broadCast,"number").print(); //2.定义广播变量“number”

        //todo env不一定非得用execute方法,用print也能触发执行



    }



}
