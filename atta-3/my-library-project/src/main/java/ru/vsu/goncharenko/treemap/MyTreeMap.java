package ru.vsu.goncharenko.treemap;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class MyTreeMap<K extends Comparable<? super K>, V>{

    public MyTreeMap() {
        this.tree = new RBTree<>();
    }

    public MyTreeMap(MyTreeMap<K, V> treeMap) {
        this.tree = treeMap.clone().tree;
    }

    protected static class MyMapTreeEntry<K extends Comparable<? super K>, V> implements Comparable<MyMapTreeEntry<K, V>> {
        private K key;
        private V value;


        public MyMapTreeEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }

        public int compareTo(MyMapTreeEntry<K, V> o) {
            return this.key.compareTo(o.key);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyMapTreeEntry<?, ?> that = (MyMapTreeEntry<?, ?>) o;
            return Objects.equals(key, that.key) && Objects.equals(value, that.value);
        }
    }

    private RBTree<MyMapTreeEntry<K, V>> tree;

    public void put(K key, V value) {
        MyMapTreeEntry<K, V> node = new MyMapTreeEntry<>(key, value);
        tree.add(node);
    }

    public void put(K key) {
        MyMapTreeEntry<K, V> node = new MyMapTreeEntry<>(key, null);
        tree.add(node);
    }

    public void remove(K key, V value){
        MyMapTreeEntry<K, V> node = new MyMapTreeEntry<>(key, value);
        tree.remove(node);
    }

    public void remove(K key){
        MyMapTreeEntry<K, V> node = new MyMapTreeEntry<>(key, null);
        tree.remove(node);
    }

    public void clear(){
        tree.clear();
    }

    public MyTreeMap<K, V> clone() {
        MyTreeMap<K, V> clone = new MyTreeMap<>();
        Iterator<MyMapTreeEntry<K, V>> iterator = tree.iterator();
        while (iterator.hasNext()){
            MyMapTreeEntry<K, V> entry = iterator.next();
            clone.put(entry.key, entry.value);
        }
        return clone;
    }

    public Set<K> keySet(){
        Set<K> set = new TreeSet<>();
        Iterator<MyMapTreeEntry<K, V>> iterator = tree.iterator();
        while (iterator.hasNext()){
            MyMapTreeEntry<K, V> entry = iterator.next();
            set.add(entry.key);
        }

        return set;
    }

    public boolean containsKey(K key){
        return tree.getNode(new MyMapTreeEntry<>(key, null)) != null;
    }

    public boolean containsValue(V value){
        if(tree.size == 0){
            return false;
        }
        Iterator<MyMapTreeEntry<K, V>> iterator = tree.iterator();
        while (iterator.hasNext()){
            MyMapTreeEntry<K, V> entry = iterator.next();
            if(entry.value != null) {
                if (entry.value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public K firstKey(){
        MyMapTreeEntry<K, V> entry = tree.getMinNode(tree.root).value;
        return entry.getKey();
    }

    public K lastKey(){
        MyMapTreeEntry<K, V> entry = tree.getMaxNode(tree.root).value;
        return entry.getKey();
    }

    public int size(){
        return tree.size();
    }

    public V getValue(K key){
        MyMapTreeEntry<K, V> node = tree.getNode(new MyMapTreeEntry<>(key, null)).value;
        return node.value;
    }

    public void setTree(MyTreeMap<K, V> treeMap) {
        this.tree = treeMap.tree;
    }

    @Override
    public String toString() {
        return tree.toString();
    }

    /*public boolean equals(MyTreeMap<K, V> treeMap) {
        Iterator<MyMapTreeEntry<K, V>> iteratorThis = this.tree.iterator();
        Iterator<MyMapTreeEntry<K, V>> iterator = treeMap.tree.iterator();

        while (iterator.hasNext() || iteratorThis.hasNext()){
            MyMapTreeEntry<K, V> entryThis = iteratorThis.next();
            MyMapTreeEntry<K, V> entry = iterator.next();

            if(entry == null || entryThis == null || !entry.equals(entryThis)) {
                return false;
            }
        }
        return true;
    }*/



    public boolean equals(MyTreeMap<K, V> treeMap) {
        return this.tree.equals(treeMap.tree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tree);
    }
}
