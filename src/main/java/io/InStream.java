
package io;

public interface InStream {
    public int read();
    public void resetToBeginning();
    public void close();
}
