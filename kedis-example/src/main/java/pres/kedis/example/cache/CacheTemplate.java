package pres.kedis.example.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author kwsc98
 */
public interface CacheTemplate<K, V> {


    void put(K key, V value);


    void put(K key, V value, long timeOut, TimeUnit timeUnit);


    V get(K key);

}
