package main.java.LZW;

import java.util.HashMap;
import main.java.io.FileInput;
import main.java.io.FileOutput;

public class LZWCoder {

    private HashMap<String, Integer> dictionary;
    private final int ALPHABET_SIZE;
    private final int MAX_TABLE_SIZE;
    
    public LZWCoder(int alphabetSize, int codeLength) {
        ALPHABET_SIZE = alphabetSize;
        int length = 1;
        for (int i = 1; i <= codeLength; i++) {
            length *= 2;
        }
        MAX_TABLE_SIZE = length;
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
            
            char k = (char) readByte;
            if (dictionary.containsKey(w.toString() + k)) {
                w.append(k);
            } else {
                // output code for w
                out.writeNumberOfBits(MAX_TABLE_SIZE, dictionary.get(w.toString()));
                w.append(k);
                if (tableSize < MAX_TABLE_SIZE) {
                    dictionary.put(w.toString(), tableSize++);
                }
                // ehkä:
                w.setLength(0);
                w.append(k);
            }
            
            if (readByte == -1) {
                break;
            }
        }
        
        in.close();
        out.close();
    }
}
