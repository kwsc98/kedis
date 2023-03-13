package pers.kedis.core.command.impl;

import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.command.CommandAbstract;
import pers.kedis.core.dto.KedisKey;
import pers.kedis.core.dto.KedisValue;
import pers.kedis.core.dto.ValueType;

import java.util.List;

/**
 * @author kwsc98
 */
public class SetCommandImpl extends CommandAbstract {

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> kedisDataList = getCommandList(channelDTO);
        String key = (String) kedisDataList.get(1).getData();
        KedisData value = kedisDataList.get(2);
        channelDTO.getKedisDb().put(new KedisKey(key, -1L), new KedisValue<>(ValueType.String, value.getData().toString()));
        return getSuccessKedisDataV2();
    }

}
