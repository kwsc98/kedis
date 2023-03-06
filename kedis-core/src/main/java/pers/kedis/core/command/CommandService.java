package pers.kedis.core.command;


import pers.kedis.core.command.impl.*;
import pers.kedis.core.common.utils.KedisUtil;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.DataType;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.exception.KedisException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class CommandService {

    private static final Map<String, Command> COMMAND_MAP;

    static {
        COMMAND_MAP = new HashMap<>();
        COMMAND_MAP.put(CommandType.SET.name().toUpperCase(), new SetCommandImpl());
        COMMAND_MAP.put(CommandType.CLIENT.name().toUpperCase(), new ClientCommandImpl());
        COMMAND_MAP.put(CommandType.PING.name().toUpperCase(), new PingCommandImpl());
        COMMAND_MAP.put(CommandType.QUIT.name().toUpperCase(), new QuitCommandImpl());
        COMMAND_MAP.put(CommandType.INFO.name().toUpperCase(), new InfoCommandImpl());
        COMMAND_MAP.put(CommandType.CONFIG.name().toUpperCase(), new ConfigCommandImpl());
        COMMAND_MAP.put(CommandType.SCAN.name().toUpperCase(), new ScanCommandImpl());
        COMMAND_MAP.put(CommandType.NSET.name().toUpperCase(), new NsetCommandImpl());
    }

    public static KedisData handler(ChannelDTO channelDTO) {
        KedisData kedisData = channelDTO.getKedisData();
        String commandName = null;
        DataType dataType = kedisData.getDataType();
        switch (dataType) {
            case SIMPLE_STRING:
            case BULK_STRING:
                commandName = (String) kedisData.getData();
                break;
            case RESP_ARRAY:
                List<KedisData> list = KedisUtil.convertList(kedisData.getData());
                commandName = (String) list.get(0).getData();
                break;
            default:
                throw new KedisException();
        }
        Command command = COMMAND_MAP.get(commandName.toUpperCase());
        if (Objects.isNull(command)) {
            command = COMMAND_MAP.get(CommandType.PING.name().toUpperCase());
        }
        return command.handler(channelDTO);
    }
}
