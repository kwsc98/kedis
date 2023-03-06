package pers.kedis.core.command.impl;

import pers.kedis.core.KedisDb;
import pers.kedis.core.command.CommandAbstract;
import pers.kedis.core.common.structure.DictType;
import pers.kedis.core.common.utils.KedisUtil;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.KedisKey;
import pers.kedis.core.dto.KedisValue;

import java.util.List;

public class NsetCommandImpl extends CommandAbstract {
    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        KedisData kedisData = channelDTO.getKedisData();
        KedisDb kedisDb = channelDTO.getKedisDb();
        List<KedisData> kedisDataList = KedisUtil.convertList(kedisData.getData());
        String key = (String) kedisDataList.get(1).getData();
        KedisData value = kedisDataList.get(2);
        kedisDb.put(DictType.String, new KedisKey(key), new KedisValue<>(value.getData().toString()));
        return getSuccessKedisData();
    }
}
