package pers.kedis.core.command.impl;

import pers.kedis.core.KedisDb;
import pers.kedis.core.KedisService;
import pers.kedis.core.command.CommandAbstract;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;

import java.util.List;

/**
 * @author kwsc98
 */
public class SelectCommandImpl extends CommandAbstract {
    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> list = getCommandList(channelDTO);
        String str = list.get(1).getData().toString();
        int index = Integer.parseInt(str);
        KedisDb kedisDb = KedisService.getkedisDb(index);
        channelDTO.setKedisDb(kedisDb);
        return getSuccessKedisDataV1();
    }
}
