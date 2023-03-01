package pers.kedis.core.command.impl;

import pers.kedis.core.KedisDb;
import pers.kedis.core.common.utils.KedisUtil;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.command.CommandAbstract;

import java.util.List;

/**
 * @author kwsc98
 */
public class SetCommandImpl extends CommandAbstract {

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        KedisData kedisData = channelDTO.getKedisData();
        KedisDb kedisDb = channelDTO.getKedisDb();
        List<KedisData> kedisDataList = KedisUtil.convertList(kedisData.getData());
        String key = (String) kedisDataList.get(1).getData();
        KedisData value = kedisDataList.get(2);
        return getSuccessKedisData();
    }

}
