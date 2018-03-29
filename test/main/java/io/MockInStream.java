
package main.java.io;

public class MockInStream implements InStream {
    
    private final String input;
    private int i;
    
    public MockInStream(String input) {
        this.input = input;
        this.i = 0;
    }

    @Override
    public int read() {
        char c = input.charAt(i);
        i++;
        return c;
    }

    @Override
    public void close() {
    }
}
