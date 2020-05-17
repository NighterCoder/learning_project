package state;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

/**
 *
 * (1,4),(2,3),(3,1),(1,2),(3,2),(1,2),(2,2),(2,9)
 * 那么事件1:8/3=2
 * 那么事件2:14/3=4
 * 这是keyBy之后的
 * Created by 凌战 on 2019/2/27
 */
public class CountWithKeyedState extends RichFlatMapFunction<Tuple2<Long,Long>,Tuple2<Long,Long>> {

    //第一个值是用于计数,达到2个数就算平均值
    //第二个值就求和
    private transient ValueState<Tuple2<Long,Long>> sum;




    public void flatMap(Tuple2<Long, Long> input, Collector<Tuple2<Long, Long>> out) throws Exception {

        //获取状态值
        Tuple2<Long,Long> currentSum=sum.value();

        //初始化
        if (null==currentSum){
            currentSum=Tuple2.of(0l,0l);
        }

        //更新计数值
        currentSum.f0+=1;

        //求和
        currentSum.f1+=input.f1;

        //更新最新求和值
        sum.update(currentSum);

        if (currentSum.f0>=3){
            //计算完后sum要重新clear
            out.collect(new Tuple2<Long, Long>(input.f0,currentSum.f1/currentSum.f0));
            sum.clear();

        }

    }


    @Override
    public void open(Configuration parameters) throws Exception {
        //最先调用的方法,相当于initializeState方法
        ValueStateDescriptor<Tuple2<Long,Long>> descriptor=new ValueStateDescriptor<Tuple2<Long, Long>>(
                "average",
                TypeInformation.of(new TypeHint<Tuple2<Long, Long>>() {})
        );

        sum=getRuntimeContext().getState(descriptor);




    }
}
