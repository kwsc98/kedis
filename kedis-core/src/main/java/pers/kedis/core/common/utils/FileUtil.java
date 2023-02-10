package pers.kedis.core.common.utils;


import lombok.extern.slf4j.Slf4j;

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
}
