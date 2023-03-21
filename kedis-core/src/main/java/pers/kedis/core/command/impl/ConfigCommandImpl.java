package pers.kedis.core.command.impl;

import pers.kedis.core.KedisService;
import pers.kedis.core.command.AbstractCommand;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.enums.DataType;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.exception.KedisException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kwsc98
 */
public class ConfigCommandImpl extends AbstractCommand {

    public String get = "get";

    public String databases = "databases";

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> list = getCommandList(channelDTO);
        String pre1 = list.get(1).getData().toString();
        String pre2 = list.get(2).getData().toString();
        if (!get.equalsIgnoreCase(pre1) || !databases.equalsIgnoreCase(pre2)) {
            throw new KedisException("Error Command");
        }
        List<KedisData> res = new ArrayList<>();
        res.add(new KedisData(DataType.BULK_STRING).setData("databases"));
        res.add(new KedisData(DataType.BULK_STRING).setData(String.valueOf(KedisService.KEDISCONFIG.getDbCount())));
        return new KedisData(DataType.RESP_ARRAY).setData(res);
    }


}
