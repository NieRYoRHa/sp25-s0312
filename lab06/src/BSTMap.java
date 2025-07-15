import java.security.Key;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K,V>{

    private class BSTNode{
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        /**
         * Instructor
         * create a new node
         *
         * @param k : the key
         * @param v : the value
         */
        BSTNode(K k,V v){
            this.key=k;
            this.value=v;
            this.left=null;
            this.right=null;
        }
        public int compareNodes(BSTNode other) {
            /* We are able to safely invoke `compareTo` on `n1.item` because we
             * know that `K` extends `Comparable<K>`, so `K` is a `Comparable`, and
             *`Comparable`s must implement `compareTo`. */
            return this.key.compareTo(other.key);
        }
    }

    private BSTNode root;
    private int size;

    BSTMap(){
        this.root = null;
    }


    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        BSTNode node=new BSTNode(key,value);
        root=insert(node,root);
    }

    /**
     * Set the appropriate link between insert and this map by using an
     * iterable method starting from the root of the map.
     * If current node is null, current node should be the insert.
     * According to the result of Comparing the key of current node and insert,
     * then set the iterator until current node is null or the comparing result is equal.
     * Return the current node.
     *
     * @param insertNode
     * @param currNode
     */
    private BSTNode insert(BSTNode insertNode, BSTNode currNode){
        if(currNode==null) {
            size++;
            return insertNode;
        }
        else if (insertNode.compareNodes(currNode)>0){
            currNode.right=insert(insertNode, currNode.right);
        }
        else if (insertNode.compareNodes(currNode)<0){
            currNode.left=insert(insertNode, currNode.left);
        }
        else if(insertNode.compareNodes(currNode)==0){
            currNode.value= insertNode.value;
        }
        return currNode;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        if (!containsKey(key)){
        return null;
        }
         return get(key,root).value;
    }

    /**
     * Returns the node of the key
     *
     * @param key
     */
    public BSTNode get(K key, BSTNode current_node){
        if (key.compareTo(current_node.key)>0){
            return get(key, current_node.right);
        }
        else if (key.compareTo(current_node.key)<0){
            return get(key, current_node.left);
        }
        else {
            return current_node;
        }
    }




    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {

        return containsKey(key,root);
    }

    public boolean containsKey(K key,BSTNode currNode) {
        if(currNode==null) {
            return false;
        }
        else if (key.compareTo(currNode.key)>0){
            return containsKey(key, currNode.right);
        }
        else if (key.compareTo(currNode.key)<0){
            return containsKey(key, currNode.left);
        }
        else if(key.compareTo(currNode.key)==0){
            return true;
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
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root=null;
        size=0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
   // @Override
    public Set<K> keySet() {
        return Set.of();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
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
    public Iterator<K> iterator() {
        return null;
    }



     public static void main(String[] args){
        BSTMap<Integer,Integer> M1 =new BSTMap<Integer,Integer>();
        M1.put(1,2);
        M1.put(2,2);
        M1.put(1,5);
    }
}
