
package structures;


public class LinkedDictionaryEntry {

    public Object key;
    public Object value;
    public LinkedDictionaryEntry next;
    
    public LinkedDictionaryEntry() {
        
    }
    
    public void add(Object key, Object value) {
        if (this.key == null && this.value == null && this.next == null) {
            this.key = key;
            this.value = value;
        } else if (this.next == null) {
            this.next = new LinkedDictionaryEntry();
            this.next.add(key, value);
        } else {
            this.next.add(key, value);
        }
        
    }
    
    public Object get(Object key) {
        if (this.key == key) {
            return this.value;
        } else if (this.next != null) {
            return this.next.get(key);
        }
        return null;
    }
    
    public boolean contains(Object key) {
        return get(key) != null;
    }
}
