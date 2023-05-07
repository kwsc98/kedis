package pers.kedis.core.common.structure;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class SkipList<K extends Comparable<K>, V> {

    private static final int MAX_LEVEL = 16;

    private int level = 1;

    private Index<K, V> head = new Index<>(new Node<>(null, null), null, null);

    public V get(K key) {
        Node<K, V> node = findIndex(key).node;
        node = node.next;
        while (node != null) {
            if (key.compareTo(node.key) == 0) {
                break;
            }
            if (key.compareTo(node.key) < 0) {
                return null;
            }
            node = node.next;
        }
        return node != null ? node.value : null;
    }

    public void put(K key, V value) {
        Node<K, V> pre = findIndex(key).node;
        Node<K, V> node = pre.next;
        while (node != null) {
            int temp = key.compareTo(node.key);
            if (temp == 0 || temp < 0) {
                break;
            }
            pre = node;
            node = node.next;
        }
        if (node != null && key.compareTo(node.key) == 0) {
            node.value = value;
            return;
        }
        Node<K, V> newNode = new Node<>(key, value);
        pre.next = newNode;
        newNode.next = node;
        int randomLevel = randomLevel();
        if (randomLevel > level) {
            randomLevel = level + 1;
            head = new Index<>(head.node, head, null);
        }
        updateIndex(key, newNode, randomLevel);
        if (randomLevel > level) {
            level = randomLevel;
        }
    }


    public static void main(String[] args) {
        SkipList<Integer, String> stringSkipList = new SkipList<>();
        Random random1 = new Random();
        for (int i = 0; i < 100; i++) {
            stringSkipList.put(random1.nextInt(100), UUID.randomUUID().toString());
        }
        System.out.println(stringSkipList);
        System.out.println();
    }

    private void updateIndex(K key, Node<K, V> node, int randomLevel) {
        Index<K, V> preIndex = head;
        Index<K, V> newIndex = new Index<>(node, null, null);
        for (int i = 0; i < level - randomLevel; i++) {
            preIndex = preIndex.down;
        }
        // 找到对应的层之后，我们开始向右继续查找前驱索引节点
        while (preIndex != null) {
            if (preIndex.right != null) {
                int cmp = key.compareTo((preIndex.right.node.key));
                if (cmp > 0) {
                    preIndex = preIndex.right;
                    continue;
                }
            }
            newIndex.right = preIndex.right;
            preIndex.right = newIndex;
            preIndex = preIndex.down;
            if (preIndex != null) {
                newIndex.down = new Index<>(node, null, null);
                newIndex = newIndex.down;
            }
        }
    }


    private Index<K, V> findIndex(K key) {
        Index<K, V> pre = head;
        while (pre.down != null) {
            if (pre.right != null) {
                Node<K, V> n = pre.right.node;
                K k = n.key;
                // 如果搜索的key大于右侧节点指向的key，那么继续向右查找
                if (key.compareTo(k) > 0) {
                    pre = pre.right;
                    continue;
                }
            }
            pre = pre.down;
        }
        return pre;
    }


    @Data
    static class Node<K, V> {

        private final K key;

        private V value;

        private Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    static class Index<K, V> {
        final Node<K, V> node;
        Index<K, V> down;
        Index<K, V> right;

        Index(Node<K, V> node, Index<K, V> down, Index<K, V> right) {
            this.node = node;
            this.down = down;
            this.right = right;
        }
    }

    private final Random random = new Random();

    private int randomLevel() {
        int res = 1;
        for (; res < MAX_LEVEL; res++) {
            if (random.nextBoolean()) {
                return res;
            }
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Index<K, V> temp = head;
        while (temp != null) {
            Index<K, V> temppre2 = temp;
            while (temppre2 != null) {
                stringBuilder.append(temppre2.node.key).append("->");
                temppre2 = temppre2.right;
            }
            temp = temp.down;
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
