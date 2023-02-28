package pers.kedis.core.common.structure;


import java.util.Map;

/**
 * @author kwsc98
 */
public class DictEntry<K, V> implements Map.Entry<K, V> {

    K key;

    V value;

    DictEntry<K, V> next;

    DictEntry(K key) {
        this.key = key;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    public DictEntry<K, V> getNext() {
        return next;
    }

    @Override
    public V setValue(V value) {
        V res = this.value;
        this.value = value;
        return res;
    }

    public DictEntry<K, V> setNext(DictEntry<K, V> next) {
        this.next = next;
        return this;
    }
}
