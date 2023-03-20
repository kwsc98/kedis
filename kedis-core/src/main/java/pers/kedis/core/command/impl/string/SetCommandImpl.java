package pers.kedis.core.command.impl.string;

import pers.kedis.core.KedisDb;
import pers.kedis.core.command.AbstractUpdateCommand;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.command.AbstractCommand;
import pers.kedis.core.dto.KedisKey;
import pers.kedis.core.dto.KedisValue;
import pers.kedis.core.dto.ValueType;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class SetCommandImpl extends AbstractUpdateCommand {

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> kedisDataList = getCommandList(channelDTO);
        KedisData key = kedisDataList.get(1);
        KedisData value = kedisDataList.get(2);
        KedisDb kedisDb = channelDTO.getKedisDb();
        KedisValue kedisValue = new KedisValue(ValueType.String, value);
        Map.Entry<KedisKey, KedisValue> entry = kedisDb.getDictEntry(new KedisKey(key));
        if (Objects.nonNull(entry)) {
            if (entry.getValue().getValueType() != ValueType.String) {
                return getErrorForKeyType();
            }
            entry.setValue(kedisValue);
        } else {
            kedisDb.put(new KedisKey(key, -1L), kedisValue);
        }
        return getSuccessKedisDataV2();
    }

}
