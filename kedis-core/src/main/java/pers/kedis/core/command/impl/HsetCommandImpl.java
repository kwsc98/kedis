package pers.kedis.core.command.impl;

import pers.kedis.core.KedisDb;
import pers.kedis.core.command.AbstractUpdateCommand;
import pers.kedis.core.command.Command;
import pers.kedis.core.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class HsetCommandImpl extends AbstractUpdateCommand {


    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> kedisDataList = getCommandList(channelDTO);
        String key = kedisDataList.get(1).getData().toString();
        KedisData field = kedisDataList.get(2);
        KedisData value = kedisDataList.get(3);
        KedisDb kedisDb = channelDTO.getKedisDb();
        KedisKey kedisKey = new KedisKey(key);
        KedisValue kedisValue = kedisDb.getValue(kedisKey);
        if (Objects.isNull(kedisValue)) {
            kedisKey.setCurrentTimeMillis(-1L);
            kedisValue = new KedisValue(ValueType.Hash, new ArrayList<>());
        }
        List<KedisData> list = kedisValue.getValue();
        boolean isHave = false;
        for (int i = 0; i < list.size(); i += 2) {
            if (Objects.equals(field.getData(), list.get(i).getData())) {
                list.set(i + 1, value);
                isHave = true;
                break;
            }
        }
        if (!isHave) {
            list.add(field);
            list.add(value);
        }
        kedisDb.put(kedisKey, kedisValue);
        return getSuccessKedisDataV3();
    }
}
