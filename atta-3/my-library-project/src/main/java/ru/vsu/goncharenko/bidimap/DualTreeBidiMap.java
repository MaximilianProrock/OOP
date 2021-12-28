package ru.vsu.goncharenko.bidimap;

import ru.vsu.goncharenko.treemap.MyTreeMap;

import java.util.Objects;

public class DualTreeBidiMap<K extends Comparable<K>, V extends Comparable<V>> implements BidiMap<K, V> {

    MyTreeMap<K, V> treeMapKey;
    MyTreeMap<V, K> treeMapValue;

    public DualTreeBidiMap() {
        this.treeMapKey = new MyTreeMap<>();
        this.treeMapValue = new MyTreeMap<>();
    }

    @Override
    public void put(K key, V value) {
        treeMapKey.put(key, value);
        treeMapValue.put(value, key);
    }

    @Override
    public DualTreeBidiMap<V, K> inverseBidiMap() {
        DualTreeBidiMap<V, K> inverseBidiMap = new DualTreeBidiMap<>();
        //inverseBidiMap.treeMapKey.setTree(this.treeMapValue);
        //inverseBidiMap.treeMapValue.setTree(this.treeMapKey);

        inverseBidiMap.treeMapKey = treeMapValue.clone();
        inverseBidiMap.treeMapValue = treeMapKey.clone();
        return inverseBidiMap;
    }

    @Override
    public void removeValue(V value) {
        treeMapKey.remove(getKey(value));
        treeMapValue.remove(value);
    }

    @Override
    public void removeKey(K key) {
        treeMapValue.remove(getValue(key));
        treeMapKey.remove(key);
    }

    @Override
    public K getKey(V value) {
        if(!containsValue(value)){
            return null;
        }
        return treeMapValue.getValue(value);
    }

    @Override
    public V getValue(K key) {
        if(!containsKey(key)){
            return null;
        }
        return treeMapKey.getValue(key);
    }

    @Override
    public boolean containsKey(K key) {
        return treeMapKey.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        return treeMapValue.containsKey(value);
    }


    public boolean equals(DualTreeBidiMap<K, V> dualTreeBidiMap) {
        return  this.treeMapValue.equals(dualTreeBidiMap.treeMapValue) && this.treeMapKey.equals(dualTreeBidiMap.treeMapKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(treeMapKey, treeMapValue);
    }

    @Override
    public String toString() {
        return "KeyTree : \n" + treeMapKey.toString() + "\nValueTree: \n" + treeMapValue.toString();
    }
}
