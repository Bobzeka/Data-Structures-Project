import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

/**
 * Test class for StringRadixTree;
 *
 * @author Austin Leal
 * @version 1.0
 */
public class StringRadixTreeTest {
    private RadixTree<StringRadixTreeElement> myTree;
    private String charSet;




    /**
     * Sets up class for tests
     */
    @Before
    public void start() {
        myTree = new RadixTree<>();
        createCharSet();
    }

    /**
     * Tests adding one element to tree
     */
    @Test
    public void testAddOne() {
        myTree.clear();
        assertTrue("testAddOne: could not add root",
                myTree.add(new StringRadixTreeElement("first")));
        assertFalse("testAddOne: empty after add()", myTree.isEmpty());
        assertEquals("testAddOne: size not one after one add()", 1,
                myTree.size());
    }

    /**
     * Tests clearing tree
     */
    @Test
    public void testClear() {
        myTree.clear();
        assertTrue("testClear: not empty after clear()", myTree.isEmpty());
        assertEquals("testClear: size not zero after clear()",
                myTree.size(), 0);
    }

    /**
     * Tests adding multiple elements with same prefix to tree
     */
    @Test
    public void testAddMultipleSamePrefix() {
        for (int i = 0; i < 75; i++) {
            assertTrue("testAddMultipleSamePrefix: not added: one" + i,
                    myTree.add(new StringRadixTreeElement("one" + i)));
            assertEquals("testAddMultipleSamePrefix: size not correct", i + 1,
                  myTree.size());
        }
    }

    /**
     * Tests multiple differing elements to tree
     */
    @Test
    public void testAddMultipleDifferent() {
        myTree.clear();
        String[] addedArray = new String[75];
        Random randy = new Random();
        for (int i = 0; i < 75; i++) {
            String newString = "";
            for (int j = 0; j < randy.nextInt(15); j++) {
                newString += charSet.charAt(randy.nextInt(charSet.length()));
            }
            addedArray[i] = newString;
            assertTrue("testAddMultipleDifferent: not added: " + newString,
                    myTree.add(new StringRadixTreeElement(newString)));
            assertEquals("testAddMultipleDifferent: size not correct", i + 1,
                    myTree.size());
        }
    }

    /**
     * Tests removing first element from tree
     */
    @Test
    public void testRemoveFirst() {
        StringRadixTreeElement first = new StringRadixTreeElement("first");
        myTree.add(first);
        myTree.add(new StringRadixTreeElement("second"));
        myTree.add(new StringRadixTreeElement("fist"));
        assertTrue("testRemoveFirst: not found: " + "first",
                myTree.contains(new StringRadixTreeElement("first")));
        assertTrue("testRemoveFirst: not removed",
                myTree.remove(new StringRadixTreeElement("first")));
        assertNotEquals("testRemoveFirst: size not one fewer. Size "
                + myTree.size(), 74, myTree.size());
    }

    /**
     * Tests that different values are handled appropriately
     */
    @Test
    public void testAddRemoveDifferent() {
        myTree.clear();
        assertTrue("testAddRemoveDifferent: not added: abc",
                myTree.add(new StringRadixTreeElement("abc")));
        assertEquals("testAddRemoveDifferent: size not correct", 1,
                myTree.size());
        assertTrue("testAddRemoveDifferent: not added: bcd",
                myTree.add(new StringRadixTreeElement("bcd")));
        assertEquals("testAddRemoveDifferent: size not correct", 2,
                myTree.size());
        assertTrue("testAddRemoveDifferent: not added: cde",
                myTree.add(new StringRadixTreeElement("cde")));
        assertEquals("testAddRemoveDifferent: size not correct", 3,
                myTree.size());
        assertTrue("testAddRemoveDifferent: not added: def",
                myTree.add(new StringRadixTreeElement("def")));
        assertEquals("testAddRemoveDifferent: size not correct", 4,
                myTree.size());
        assertTrue("testAddRemoveDifferent: not added: efg",
                myTree.add(new StringRadixTreeElement("efg")));
        assertEquals("testAddRemoveDifferent: size not correct", 5,
                myTree.size());
        assertTrue("testAddRemoveDifferent: not removed: cde",
                myTree.remove(new StringRadixTreeElement("cde")));
        assertEquals("testAddRemoveDifferent: size not correct", 4,
                myTree.size());
        assertTrue("testAddRemoveDifferent: not removed: def",
                myTree.remove(new StringRadixTreeElement("def")));
        assertEquals("testAddRemoveDifferent: size not correct", 3,
                myTree.size());
        assertTrue("testAddRemoveDifferent: not removed: abc",
                myTree.remove(new StringRadixTreeElement("abc")));
        assertEquals("testAddRemoveDifferent: size not correct", 2,
                myTree.size());
        assertTrue("testAddRemoveDifferent: not removed: bcd",
                myTree.remove(new StringRadixTreeElement("bcd")));
        assertEquals("testAddRemoveDifferent: size not correct", 1,
                myTree.size());
        assertTrue("testAddRemoveDifferent: not removed: efg",
                myTree.remove(new StringRadixTreeElement("efg")));
        assertEquals("testAddRemoveDifferent: size not correct", 0,
                myTree.size());
    }


    /**
     * tests that elements that are children of other elements are
     * handled properly
     */
    @Test
    public void testAddRemoveDifferentOne() {
        myTree.clear();
        assertTrue("testAddRemoveDifferentOne: not added: abc",
                myTree.add(new StringRadixTreeElement("abc")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 1,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: abc",
                myTree.contains(new StringRadixTreeElement("abc")));
        assertTrue("testAddRemoveDifferentOne: not added: bcd",
                myTree.add(new StringRadixTreeElement("bcd")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 2,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: bcd",
                myTree.contains(new StringRadixTreeElement("bcd")));
        assertTrue("testAddRemoveDifferentOne: not added: cde1",
                myTree.add(new StringRadixTreeElement("cde")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 3,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: cde1",
                myTree.contains(new StringRadixTreeElement("cde")));
        assertTrue("testAddRemoveDifferentOne: not added: def",
                myTree.add(new StringRadixTreeElement("def")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 4,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: def",
                myTree.contains(new StringRadixTreeElement("def")));
        assertTrue("testAddRemoveDifferentOne: not added: defg1",
                myTree.add(new StringRadixTreeElement("defg")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 5,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: defg1",
                myTree.contains(new StringRadixTreeElement("defg")));
        assertTrue("testAddRemoveDifferentOne: not added: cde2",
                myTree.add(new StringRadixTreeElement("cde")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 6,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: cde2",
                myTree.contains(new StringRadixTreeElement("cde")));
        assertTrue("testAddRemoveDifferentOne: not added: cde3",
                myTree.add(new StringRadixTreeElement("cde")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 7,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: cde3",
                myTree.contains(new StringRadixTreeElement("cde")));
        assertTrue("testAddRemoveDifferentOne: not added: defg2",
                myTree.add(new StringRadixTreeElement("defg")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 8,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: defg2",
                myTree.contains(new StringRadixTreeElement("defg")));
        assertTrue("testAddRemoveDifferentOne: not added: def2",
                myTree.add(new StringRadixTreeElement("def")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 9,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: def2",
                myTree.contains(new StringRadixTreeElement("def")));
        assertTrue("testAddRemoveDifferentOne: not added: defh",
                myTree.add(new StringRadixTreeElement("defh")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 10,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: defh",
                myTree.contains(new StringRadixTreeElement("defh")));

        assertTrue("testAddRemoveDifferentOne: not removed: cde3",
                myTree.remove(new StringRadixTreeElement("cde")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 9,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: cde3",
                myTree.contains(new StringRadixTreeElement("cde")));
        assertTrue("testAddRemoveDifferentOne: not added: cde3",
                myTree.add(new StringRadixTreeElement("cde")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 10,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: cde3",
                myTree.contains(new StringRadixTreeElement("cde")));

        assertTrue("testAddRemoveDifferentOne: not removed: defh",
                myTree.remove(new StringRadixTreeElement("defh")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 9,
                myTree.size());
        assertFalse("testAddRemoveDifferentOne: still contains: defh",
                myTree.contains(new StringRadixTreeElement("defh")));
        assertTrue("testAddRemoveDifferentOne: not removed: def2",
                myTree.remove(new StringRadixTreeElement("def")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 8,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: def2",
                myTree.contains(new StringRadixTreeElement("def")));
        assertTrue("testAddRemoveDifferentOne: not removed: abc",
                myTree.remove(new StringRadixTreeElement("abc")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 7,
                myTree.size());
        assertFalse("testAddRemoveDifferentOne: still contains: abc",
                myTree.contains(new StringRadixTreeElement("abc")));
        assertTrue("testAddRemoveDifferentOne: not removed: cde3",
                myTree.remove(new StringRadixTreeElement("cde")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 6,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: cde3",
                myTree.contains(new StringRadixTreeElement("cde")));
        assertTrue("testAddRemoveDifferentOne: not removed: defg2",
                myTree.remove(new StringRadixTreeElement("defg")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 5,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: no contains: defg2",
                myTree.contains(new StringRadixTreeElement("defg")));
        assertTrue("testAddRemoveDifferentOne: not removed: def",
                myTree.remove(new StringRadixTreeElement("def")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 4,
                myTree.size());
        assertFalse("testAddRemoveDifferentOne: still contains: def",
                myTree.contains(new StringRadixTreeElement("def")));
        assertTrue("testAddRemoveDifferentOne: not removed: cde2",
                myTree.remove(new StringRadixTreeElement("cde")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 3,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: cde2",
                myTree.contains(new StringRadixTreeElement("cde")));
        assertTrue("testAddRemoveDifferentOne: not removed: bcd",
                myTree.remove(new StringRadixTreeElement("bcd")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 2,
                myTree.size());
        assertFalse("testAddRemoveDifferentOne: still contains: bcd",
                myTree.contains(new StringRadixTreeElement("bcd")));
        assertTrue("testAddRemoveDifferentOne: not removed: cde1",
                myTree.remove(new StringRadixTreeElement("cde")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 1,
                myTree.size());
        assertFalse("testAddRemoveDifferentOne: still contains: cde1",
                myTree.contains(new StringRadixTreeElement("cde")));
        assertTrue("testAddRemoveDifferentOne: not removed: defg1",
                myTree.remove(new StringRadixTreeElement("defg")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 0,
                myTree.size());
        assertFalse("testAddRemoveDifferentOne: still contains: defg1",
                myTree.contains(new StringRadixTreeElement("defg")));
        assertTrue("testAddRemoveDifferentOne: not isEmpty()",
                myTree.isEmpty());
        assertEquals("testAddRemoveDifferentOne: size not zero", 0,
                myTree.size());
    }

    /**
     * Tests that duplicates are handled correctly
     */
    @Test
    public void testAddRemoveDuplicates() {
        myTree.clear();
        assertTrue("testAddRemoveDifferentOne: not added: abc",
                myTree.add(new StringRadixTreeElement("abc")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 1,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: abc",
                myTree.contains(new StringRadixTreeElement("abc")));

        assertTrue("testAddRemoveDifferentOne: not added: bcd",
                myTree.add(new StringRadixTreeElement("bcd")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 2,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: bcd",
                myTree.contains(new StringRadixTreeElement("bcd")));

        assertTrue("testAddRemoveDifferentOne: not added: cde",
                myTree.add(new StringRadixTreeElement("cde")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 3,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: cde",
                myTree.contains(new StringRadixTreeElement("cde")));

        assertTrue("testAddRemoveDifferentOne: not added: def",
                myTree.add(new StringRadixTreeElement("def")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 4,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: def",
                myTree.contains(new StringRadixTreeElement("def")));

        assertTrue("testAddRemoveDifferentOne: not added: defg",
                myTree.add(new StringRadixTreeElement("defg")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 5,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: defg",
                myTree.contains(new StringRadixTreeElement("defg")));




        assertTrue("testAddRemoveDifferentOne: not removed: defg",
                myTree.remove(new StringRadixTreeElement("defg")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 4,
                myTree.size());
        assertFalse("testAddRemoveDifferentOne: still contains: defg",
                myTree.contains(new StringRadixTreeElement("defg")));

        assertTrue("testAddRemoveDifferentOne: not added: defg",
                myTree.add(new StringRadixTreeElement("defg")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 5,
                myTree.size());
        assertTrue("testAddRemoveDifferentOne: not contains: defg",
                myTree.contains(new StringRadixTreeElement("defg")));



        assertTrue("testAddRemoveDifferentOne: not removed: abc",
                myTree.remove(new StringRadixTreeElement("abc")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 4,
                myTree.size());
        assertFalse("testAddRemoveDifferentOne: still contains: abc",
                myTree.contains(new StringRadixTreeElement("abc")));

        assertTrue("testAddRemoveDifferentOne: not removed: cde",
                myTree.remove(new StringRadixTreeElement("cde")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 3,
                myTree.size());
        assertFalse("testAddRemoveDifferentOne: still contains: cde",
                myTree.contains(new StringRadixTreeElement("cde")));

        assertTrue("testAddRemoveDifferentOne: not removed: def",
                myTree.remove(new StringRadixTreeElement("def")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 2,
                myTree.size());
        assertFalse("testAddRemoveDifferentOne: still contains: def",
                myTree.contains(new StringRadixTreeElement("def")));

        assertTrue("testAddRemoveDifferentOne: not removed: bcd",
                myTree.remove(new StringRadixTreeElement("bcd")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 1,
                myTree.size());
        assertFalse("testAddRemoveDifferentOne: still contains: bcd",
                myTree.contains(new StringRadixTreeElement("bcd")));

        assertTrue("testAddRemoveDifferentOne: not removed: defg",
                myTree.remove(new StringRadixTreeElement("defg")));
        assertEquals("testAddRemoveDifferentOne: size not correct", 0,
                myTree.size());
        assertFalse("testAddRemoveDifferentOne: still contains: defg",
                myTree.contains(new StringRadixTreeElement("defg")));

        assertTrue("testAddRemoveDifferentOne: not isEmpty()",
                myTree.isEmpty());
        assertEquals("testAddRemoveDifferentOne: size not zero", 0,
                myTree.size());
    }

    /**
     * Tests removing all elements from tree systematically
     */
    @Test
    public void testRemoveAllSystematic() {
        String[] addedArray = addMultipleDifferent(75);
        String[] checkArray = new String[75];
        for (int i = 0; i < 75; i++) {
            StringRadixTreeElement current = new StringRadixTreeElement(
                    addedArray[i]);
            if (current != null) {
                myTree.remove(current);
                assertEquals("testRemoveAllRandom: size not decreased", 74 - i,
                        myTree.size());
                checkArray[i] = addedArray[i].substring(0, 1);
            }
        }
        for (Object currentObject : addedArray) {
            assertFalse(myTree.contains(new StringRadixTreeElement(
                    (String) currentObject)));
        }
    }

    /**
     * Tests removing all elements from tree randomly
     */
    @Test
    public void testRemoveAllRandom() {
        String[] addedArray = addMultipleDifferent(75);
        Random randy = new Random();
        int i = 75;
        while (!myTree.isEmpty()) {
            int index = randy.nextInt(75);
            StringRadixTreeElement current = new StringRadixTreeElement(
                    addedArray[index]);
            if (current != null && current.getData() != null) {
                myTree.remove(current);
                assertEquals("testRemoveAllRandom: size not decreased", --i,
                        myTree.size());
                addedArray[index] = null;
            }
        }
        for (Object currentObject : addedArray) {
            assertFalse(myTree.contains(new StringRadixTreeElement(
                    (String) currentObject)));
        }
    }

    /**
     * tests iterator
     */
    @Test
    public void testIterator() {
        Random randy = new Random();
        for (int i = 0; i < 75; i++) {
            String newString = "";
            for (int j = 0; j < randy.nextInt(15); j++) {
                newString += charSet.charAt(randy.nextInt(charSet.length()));
            }
            myTree.add(new StringRadixTreeElement(newString));
        }
        int i = 0;
        for (StringRadixTreeElement element : myTree) {
            assertNotNull("testIterator: Iterator returned null", element);
            i++;
        }
        assertEquals(
                "testIterator: Did not iterate through whole list first time",
                i, 75);
        i = 0;
        for (StringRadixTreeElement element : myTree) {
            assertNotNull("testIterator: Iterator returned null", element);
            i++;
        }
        assertEquals(
                "testIterator: Did not iterate through whole list second time",
                i, 75);
    }

    /**
     * Adds multiple differing elements to tree
     */
    private String[] addMultipleDifferent(int count) {
        myTree.clear();
        String[] addedArray = new String[count];
        Random randy = new Random();
        for (int i = 0; i < count; i++) {
            String newString = "";
            for (int j = 0; j < (randy.nextInt(14) + 1); j++) {
                newString += charSet.charAt(randy.nextInt(charSet.length()));
            }
            addedArray[i] = newString;
            myTree.add(new StringRadixTreeElement(newString));
        }
        return addedArray;
    }

    /**
     * Creates charSet to be used to pull characters from
     */
    private void createCharSet() {
        charSet = "";
        for (int i = 'a'; i <= 'z'; i++) {
            charSet += (char) i;
        }
    }
}