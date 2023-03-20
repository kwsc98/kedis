package pers.kedis.core.command.impl;

import pers.kedis.core.command.AbstractCommand;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.DataType;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.KedisKey;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author kwsc98
 */
public class ScanCommandImpl extends AbstractCommand {
    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> kedisDataList = getCommandList(channelDTO);
        int index = Integer.parseInt(kedisDataList.get(1).getData().toString());
        String patternStr = "*";
        int count = 10;
        if (kedisDataList.size() >= 4) {
            patternStr = kedisDataList.get(3).getData().toString();
        }
        if (kedisDataList.size() >= 6) {
            count = Integer.parseInt(kedisDataList.get(5).getData().toString());
        }
        Pattern pattern = Pattern.compile(
                patternStr.replace("*", "/*")
        );
        List<KedisKey> list = new ArrayList<>();
        long indexRes = channelDTO.getKedisDb().getPatternKey(list, pattern, index, count);
        List<KedisData> keyLisy = new ArrayList<>();
        for (KedisKey kedisKey : list) {
            keyLisy.add(new KedisData(DataType.BULK_STRING).setData(kedisKey.getKey().getData()));
        }
        List<KedisData> res = new ArrayList<>();
        res.add(new KedisData(DataType.BULK_STRING).setData(String.valueOf(indexRes)));
        res.add(new KedisData(DataType.RESP_ARRAY).setData(keyLisy));
        return new KedisData(DataType.RESP_ARRAY).setData(res);
    }
}
