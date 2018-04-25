package io;

import io.FileInput;
import io.OutputByteStream;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class OutputByteStreamTest {
    
    private OutputByteStream out;

    @Test
    public void testWrite() {
        out = new OutputByteStream("src/test/resources/output.txt");
        out.write(116);
        out.close();
        FileInput in = new FileInput("src/test/resources/output.txt");
        assertEquals(116, in.readByte());
    }
}
