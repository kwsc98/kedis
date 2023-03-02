package pers.kedis.core.command.impl;

import pers.kedis.core.command.CommandAbstract;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.DataType;
import pers.kedis.core.dto.KedisData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kwsc98
 */
public class InfoCommandImpl extends CommandAbstract {

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> res = new ArrayList<>();
        res.add(new KedisData(DataType.BULK_STRING).setData("kedis_version:kedis_1.0.0"));
        res.add(new KedisData(DataType.BULK_STRING).setData("os:" + System.getProperty("os.name")));
        return new KedisData(DataType.RESP_ARRAY).setData(res);
    }

}
