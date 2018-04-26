
package LZW;

import io.FileInput;
import io.FileOutput;
import io.MockOutStream;
import org.junit.Test;
import static org.junit.Assert.*;

public class LZWDecoderTest {

    @Test
    public void testDecompress() {
        FileInput in = new FileInput("src/test/resources/lzw_simple_coded.txt");
        MockOutStream out = new MockOutStream();
        LZWDecoder decoder = new LZWDecoder(256, 12);
        decoder.decompress(in, new FileOutput(out));
        
        assertEquals("01110100", out.output.get(0)); // t
        assertEquals("01101000", out.output.get(1)); // h
        assertEquals("01101001", out.output.get(2)); // i
        assertEquals("01110011", out.output.get(3)); // s
        assertEquals("01101001", out.output.get(4)); // i
        assertEquals("01110011", out.output.get(5)); // s
        assertEquals("01110100", out.output.get(6)); // t
        assertEquals("01101000", out.output.get(7)); // h
        assertEquals("01100101", out.output.get(8)); // e
    }
    
    @Test
    public void testInitDictionary() {
        LZWDecoder decoder = new LZWDecoder(256, 12);
        decoder.initDictionary();
        
        assertEquals("" + (char) 0, decoder.dictionary.get(0));
        assertEquals("" + (char) 116, decoder.dictionary.get(116));
        assertEquals("" + (char) 255, decoder.dictionary.get(255));
        assertNull(decoder.dictionary.get(256));
    }
}
