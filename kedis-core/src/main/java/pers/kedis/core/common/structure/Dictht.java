package pers.kedis.core.common.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class Dictht<K, V> {

    DictEntry<K, V>[] dictEntries;

    long size;

    long sizemask;

    long used;

    @SuppressWarnings({"unchecked"})
    Dictht(int size) {
        assert size > 1 : "Init size Error";
        this.dictEntries = (DictEntry<K, V>[]) new DictEntry[size];
        this.size = size;
        this.sizemask = size - 1;
        this.used = 0;
    }


    public void put(K key, V value) {
        int hash = hash(key);
        DictEntry<K, V>[] dictEntries = this.dictEntries;
        int index = (dictEntries.length - 1) & hash;
        DictEntry<K, V> dictEntry = dictEntries[index];
        if (dictEntry == null) {
            used++;
            dictEntry = new DictEntry<K, V>().setKey(key).setValue(value);
            dictEntries[index] = dictEntry;
        } else {
            while (!Objects.equals(key, dictEntry.key)) {
                if (dictEntry.next == null) {
                    break;
                }
                dictEntry = dictEntry.next;
            }
            if (Objects.equals(key, dictEntry.key)) {
                dictEntry.setValue(value);
            } else {
                used++;
                dictEntry.next = new DictEntry<K, V>().setKey(key).setValue(value);
            }
        }
    }

    public V get(K key) {
        int hash = hash(key);
        DictEntry<K, V>[] dictEntries = this.dictEntries;
        int index = (dictEntries.length - 1) & hash;
        DictEntry<K, V> dictEntry = dictEntries[index];
        V value = null;
        while (dictEntry != null) {
            if (Objects.equals(key, dictEntry.key)) {
                value = dictEntry.value;
                break;
            }
            dictEntry = dictEntry.next;
        }
        return value;
    }

    public V remove(K key) {
        int hash = hash(key);
        DictEntry<K, V>[] dictEntries = this.dictEntries;
        int index = (dictEntries.length - 1) & hash;
        DictEntry<K, V> dictEntry = dictEntries[index];
        V value = null;
        if (dictEntry == null || Objects.equals(dictEntry.key, key)) {
            if (dictEntry != null) {
                value = dictEntry.value;
                used--;
            }
            dictEntries[index] = null;
        } else {
            while (dictEntry.next != null && !Objects.equals(key, dictEntry.next.key)) {
                dictEntry = dictEntry.next;
            }
            if (dictEntry.next != null) {
                used--;
                value = dictEntry.next.value;
                dictEntry.next = null;
            }
        }
        return value;
    }


    public static void main(String[] args) {
        Dictht<String, String> dictht = new Dictht<>(16);
        dictht.put("1", "1");
        dictht.put("2", "2");
        dictht.put("3", "3");
        dictht.put("4", "4");
        dictht.put("532323", "5");
        dictht.put("6", "6");
        dictht.put("7", "7");
        dictht.put("8", "8");
        dictht.put("9", "9");
        dictht.put("12112", "10");
        dictht.put("11", "11");
        String str = dictht.get("1");
        str = dictht.remove("2");
        str = dictht.remove("3");
        str = dictht.remove("4");
        str = dictht.remove("532323");
        str = dictht.remove("6");
        str = dictht.remove("wwewewe");

//        new HashMap<>().put();
        System.out.println();
    }

    static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }


}
