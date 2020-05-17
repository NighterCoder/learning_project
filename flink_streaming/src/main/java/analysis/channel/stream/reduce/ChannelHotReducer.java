package analysis.channel.stream.reduce;

import analysis.channel.entity.ChannelHot;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * Created by 凌战 on 2019/2/15
 */
public class ChannelHotReducer implements ReduceFunction<ChannelHot> {

    //每两个ChannelHot(id相同)相加生成新的ChannelHot
    public ChannelHot reduce(ChannelHot value1, ChannelHot value2) throws Exception {
        ChannelHot channelHot=new ChannelHot();
        channelHot.setChannelId(value1.getChannelId());
        channelHot.setCount(value1.getCount()+value2.getCount());
        return channelHot;
    }
}
