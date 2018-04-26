
package LZW;

import io.FileInput;
import io.FileOutput;
import io.MockInStream;
import io.MockOutStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class LZWCoderTest {

    @Test
    public void testCompress() {
        MockInStream in = new MockInStream("thisisthe");
        MockOutStream out = new MockOutStream();
        LZWCoder coder = new LZWCoder(256, 12);
        coder.compress(new FileInput(in), new FileOutput(out));
        
        assertEquals("00000111", out.output.get(0)); // 00000111 0100 = 116 = "t" (space means its part of the next byte)
        assertEquals("01000000", out.output.get(1)); // 0000 01101000 = 104 = "h"
        assertEquals("01101000", out.output.get(2)); // 
        assertEquals("00000110", out.output.get(3)); // 00000110 1001 = 105 = "i"
        assertEquals("10010000", out.output.get(4)); // 0000 01110011 = 115 = "s"
        assertEquals("01110011", out.output.get(5)); // 
        assertEquals("00010000", out.output.get(6)); // 00010000 0010 = 258 = "is"
        assertEquals("00100001", out.output.get(7)); // 0001 00000000 = 256 = "th"
        assertEquals("00000000", out.output.get(8)); // 
        assertEquals("00000110", out.output.get(9)); // 00000110 0101 = 101 = "e"
        assertEquals("01010000", out.output.get(10)); // 0000 (padding, not read since not 12 bits)
    }
    
    @Test
    public void testInitDictionary() {
        LZWCoder coder = new LZWCoder(256, 12);
        coder.initDictionary();
        
        assertEquals(0, (int) coder.dictionary.get("" + (char) 0));
        assertEquals(116, (int) coder.dictionary.get("t"));
        assertEquals(255, (int) coder.dictionary.get(("" + (char) 255)));
        assertNull(coder.dictionary.get("" + (char) 256));
    }
}
