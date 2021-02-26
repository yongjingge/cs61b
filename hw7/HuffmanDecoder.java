public class HuffmanDecoder {

    public static void main(String[] args) {

        // read
        ObjectReader or = new ObjectReader(args[0]);
        BinaryTrie binaryTrie = (BinaryTrie) or.readObject();
        int numberOfSymbols = (Integer) or.readObject();
        BitSequence bsAssembled = (BitSequence) or.readObject();

        /**
         * Repeat until there are no more symbols:
         *     4a: Perform a longest prefix match on the massive sequence.
         *     4b: Record the symbol in some data structure.
         *     4c: Create a new bit sequence containing the remaining unmatched bits.
         */
        char[] reverseSymbols = new char[numberOfSymbols];
        for (int i = 0; i < numberOfSymbols; i += 1) {
            Match match = binaryTrie.longestPrefixMatch(bsAssembled);
            reverseSymbols[i] = match.getSymbol();
            bsAssembled = bsAssembled.allButFirstNBits(match.getSequence().length());
        }

        // write the symbols
        FileUtils.writeCharArray(args[1], reverseSymbols);
    }
}
