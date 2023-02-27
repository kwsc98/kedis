package pers.kedis.core.common.structure;

/**
 * @author kwsc98
 */
public class DictEntry<K, V> {

    K key;

    V value;

    DictEntry<K, V> next;

    public DictEntry<K, V> setKey(K key) {
        this.key = key;
        return this;
    }

    public DictEntry<K, V> setValue(V value) {
        this.value = value;
        return this;
    }

    public DictEntry<K, V> setNext(DictEntry<K, V> next) {
        this.next = next;
        return this;
    }
}
