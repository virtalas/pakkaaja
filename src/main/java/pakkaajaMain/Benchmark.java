
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

    public static void runBenchmarkTests() {
        String wizardOfOzSource = "src/test/resources/WizardOfOz.txt";

        System.out.println("\n=== Huffman coding ===\n");
        HuffmanCoder huffmanCoder = new HuffmanCoder(ALPHABET_SIZE);
        compressDecompressBenchmark(wizardOfOzSource, "Wizard of Oz", huffmanCoder, new HuffmanDecoder());

        System.out.println("\n=== Lempel-Ziv-Welch ===\n");
        compressDecompressBenchmark(wizardOfOzSource, "Wizard of Oz",
                new LZWCoder(ALPHABET_SIZE, CODE_LENGTH), new LZWDecoder(ALPHABET_SIZE, CODE_LENGTH));
    }

    private static void compressDecompressBenchmark(String testFileSource, String fileName, Coder coder, Decoder decoder) {
        long fileSize = new File(testFileSource).length();

        System.out.println("\"" + fileName + "\", " + fileSize + " bytes:");

        // Compress 100 times
        compressBenchmark(testFileSource, coder);

        // Decompress 100 times
        decompressBenchmark(decoder, fileSize);
    }

    private static void compressBenchmark(String testFileSource, Coder coder) {
        long totalCompressTime = 0;
        for (int i = 0; i < 100; i++) {
            long startTime = System.currentTimeMillis();
            coder.compress(new FileInput(testFileSource), new FileOutput("src/test/resources/output.txt"));
            long endTime = System.currentTimeMillis();
            totalCompressTime += endTime - startTime;
        }

        System.out.println("  Compress time: " + (totalCompressTime / 100) + " ms");
    }

    private static void decompressBenchmark(Decoder decoder, long fileSize) {
        long totalDecompressTime = 0;
        for (int i = 0; i < 100; i++) {
            long startTime = System.currentTimeMillis();
            decoder.decompress(new FileInput("src/test/resources/output.txt"), new FileOutput("src/test/resources/output2.txt"));
            long endTime = System.currentTimeMillis();
            totalDecompressTime += endTime - startTime;
        }

        long fileCompressedSize = new File("src/test/resources/output.txt").length();
        int efficiency = (int) (((double) fileCompressedSize / fileSize) * 100);

        System.out.println("  Decompress time: " + (totalDecompressTime / 100) + " ms");
        System.out.println("  Compressed size: " + fileCompressedSize + " bytes (" + efficiency + "%)");
    }
}
