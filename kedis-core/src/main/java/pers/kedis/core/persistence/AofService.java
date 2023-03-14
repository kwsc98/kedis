package pers.kedis.core.persistence;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import pers.kedis.core.KedisProperties;
import pers.kedis.core.KedisService;
import pers.kedis.core.codec.resp.RespUtil;
import pers.kedis.core.common.utils.FileUtil;
import pers.kedis.core.dto.ChannelDTO;
import pers.kedis.core.exception.KedisException;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kwsc98
 */
@Slf4j
public class AofService implements PersistenInterface {

    private KedisProperties kedisConfig;

    private BufferedWriter[] bufferedWriters;

    private File[] files;

    @Override
    public void init(KedisProperties kedisConfig) {
        bufferedWriters = new BufferedWriter[kedisConfig.getDbCount()];
        files = new File[kedisConfig.getDbCount()];
        try {
            this.kedisConfig = kedisConfig;
            String dataResourcesPath = kedisConfig.getDataResourcesPath();
            File file = new File(dataResourcesPath);
            if (!file.isDirectory()) {
                throw new KedisException("Aof Init Error : DataResourcesPath Is NoDirectory");
            }
            File[] filesArray = file.listFiles();
            if (filesArray == null) {
                return;
            }
            List<File> fileList = Arrays.stream(filesArray).filter(e -> {
                String fileName = e.getName();
                String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                return "aof".equals(suffix);
            }).sorted((e1, e2) -> e1.getName().compareTo(e2.getName())).collect(Collectors.toList());
            if (fileList.isEmpty()) {
                return;
            }
            for (File filePre : fileList) {
                List<String> commandList = FileUtil.readLines(filePre);
                int dbIndex = Integer.parseInt(filePre.getName().substring(0, 3));
                if (dbIndex >= files.length) {
                    return;
                }
                files[dbIndex] = filePre;
                ChannelDTO channelDTO = new ChannelDTO(KedisService.getkedisDb(dbIndex), null);
                for (String command : commandList) {
                    ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
                    buffer.writeBytes(Hex.decodeHex(command));
                    channelDTO.setKedisDataList(RespUtil.decodeAll(buffer));
                    KedisService.handles(channelDTO);
                }
            }
        } catch (Exception e) {
            log.error("Aof Init Error : {}", e.getMessage());
            throw new KedisException(e.getMessage());
        }
    }


    public void saveCommand(byte[] bytes, int dbIndex) {
        try {
            BufferedWriter bufferedWriter = getOutputStream(dbIndex);
            bufferedWriter.write(Hex.encodeHexString(bytes) + "\n");
            bufferedWriter.flush();
        } catch (Exception e) {
            throw new KedisException("SaveCommand Error");
        }
    }

    private BufferedWriter getOutputStream(int dbIndex) throws IOException {

        File file = files[dbIndex];
        long fileSize = 1024 * 1024 * 15;
        if (file == null || file.length() > fileSize) {
            if (bufferedWriters[dbIndex] != null) {
                bufferedWriters[dbIndex].close();
                bufferedWriters[dbIndex] = null;
            }
            String dataResourcesPath = kedisConfig.getDataResourcesPath();
            file = new File(dataResourcesPath + "/" + String.format("%03d", dbIndex) + System.currentTimeMillis() + ".aof");
            files[dbIndex] = file;
        }
        if (bufferedWriters[dbIndex] == null) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bufferedWriters[dbIndex] = bw;
        }
        return bufferedWriters[dbIndex];

    }

}
