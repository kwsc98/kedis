package pers.kedis.core.command.impl;

import pers.kedis.core.command.AbstractCommand;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;

import java.util.List;

/**
 * @author kwsc98
 */
public class ClientCommandImpl extends AbstractCommand {

    public final String setname = "SETNAME";

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        List<KedisData> list = getCommandList(channelDTO);
        String command2 = list.get(1).getData();
        String clientName = null;
        if (list.size() >= 3) {
            clientName = list.get(2).getData();
        }
        if (setname.equalsIgnoreCase(command2)) {
            channelDTO.setClientName(clientName);
        }
        return getSuccessKedisDataV1();
    }

}
