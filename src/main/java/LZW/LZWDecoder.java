package main.java.LZW;

import java.util.HashMap;
import main.java.io.FileInput;
import main.java.io.FileOutput;
import main.java.pakkaaja.Main;

public class LZWDecoder {

    private HashMap<Integer, String> dictionary;
    private final int ALPHABET_SIZE;
    private final int MAX_TABLE_SIZE;
    private final int CODE_LENGTH;

    public LZWDecoder(int alphabetSize, int codeLength) {
        ALPHABET_SIZE = alphabetSize;
        CODE_LENGTH = codeLength;
        MAX_TABLE_SIZE = Main.twoToPower(codeLength);
    }

    public void decompress(FileInput in, FileOutput out) {
        dictionary = new HashMap<>();
        int tableSize = ALPHABET_SIZE;

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            dictionary.put(i, "" + (char) i);
        }

        String previosCharacters = "";

        while (true) {
            int readByte = in.readNumberOfBits(CODE_LENGTH);
            if (readByte == -1) {
                break;
            }
            String charactersToWrite = null;

            if (dictionary.containsKey(readByte)) {
                charactersToWrite = dictionary.get(readByte);
            } else if (readByte == tableSize) {
                charactersToWrite = previosCharacters + previosCharacters.charAt(0);
            }

            out.writeCharacters(charactersToWrite);

            if (tableSize < MAX_TABLE_SIZE && !previosCharacters.isEmpty()) {
                dictionary.put(tableSize++, previosCharacters + charactersToWrite.charAt(0));
            }

            previosCharacters = charactersToWrite;
        }

        in.close();
        out.close();
    }
}
