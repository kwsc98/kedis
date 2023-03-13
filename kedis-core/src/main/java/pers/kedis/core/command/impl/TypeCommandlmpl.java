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
public class TypeCommandlmpl extends CommandAbstract {
    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> list = getCommandList(channelDTO);
        KedisData key = list.get(1);
        KedisValue<?> kedisValue = channelDTO.getKedisDb().getValue(new KedisKey(key.getData().toString()));
        String res = "none";
        if (Objects.nonNull(kedisValue)) {
            res = kedisValue.getValueType().name().toLowerCase();
        }
        return new KedisData(DataType.BULK_STRING).setData(res);
    }
}