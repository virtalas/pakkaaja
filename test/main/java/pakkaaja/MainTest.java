package main.java.pakkaaja;

import main.java.io.FileInput;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class MainTest {

    public MainTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testStartPrintsUsageWhenIncorrectNumberOfParameters() {
        assertEquals(Main.usageInstructions(), Main.start(new String[]{"hc"}));
        assertEquals(Main.usageInstructions(), Main.start(new String[]{"hc", "file1"}));
        assertEquals(Main.usageInstructions(), Main.start(new String[]{"hc", "file1", "file2", "file3"}));
    }

    @Test
    public void testStartPrintsUsageWhenIncorrectParameters() {
        assertEquals(Main.usageInstructions(), Main.start(new String[]{"hcc", "file1", "file2"}));
        assertEquals(Main.usageInstructions(), Main.start(new String[]{"", "file1", "file2"}));
        assertEquals(Main.usageInstructions(), Main.start(new String[]{"file", "file1", "file2"}));
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testStartPrintsErrorWhenFileNotFoundHc() {
        exception.expect(Exception.class);
        Main.start(new String[]{"hc", "file1", "file2"});
    }

    @Test
    public void testStartPrintsErrorWhenFileNotFoundHd() {
        exception.expect(Exception.class);
        Main.start(new String[]{"hd", "file1", "file2"});
    }

    @Test
    public void testHuffmanCompressRuns() {
        Main.start(new String[]{"hc", "test/resources/h_simple.txt", "test/resources/output.txt"});
        FileInput in = new FileInput("test/resources/output.txt");
        assertEquals(168, in.readByte()); // 10101000 first byte, tree structure
    }

    @Test
    public void testHuffmanDecompressRuns() {
        Main.start(new String[]{"hd", "test/resources/h_simple_coded.txt", "test/resources/output.txt"});
        FileInput in = new FileInput("test/resources/output.txt");
        assertEquals(116, in.readByte()); // 01110100 first byte, "t"
    }
}
