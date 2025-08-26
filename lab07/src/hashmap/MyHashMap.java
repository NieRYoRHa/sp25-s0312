package hashmap;

import org.checkerframework.common.returnsreceiver.qual.This;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    private int size;
    private int Capacity;
    private double Factor;

    @Override
    public void put(K key, V value) {

        if((double) size/Capacity>=Factor){
            reSize();
        }

        Node insertNode = new Node(key,value);

        if(containsKey(key)){
            update(insertNode);
        }
        else {
            map(insertNode);
            size++;
        }
    }

    /**
     * Update the value of key
     */
    public void update(Node node){
        for(Node n:buckets[Math.floorMod(node.hashCode(),buckets.length)]){
            if(n.equals(node)){
                n.value=node.value;
            }
        }
    }

    public void map(Node node){
        buckets[Math.floorMod(node.hashCode(),buckets.length)].add(node);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        if(containsKey(key)){
            for(Node n:buckets[Math.floorMod(key.hashCode(), buckets.length)]){
                if(n.key.equals(key)){
                    return n.value;
                }
            }
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        for(Node n:buckets[Math.floorMod(key.hashCode(), buckets.length)]){
            if(n.key.equals(key)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * When elements number larger than
     * initialCapacity * loadFactor,
     * double the initialCapacity
     */
    public void reSize(){
        int newCapacity=Capacity*2;
        Collection<Node>[] newBuckets = new Collection[newCapacity];
        for(int i=0;i<newCapacity;i++){
            newBuckets[i]=this.createBucket();
        }
        for(K k:this){
            newBuckets[Math.floorMod(k.hashCode(),newCapacity)].add(new Node(k,this.get(k)));
        }
        Capacity=newCapacity;
        buckets=newBuckets;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        for(Collection<Node> n:buckets){
            n.clear();
        }
        size=0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator iterator() {
        return new MyHashMapIterator();
    }

    public class MyHashMapIterator<K> implements Iterator<K>{

        private int index;
        private Iterator<Node> coIterator;
        MyHashMapIterator(){
            coIterator = buckets[index].iterator();
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            //current Node hasNext?
            if(coIterator.hasNext()){
                return true;
            }else {
                index++;
            }
            //more bucket left?
            if (index<Capacity){
                coIterator = buckets[index].iterator();
                return this.hasNext();
            }
            return false;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public K next() {
            return (K) coIterator.next().key;
        }
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }

        @Override
        public boolean equals(Object obj){
            if(obj instanceof MyHashMap<?,?>.Node){
                Node other = (Node) obj;
                return other.key.equals(this.key);
            }
            return false;
        }

        @Override
        public int hashCode(){
            return this.key.hashCode();
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this(16,0.75);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity,0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        Capacity=initialCapacity;
        Factor=loadFactor;
        buckets = new Collection[Capacity];
        for(int i=0;i<Capacity;i++){
            buckets[i]=this.createBucket();
        }
        size=0;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new LinkedList<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

}
