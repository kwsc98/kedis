package pers.kedis.core.command;

import pers.kedis.core.common.utils.KedisUtil;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.enums.DataType;

import java.util.List;

/**
 * @author kwsc98
 */
public abstract class AbstractCommand implements Command {

    protected KedisData getNullKedisData() {
        return new KedisData(DataType.BULK_STRING).setData(null);
    }

    protected KedisData getSuccessKedisDataV1() {
        return new KedisData(DataType.BULK_STRING).setData("1");
    }

    protected KedisData getSuccessKedisDataV2() {
        return new KedisData(DataType.BULK_STRING).setData("OK");
    }

    protected KedisData getSuccessKedisDataV3() {
        return new KedisData(DataType.INTEGER).setData(1L);
    }

    protected KedisData getErrorKedisDataV1() {
        return new KedisData(DataType.INTEGER).setData(0L);
    }

    protected KedisData getErrorForKeyType() {
        return new KedisData(DataType.ERROR).setData("WRONGTYPE Operation against a key holding the wrong kind of value");
    }

    protected List<KedisData> getCommandList(ChannelDTO channelDTO) {
        KedisData kedisData = channelDTO.getKedisData();
        return KedisUtil.convertList(kedisData.getData());
    }

    protected KedisData getErrorKedisData() {
        return new KedisData(DataType.BULK_STRING).setData("0");
    }

}
