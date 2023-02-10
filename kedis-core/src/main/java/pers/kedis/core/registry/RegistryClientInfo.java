package pers.kedis.core.registry;


import lombok.Data;

import java.util.Arrays;

/**
 * kedis注册中心参数
 * 2022/7/28 17:09
 *
 * @author wangsicheng
 **/
@Data
public class RegistryClientInfo {

    private String serverAddr;

    private Client client;

    public static RegistryClientInfo build(String serverAddr) {
        String[] strings = serverAddr.split("://");
        return new RegistryClientInfo().setClient(strings[0]).setServerAddr(strings[1]);
    }

    private RegistryClientInfo setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
        return this;
    }

    private RegistryClientInfo setClient(String client) {
        this.client = Client.getClient(client);
        return this;
    }

    public enum Client {
        /**
         * Configuration
         * Zookeeper
         * Nacos
         **/
        Configuration, Zookeeper, Nacos;

        public static Client getClient(String s) {
            return Arrays.stream(Client.values()).filter(e -> e.name().equalsIgnoreCase(s)).findFirst().orElse(Configuration);
        }
    }


}
