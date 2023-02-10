package pers.kedis.core.handler;

/**
 * @author kwsc98
 */
public abstract class ComponentHandler<T, C> {

    public abstract Object handle(T object, C config);

    public C init(String configStr) {
        return null;
    }

}
