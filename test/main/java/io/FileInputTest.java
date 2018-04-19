
package main.java.io;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileInputTest {
    
    public FileInputTest() {
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
