package pakkaajaMain;

import io.FileInput;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class MainTest {

    @Test
    public void testStartPrintsUsageWhenIncorrectNumberOfParameters() {
        assertEquals(Main.usageInstructions(), Main.start(new String[]{"hc", "src/test/resources/file1"}));
        assertEquals(Main.usageInstructions(), Main.start(new String[]{"hc", "src/test/resources/file1", "src/test/resources/file2", "src/test/resources/file3"}));
    }

    @Test
    public void testStartPrintsUsageWhenIncorrectParameters() {
        assertEquals(Main.usageInstructions(), Main.start(new String[]{"hcc", "src/test/resources/file1", "src/test/resources/file2"}));
        assertEquals(Main.usageInstructions(), Main.start(new String[]{"", "src/test/resources/file1", "src/test/resources/file2"}));
        assertEquals(Main.usageInstructions(), Main.start(new String[]{"src/test/resources/file", "src/test/resources/file1", "src/test/resources/file2"}));
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testStartPrintsErrorWhenFileNotFoundHc() {
        exception.expect(Exception.class);
        Main.start(new String[]{"hc", "src/test/resources/file1", "src/test/resources/file2"});
    }

    @Test
    public void testStartPrintsErrorWhenFileNotFoundHd() {
        exception.expect(Exception.class);
        Main.start(new String[]{"hd", "src/test/resources/file1", "src/test/resources/file2"});
    }

    @Test
    public void testHuffmanCompressRuns() {
        Main.start(new String[]{"hc", "src/test/resources/h_simple.txt", "src/test/resources/output.txt"});
        FileInput in = new FileInput("src/test/resources/output.txt");
        assertEquals(168, in.readByte()); // 10101000 first byte, tree structure
    }

    @Test
    public void testHuffmanDecompressRuns() {
        Main.start(new String[]{"hd", "src/test/resources/h_simple_coded.txt", "src/test/resources/output.txt"});
        FileInput in = new FileInput("src/test/resources/output.txt");
        assertEquals(116, in.readByte()); // 01110100 first byte, "t"
    }
    
    @Test
    public void testLZWCompressRuns() {
        Main.start(new String[]{"lc", "src/test/resources/lzw_simple.txt", "src/test/resources/output.txt"});
        FileInput in = new FileInput("src/test/resources/output.txt");
        assertEquals(7, in.readByte()); // 00000111
        assertEquals(64, in.readByte()); // + 01000000: first char, uses 12 bits; 000001110100 = 116 = "t"
    }
    
    @Test
    public void testLZWDecompressRuns() {
        Main.start(new String[]{"ld", "src/test/resources/lzw_simple_coded.txt", "src/test/resources/output.txt"});
        FileInput in = new FileInput("src/test/resources/output.txt");
        assertEquals(116, in.readByte()); // 01110100 first byte, "t"
    }
    
    @Test
    public void testTwoToPower() {
        assertEquals(1, MathUtils.twoToPower(0));
        assertEquals(2, MathUtils.twoToPower(1));
        assertEquals(4, MathUtils.twoToPower(2));
        assertEquals(8, MathUtils.twoToPower(3));
        assertEquals(256, MathUtils.twoToPower(8));
        assertEquals(4096, MathUtils.twoToPower(12));
    }
    
    @Test
    public void testTwoToPowerNegativeArgumentReturnsZero() {
        assertEquals(0, MathUtils.twoToPower(-1));
        assertEquals(0, MathUtils.twoToPower(-7));
        assertEquals(0, MathUtils.twoToPower(-6543));
    }
}
