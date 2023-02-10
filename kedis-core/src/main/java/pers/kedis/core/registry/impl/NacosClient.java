package pers.kedis.core.registry.impl;


import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import pers.kedis.core.common.JsonUtils;
import pers.kedis.core.dto.GroupDTO;
import pers.kedis.core.exception.ApiGateWayException;
import pers.kedis.core.handler.ChannelService;
import pers.kedis.core.registry.RegistryClient;
import pers.kedis.core.registry.RegistryClientInfo;

import java.util.*;
import java.util.concurrent.Executor;

/**
 * kedis
 * 2022/8/17 14:12
 *
 * @author wangsicheng
 **/
@Slf4j
public class NacosClient implements RegistryClient, Listener {

    private final ChannelService channelService;

    public NacosClient(ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    public void init(RegistryClientInfo registryClientInfo) {
        log.info("NacosClient Init Start");
        try {
            Properties properties = new Properties();
            properties.put("serverAddr", registryClientInfo.getServerAddr());
            ConfigService configService = NacosFactory.createConfigService(properties);
            String yamlStr = configService.getConfigAndSignListener("kedis", "kedis_group", 5000, this);
            List<GroupDTO> list = getGroupDTOList(yamlStr);
            doRefresh(list);
            log.info("NacosClient Init Done");
        } catch (Exception e) {
            log.error("NacosClient Init Error : {}", e.toString(), e);
        }
    }

    @Override
    public void doRefresh(List<GroupDTO> list) {
        channelService.registrationGroupList(list);
    }


    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void receiveConfigInfo(String configInfo) {
        log.info("NacosClient Monitor Change Start");
        List<GroupDTO> list = getGroupDTOList(configInfo);
        doRefresh(list);
        log.info("NacosClient Monitor Change Done");
    }

    private List<GroupDTO> getGroupDTOList(String configInfo) {
        try {
            Map<String, Object> map = new Yaml().load(configInfo);
            Object group = map.get("group");
            String json = JsonUtils.writeValueAsString(group);
            return JsonUtils.readValue(json, new TypeReference<List<GroupDTO>>() {
            });
        } catch (Exception e) {
            log.error("Build GroupList Error : {}", e.toString(), e);
            throw new ApiGateWayException();
        }
    }

}
