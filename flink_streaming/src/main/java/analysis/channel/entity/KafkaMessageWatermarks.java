package analysis.channel.entity;

import com.tiangou.info_service.entity.KafkaMessage;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

/**
 * 为KafkaMessage添加水位线
 * Created by 凌战 on 2019/2/15
 */
public class KafkaMessageWatermarks implements AssignerWithPeriodicWatermarks<KafkaMessage> {

    private long currentTimeStamp=Long.MIN_VALUE;

    @Nullable
    public Watermark getCurrentWatermark() {
        Watermark watermark=new Watermark(currentTimeStamp);
        return watermark;
    }

    //抽取时间戳
    public long extractTimestamp(KafkaMessage kafkaMessage, long l) {
        this.currentTimeStamp=kafkaMessage.getTimestamp();
        return kafkaMessage.getTimestamp();
    }

}
