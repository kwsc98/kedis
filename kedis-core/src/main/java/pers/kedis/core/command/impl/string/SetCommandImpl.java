package pers.kedis.core.command.impl.string;

import pers.kedis.core.KedisDb;
import pers.kedis.core.command.AbstractUpdateCommand;
import pers.kedis.core.common.utils.DateTimeUtils;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.KedisKey;
import pers.kedis.core.dto.KedisValue;
import pers.kedis.core.dto.enums.DateTimeType;
import pers.kedis.core.dto.enums.ValueType;

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
        KedisKey kedisKey = new KedisKey(key, -1L);
        boolean nx = false;
        DateTimeType dateTimeType = null;
        long timeOut = -1;
        if (kedisDataList.size() >= 4) {
            for (int i = 3; i < kedisDataList.size(); i++) {
                String str = kedisDataList.get(i).getData();
                if ("NX".equalsIgnoreCase(str)) {
                    nx = true;
                }
                DateTimeType dateTimeTypeTemp = DateTimeType.getEnum(str);
                if (dateTimeTypeTemp != null) {
                    dateTimeType = dateTimeTypeTemp;
                    KedisData kedisPre1 = kedisDataList.get(i);
                    KedisData kedisPre2 = kedisDataList.get(++i);
                    timeOut = Long.parseLong(kedisPre2.getData());
                    timeOut = DateTimeUtils.getDateTime(dateTimeType, timeOut);
                    //为了防止aof持久化异常
                    kedisPre1.setData(DateTimeType.TTL.name());
                    kedisPre2.setData(String.valueOf(timeOut));
                }
            }
        }
        KedisDb kedisDb = channelDTO.getKedisDb();
        KedisValue kedisValue = new KedisValue(ValueType.String, value);
        Map.Entry<KedisKey, KedisValue> entry = kedisDb.getDictEntry(kedisKey);
        if (Objects.nonNull(entry)) {
            if (entry.getValue().getValueType() != ValueType.String) {
                return getErrorForKeyType();
            } else if (nx) {
                return getNullKedisData();
            }
            if (Objects.nonNull(dateTimeType)) {
                entry.getKey().setCurrentTimeMillis(timeOut);
            }
            entry.setValue(kedisValue);
        } else {
            kedisDb.put(kedisKey.setCurrentTimeMillis(timeOut), kedisValue);
        }
        return getSuccessKedisDataV2();
    }

}
