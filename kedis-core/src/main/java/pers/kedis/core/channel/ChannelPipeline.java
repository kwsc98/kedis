package pers.kedis.core.channel;


import pers.kedis.core.handler.ComponentHandler;

/**
 * @author kwsc98
 */
public interface ChannelPipeline {


    NodeIterator getIterator();

    ChannelPipeline addFirst(ComponentNode componentNode);

    ChannelPipeline addLast(ComponentNode componentNode);

    ChannelPipeline addFirst(ComponentHandler<Object,Object> handler, String configJsonStr);

    ChannelPipeline addLast(ComponentHandler<Object,Object> handler, String configJsonStr);

}
