package pers.kedis.core.registry;

import pers.kedis.core.KedisProperties;
import pers.kedis.core.dto.KedisConfig;

import java.util.List;

/**
 * kagw注册中心接口
 * 2022/7/28 15:53
 *
 * @author wangsicheng
 **/
public interface RegistryClient {

    String ROOT_PATH = "kedisApplication";

    /**
     * 注册中心初始化方法
     **/
    void init(RegistryClientInfo registryClientInfo);


    /**
     * 网关配置文件刷新
     **/
    void doRefresh(KedisConfig kedisConfig);


}
