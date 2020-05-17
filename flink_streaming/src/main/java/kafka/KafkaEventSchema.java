package kafka;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

/**
 *
 * Created by 凌战 on 2019/2/11.
 */

public class KafkaEventSchema implements DeserializationSchema<KafkaEvent>, SerializationSchema<KafkaEvent> {

    //反序列化
    public KafkaEvent deserialize(byte[] message) throws IOException {
        return KafkaEvent.fromString(new String(message));
    }

    public boolean isEndOfStream(KafkaEvent kafkaEvent) {
        return false;
    }

    //序列化
    public byte[] serialize(KafkaEvent kafkaEvent) {
        return kafkaEvent.toString().getBytes();
    }

    public TypeInformation<KafkaEvent> getProducedType() {
        return TypeInformation.of(KafkaEvent.class);
    }
}
