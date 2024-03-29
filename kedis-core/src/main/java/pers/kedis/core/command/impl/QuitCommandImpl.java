package pers.kedis.core.command.impl;

import pers.kedis.core.command.AbstractCommand;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;

/**
 * @author kwsc98
 */
public class QuitCommandImpl extends AbstractCommand {

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        channelDTO.close();
        return getSuccessKedisDataV1();
    }
}
