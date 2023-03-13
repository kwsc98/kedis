package pers.kedis.core.command;

import pers.kedis.core.common.utils.KedisUtil;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.DataType;

import java.util.List;

/**
 * @author kwsc98
 */
public abstract class CommandAbstract implements Command {

    protected KedisData getNullKedisData() {
        return new KedisData(DataType.BULK_STRING).setData(null);
    }

    protected KedisData getSuccessKedisDataV1() {
        return new KedisData(DataType.BULK_STRING).setData("1");
    }

    protected KedisData getSuccessKedisDataV2() {
        return new KedisData(DataType.BULK_STRING).setData("OK");
    }

    protected List<KedisData> getCommandList(ChannelDTO channelDTO) {
        KedisData kedisData = channelDTO.getKedisData();
        return KedisUtil.convertList(kedisData.getData());
    }

    protected KedisData getErrorKedisData() {
        return new KedisData(DataType.BULK_STRING).setData("0");
    }

}
