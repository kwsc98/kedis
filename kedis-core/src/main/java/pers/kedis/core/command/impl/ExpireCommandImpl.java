package pers.kedis.core.command.impl;

import pers.kedis.core.command.AbstractCommand;
import pers.kedis.core.command.AbstractUpdateCommand;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.KedisKey;

import java.util.List;

/**
 * @author kwsc98
 */
public class ExpireCommandImpl extends AbstractUpdateCommand {

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> list = getCommandList(channelDTO);
        String key = list.get(1).getData().toString();
        Long time = Long.valueOf(list.get(2).getData().toString());
        KedisKey kedisKey = new KedisKey(key, time);
        channelDTO.getKedisDb().put(kedisKey, null);
        return getSuccessKedisDataV1();
    }

}
