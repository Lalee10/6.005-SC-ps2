/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;
import java.io.*; 
import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

	}
    
    // Testing strategy
	/*
	 * 
	 * 
	 * The partitions are as follows:
	 * 	1) Number of bridges: 0,1 > 1
	 * 	2) Number of associations (in graph): 1, > 1
	 */
	
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    @Test
    public void noBridge() {
    	File f = new File("./poet.txt");
        GraphPoet gp = new GraphPoet(f);
        String inputText = "No word here is present in the graph";
        String expectedOutput = "No word here is present in the graph";
        assertTrue(gp.poem(inputText).equals(expectedOutput));
    }
    
    @Test void SingleBridge() {
    	File f = new File("./poet.txt");
        GraphPoet gp = new GraphPoet(f);
        String inputText = "Seek to explore new plus exciting synergies!";
        String expectedOutput = "Seek to explore strange new life and exciting synergies!";
        assertTrue(gp.poem(inputText).equals(expectedOutput));
    }
    
    @Test void multipleAssociations() {
    	File f = new File("./poet1.txt");
        GraphPoet gp = new GraphPoet(f);
        String inputText = "A B D";
        String expectedOutput = "A B C D";
        assertTrue(gp.poem(inputText).equals(expectedOutput));
        
    }
    
    @Test
    public void twoBridges() {
        File f = new File("./poet.txt");
        GraphPoet gp = new GraphPoet(f);
        String inputText = "Seek to explore new and exciting synergies!";
        String expectedOutput = "Seek to explore strange new life and exciting synergies!";
        assertTrue(gp.poem(inputText).equals(expectedOutput));
    }
    
    
    
    
    // TODO tests
    
}
