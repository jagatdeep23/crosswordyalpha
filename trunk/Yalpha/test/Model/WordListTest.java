/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick
 */
public class WordListTest {

    public WordListTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class WordList.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        String word = "";
        WordList instance = new WordList();
        boolean expResult = false;
        boolean result = instance.add(word);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class WordList.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        String word = "";
        WordList instance = new WordList();
        boolean expResult = false;
        boolean result = instance.remove(word);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}