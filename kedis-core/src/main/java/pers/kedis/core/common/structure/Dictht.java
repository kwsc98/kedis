package pers.kedis.core.common.structure;

import java.util.Objects;

/**
 * @author kwsc98
 */
public class Dictht<K, V> {

    DictEntry<K, V>[] dictEntries;

    int size;

    int used;

    @SuppressWarnings({"unchecked"})
    Dictht(int size) {
        assert size > 1 : "Init size Error";
        this.dictEntries = (DictEntry<K, V>[]) new DictEntry[size];
        this.size = size;
        this.used = 0;
    }


    public V put(K key, V value) {
        int hash = hash(key);
        DictEntry<K, V>[] dictEntries = this.dictEntries;
        int index = (dictEntries.length - 1) & hash;
        DictEntry<K, V> dictEntry = dictEntries[index];
        V res = null;
        if (dictEntry == null) {
            used++;
            dictEntry = new DictEntry<K, V>(key);
            dictEntry.setValue(value);
            dictEntries[index] = dictEntry;
        } else {
            while (!Objects.equals(key, dictEntry.key)) {
                if (dictEntry.next == null) {
                    break;
                }
                dictEntry = dictEntry.next;
            }
            if (Objects.equals(key, dictEntry.key)) {
                res = dictEntry.setValue(value);
            } else {
                used++;
                dictEntry.next = new DictEntry<K, V>(key);
                dictEntry.next.setValue(value);
            }
        }
        return res;
    }

    protected DictEntry<K, V> getDictEntry(Object key) {
        int hash = hash(key);
        DictEntry<K, V>[] dictEntries = this.dictEntries;
        int index = (dictEntries.length - 1) & hash;
        DictEntry<K, V> dictEntry = dictEntries[index];
        DictEntry<K, V> value = null;
        while (dictEntry != null) {
            if (Objects.equals(key, dictEntry.key)) {
                value = dictEntry;
                break;
            }
            dictEntry = dictEntry.next;
        }
        return value;
    }

    public V get(K key) {
        V value = null;
        DictEntry<K, V> dictEntry = getDictEntry(key);
        if (dictEntry != null) {
            value = dictEntry.value;
        }
        return value;
    }

    public boolean containsKey(Object key) {
        return getDictEntry(key) != null;
    }

    public V remove(Object key) {
        int hash = hash(key);
        DictEntry<K, V>[] dictEntries = this.dictEntries;
        int index = (dictEntries.length - 1) & hash;
        DictEntry<K, V> dictEntry = dictEntries[index];
        V value = null;
        if (dictEntry == null || Objects.equals(dictEntry.key, key)) {
            if (dictEntry != null) {
                dictEntries[index] = dictEntry.next;
                value = dictEntry.value;
                used--;
            } else {
                dictEntries[index] = null;
            }
        } else {
            while (dictEntry.next != null && !Objects.equals(key, dictEntry.next.key)) {
                dictEntry = dictEntry.next;
            }
            if (dictEntry.next != null) {
                used--;
                value = dictEntry.next.value;
                dictEntry.next = dictEntry.next.next;
            }
        }
        return value;
    }

    static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }


}
