package pers.kedis.core.command.impl;

import pers.kedis.core.codec.resp.RespConstants;
import pers.kedis.core.command.AbstractCommand;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.DataType;
import pers.kedis.core.dto.KedisData;

/**
 * @author kwsc98
 */
public class InfoCommandImpl extends AbstractCommand {

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        return new KedisData(DataType.BULK_STRING)
                .setData(
                        "redis_version:kedis_1.0.0" + new String(RespConstants.CRLF) +
                                "os:" + System.getProperty("os.name") + new String(RespConstants.CRLF));
    }

}
