package pers.kedis.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import pers.kedis.core.codec.resp.RespConstants;
import pers.kedis.core.command.CommandService;
import pers.kedis.core.common.utils.KedisUtil;
import pers.kedis.core.dto.KedisData;
import pers.kedis.core.dto.DataType;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.dto.KedisConfig;
import pers.kedis.core.exception.KedisException;

import java.util.*;

/**
 * @author kwsc98
 */
@Slf4j
public class KedisService {
    @Getter
    private static KedisDb[] KEDIS_DB_ARRAY;

    public static KedisConfig KEDISCONFIG;

    public static void init(int dbCount) {
        KEDIS_DB_ARRAY = new KedisDb[dbCount];
        for (int i = 0; i < KEDIS_DB_ARRAY.length; i++) {
            KEDIS_DB_ARRAY[i] = new KedisDb();
        }
    }

    public static KedisDb getkedisDb(int i) {
        return KEDIS_DB_ARRAY[i];
    }


    public List<KedisData> handles(ChannelDTO channelDTO) {
        List<KedisData> res = new ArrayList<>();
        List<KedisData> kedisDataList = channelDTO.getKedisDataList();
        for (KedisData kedisData : kedisDataList) {
            res.add(handle(channelDTO.setKedisData(kedisData)));
        }
        return res;
    }

    private KedisData handle(ChannelDTO channelDTO) {
        printRequestCommand(channelDTO);
        try {
            KedisData kedisData = CommandService.handler(channelDTO);
            printResponeCommand(kedisData);
            return kedisData;
        } catch (Exception e) {
            log.error("CommandService Error");
            String info = "Kedis Error";
            if (e instanceof KedisException) {
                KedisException exception = (KedisException) e;
                if (StringUtils.isNotEmpty(exception.getMessage())) {
                    info = exception.getMessage();
                }
            }
            return new KedisData(DataType.ERROR).setData(info);
        }
    }


    private void printRequestCommand(ChannelDTO channelDTO) {
        StringBuilder stringBuilder = new StringBuilder();
        KedisData kedisData = channelDTO.getKedisData();
        if (DataType.RESP_ARRAY == kedisData.getDataType()) {
            List<KedisData> list = KedisUtil.convertList(kedisData.getData());
            for (KedisData kedisDataPre : list) {
                stringBuilder.append(kedisDataPre.getData().toString()).append(" ");
            }
        }
        log.debug("Recrive Command : {} Channel : {} KedisDb : {}", stringBuilder.toString(), channelDTO.getChannel(), channelDTO.getKedisDb());
    }

    private void printResponeCommand(KedisData kedisData) {
        StringBuilder stringBuilder = new StringBuilder();
        if (DataType.RESP_ARRAY == kedisData.getDataType()) {
            List<KedisData> list = KedisUtil.convertList(kedisData.getData());
            for (KedisData kedisDataPre : list) {
                stringBuilder.append(kedisDataPre.getData().toString()).append(" ");
            }
        } else {
            stringBuilder.append(kedisData.getData().toString());
        }
        log.debug("Command Response : {} ", stringBuilder.toString().replace(new String(RespConstants.CRLF), "||"));
    }


    public synchronized static void refresh(KedisConfig kedisConfig) {
        if (Objects.isNull(KEDIS_DB_ARRAY)) {
            init(kedisConfig.getDbCount());
        }
        KedisService.KEDISCONFIG = kedisConfig;
    }

}
