
package main.java.huffman;

/**
 * A node in the Huffman tree that has two subtrees and their combined frequency.
 */
class HuffmanInternalNode extends HuffmanTree {
    
    /**
     * Left subtree.
     */
    public HuffmanTree left;
    
    /**
     * Right subtree.
     */
    public HuffmanTree right;
    
    /**
     * Initializes an empty node.
     */
    public HuffmanInternalNode() {
        super(0);
    }
 
    /**
     * Initializes the subtrees and sums their frequencies.
     * @param left subtree
     * @param right subtree
     */
    public HuffmanInternalNode(HuffmanTree left, HuffmanTree right) {
        super(left.frequency + right.frequency);
        this.left = left;
        this.right = right;
    }
}
