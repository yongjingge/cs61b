package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    /* constructor with an ArrayMap List of default size*/
    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* constructor with an ArrayMap List of customized size */
    public MyHashMap (int customSize) {
        buckets = new ArrayMap[customSize];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        int hashedKey = hash(key);
        return buckets[hashedKey].get(key); //ArrayMap.get(key) to get a specific value
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        // resize
        if (loadFactor() > MAX_LF) {
            resizeHelper(buckets.length * 2);
        }

        int hashedKey = hash(key);
        if (! buckets[hashedKey].containsKey(key)) {
            size += 1;
        }
        buckets[hashedKey].put(key, value); // ArrayMap.put(key, value) method
    }

    /* resize helper method with a customized size argument */
    private void resizeHelper (int newSize) {
        MyHashMap<K, V> temp = new MyHashMap<>(newSize);
        for (K eachKey : keySet()) { // MyHashMap.keySet() method
            temp.put(eachKey, get(eachKey)); // MyHashMap.get(key) method, will return a mapped value
        }
        this.size = temp.size;
        this.buckets = temp.buckets;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> res = new HashSet<>();
        for (ArrayMap<K, V> bucket : buckets) {
            for (K eachKey : bucket.keySet()) { // ArrayMap.keySet() method, will return all its keys
                res.add(eachKey);
            }
        }
        return res;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        Set<K> keys = keySet();
        if (keys.contains(key)) {
            int targetHash = hash(key);
            return buckets[targetHash].remove(key); // ArrayMap.remove(key) method
        } else {
            return null;
        }
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        Set<K> keys = keySet();
        if (keys.contains(key)) {
            int targetHash = hash(key);
            V targetValue = get(key);
            return buckets[targetHash].remove(key, targetValue);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
