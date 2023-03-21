package pers.kedis.core.command.impl.hash;

import pers.kedis.core.KedisDb;
import pers.kedis.core.command.AbstractCommand;
import pers.kedis.core.command.impl.hash.service.HashHandler;
import pers.kedis.core.dto.*;
import pers.kedis.core.dto.enums.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class HgetAllCommandImpl extends AbstractCommand {
    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> kedisDataList = getCommandList(channelDTO);
        KedisData key = kedisDataList.get(1);
        KedisDb kedisDb = channelDTO.getKedisDb();
        KedisValue kedisValue = kedisDb.getValue(new KedisKey(key));
        List<KedisData> res = new ArrayList<>();
        if (Objects.nonNull(kedisValue)) {
            res = HashHandler.getAllData(kedisValue);
        }
        return new KedisData(DataType.RESP_ARRAY).setData(res);
    }
}
