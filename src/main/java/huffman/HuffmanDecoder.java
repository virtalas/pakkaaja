package huffman;

import coder.Decoder;
import io.FileInput;
import io.FileOutput;

/**
 * Implements decompressing a huffman coded file.
 */
public class HuffmanDecoder implements Decoder {

    /**
     * Creates the Huffman tree and writes the decompressed file. First the tree
     * is read from the beginning of the file. Finally the file is read again
     * and decompressed and written out according to the tree.
     *
     * @param in reads the file
     * @param out writes the file
     */
    @Override
    public void decompress(FileInput in, FileOutput out) {
        HuffmanTree root = readHuffmanTree(in);
        writeDecompressedContent(in, out, root);
        out.close();
    }

    /**
     * Reads the tree's structure. The byte containing the final part of the
     * structure is possibly not a full byte padded with zeros at the end, so
     * the FileInput is advanced to the beginning of the next full byte. Then
     * the values for the tree leaves are read and updated to the tree.
     *
     * @param in reads the file
     * @return the root of the HuffmanTree that contains the structure with
     * empty leaves.
     */
    public HuffmanTree readHuffmanTree(FileInput in) {
        HuffmanTree root = readTreeStructure(in);
        in.advanceToNextByte();
        readTreeLeaves(in, root);
        return root;
    }

    /**
     * Traverses the tree in pre-order and creates the tree with empty leaves. A
     * leaf is marked with 0 and an internal node with 1.
     *
     * @param in reads the file
     * @return HuffmanTree with empty leaves
     */
    private HuffmanTree readTreeStructure(FileInput in) {
        if (in.readBit() == 0) {
            HuffmanLeaf leaf = new HuffmanLeaf();
            return leaf;
        } else {
            HuffmanInternalNode node = new HuffmanInternalNode();
            node.left = readTreeStructure(in);
            node.right = readTreeStructure(in);
            return node;
        }
    }

    /**
     * Traverses the tree in pre-order and updates the leaf values with
     * corresponding bytes read from the file.
     *
     * @param in reads the file
     * @param tree HuffmanTree with empty leaves, but correct structure
     */
    public void readTreeLeaves(FileInput in, HuffmanTree tree) {
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;
            leaf.value = in.readByte();
        } else {
            HuffmanInternalNode node = (HuffmanInternalNode) tree;
            readTreeLeaves(in, node.left);
            readTreeLeaves(in, node.right);
        }
    }

    /**
     * Reads the compressed content from the source file and writes the
     * corresonding decompressed content into the destination file.
     * 
     * Stops writing at the end of file character (the NUL character with a value of 0).
     */
    private void writeDecompressedContent(FileInput in, FileOutput out, HuffmanTree root) {
        int readByte = readNextCharacterByte(in, root);

        while (readByte != -1 && readByte != 0) {
            out.writeByte(readByte);
            readByte = readNextCharacterByte(in, root);
        }
    }

    /**
     * Traverses the tree according to the Huffman coding. 0 means traversing
     * left and 1 traversing right. When a leaf is found, its value is returned.
     *
     * @param in reads the file
     * @param tree Huffman tree to traverse
     * @return the next byte
     */
    public int readNextCharacterByte(FileInput in, HuffmanTree tree) {
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;
            return leaf.value;
        }
        
        int readBit = in.readBit();
        if (readBit == -1) {
            return readBit;
        }

        int readByte = -1;
        
        if (readBit == 0) {
            HuffmanInternalNode node = (HuffmanInternalNode) tree;
            readByte = readNextCharacterByte(in, node.left);
        } else {
            HuffmanInternalNode node = (HuffmanInternalNode) tree;
            readByte = readNextCharacterByte(in, node.right);
        }

        return readByte;
    }
}
