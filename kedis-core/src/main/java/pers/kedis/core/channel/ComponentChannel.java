package pers.kedis.core.channel;

/**
 * @author kwsc98
 */
public class ComponentChannel implements Channel {

    ComponentChannelPipeline preChannelPipeline;

    public ComponentChannel() {
        this.preChannelPipeline = ComponentChannelPipeline.build();
    }

    @Override
    public ChannelPipeline pipeline() {
        return preChannelPipeline;
    }

}
