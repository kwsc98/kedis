package pers.kedis.core.command.impl.hash;

import pers.kedis.core.KedisDb;
import pers.kedis.core.command.AbstractUpdateCommand;
import pers.kedis.core.command.impl.hash.service.HashHandler;
import pers.kedis.core.common.structure.Dict;
import pers.kedis.core.dto.*;
import pers.kedis.core.dto.enums.ValueType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class HsetCommandImpl extends AbstractUpdateCommand {


    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> kedisDataList = getCommandList(channelDTO);
        KedisData key = kedisDataList.get(1);
        KedisData field = kedisDataList.get(2);
        KedisData value = kedisDataList.get(3);
        KedisDb kedisDb = channelDTO.getKedisDb();
        KedisKey kedisKey = new KedisKey(key);
        KedisValue kedisValue = kedisDb.getValue(kedisKey);
        if (Objects.isNull(kedisValue)) {
            kedisKey.setCurrentTimeMillis(-1L);
            kedisValue = new KedisValue(ValueType.Hash, new ArrayList<>());
        } else {
            if (kedisValue.getValueType() != ValueType.Hash) {
                return getErrorForKeyType();
            }
        }
        kedisValue = HashHandler.setData(kedisValue, field, value);
        kedisDb.put(kedisKey, kedisValue);
        return getSuccessKedisDataV3();
    }

}
