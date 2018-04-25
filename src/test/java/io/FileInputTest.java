
package io;

import main.java.io.FileInput;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileInputTest {

    @Test
    public void testReadNext() {
        FileInput in = new FileInput(new MockInStream("te"));
        
        assertEquals(116, in.readByte());
        assertEquals(101, in.readByte());
    }
    
    @Test
    public void testReadBit() {
        FileInput in = new FileInput(new MockInStream("11110010"+"01100000", false));
        
        assertEquals(1, in.readBit());
        assertEquals(1, in.readBit());
        assertEquals(1, in.readBit());
        assertEquals(1, in.readBit());
        assertEquals(0, in.readBit());
        assertEquals(0, in.readBit());
        assertEquals(1, in.readBit());
        assertEquals(0, in.readBit());
        
        assertEquals(0, in.readBit());
        assertEquals(1, in.readBit());
        assertEquals(1, in.readBit());
        assertEquals(0, in.readBit());
        assertEquals(0, in.readBit());
        assertEquals(0, in.readBit());
        assertEquals(0, in.readBit());
        assertEquals(0, in.readBit());
        
        assertEquals(-1, in.readBit());
    }
    
    @Test
    public void testReadNumberOfBits() {
        FileInput in = new FileInput(new MockInStream("10010011", false));
        
        assertEquals(1, in.readNumberOfBits(1));
        assertEquals(9, in.readNumberOfBits(6));
        assertEquals(1, in.readNumberOfBits(1));
        assertEquals(-1, in.readNumberOfBits(1));
    }
}
