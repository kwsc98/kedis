package pers.kedis.core.dto;

import io.netty.channel.Channel;
import lombok.Getter;
import pers.kedis.core.KedisDb;
import pers.kedis.core.exception.KedisException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author kwsc98
 */
@Getter
public class ChannelDTO {

    private String clientName;

    private KedisDb kedisDb;

    private List<KedisData> kedisDataList;

    private KedisData kedisData;

    private final Channel channel;

    private final boolean update;

    public ChannelDTO(KedisDb kedisDb, Channel channel) {
        this.channel = channel;
        this.update = true;
        setKedisDb(kedisDb);
    }

    public ChannelDTO(KedisDb kedisDb, Channel channel, boolean update) {
        this.channel = channel;
        this.update = update;
        setKedisDb(kedisDb);
    }

    public boolean isUpdate() {
        return update;
    }

    public void setKedisDb(KedisDb kedisDb) {
        if (Objects.isNull(kedisDb)) {
            throw new KedisException("KedisDb Is Null");
        }
        this.kedisDb = kedisDb;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setKedisDataList(List<KedisData> kedisDataList) {
        this.kedisDataList = kedisDataList;
    }

    public ChannelDTO setKedisData(KedisData kedisData) {
        this.kedisData = kedisData;
        return this;
    }

    public void close() {
        this.channel.close();
    }


}
