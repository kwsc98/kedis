package pers.kedis.core.command.impl;

import pers.kedis.core.KedisDb;
import pers.kedis.core.command.AbstractUpdateCommand;
import pers.kedis.core.dto.*;
import pers.kedis.core.dto.enums.DataType;

import java.util.List;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class DelCommandImpl extends AbstractUpdateCommand {
    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> kedisDataList = getCommandList(channelDTO);
        KedisDb kedisDb = channelDTO.getKedisDb();
        long res = 0;
        for (int i = 1; i < kedisDataList.size(); i++) {
            KedisData key = kedisDataList.get(i);
            if (Objects.nonNull(kedisDb.remove(new KedisKey(key)))) {
                res++;
            }
        }
        return new KedisData(DataType.INTEGER).setData(res);
    }
}
