package com.lingzhan.flink;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

/**
 * 案例讲解 事件流：1,2,3,4,5,1,3,4,5,6,7,1,4,5,3,9,9,2,1
 * 返回两个1之间发生过多少次其他事件,分别是什么事件
 * 4,(2,3,4,5) 5,(3,4,5,6,7) 6,(4,5,3,9,9,2)
 * Created by 凌战 on 2019/7/7
 */
public class OperatorStateExample {



    //应该是实现哪一个RichFunction接口类??? 不是一进一出,应该是FlatMap
    private static class CountWithStateOperator extends RichFlatMapFunction<Long, Tuple2<Integer, List<String>>> implements CheckpointedFunction {
        //来一个Long元素,就存放到ListState
        /**
         * 中途如果挂了的话,托管状态已经备份到后端,重新启动可以拿到数据
         * 这时候原生状态就需要依赖托管状态来恢复
         */
        private transient ListState<Long> checkPointCountList;

        /**
         * 这里为什么还要定义一个原始状态,为甚么不直接用托管状态呢?
         * 因为我们需要用到两个1之间保存的数的大小,如果size>0,说明这个1是结束的1,可以输出了
         * 而托管状态没有size
         */
        private List<Long> listBufferElements;

        //最开始调用的方法
        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            listBufferElements=new ArrayList<>();
        }

        //会调用一次
        @Override
        public void initializeState(FunctionInitializationContext context) throws Exception {
            //context.getOperatorStateStore().getListState();
        }


        @Override
        public void flatMap(Long in, Collector<Tuple2<Integer, List<String>>> out) throws Exception {

            if (in==1){
                //如果输入的是1,则准备初始化这一阶段的Tuple2或者直接输出
                if (listBufferElements.size()>0){
                    List<String> res=new ArrayList<>();
                    for(Long ele:listBufferElements){
                        res.add(String.valueOf(ele));
                    }
                    out.collect(Tuple2.of(listBufferElements.size(),res));
                    listBufferElements.clear();
                }

            }else{

                listBufferElements.add(in);
            }



        }

        @Override
        /**
         * checkpoint的时候执行
         */
        public void snapshotState(FunctionSnapshotContext functionSnapshotContext) throws Exception {

        }






    }


    public static void main(String[] args) {


    }


}
