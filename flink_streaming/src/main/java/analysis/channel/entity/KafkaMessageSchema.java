package analysis.channel.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tiangou.info_service.entity.KafkaMessage;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

/**
 * Created by 凌战 on 2019/2/15
 */
public class KafkaMessageSchema implements DeserializationSchema<KafkaMessage>, SerializationSchema<KafkaMessage> {

    //这里的bytes是KafkaMessage转换成的json串，并且字节化
    public KafkaMessage deserialize(byte[] bytes) {
        String jsonStr=new String(bytes);
        KafkaMessage kafkaMessage=JSONObject.parseObject(jsonStr,KafkaMessage.class);
        return kafkaMessage;
    }

    public boolean isEndOfStream(KafkaMessage kafkaMessage) {
        return false;
    }

    public byte[] serialize(KafkaMessage kafkaMessage) {
        return JSON.toJSONString(kafkaMessage).getBytes();
    }

    public TypeInformation<KafkaMessage> getProducedType() {
        return TypeInformation.of(KafkaMessage.class);
    }

}
