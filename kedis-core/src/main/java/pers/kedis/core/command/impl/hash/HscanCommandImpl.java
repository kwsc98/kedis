package pers.kedis.core.command.impl.hash;

import pers.kedis.core.command.AbstractCommand;
import pers.kedis.core.command.impl.hash.service.HashHandler;
import pers.kedis.core.common.structure.Dict;
import pers.kedis.core.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author kwsc98
 */
public class HscanCommandImpl extends AbstractCommand {

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> list = getCommandList(channelDTO);
        KedisData key = list.get(1);
        int index = Integer.parseInt(list.get(2).getData());
        String patternStr = list.get(4).getData().toString().replace("*", "/*");
        int count = 10;
        if (list.size() >= 7 && Objects.nonNull(list.get(6))) {
            count = Integer.parseInt(list.get(6).getData());
        }
        List<KedisData> dataList = new ArrayList<>();
        KedisValue kedisValue = channelDTO.getKedisDb().getValue(new KedisKey(key));
        if (Objects.nonNull(kedisValue)) {
            if (kedisValue.getValueType() != ValueType.Hash) {
                return getErrorForKeyType();
            }
            Pattern pattern = Pattern.compile(patternStr);
            index = HashHandler.getValueByPattern(kedisValue, dataList, index, count, pattern);
        } else {
            index = 0;
        }
        List<KedisData> res = new ArrayList<>();
        res.add(new KedisData(DataType.BULK_STRING).setData(String.valueOf(index)));
        res.add(new KedisData(DataType.RESP_ARRAY).setData(dataList));
        return new KedisData(DataType.RESP_ARRAY).setData(res);
    }


}
