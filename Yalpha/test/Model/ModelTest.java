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
public class ModelTest {

    public ModelTest() {
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
     * Test of test method, of class Model.
     */
    @Test
    public void testTest() {
        System.out.println("test");
        Model instance = new Model();
        boolean expResult = false;
        boolean result = instance.test();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of choosePuzzle method, of class Model.
     */
    @Test
    public void testChoosePuzzle() {
        System.out.println("choosePuzzle");
        int puzType = 0;
        Model instance = new Model();
        instance.choosePuzzle(puzType);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class Model.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        String temp = "";
        Model instance = new Model();
        boolean expResult = false;
        boolean result = instance.add(temp);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class Model.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        String temp = "";
        Model instance = new Model();
        instance.remove(temp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeAll method, of class Model.
     */
    @Test
    public void testRemoveAll() {
        System.out.println("removeAll");
        Model instance = new Model();
        instance.removeAll();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of savePuzzle method, of class Model.
     */
    @Test
    public void testSavePuzzle() {
        System.out.println("savePuzzle");
        String temp = "";
        Model instance = new Model();
        instance.savePuzzle(temp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadPuzzle method, of class Model.
     */
    @Test
    public void testLoadPuzzle() {
        System.out.println("loadPuzzle");
        String temp = "";
        Model instance = new Model();
        instance.loadPuzzle(temp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadWordList method, of class Model.
     */
    @Test
    public void testLoadWordList() {
        System.out.println("loadWordList");
        String temp = "";
        Model instance = new Model();
        instance.loadWordList(temp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveWordList method, of class Model.
     */
    @Test
    public void testSaveWordList() {
        System.out.println("saveWordList");
        String temp = "";
        Model instance = new Model();
        instance.saveWordList(temp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generate method, of class Model.
     */
    @Test
    public void testGenerate() {
        System.out.println("generate");
        Model instance = new Model();
        instance.generate();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMatrix method, of class Model.
     */
    @Test
    public void testGetMatrix() {
        System.out.println("getMatrix");
        Model instance = new Model();
        char[][] expResult = null;
        char[][] result = instance.getMatrix();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getwordList method, of class Model.
     */
    @Test
    public void testGetwordList() {
        System.out.println("getwordList");
        Model instance = new Model();
        String[] expResult = null;
        String[] result = instance.getwordList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}