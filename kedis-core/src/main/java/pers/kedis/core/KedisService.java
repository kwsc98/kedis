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
import pers.kedis.core.exception.KedisException;
import pers.kedis.core.persistence.AofService;
import pers.kedis.core.persistence.PersistenInterface;
import pers.kedis.core.persistence.PersistenService;

import java.util.*;

/**
 * @author kwsc98
 */
@Slf4j
public class KedisService {
    @Getter
    private static KedisDb[] KEDIS_DB_ARRAY;

    public static KedisProperties KEDISCONFIG;

    public static PersistenService persistenService;

    public synchronized static void init(KedisProperties kedisProperties) {
        KedisService.KEDISCONFIG = kedisProperties;
        int dbCount = kedisProperties.getDbCount();
        KedisDb[] kedisDbs = new KedisDb[dbCount];
        for (int i = 0; i < kedisDbs.length; i++) {
            kedisDbs[i] = new KedisDb(i);
        }
        KEDIS_DB_ARRAY = kedisDbs;
    }

    public static KedisDb getkedisDb(int i) {
        return KEDIS_DB_ARRAY[i];
    }


    public static List<KedisData> handles(ChannelDTO channelDTO) {
        List<KedisData> res = new ArrayList<>();
        List<KedisData> kedisDataList = channelDTO.getKedisDataList();
        for (KedisData kedisData : kedisDataList) {
            res.add(handle(channelDTO.setKedisData(kedisData)));
        }
        return res;
    }

    private static KedisData handle(ChannelDTO channelDTO) {
        try {
            printRequestCommand(channelDTO);
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


    private static void printRequestCommand(ChannelDTO channelDTO) {
        StringBuilder stringBuilder = new StringBuilder();
        KedisData kedisData = channelDTO.getKedisData();
        if (DataType.RESP_ARRAY == kedisData.getDataType()) {
            List<KedisData> list = KedisUtil.convertList(kedisData.getData());
            for (KedisData kedisDataPre : list) {
                stringBuilder.append(kedisDataPre.getData() == null ? "null" : kedisData.getData().toString()).append(" ");
            }
        }
        log.debug("Recrive Command : {} Channel : {} KedisDb : {}", stringBuilder, channelDTO.getChannel(), channelDTO.getKedisDb());
    }

    private static void printResponeCommand(KedisData kedisData) {
        StringBuilder stringBuilder = new StringBuilder();
        if (DataType.RESP_ARRAY == kedisData.getDataType()) {
            List<KedisData> list = KedisUtil.convertList(kedisData.getData());
            for (KedisData kedisDataPre : list) {
                stringBuilder.append(kedisDataPre.getData().toString()).append(" ");
            }
        } else {
            stringBuilder.append(kedisData.getData() == null ? "null" : kedisData.getData().toString());
        }
        log.debug("Command Response : {} ", stringBuilder.toString().replace(new String(RespConstants.CRLF), "||"));
    }

}
