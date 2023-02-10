package pers.kedis.core.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kwsc98
 */
public class HandlerService {
    private final Map<String, ComponentHandler<?,?>> channelMap = new HashMap<>();

    public void registerChannelHandler(String name, ComponentHandler<?,?> componentHandler) {
        channelMap.put(name, componentHandler);
    }

    public ComponentHandler<?,?> getComponentHandler(String name) {
        return channelMap.get(name);
    }


    public static void main(String[] args) {

    }

}
