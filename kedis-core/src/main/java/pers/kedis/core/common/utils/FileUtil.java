package pers.kedis.core.common.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * GetUserInfoByCardNum
 * 2022/1/26 11:33
 *
 * @author wangsicheng
 * @since
 **/
@Slf4j
public class FileUtil {
    public static String getRootPath() {
        String rootPath = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (rootPath.endsWith(".jar")) {
            rootPath = rootPath.substring(0, rootPath.lastIndexOf("/") + 1);
        }
        return rootPath;
    }

    public static List<String> readLines(File file) throws IOException {
        try {
            List<String> list = new ArrayList<>();
            //读取文件里每一行信息
            //按行写入文件
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            int i = 0;
            while ((str = bf.readLine()) != null) {
                list.add(str);
            }
            fr.close();
            bf.close();
            return list;
        } catch (Exception e) {
            log.debug("文件读取异常");
            throw e;
        }
    }

}
