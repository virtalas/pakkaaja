
package main.java.io;

import java.util.ArrayList;

public class MockOutStream implements OutStream {
    
    public ArrayList<String> output;

    public MockOutStream() {
        output = new ArrayList<>();
    }

    @Override
    public void write(int b) {
        output.add(Integer.toBinaryString(b));
    }

    @Override
    public void close() {
    }
}
