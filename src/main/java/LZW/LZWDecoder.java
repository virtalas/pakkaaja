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
        StringBuffer w = new StringBuffer();
        int tableSize = ALPHABET_SIZE;

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            dictionary.put(i, "" + (char) i);
        }

        String Encode_values = "";

        while (true) {
            int readByte = in.readNumberOfBits(CODE_LENGTH);
            if (readByte == -1) {
                break;
            }
            String get_value_from_table = null;

            if (dictionary.containsKey(readByte)) {
                get_value_from_table = dictionary.get(readByte);
            } else if (readByte == tableSize) {
                get_value_from_table = Encode_values + Encode_values.charAt(0);
            }

            out.writeCharacters(get_value_from_table);

            if (tableSize < MAX_TABLE_SIZE && !Encode_values.isEmpty()) {
                dictionary.put(tableSize++, Encode_values + get_value_from_table.charAt(0));
            }

            Encode_values = get_value_from_table;
        }

        in.close();
        out.close();
    }
}
