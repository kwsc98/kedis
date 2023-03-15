package pers.kedis.core.command.impl.hash;

import pers.kedis.core.command.AbstractCommand;
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
        String key = list.get(1).getData();
        int index = Integer.parseInt(list.get(2).getData());
        String patternStr = list.get(4).getData().toString().replace("*", "/*");
        int count = 10;
        if (list.size() >= 7) {
            count = Integer.parseInt(list.get(6).getData());
        }
        List<KedisData> dataList = new ArrayList<>();
        KedisValue kedisValue = channelDTO.getKedisDb().getValue(new KedisKey(key));
        if (Objects.nonNull(kedisValue)) {
            if (kedisValue.getValueType() != ValueType.Hash) {
                return getErrorForKeyType();
            }
            Pattern pattern = Pattern.compile(patternStr);
            if (kedisValue.getValue() instanceof List) {
                index = getDataByList(kedisValue.getValue(), dataList, index, count, pattern);
            } else {
                index = getDataByMap(kedisValue.getValue(), dataList, index, count, pattern);
            }
        } else {
            index = 0;
        }
        List<KedisData> res = new ArrayList<>();
        res.add(new KedisData(DataType.BULK_STRING).setData(String.valueOf(index)));
        res.add(new KedisData(DataType.RESP_ARRAY).setData(dataList));
        return new KedisData(DataType.RESP_ARRAY).setData(res);
    }

    private int getDataByList(List<KedisData> preList, List<KedisData> dataList, int index, int count, Pattern pattern) {
        for (int i = 0; index < preList.size() && i < count; index += 2, i++) {
            if (pattern.matcher(preList.get(index).getData()).find()) {
                dataList.add(preList.get(index));
                dataList.add(preList.get(index + 1));
            }
        }
        if (index >= preList.size()) {
            index = 0;
        }
        return index;
    }

    private int getDataByMap(Dict<KedisData, KedisData> preDict, List<KedisData> dataList, int index, int count, Pattern pattern) {
        List<Map.Entry<KedisData, KedisData>> temp = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            index = preDict.getPatternKey(temp, pattern, index);
            if (index == 0) {
                break;
            }
        }
        for (Map.Entry<KedisData, KedisData> entry : temp) {
            dataList.add(entry.getKey());
            dataList.add(entry.getValue());
        }
        return index;
    }


}
