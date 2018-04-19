
package main.java.io;

import java.util.ArrayList;

public class MockOutStream implements OutStream {
    
    public ArrayList<String> output;

    public MockOutStream() {
        output = new ArrayList<>();
    }

    @Override
    public void write(int b) {
        if (b > 256) {
            throw new RuntimeException("Byte value can't be over 256.");
        }
        
        String s = Integer.toBinaryString(b);
        while (s.length() != 8) {
            s = "0" + s;
        }
        output.add(s);
    }

    @Override
    public void close() {
    }
}
