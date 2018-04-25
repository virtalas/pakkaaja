
package io;

import io.InStream;

public class MockInStream implements InStream {
    
    private final String input;
    private int i;
    private boolean charMode; // are the input characters chars, if not, they represent bits
    
    public MockInStream(String input) {
        this.input = input;
        this.i = 0;
        this.charMode = true;
    }
    
    public MockInStream(String input, boolean charMode) {
        this.input = input;
        this.i = 0;
        this.charMode = charMode;
    }
    
    @Override
    public int read() {
        if (charMode) {
            return readByteAsCharacters();
        }
        return readByteAsBits();
    }

    public int readByteAsCharacters() {
        if (i == input.length()) {
            return -1;
        }
        char c = input.charAt(i);
        i++;
        return c;
    }
    
    /**
     * Used when the set input represents bits, e.g. input="01101010..."
     */
    public int readByteAsBits() {
        if (i >= input.length()) {
            return -1;
        }
        
        String readByte = input.substring(i, i+8);
        i += 8;
                
        return Integer.parseInt(readByte, 2);
    }

    @Override
    public void close() {
    }
}
