package pers.kedis.core.common.structure;

import pers.kedis.core.dto.KedisData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kwsc98
 */
public class Dict<K, V> {

    private static final int DICT_OK = 0;

    private static final int DICT_ERR = 1;

    private static final int DICT_HT_INITIAL_EXP = 2;

    private static final int DICT_HT_INITIAL_SIZE = 1 << DICT_HT_INITIAL_EXP;

    List<Dictht<K, V>> dicthtArray;

    long rehashidx;

    public Dict(Integer size) {
        if (size == null) {
            size = DICT_HT_INITIAL_SIZE;
        }
        this.dicthtArray = new ArrayList<>();
        this.dicthtArray.add(new Dictht<>(size));
        this.dicthtArray.add(new Dictht<>(size));
        this.rehashidx = -1;
    }

    public void put(K key, V value) {
        if (dictIsRehashing()) {
            this.dicthtArray.get(1).put(key, value);
        }
    }

    /**
     * 判断dict是否正在进行rehash
     */
    public boolean dictIsRehashing() {
        return rehashidx != -1;
    }


}
