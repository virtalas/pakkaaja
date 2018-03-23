
package huffman;

class HuffmanInternalNode extends HuffmanTree {
    public final HuffmanTree left;
    public final HuffmanTree right;
 
    public HuffmanInternalNode(HuffmanTree left, HuffmanTree right) {
        super(left.frequency + right.frequency);
        this.left = left;
        this.right = right;
    }
}
