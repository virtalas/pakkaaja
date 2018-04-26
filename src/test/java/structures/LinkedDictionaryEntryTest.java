package structures;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LinkedDictionaryEntryTest {

    private LinkedDictionaryEntry list;

    @Before
    public void setUp() {
        list = new LinkedDictionaryEntry();
    }

    @Test
    public void testAddIntegerString() {
        list.add(5, "five");
        assertEquals("five", list.value);

        list.add(3, "three");
        assertEquals("three", list.next.value);

        list.add(27, "twenty seven");
        assertEquals("twenty seven", list.next.next.value);

        assertEquals("five", list.value);
        assertEquals("three", list.next.value);

    }

    @Test
    public void testAddStringInteger() {
        list.add("five", 5);
        assertEquals(5, list.value);

        list.add("one", 1);
        assertEquals(1, list.next.value);

        list.add("two", 2);
        assertEquals(2, list.next.next.value);

        assertEquals(5, list.value);
        assertEquals(1, list.next.value);
    }

    @Test
    public void testGetIntegerString() {
        assertNull(list.get(0));

        list.add(0, "zero");
        assertEquals("zero", list.get(0));

        list.add(10, "ten");
        list.add(5, "five");
        list.add(-3, "minus three");

        assertEquals("five", list.get(5));
        assertEquals("ten", list.get(10));
        assertEquals("zero", list.get(0));
        assertEquals("minus three", list.get(-3));
    }

    @Test
    public void testGetStringInteger() {
        assertNull(list.get("one"));

        list.add("one", 1);
        list.add("eleven", 11);
        list.add("twelve", 12);
        
        assertEquals(11, list.get("eleven"));
        assertEquals(1, list.get("one"));
        assertEquals(12, list.get("twelve"));
    }

    @Test
    public void testContains() {
        assertFalse(list.contains("one"));
        
        list.add("three", 3);
        list.add(1, "one");
        
        assertTrue(list.contains("three"));
        assertTrue(list.contains(1));
    }
}
