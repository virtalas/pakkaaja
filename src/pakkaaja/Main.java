package pakkaaja;

import io.FileInput;
import io.FileOutput;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    /**
     * @param args the command line arguments are source, destination.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Wrong number of arguments");
            return;
        }
        String sourcePath = args[0];
        String destinationPath = args[1];
        
        try {
            FileInput in = new FileInput(sourcePath);
            FileOutput out = new FileOutput(destinationPath);
            
            int readByte = in.read();
            while (readByte != -1) {
                out.write(readByte);
            }
            out.close();
        } catch (FileNotFoundException ex) {
        }
    }

}
