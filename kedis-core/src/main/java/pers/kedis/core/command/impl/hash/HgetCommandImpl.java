package pers.kedis.core.command.impl.hash;

import pers.kedis.core.KedisDb;
import pers.kedis.core.command.AbstractCommand;
import pers.kedis.core.command.impl.hash.service.HashHandler;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.KedisKey;
import pers.kedis.core.dto.KedisValue;

import java.util.List;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class HgetCommandImpl extends AbstractCommand {

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> kedisDataList = getCommandList(channelDTO);
        KedisData key = kedisDataList.get(1);
        KedisData field = kedisDataList.get(2);
        KedisDb kedisDb = channelDTO.getKedisDb();
        KedisValue kedisValue = kedisDb.getValue(new KedisKey(key));
        if (Objects.nonNull(kedisValue)) {
            KedisData kedisData = HashHandler.getValueByField(kedisValue, field);
            if (Objects.nonNull(kedisData)) {
                return kedisData;
            }
        }
        return getNullKedisData();
    }

}
