package main.java.huffman;

/**
 * Used for HuffmanLeaf and HuffmanInternalNode.
 */
public abstract class HuffmanTree implements Comparable<HuffmanTree> {

    /**
     * How many times the character was found in the source, or combined frequency of subtrees.
     */
    public final int frequency;

    /**
     * Sets the frequency.
     * @param freq frequency of the character, or combined frequency of subtrees
     */
    public HuffmanTree(int freq) {
        if (freq < 0) {
            throw new RuntimeException("Frequency can't be negative.");
        }
        frequency = freq;
    }

    /**
     * Compares two trees based on frequency. Used for PriorityQueue.
     * @param tree compare against this tree
     * @return positive or negative comparison
     */
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}
