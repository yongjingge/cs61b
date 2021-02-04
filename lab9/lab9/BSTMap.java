package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* BSTMap fields contains a root node and an int size */
    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     *  -- search a BST
     */
    private V getHelper(K key, Node p) {
        if (key.compareTo(p.key) == 0) {
            return p.value;
        } else if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        }
        if (key.compareTo(p.key) == 0) {
            p.value = value;
        } else if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.right = putHelper(key, value, p.right);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        // size is one of the fields BSTMap have, so just return it.
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map.
    * -- BST traversal
    * */
    @Override
    public Set<K> keySet() {
        return keySetHelper(root);
    }

    /**
     * Returns keys contained in this map, starts from node p
     * BST preorder traversal
     * @param p
     * @return
     */
    private Set<K> keySetHelper (Node p) {
        if (p == null) {
            return null;
        }
        Set<K> keySet = new HashSet<>();
        keySet.add(p.key);
        keySetHelper(p.left);
        keySetHelper(p.right);
        return keySet;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (get(key) == null) {
            return null;
        }
        root = removeHelper(key, root);
        size -= 1;
        return root.value;
    }

    private Node removeHelper (K key, Node p) {
        if (p == null) {
            return null;
        }
        if (key.compareTo(p.key) < 0) {
            removeHelper(key, p.left);
        } else if (key.compareTo(p.key) > 0) {
            removeHelper(key, p.right);
        } else {
            // current p.key equals target key
            if (p.left == null && p.right == null) {
                return null;
            } else if (p.left == null) {
                return p.right;
            } else if (p.right == null) {
                return p.left;
            } else {
                Node t = p;
                p = minFromRight(p.right);
                p.left = t.left;
                // remove p's origin min-right and return the remaining nodes
                p.right = removeHelper(p.key, t.right);
            }
        }
        return p;
    }

    /**
     * Returns the minimum node from the right subtree
     * @param p
     * @return
     */
    private Node minFromRight (Node p) {
        if (p.left == null) {
            return p;
        } else {
            // has no left subtree
            return minFromRight(p.left);
        }
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V keyPairValue = get(key);
        if (keyPairValue != value || keyPairValue == null) {
            return null;
        }
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
