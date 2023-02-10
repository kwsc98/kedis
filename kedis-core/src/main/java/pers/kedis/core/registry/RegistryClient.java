package pers.kedis.core.registry;


import pers.kedis.core.dto.GroupDTO;

import java.util.List;

/**
 * kedis注册中心接口
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
    void doRefresh(List<GroupDTO> list);


}
