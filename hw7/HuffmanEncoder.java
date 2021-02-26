import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {

    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> frequencyTable = new HashMap<>();
        for (char c : inputSymbols) {
            frequencyTable.compute(c, (k, v) -> (v == null) ? 1 : v + 1);
        }
        return frequencyTable;
    }

    public static void main(String[] args) {
        // read files
        char[] inputSymbols = FileUtils.readFile(args[0]);
        // build frequencyTable
        Map<Character, Integer> freqTable = buildFrequencyTable(inputSymbols);
        // construct a trie
        BinaryTrie trie = new BinaryTrie(freqTable);
        // write the trie to the .huf file
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(trie);
        ow.writeObject(inputSymbols.length);
        // create lookup table for encoding
        Map<Character, BitSequence> lookupTable = trie.buildLookupTable();
        // create a list of BitSequences
        List<BitSequence> bsList = new ArrayList<>();
        for (char c : inputSymbols) {
            BitSequence charBs = lookupTable.get(c);
            bsList.add(charBs);
        }
        // assemble all bitSequences in the list
        BitSequence assembledBs = BitSequence.assemble(bsList);
        // write the assembled result into the .huf file
        ow.writeObject(assembledBs);
    }

}
