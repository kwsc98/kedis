package pers.kedis.core.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author kwsc98
 */
public class Trie<T> {

    private final TrieNode<T> trieNode = new TrieNode<>();

    public T get(List<String> keys) {
        T value = null;
        Map<String, TrieNode<T>> trieNodeMap = trieNode.trieNodeMap;
        for (String key : keys) {
            TrieNode<T> trieNode = trieNodeMap.get(key);
            if (Objects.isNull(trieNode)) {
                break;
            }
            if (Objects.nonNull(trieNode.value)) {
                value = trieNode.value;
            }
            trieNodeMap = trieNode.trieNodeMap;
        }
        return value;
    }

    public void put(List<String> keys, T value) {
        Map<String, TrieNode<T>> trieNodeMap = trieNode.trieNodeMap;
        for (int i = 0; i < keys.size(); i++) {
            TrieNode<T> trieNode = trieNodeMap.get(keys.get(i));
            if (Objects.isNull(trieNode)) {
                trieNode = new TrieNode<>();
                trieNodeMap.put(keys.get(i), trieNode);
            }
            if (i == keys.size() - 1) {
                trieNode.value = value;
            }
            trieNodeMap = trieNode.trieNodeMap;
        }
    }


    static class TrieNode<T> {

        String key;

        T value;

        Map<String, TrieNode<T>> trieNodeMap = new HashMap<>();

    }

}
