package pers.kedis.core.channel;

import java.util.Objects;

/**
 * @author kwsc98
 */
public class NodeIterator {

    private ComponentNode componentNode;

    public NodeIterator(ComponentNode componentNode) {
        this.componentNode = componentNode;
    }


    public boolean hasNext() {
        return Objects.nonNull(componentNode.getSufNode()) && Objects.nonNull(componentNode.getSufNode().getSufNode());
    }

    public ComponentNode next() {
        return this.componentNode = this.componentNode.getSufNode();
    }


}
