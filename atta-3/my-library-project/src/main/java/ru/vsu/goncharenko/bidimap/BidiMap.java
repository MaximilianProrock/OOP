package ru.vsu.goncharenko.bidimap;

public interface BidiMap<K extends Comparable<K>, V extends Comparable<V>>{
    void put(K key, V value);
    DualTreeBidiMap<V, K> inverseBidiMap();
    void removeValue(V value);
    void removeKey(K key);
    K getKey(V value);
    V getValue(K key);
    boolean containsKey(K key);
    boolean containsValue(V value);
}
