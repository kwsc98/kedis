package pers.kedis.core.command.impl;

import pers.kedis.core.command.AbstractCommand;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.enums.DataType;
import pers.kedis.core.dto.KedisData;

/**
 * @author kwsc98
 */
public class DbSizeImplCommand extends AbstractCommand {

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        return new KedisData(DataType.INTEGER).setData(channelDTO.getKedisDb().size());
    }


}
