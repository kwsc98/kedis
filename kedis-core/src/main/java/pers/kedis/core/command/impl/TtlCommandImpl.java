package pers.kedis.core.command.impl;

import pers.kedis.core.command.Command;
import pers.kedis.core.command.CommandAbstract;
import pers.kedis.core.common.utils.KedisUtil;
import pers.kedis.core.dto.*;

import java.util.List;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class TtlCommandImpl extends CommandAbstract {
    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> kedisDataList = getCommandList(channelDTO);
        String key = kedisDataList.get(1).getData().toString();
        KedisKey kedisKey = channelDTO.getKedisDb().getKey(new KedisKey(key));
        long res = -2;
        if (Objects.nonNull(kedisKey)) {
            res = kedisKey.getCurrentTimeMillis();
        }
        return new KedisData(DataType.INTEGER).setData(res);
    }
}