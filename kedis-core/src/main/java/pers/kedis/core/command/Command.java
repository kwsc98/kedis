package pers.kedis.core.command;

import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;

/**
 * @author kwsc98
 */
public interface Command {

    /**
     * Kedis Command Interface
     */
    KedisData handler(ChannelDTO channelDTO);


}
