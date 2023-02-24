package pers.kedis.core.command.impl;

import pers.kedis.core.KedisService;
import pers.kedis.core.command.CommandAbstract;
import pers.kedis.core.common.utils.KedisUtil;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.DataType;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.exception.KedisException;

import java.util.List;

/**
 * @author kwsc98
 */
public class ConfigCommandImpl extends CommandAbstract {

    public String get = "get";

    public String databases = "databases";

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        KedisData kedisData = channelDTO.getKedisData();
        List<KedisData> kedisDataList = KedisUtil.convertList(kedisData.getData());
        String pre1 = kedisDataList.get(1).getData().toString();
        String pre2 = kedisDataList.get(2).getData().toString();
        if (!get.equalsIgnoreCase(pre1) || !databases.equals(pre2)) {
            throw new KedisException("Error Command");
        }
        return new KedisData(DataType.INTEGER).setData(KedisService.KEDISCONFIG.getDbCount());
    }


}
