/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Reads bytes from the source file.
 */
public class InputByteStream implements InStream {

    /**
     * Used for reading bytes from the source file.
     */
    private InputStream in;

    /**
     * Initializes InputStream, and marks the beginning so the stream can be resetToBeginning.
     * @param path source file path
     */
    public InputByteStream(String path) {
        try {
            File file = new File(path);
            in = new BufferedInputStream(new FileInputStream(file));
            
            try {
                in.mark((int) file.length() + 1);
            } catch (Exception e) {
                System.out.println("The file is too large. " + e);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File was not found: " + ex);
        }
    }

    /**
     * Reads the next byte.
     *
     * @return the next byte or -1 if the file has ended or could not be read
     */
    @Override
    public int read() {
        try {
            return in.read();
        } catch (IOException ex) {
            System.out.println("Could not read file: " + ex);
            return -1;
        }
    }
    
    /**
     * Resets the input to the beginning, so that the next byte read will be the first byte of the file.
     */
    @Override
    public void resetToBeginning() {
        try {
            in.reset();
        } catch (IOException ex) {
            System.out.println("InStream could not be reset: " + ex);
        }
    }
    
    /**
     * Closes the input stream.
     */
    @Override
    public void close() {
        try {
            in.close();
        } catch (IOException ex) {
            System.out.println("Could not close OutputStream: " + ex);
        }
    }
}
