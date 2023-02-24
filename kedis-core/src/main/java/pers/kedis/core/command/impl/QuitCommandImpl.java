package pers.kedis.core.command.impl;

import pers.kedis.core.command.CommandAbstract;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;

/**
 * @author kwsc98
 */
public class QuitCommandImpl extends CommandAbstract {

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        channelDTO.close();
        return getSuccessKedisData();
    }
}
