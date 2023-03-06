package pers.kedis.core.common.structure;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author kwsc98
 */
@Slf4j
public class Dict<K, V> implements Map<K, V> {

    private static final int DICT_HT_INITIAL_EXP = 2;

    private static final int DICT_HT_INITIAL_SIZE = 1 << DICT_HT_INITIAL_EXP;

    private int capacity;

    List<Dictht<K, V>> dicthtArray;

    int rehashidx;

    public Dict(Integer size) {
        if (size == null) {
            size = DICT_HT_INITIAL_SIZE;
        }
        capacity = size;
        init(size);
    }

    public void init(Integer size) {
        List<Dictht<K, V>> list = new ArrayList<>();
        list.add(new Dictht<>(size));
        list.add(null);
        this.rehashidx = -1;
        this.dicthtArray = list;
    }

    /**
     * 进行rehash
     */
    private void doReHash() {
        if (!dictExpandIfNeeded()) {
            return;
        }
        if (this.rehashidx == -1) {
            dicthtArray.set(1, new Dictht<>(capacity <<= 1));
        }
        DictEntry<K, V>[] dictEntries = dicthtArray.get(0).dictEntries;
        if (++this.rehashidx >= dictEntries.length) {
            dicthtArray.set(0, dicthtArray.get(1));
            dicthtArray.set(1, null);
            this.rehashidx = -1;
            return;
        }
        DictEntry<K, V> dictEntry = dictEntries[this.rehashidx];
        while (dictEntry != null) {
            put(dictEntry.getKey(), dictEntry.getValue(), false);
            dictEntry = dictEntry.next;
        }
    }


    /**
     * 判断dict是否正在进行rehash
     */
    public boolean dictExpandIfNeeded() {
        if (dictIsRehashing()) {
            return true;
        }
        Dictht<K, V> dictht = dicthtArray.get(0);
        return dictht.used > this.capacity;
    }

    /**
     * 判断dict是否正在进行rehash
     */
    public boolean dictIsRehashing() {
        return rehashidx != -1;
    }


    @Override
    public int size() {
        int size = dicthtArray.get(0).used;
        if (dictIsRehashing()) {
            size += dicthtArray.get(1).used;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean isHave = dicthtArray.get(0).containsKey(key);
        return isHave || (dictIsRehashing() && dicthtArray.get(1).containsKey(key));
    }

    @Override
    public V get(Object key) {
        Map.Entry<K, V> dictEntry = getEntry(key);
        return dictEntry == null ? null : dictEntry.getValue();
    }

    public Map.Entry<K, V> getEntry(Object key) {
        doReHash();
        DictEntry<K, V> dictEntry = dicthtArray.get(0).getDictEntry(key);
        if (dictEntry == null && dictIsRehashing()) {
            dictEntry = dicthtArray.get(1).getDictEntry(key);
        }
        return dictEntry;
    }


    @Nullable
    @Override
    public V put(K key, V value) {
        return put(key, value, true);
    }

    public V put(K key, V value, boolean isRehash) {
        if (isRehash) {
            doReHash();
        }
        V res1 = dicthtArray.get(0).remove(key);
        Dictht<K, V> dictht = dicthtArray.get(0);
        if (dictIsRehashing()) {
            dictht = dicthtArray.get(1);
        }
        V res2 = dictht.put(key, value);
        if (res1 == null) {
            res1 = res2;
        }
        return res1;
    }

    @Override
    public V remove(Object key) {
        doReHash();
        V value = dicthtArray.get(0).remove(key);
        if (value == null && dictIsRehashing()) {
            value = dicthtArray.get(1).remove(key);
        }
        return value;
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        Dictht<K, V> dictht = dicthtArray.get(0);
        if (dictIsRehashing()) {
            dictht = dicthtArray.get(1);
        }
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            K key = e.getKey();
            V value = e.getValue();
            dictht.put(key, value);
        }
    }

    public int getPatternKey(@NotNull List<K> kedisKeyList, String pattern, int index) {
        DictEntry<K, V>[] dictEntries = dicthtArray.get(0).dictEntries;
        int res = getNextIndex(index, dictEntries.length);
        convertKedisKeyList(kedisKeyList, dictEntries[index], pattern);
        if (!dictIsRehashing()) {
            return res;
        }
        DictEntry<K, V>[] dictEntries2 = dicthtArray.get(1).dictEntries;
        while (index != res) {
            convertKedisKeyList(kedisKeyList, dictEntries2[index], pattern);
            index = getNextIndex(index, dictEntries2.length);
        }
        return res;
    }


    private int getNextIndex(int index, int length) {
        index |= ~length + 1;
        index = rev(index);
        index++;
        index = rev(index);
        return index;
    }

    private int rev(int index) {
        int rev = 0;
        for (int i = 0; i < 32 && index != 0; ++i) {
            rev |= (index & 1) << (31 - i);
            index >>>= 1;
        }
        return rev;
    }


    private void convertKedisKeyList(List<K> kedisKeyList, DictEntry<K, V> entry, String pattern) {
        while (entry != null) {
            if (Pattern.matches(pattern, entry.key.toString())) {
                kedisKeyList.add(entry.key);
            }
            entry = entry.next;
        }
    }

    @Override
    public void clear() {
        init(this.capacity);
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @NotNull
    @Override
    public Set<K> keySet() {
        return new HashSet<>();
    }

    @NotNull
    @Override
    public Collection<V> values() {
        return new ArrayList<>();
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new HashSet<>();
    }


    public static void main(String[] args) throws InterruptedException {
        Dict dict = new Dict(4);
        int index = 0;
        for (int i = 0; i < 10; i++) {
            index = dict.getNextIndex(index, 8);
            System.out.println(index);
        }


        Map<String, String> map = new Dict<>(1024);
        List<String> list = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            String uuid = UUID.randomUUID().toString();
            map.put(uuid, uuid);
            list.add(uuid);
        }
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        int i = 0;
        for (String s : list) {
            String ans = map.get(s);
            if (!ans.equals(s)) {
                throw new RuntimeException();
            }
            System.out.println(i++);
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
