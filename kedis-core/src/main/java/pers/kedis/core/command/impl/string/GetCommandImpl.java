package pers.kedis.core.command.impl.string;

import pers.kedis.core.command.AbstractCommand;
import pers.kedis.core.dto.*;

import java.util.List;
import java.util.Objects;


/**
 * @author kwsc98
 */
public class GetCommandImpl extends AbstractCommand {
    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> list = getCommandList(channelDTO);
        String key = list.get(1).getData().toString();
        KedisValue value = channelDTO.getKedisDb().getValue(new KedisKey(key));
        KedisData kedisData = new KedisData(DataType.BULK_STRING).setData(null);
        if (Objects.isNull(value)) {
            return kedisData;
        }
        if (value.getValueType() != ValueType.String) {
            return getErrorForKeyType();
        }
        return value.getValue();
    }
}
