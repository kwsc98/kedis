package pers.kedis.core.command.impl;

import pers.kedis.core.command.AbstractCommand;
import pers.kedis.core.dto.*;
import pers.kedis.core.dto.enums.DataType;

import java.util.List;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class TtlCommandImpl extends AbstractCommand {
    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> kedisDataList = getCommandList(channelDTO);
        KedisData key = kedisDataList.get(1);
        KedisKey kedisKey = channelDTO.getKedisDb().getKey(new KedisKey(key));
        long res = -2;
        if (Objects.nonNull(kedisKey)) {
            res = kedisKey.getCurrentTimeMillis();
        }
        return new KedisData(DataType.INTEGER).setData(res);
    }
}
