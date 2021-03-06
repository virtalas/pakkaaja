package io;

import org.junit.Test;
import static org.junit.Assert.*;

public class FileOutputTest {

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
        
        assertEquals(0, mockOut.output.size());
        
        out.writeBit(0);

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
    
    @Test
    public void testWriteNumberOfBits() {
//        MockOutStream mockOut = new MockOutStream();
//        FileOutput out = new FileOutput(mockOut);
//        System.out.println("start");
//        out.writeNumberOfBits(12, 1);
//        
//        assertEquals(0, mockOut.output.get(0));
    }
}
