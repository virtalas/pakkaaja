
package pakkaajaMain;

import LZW.LZWCoder;
import LZW.LZWDecoder;
import coder.Coder;
import coder.Decoder;
import huffman.HuffmanCoder;
import huffman.HuffmanDecoder;
import io.FileInput;
import io.FileOutput;
import java.io.File;
import static pakkaajaMain.Main.ALPHABET_SIZE;
import static pakkaajaMain.Main.CODE_LENGTH;

public class Benchmark {

    private static int t;

    public static void runBenchmarkTests(int runTimes) {
        t = runTimes != -1 ? runTimes : 10;
        
        String wizardOfOzSource = "src/test/resources/WizardOfOz.txt";
        String draculaCroppedSource = "src/test/resources/Dracula.txt";
        String jpnSource = "src/test/resources/jpn.txt";
        String tenSource = "src/test/resources/10.txt";

        System.out.println("\n=== Huffman coding ===\n");
        compressDecompressBenchmark(wizardOfOzSource, "Wizard of Oz", new HuffmanCoder(ALPHABET_SIZE), new HuffmanDecoder());
        compressDecompressBenchmark(draculaCroppedSource, "Dracula (cropped)", new HuffmanCoder(ALPHABET_SIZE), new HuffmanDecoder());
        compressDecompressBenchmark(jpnSource, "雲形紋章", new HuffmanCoder(ALPHABET_SIZE), new HuffmanDecoder());
        compressDecompressBenchmark(tenSource, "10MB of english text", new HuffmanCoder(ALPHABET_SIZE), new HuffmanDecoder());

        System.out.println("\n=== Lempel-Ziv-Welch ===\n");
        compressDecompressBenchmark(wizardOfOzSource, "Wizard of Oz",
                new LZWCoder(ALPHABET_SIZE, CODE_LENGTH), new LZWDecoder(ALPHABET_SIZE, CODE_LENGTH));
        compressDecompressBenchmark(draculaCroppedSource, "Dracula (cropped)",
                new LZWCoder(ALPHABET_SIZE, CODE_LENGTH), new LZWDecoder(ALPHABET_SIZE, CODE_LENGTH));
        compressDecompressBenchmark(jpnSource, "雲形紋章",
                new LZWCoder(ALPHABET_SIZE, CODE_LENGTH), new LZWDecoder(ALPHABET_SIZE, CODE_LENGTH));
        compressDecompressBenchmark(tenSource, "10MB of english text",
                new LZWCoder(ALPHABET_SIZE, CODE_LENGTH), new LZWDecoder(ALPHABET_SIZE, CODE_LENGTH));

        System.out.println("\n=== LZW & Huffman ===\n");
        lzwHuffmanCompressDecompressBenchmark(draculaCroppedSource, "Dracula");
    }

    private static void compressDecompressBenchmark(String testFileSource, String fileName, Coder coder, Decoder decoder) {
        long fileSize = new File(testFileSource).length();

        System.out.println("\"" + fileName + "\", " + fileSize + " bytes:");

        // Compress x times
        compressBenchmark(testFileSource, coder);

        // Decompress x times
        decompressBenchmark(decoder, fileSize);
    }

    private static void compressBenchmark(String testFileSource, Coder coder) {
        long totalCompressTime = 0;
        for (int i = 0; i < t; i++) {
            long startTime = System.currentTimeMillis();
            coder.compress(new FileInput(testFileSource), new FileOutput("src/test/resources/output.txt"));
            long endTime = System.currentTimeMillis();
            totalCompressTime += endTime - startTime;
        }

        System.out.println("  Compress time: " + (totalCompressTime / t) + " ms");
    }

    private static void decompressBenchmark(Decoder decoder, long fileSize) {
        long totalDecompressTime = 0;
        for (int i = 0; i < t; i++) {
            long startTime = System.currentTimeMillis();
            decoder.decompress(new FileInput("src/test/resources/output.txt"), new FileOutput("src/test/resources/output2.txt"));
            long endTime = System.currentTimeMillis();
            totalDecompressTime += endTime - startTime;
        }

        long fileCompressedSize = new File("src/test/resources/output.txt").length();
        int efficiency = (int) (((double) fileCompressedSize / fileSize) * 100);

        System.out.println("  Decompress time: " + (totalDecompressTime / t) + " ms");
        System.out.println("  Compressed size: " + fileCompressedSize + " bytes (" + efficiency + "%)");
    }

    private static void lzwHuffmanCompressDecompressBenchmark(String testFileSource, String fileName) {
        long fileSize = new File(testFileSource).length();
        System.out.println("\"" + fileName + "\", " + fileSize + " bytes:");

        // Compress
        long totalCompressTime = 0;
        for (int i = 0; i < t; i++) {
            long startTime = System.currentTimeMillis();
            Main.lzwHuffmanCompress(testFileSource, "src/test/resources/output.txt");
            long endTime = System.currentTimeMillis();
            totalCompressTime += endTime - startTime;
        }

        System.out.println("  Compress time: " + (totalCompressTime / t) + " ms");

        // Decompress
        long totalDecompressTime = 0;
        for (int i = 0; i < t; i++) {
            long startTime = System.currentTimeMillis();
            Main.lzwHuffmanDecompress("src/test/resources/output.txt", "src/test/resources/output2.txt");
            long endTime = System.currentTimeMillis();
            totalDecompressTime += endTime - startTime;
        }

        long fileCompressedSize = new File("src/test/resources/output.txt").length();
        int efficiency = (int) (((double) fileCompressedSize / fileSize) * 100);

//        System.out.println("  Decompress time: " + (totalDecompressTime / T) + " ms");
        System.out.println("  Compressed size: " + fileCompressedSize + " bytes (" + efficiency + "%)");
    }
}
