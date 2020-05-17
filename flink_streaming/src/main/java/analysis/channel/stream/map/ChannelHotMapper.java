package analysis.channel.stream.map;

import analysis.channel.entity.ChannelHot;
import com.alibaba.fastjson.JSONObject;
import com.tiangou.info_service.entity.KafkaMessage;
import com.tiangou.info_service.entity.UserLog;
import org.apache.flink.api.common.functions.RichMapFunction;

/**
 * 频道热点的map转换,将KafkaMessage转换成ChannelHot
 * Created by 凌战 on 2019/2/15
 */
public class ChannelHotMapper extends RichMapFunction<KafkaMessage, ChannelHot> {


    /**
     * ？？疑问：KafkaMessage是以json串的形式传入到Kafka中的，是如何转成KafkaMessage对象的呢？？
     * @param kafkaMessage Kafka Producer中以KafkaMessage进行传输
     * @return ChannelHot
     * @throws Exception
     */
    public ChannelHot map(KafkaMessage kafkaMessage) throws Exception {
        String jsonStr=kafkaMessage.getJsonMessage();
        //将json串转换成UserLog对象
        UserLog userLog= JSONObject.parseObject(jsonStr,UserLog.class);
        //再通过转换生成ChannelHot对象
        long channelId=userLog.getChannelId();
        long count=kafkaMessage.getCount();
        ChannelHot channelHot=new ChannelHot();
        channelHot.setChannelId(channelId);
        channelHot.setCount(count);
        return channelHot;
    }



}
