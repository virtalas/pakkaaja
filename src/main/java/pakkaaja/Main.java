package main.java.pakkaaja;

import main.java.huffman.HuffmanCoder;
import main.java.huffman.HuffmanDecoder;
import main.java.io.FileInput;

/**
 * Main class.
 */
public class Main {

    /**
     * Size of the array used to store character frequencies, or how many unique
     * characters there are. In this case it is the different values of one
     * byte.
     */
    public static final int ALPHABET_SIZE = 256;

    /**
     * Main program for compressing. A character is assumed to be one byte long.
     * In the case it is not, the program will still work, just not as
     * efficiently space saving wise.
     * 
     * The command argument is either hc for huffman compress or hd for huffman decompress.
     *
     * @param args the command line arguments are: command, source, destination.
     */
    public static void main(String[] args) {
        // TODO: agruments for compression/decompression
        if (args.length != 3) {
            printUsage();
            return;
        }

        String command = args[0];
        String sourcePath = args[1];
        String destinationPath = args[2];
        
        
        // Pre-coded content:
        FileInput in3 = new FileInput(sourcePath);
        int readByte3 = in3.readByte();
        while (readByte3 != -1) {
            System.out.println(String.format("%8s", Integer.toBinaryString(readByte3 & 0xFF)).replace(' ', '0'));
            readByte3 = in3.readByte();
        }
        in3.close();

        
        switch (command) {
            case "hc":
                huffmanCompress(sourcePath, destinationPath);
                break;
            case "hd":
                huffmanDecompress(sourcePath, destinationPath);
                break;
            default:
                printUsage();
                break;
        }

        // View some of the destination content
        System.out.println("\n\nDestination content:");
        FileInput in2 = new FileInput(destinationPath);
        int readByte2 = in2.readByte();
        for (int i = 0; i < 30 && readByte2 != -1; i++) {
            System.out.println(String.format("%8s", Integer.toBinaryString(readByte2 & 0xFF)).replace(' ', '0'));
            readByte2 = in2.readByte();
        }
        in2.close();
    }

    /**
     * Reads the file and generates the
     * character frequencies into a list that is passed on to the coder that
     * will huffman compress the file.
     * 
     * @param sourcePath
     * @param destinationPath 
     */
    public static void huffmanCompress(String sourcePath, String destinationPath) {
        int[] byteFrequencies = new int[ALPHABET_SIZE];

        FileInput in = new FileInput(sourcePath);
        int readByte = in.readByte();

        while (readByte != -1) {
            byteFrequencies[readByte]++; // Character as index, frequency count as value.
            readByte = in.readByte();
        }
        in.close();

        HuffmanCoder coder = new HuffmanCoder(byteFrequencies, ALPHABET_SIZE);
        coder.compress(sourcePath, destinationPath);
    }

    /**
     * Initiates the huffman decompressing.
     * 
     * @param sourcePath
     * @param destinationPath 
     */
    public static void huffmanDecompress(String sourcePath, String destinationPath) {
        HuffmanDecoder decoder = new HuffmanDecoder();
        decoder.decompress(sourcePath, destinationPath);
    }

    /**
     * Prints usage instructions.
     */
    public static void printUsage() {
        System.out.println("Usage: [hc|hd] [source] [destination]");
        System.out.println("hc = Huffman compress");
        System.out.println("hd = Huffman decompress");
    }
}
