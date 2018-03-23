package main.java.pakkaaja;

import huffman.HuffmanCoder;
import main.java.io.FileInput;
import main.java.io.FileOutput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static final int ALPHABET_SIZE = 256;

    /**
     * @param args the command line arguments are source, destination.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Wrong number of arguments");
            return;
        }

        String sourcePath = args[0];
        String destinationPath = args[1];
        int[] byteFrequencies = new int[ALPHABET_SIZE];

        FileInput in = new FileInput(sourcePath);

        int readByte = in.readNext();

        while (readByte != -1) {
            byteFrequencies[readByte]++;
            readByte = in.readNext();
        }

        HuffmanCoder coder = new HuffmanCoder(byteFrequencies, ALPHABET_SIZE);

        coder.compress(sourcePath, destinationPath);

        // Check the compressed content
        FileInput in2 = new FileInput(destinationPath);

        int readByte2 = in2.readNext();

        for (int i = 0; i < 100; i++) {
            System.out.println(String.format("%8s", Integer.toBinaryString(readByte2 & 0xFF)).replace(' ', '0'));
            readByte2 = in2.readNext();
        }
    }
}
