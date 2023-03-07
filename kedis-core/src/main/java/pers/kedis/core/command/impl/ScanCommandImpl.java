package pers.kedis.core.command.impl;

import pers.kedis.core.KedisDb;
import pers.kedis.core.command.CommandAbstract;
import pers.kedis.core.common.structure.DictType;
import pers.kedis.core.common.utils.KedisUtil;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.DataType;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.KedisKey;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kwsc98
 */
public class ScanCommandImpl extends CommandAbstract {
    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        KedisDb kedisDb = channelDTO.getKedisDb();
        List<KedisData> kedisDataList = KedisUtil.convertList(channelDTO.getKedisData().getData());
        int index = Integer.parseInt(kedisDataList.get(1).getData().toString());
        String pattern = "*";
        int count = 10;
        if (kedisDataList.size() >= 4) {
            pattern = kedisDataList.get(3).getData().toString();
        }
        if (kedisDataList.size() >= 6) {
            count = Integer.parseInt(kedisDataList.get(5).getData().toString());
        }
        List<String> list = new ArrayList<>();
        long indexRes = kedisDb.getPatternKey(DictType.String, list, pattern, index, count);
        List<KedisData> res = new ArrayList<>();
        res.add(new KedisData(DataType.INTEGER).setData(indexRes));
        for (String key : list) {
            res.add(new KedisData(DataType.BULK_STRING).setData(key));
        }
        return new KedisData(DataType.RESP_ARRAY).setData(res);
    }
}
