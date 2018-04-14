package main.java.LZW;

import java.util.HashMap;
import main.java.io.FileInput;
import main.java.io.FileOutput;

public class LZWCoder {

    private HashMap<String, Integer> dictionary;
    private final int ALPHABET_SIZE;
    
    public LZWCoder(int alphabetSize) {
        ALPHABET_SIZE = alphabetSize;
    }

    public void compress(FileInput in, FileOutput out) {
        dictionary = new HashMap<>();
        StringBuffer w = new StringBuffer();
        int tableSize =  ALPHABET_SIZE;

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            dictionary.put("" + (char) i, i);
        }

        while (true) {
            int readByte = in.readByte();
            if (readByte == -1) {
                break;
            }
            int k = (char) readByte;
            if (dictionary.containsKey(w.toString() + k)) {
                w.append(k);
            } else {
                // output code for w
                w.append(k);
                dictionary.put(w.toString(), ++tableSize);
            }
        }
    }
}
