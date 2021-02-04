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

    /* constructor */
    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
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
        int hashedKey = hash(key);
        return buckets[hashedKey].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        // resize
        if (loadFactor() > MAX_LF) {
            resize();
        }

        int hashedKey = hash(key);
        if (! buckets[hashedKey].containsKey(key)) {
            size += 1;
        }
        buckets[hashedKey].put(key, value);
    }

    private void resize () {
        ArrayMap<K, V>[] origins = buckets;
        buckets = new ArrayMap[buckets.length * 2];
        for (int i = 0; i < buckets.length; i += 1) {
            buckets[i] = new ArrayMap<>();
        }
        for (ArrayMap<K, V> origin : origins) {
            for (K eachKey : origin.keySet()) {
                V eachValue = get(eachKey);
                int eachHash = hash(eachKey);
                buckets[eachHash].put(eachKey, eachValue);
            }
        }
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
            for (K eachKey : bucket.keySet()) {
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
            return buckets[targetHash].remove(key);
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
