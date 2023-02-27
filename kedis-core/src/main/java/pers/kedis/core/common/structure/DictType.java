package pers.kedis.core.common.structure;

/**
 * @author kwsc98
 */
public interface DictType {

    long hashFunction(String key);

    boolean keyCompare(String key1,String key2);


}
