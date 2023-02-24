package pers.kedis.core.command.impl;

import pers.kedis.core.command.CommandAbstract;
import pers.kedis.core.common.utils.KedisUtil;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisData;

import java.util.List;

/**
 * @author kwsc98
 */
public class ClientCommandImpl extends CommandAbstract {

    public final String setname = "SETNAME";

    @Override
    public KedisData handler(ChannelDTO channelDTO) {
        KedisData kedisData = channelDTO.getKedisData();
        List<KedisData> list = KedisUtil.convertList(kedisData.getData());
        String command2 = (String) list.get(1).getData();
        String clientName = null;
        if (list.size() >= 3) {
            clientName = (String) list.get(2).getData();
        }
        if (setname.equalsIgnoreCase(command2)) {
            channelDTO.setClientName(clientName);
        }
        return getSuccessKedisData();
    }

}
