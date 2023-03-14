package pers.kedis.core.command;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import pers.kedis.core.codec.resp.RespUtil;
import pers.kedis.core.command.impl.*;
import pers.kedis.core.common.utils.KedisUtil;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.DataType;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.exception.KedisException;
import pers.kedis.core.persistence.PersistenService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author kwsc98
 */
@Slf4j
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
        COMMAND_MAP.put(CommandType.TYPE.name().toUpperCase(), new TypeCommandlmpl());
        COMMAND_MAP.put(CommandType.TTL.name().toUpperCase(), new TtlCommandImpl());
        COMMAND_MAP.put(CommandType.GET.name().toUpperCase(), new GetCommandImpl());
        COMMAND_MAP.put(CommandType.EXPIRE.name().toUpperCase(), new ExpireCommandImpl());
        COMMAND_MAP.put(CommandType.SELECT.name().toUpperCase(), new SelectCommandImpl());
        COMMAND_MAP.put(CommandType.DBSIZE.name().toUpperCase(), new DbSizeImplCommand());
        COMMAND_MAP.put(CommandType.HSET.name().toUpperCase(), new HsetCommandImpl());
        COMMAND_MAP.put(CommandType.HSCAN.name().toUpperCase(), new HscanCommandImpl());


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
        KedisData response = null;
        try {
            response = command.handler(channelDTO);
        } catch (Exception e) {
            log.error("CommandService Error");
            String info = "Kedis Error";
            if (e instanceof KedisException) {
                KedisException exception = (KedisException) e;
                if (StringUtils.isNotEmpty(exception.getMessage())) {
                    info = exception.getMessage();
                }
            }
            response = new KedisData(DataType.ERROR).setData(info);
        }
        if (DataType.ERROR != response.getDataType() && command instanceof AbstractUpdateCommand) {
            ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
            RespUtil.encode(channelDTO.getKedisData(), byteBuf);
            PersistenService.saveCommand(byteBuf, channelDTO.getKedisDb().getIndex());
            log.debug("This Command Is Update");
        }
        return response;
    }
}
