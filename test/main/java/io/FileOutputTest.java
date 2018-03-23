package main.java.io;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileOutputTest {

    public FileOutputTest() {
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
    public void testWriteBitsDoesntWriteUntilFullByte() {
        MockOutStream mockOut = new MockOutStream();
        FileOutput out = new FileOutput(mockOut);

        out.writeBits("1010101");
        assertEquals(0, mockOut.output.size());

        out.writeBits("0");
        assertEquals("10101010", mockOut.output.get(0));
    }

    @Test
    public void testWriteBitsWritesOnlyOnesOrZeros() {
        MockOutStream mockOut = new MockOutStream();
        FileOutput out = new FileOutput(mockOut);

        try {
            out.writeBits("fhjldgsgldshjldskfghjkd");
        } catch (Exception e) {
        }
        assertEquals(0, mockOut.output.size());

        try {
            out.writeBits("056397987013576");
        } catch (Exception e) {
        }
        assertEquals(0, mockOut.output.size());
    }

    @Test
    public void testWriteBitDoesntWriteUntilFullByte() {
        MockOutStream mockOut = new MockOutStream();
        FileOutput out = new FileOutput(mockOut);

        out.writeBit(1);
        out.writeBit(1);
        out.writeBit(1);

        assertEquals(0, mockOut.output.size());

        out.writeBit(1);
        out.writeBit(1);
        out.writeBit(1);
        out.writeBit(1);
        out.writeBit(0);

        System.out.println(mockOut.output.toString());
        assertEquals(1, mockOut.output.size());
    }

    @Test
    public void testWriteBitWritesOnlyOneOrZero() {
        MockOutStream mockOut = new MockOutStream();
        FileOutput out = new FileOutput(mockOut);

        try {
            out.writeBit(-1);
        } catch (Exception e) {
        }
        try {
            out.writeBit(4);
        } catch (Exception e) {
        }
        try {
            out.writeBit(1010);
        } catch (Exception e) {
        }
        try {
            out.writeBit(001);
        } catch (Exception e) {
        }

        assertEquals(0, mockOut.output.size());
    }
}
