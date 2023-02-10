package pers.kedis.core.channel;

import lombok.Getter;
import pers.kedis.core.handler.ComponentHandler;

import java.util.Objects;

/**
 * @author kwsc98
 */
@Getter
public class ComponentChannelPipeline implements ChannelPipeline {

    private ComponentNode firstNode;

    private ComponentNode lastNode;

    public static ComponentChannelPipeline build() {
        ComponentNode firstNode = ComponentNode.build();
        ComponentNode lastNode = ComponentNode.build();
        firstNode.setSufNode(lastNode);
        lastNode.setPreNode(firstNode);
        return new ComponentChannelPipeline().setFirstNode(firstNode).setLastNode(lastNode);
    }

    @Override
    public NodeIterator getIterator() {
        return new NodeIterator(firstNode);
    }


    @Override
    public ChannelPipeline addFirst(ComponentNode componentNode) {
        ComponentNode sufNode = firstNode.getSufNode();
        firstNode.setSufNode(componentNode);
        componentNode.setPreNode(firstNode);
        if (!Objects.isNull(sufNode)) {
            sufNode.setPreNode(componentNode);
            componentNode.setSufNode(sufNode);
        }
        return this;
    }

    @Override
    public ChannelPipeline addLast(ComponentNode componentNode) {
        ComponentNode preNode = lastNode.getPreNode();
        lastNode.setPreNode(componentNode);
        componentNode.setSufNode(lastNode);
        if (!Objects.isNull(preNode)) {
            preNode.setSufNode(componentNode);
            componentNode.setPreNode(preNode);
        }
        return this;
    }

    @Override
    public ChannelPipeline addFirst(ComponentHandler<Object,Object> handler, String configJsonStr) {
        return this.addFirst(ComponentNode.build().setHandler(handler).setConfigJsonStr(configJsonStr));
    }

    @Override
    public ChannelPipeline addLast(ComponentHandler<Object,Object> handler, String configJsonStr) {
        return this.addLast(ComponentNode.build().setHandler(handler).setConfigJsonStr(configJsonStr));
    }

    public ComponentChannelPipeline setFirstNode(ComponentNode firstNode) {
        this.firstNode = firstNode;
        return this;
    }

    public ComponentChannelPipeline setLastNode(ComponentNode lastNode) {
        this.lastNode = lastNode;
        return this;
    }
}
