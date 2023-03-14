package pers.kedis.core.command.impl;

import pers.kedis.core.command.AbstractCommand;
import pers.kedis.core.dto.*;

import java.util.ArrayList;
import java.util.List;
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
            Pattern pattern = Pattern.compile(patternStr);
            List<KedisData> preList = kedisValue.getValue();
            for (int i = 0; index < preList.size() && i < count; index += 2, i++) {
                if (pattern.matcher(preList.get(index).getData()).find()) {
                    dataList.add(preList.get(index));
                    dataList.add(preList.get(index + 1));
                }
            }
            if (index >= preList.size()) {
                index = 0;
            }
        }
        if (Objects.isNull(kedisValue)) {
            index = 0;
        }
        List<KedisData> res = new ArrayList<>();
        res.add(new KedisData(DataType.BULK_STRING).setData(String.valueOf(index)));
        res.add(new KedisData(DataType.RESP_ARRAY).setData(dataList));
        return new KedisData(DataType.RESP_ARRAY).setData(res);
    }

}
