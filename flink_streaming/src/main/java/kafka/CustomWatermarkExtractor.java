package kafka; /**
 * Created by 凌战 on 2019/2/11.
 */

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * 自定义EventTime水印
 * Created by 凌战 on 2019/2/11.
 */

public class CustomWatermarkExtractor implements AssignerWithPeriodicWatermarks<KafkaEvent>, Serializable {

    private long currentTimestamp=Long.MIN_VALUE;


    @Nullable
    public Watermark getCurrentWatermark() {
        return new Watermark(currentTimestamp==Long.MIN_VALUE?Long.MIN_VALUE:currentTimestamp-1);
    }

    public long extractTimestamp(KafkaEvent element, long previousElementTimestamp) {
        this.currentTimestamp=element.getTimestamp();
        return element.getTimestamp();
    }

}
