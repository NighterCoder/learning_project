package com.lingzhan.flink;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * Created by 凌战 on 2019/7/26
 */
public class PassParametersExample1 {

    public static void main(String[] args) throws Exception {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Integer> toFilter = env.fromElements(1, 2, 3);

        toFilter.filter(new MyFilter(2)).print();


    }

    private static class MyFilter implements FilterFunction<Integer> {

        private final int limit;

        public MyFilter(int limit) {
            this.limit = limit;
        }

        @Override
        public boolean filter(Integer value) throws Exception {
            return value > limit;
        }
    }


}
