package analysis.channel.entity;

import lombok.Data;

/**
 * 频道热点,通过实时更新各channel的访问数，来统计channel热点
 * Created by 凌战 on 2019/2/15
 */
@Data
public class ChannelHot {

    private Long channelId;
    private Long count;


    @Override
    public String toString() {
        return "ChannelHot{" +
                "channelId=" + channelId +
                ", count=" + count +
                '}';
    }
}
