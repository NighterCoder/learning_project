package state;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.util.Collector;

import java.util.List;

/**
 * Created by 凌战 on 2019/2/27
 */
public class CountWithOperatorState extends RichFlatMapFunction<Long,String> implements CheckpointedFunction {


    private transient ListState<Long> checkPointCountList;
    private List<Long> listBufferElements;


    public void flatMap(Long r, Collector<String> collector) throws Exception {
        if (r == 1) {
            if (listBufferElements.size() > 0) {
                StringBuffer buffer = new StringBuffer();
                for(int i = 0 ; i < listBufferElements.size(); i ++) {
                    buffer.append(listBufferElements.get(i) + " ");
                }
                collector.collect(buffer.toString());
                listBufferElements.clear();
            }
        } else {
            listBufferElements.add(r);
        }
    }


    //隔一段时间做一次快照
    public void snapshotState(FunctionSnapshotContext functionSnapshotContext) throws Exception {
        //先进行一次clear,因为当前保存到数据已经通过上一次checkpoint记录下来
        checkPointCountList.clear();
        for(int i=0;i<listBufferElements.size();i++){
            checkPointCountList.add(listBufferElements.get(i));
        }

    }

    public void initializeState(FunctionInitializationContext functionInitializationContext) throws Exception {

        //1.对ListState进行存储类型描述,就是定义一个ListStateDescriptor类
        ListStateDescriptor<Long> listStateDescriptor=new ListStateDescriptor<Long>("listForThree", TypeInformation.of(new TypeHint<Long>() {}));

        //2.通过上下文,再根据上面的类型描述获取对应的ListState
        checkPointCountList=functionInitializationContext.getOperatorStateStore().getListState(listStateDescriptor);

        //3.如果处于数据恢复阶段
        if(functionInitializationContext.isRestored()){
            //如果有数据就添加进去
            for(Long element:checkPointCountList.get()){
                listBufferElements.add(element);
            }
        }


    }
}
