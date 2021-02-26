import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BinaryTrie implements Serializable {

    private Node root;

    /* Trie Node */
    private static class Node implements Comparable<Node>, Serializable {
        private final char ch;
        private final int freq;
        private final Node left;
        private final Node right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        /* is the node a leaf node? */
        private boolean isLeaf() {
            return (left == null) && (right == null);
        }

        /* compare, based on frequency (freq) */
        public int compareTo(Node x) {
            return this.freq - x.freq;
        }
    }

    /* Constructor:
    * build a Node-based Huffman decoding trie */
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        this.root = buildTrie(frequencyTable);
    }

    /* helper method to build the decoding trie */
    private static Node buildTrie(Map<Character, Integer> freq) {
        MinPQ<Node> pq = new MinPQ<>();
        // insert all frequencyTable's keys
        for (char ch : freq.keySet()) {
            Node trie = new Node(ch, freq.get(ch), null, null);
            pq.insert(trie);
        }
        // merge two smallest tries
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }

    /**
     * Finds the longest prefix that matches the given BitSequence
     * @param querySequence -- a BitSequence
     * @return a Match object
     */
    public Match longestPrefixMatch(BitSequence querySequence) {
        Node cur = root;
        BitSequence bitSequence = new BitSequence();
        // use bits to walk down the trie
        for (int i = 0; i < querySequence.length(); i += 1) {
            // output ever time you reaches a leaf
            if (cur.isLeaf()) {
                break;
            }
            if (querySequence.bitAt(i) == 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
            bitSequence = bitSequence.appended(querySequence.bitAt(i));
        }
        return new Match(bitSequence, cur.ch);
    }

    /**
     * Returns an inverse of the coding trie.
     * @return
     */
    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> lookupTable = new HashMap<>();
        BitSequence bs = new BitSequence();
        buildTable(lookupTable, root, bs);
        return lookupTable;
    }

    /* helper method to build the encoding map */
    private static void buildTable(Map<Character, BitSequence> map, Node x, BitSequence bs) {
        if (! x.isLeaf()) {
            buildTable(map, x.left, bs.appended(0));
            buildTable(map, x.right, bs.appended(1));
        } else {
            map.put(x.ch, bs);
        }
    }
}
