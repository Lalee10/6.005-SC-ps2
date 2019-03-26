/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import graph.ConcreteEdgesGraph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
    
    /*	Abstraction function:
     * 		A function that takes an input and converts it into
     * 		poetry using a corpus of text provided
     */
    
    /*	Representation invariant:
     * 		Graph that has immutable types as Vertices and is non-null
     */   	
    
    /*	Safety from rep exposure:
     * 		- Graph is private and final
     * 		- No instance of Graph is exposed to the client for mutation
     * 		- Vertices of Graph are immutable
     */
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
    	List<String> listOfWords = getWordsFromTextFile(corpus);
		for (int i = 0; i < listOfWords.size() - 1; i++) {
			String source = listOfWords.get(i);
			String target = listOfWords.get(i + 1);
			int prev = graph.set(source, target, 1);
            graph.set(source, target, prev + 1);
		}
    }
    
    /**
     * 	Convert file text to a list of words
     *	
     *	@author Lalee
     * 	@param	input File to read
     * 	@return list of words extracted from file
     * @throws IOException 
     */
    
	public List<String> getWordsFromTextFile(File corpus) throws IOException {
		checkRep();
		URI uri = corpus.toURI();
		List<String> words = new ArrayList<String>();
		List<String> lines = Files.readAllLines(Paths.get(uri));
		String textFromFile = String.join("", lines);
		Pattern p = Pattern.compile("[\\w']+");
		Matcher m = p.matcher(textFromFile);

		while (m.find()) {
			words.add(textFromFile.substring(m.start(), m.end()).toLowerCase());
		}
		return words;
	}
    
    // TODO checkRep
	private void checkRep() {
		assert graph != null;
	}
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    
    // TODO toString()
    public String poem(String input) {
    	System.out.println(input);
    	String[] inputWords = input.split("\\s");
        StringBuilder poem = new StringBuilder(input);
        int fromIndex = 0;
        
        for (int i = 0; i < inputWords.length; i++) {
            if (i + 1 >= inputWords.length) {
                break;
            }
            Map<String, Integer> word1Targets = 
                    graph.targets(inputWords[i].toLowerCase());
            Map<String, Integer> word2Sources =
                    graph.sources(inputWords[i+1].toLowerCase());
            Set<String> probableBridges = word1Targets.keySet();
            
            List<String> allBridges = probableBridges.stream()
                    .filter(possibleBridge -> word2Sources.containsKey(possibleBridge))
                    .collect(Collectors.toList());
            
            if (!allBridges.isEmpty()) {
                Random rand = new Random();
                int  n = rand.nextInt(allBridges.size());
                String bridge = allBridges.get(n);
                // get the index of word 2 from the poem
                int insertAt = poem.indexOf(inputWords[i+1], fromIndex);
                // insert the bridge word before that word
                poem.insert(insertAt, bridge + " ");
            }
        }
        checkRep();
        System.out.println(poem.toString());
        return poem.toString();
    }
    
}
