package pers.kedis.core.command.impl.hash;

import pers.kedis.core.KedisDb;
import pers.kedis.core.command.AbstractUpdateCommand;
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
        String key = kedisDataList.get(1).getData();
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
        long isDel = 0;
        if (entry.getValue().getValue() instanceof List) {
            isDel = delByList(entry.getValue().getValue(), fieldSet);
        } else {
            isDel = delByDict(entry.getValue().getValue(), fieldSet);
        }
        return new KedisData(DataType.INTEGER).setData(isDel);
    }

    private int delByList(List<KedisData> dataList, Set<KedisData> fieldSet) {
        int isDel = 0;
        Iterator<KedisData> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            KedisData nextStr = iterator.next();
            if (fieldSet.contains(nextStr)) {
                iterator.remove();
                iterator.next();
                iterator.remove();
                isDel = 1;
            } else {
                iterator.next();
            }
        }
        return isDel;
    }

    private int delByDict(Dict<KedisData, KedisData> dict, Set<KedisData> fieldSet) {
        KedisData kedisData = null;
        for (KedisData field : fieldSet
        ) {
            kedisData = dict.remove(field);
        }
        if (Objects.nonNull(kedisData)) {
            return 1;
        } else {
            return 0;
        }
    }


}
