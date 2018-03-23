package huffman;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.io.FileInput;
import main.java.io.FileOutput;

public class HuffmanCoder {

    private final int[] byteFrequencies;
    private String[] codes;

    public HuffmanCoder(int[] bytes, int alphabetSize) {
        this.byteFrequencies = bytes;
        this.codes = new String[alphabetSize];
    }

    public void compress(String sourcePath, String destinationPath) throws FileNotFoundException {
        HuffmanTree root = buildTree();
        buildCodeList(root, new StringBuffer());

        // TODO: Save root tree to the beginning of the file.
        writeCompressedContent(sourcePath, destinationPath);
    }

    private void writeCompressedContent(String sourcePath, String destinationPath) throws FileNotFoundException {
        FileInput in = new FileInput(sourcePath);
        FileOutput out = new FileOutput(destinationPath);

        int readByte = in.readNext();

        while (readByte != -1) {
            out.writeBits(codes[readByte]);
            readByte = in.readNext();
        }
    }

    private HuffmanTree buildTree() {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        // initially, we have a forest of leaves
        // one for each non-empty character
        for (int i = 0; i < byteFrequencies.length; i++) {
            if (byteFrequencies[i] > 0) {
                trees.offer(new HuffmanLeaf(byteFrequencies[i], (char) i));
            }
        }

        // loop until there is only one tree left
        while (trees.size() > 1) {
            // two trees with least frequency
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();

            // put into new node and re-insert into queue
            trees.offer(new HuffmanInternalNode(a, b));
        }
        return trees.poll();
    }

    private void buildCodeList(HuffmanTree tree, StringBuffer prefix) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;

            // print out character, frequency, and code for this leaf (which is just the prefix)
            System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix);

            codes[leaf.value] = prefix.toString();

        } else if (tree instanceof HuffmanInternalNode) {
            HuffmanInternalNode node = (HuffmanInternalNode) tree;

            // traverse left
            prefix.append('0');
            buildCodeList(node.left, prefix);
            prefix.deleteCharAt(prefix.length() - 1);

            // traverse right
            prefix.append('1');
            buildCodeList(node.right, prefix);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}
