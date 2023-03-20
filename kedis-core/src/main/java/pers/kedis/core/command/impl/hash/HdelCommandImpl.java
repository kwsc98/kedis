package pers.kedis.core.command.impl.hash;

import pers.kedis.core.KedisDb;
import pers.kedis.core.command.AbstractUpdateCommand;
import pers.kedis.core.command.impl.hash.service.HashHandler;
import pers.kedis.core.common.structure.Dict;
import pers.kedis.core.dto.*;

import java.util.*;

/**
 * @author kwsc98
 */
public class HdelCommandImpl extends AbstractUpdateCommand {


    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> kedisDataList = getCommandList(channelDTO);
        KedisData key = kedisDataList.get(1);
        KedisDb kedisDb = channelDTO.getKedisDb();
        Map.Entry<KedisKey, KedisValue> entry = kedisDb.getDictEntry(new KedisKey(key));
        Set<KedisData> fieldSet = new HashSet<>();
        for (int i = 2; i < kedisDataList.size(); i++) {
            fieldSet.add(kedisDataList.get(i));
        }
        if (Objects.isNull(entry)) {
            return getErrorKedisDataV1();
        }
        if (entry.getValue().getValueType() != ValueType.Hash) {
            return getErrorForKeyType();
        }
        long isDel = HashHandler.delByFields(entry.getValue(), fieldSet);
        return new KedisData(DataType.INTEGER).setData(isDel);
    }


}
