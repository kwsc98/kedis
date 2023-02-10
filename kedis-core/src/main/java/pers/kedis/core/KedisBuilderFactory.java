package pers.kedis.core;

import pers.kedis.core.registry.RegistryBuilderFactory;

/**
 * @author kwsc98
 */
public class KedisBuilderFactory {

    private RegistryBuilderFactory registryBuilderFactory;

    private int port;

    public static KedisBuilderFactory builder(){
        return new KedisBuilderFactory();
    }



    public KedisBuilderFactory setRegistryBuilderFactory(RegistryBuilderFactory registryBuilderFactory) {
        this.registryBuilderFactory = registryBuilderFactory;
        return this;
    }

    public KedisBuilderFactory setPort(int port) {
        this.port = port;
        return this;
    }

    public KedisApplicationContext build() {
        return new KedisApplicationContext(this.registryBuilderFactory,this.port);
    }

}
