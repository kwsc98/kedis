package pers.kedis.core.command.impl.hash;

import pers.kedis.core.KedisDb;
import pers.kedis.core.command.AbstractUpdateCommand;
import pers.kedis.core.common.structure.Dict;
import pers.kedis.core.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class HdelCommandImpl extends AbstractUpdateCommand {


    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> kedisDataList = getCommandList(channelDTO);
        String key = kedisDataList.get(1).getData();
        KedisData field = kedisDataList.get(2);
        KedisDb kedisDb = channelDTO.getKedisDb();
        Map.Entry<KedisKey, KedisValue> entry = kedisDb.getDictEntry(new KedisKey(key));
        if (Objects.isNull(entry)) {
            return getErrorKedisDataV1();
        }
        if (entry.getValue().getValueType() != ValueType.Hash) {
            return getErrorForKeyType();
        }
        long isDel = 0;
        if (entry.getValue().getValue() instanceof List) {
            isDel = delByList(entry.getValue().getValue(), field);
        } else {
            isDel = delByDict(entry.getValue().getValue(), field);
        }
        return new KedisData(DataType.INTEGER).setData(isDel);
    }

    private int delByList(List<KedisData> dataList, KedisData field) {
        for (int i = 0; i < dataList.size(); i += 2) {
            if (field.equals(dataList.get(i))) {
                dataList.remove(i);
                dataList.remove(i);
                return 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.remove(2);
        list.remove(2);
        System.out.println();
    }

    private int delByDict(Dict<KedisData, KedisData> dict, KedisData field) {
        KedisData kedisData = dict.remove(field);
        if (Objects.nonNull(kedisData)) {
            return 1;
        } else {
            return 0;
        }
    }


}
