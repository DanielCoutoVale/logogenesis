package org.logogenesis.wording.io;

import java.util.List;

import org.logogenesis.wording.Word;

/**
 * A wording chart in the form of a Directed Acyclic Graph. The number of nodes in the
 * directed acyclic graph is the number of characters in a wording plus 1. The nodes are enumerated.
 * A word is a directed edge from a node whose number is the start index of the word in the wording
 * to a node whose number is the start index plus the character length of the word. The words in
 * the string "I love you!" would be "I" (0, 1), " " (1, 2), "love" (2, 6), "you" (6, 9), and "!"
 * (9, 10). Words are identified by unique integers randomly.
 *
 * @author Daniel Couto Vale <danielvale@uni-bremen.de>
 */
public interface WordingChart {

	/**
	 * A wording chart is listable if there is only one path between the initial node and
	 * the final node of the wording chart.
	 *  
	 * @return <code>true</code> if there is only one path between the initial node and the final
	 *     node and <code>false</code> otherwise. 
	 */
	public boolean isListable();

	/**
	 * Sets the wording of the chart.
	 * 
	 * @param wording the wording of the chart.
	 */
	public void setWording(String wording);

	/**
	 * Gets the wording of the chart
	 * 
	 * @return the wording of the chart.
	 */
	public String getWording();

	/**
	 * Adds a word to the chart
	 * 
	 * @param word the word to be added to the chart
	 */
	public void addWord(Word word);

	/**
	 * Adds a word to the chart.
	 * 
	 * @param id a unique identifier for the word
	 * @param start the start node
	 * @param length the length in characters
	 * @param the word types of which the form can be an instance of
	 */
	public void addWord(int id, int start, int length, int[] type);

	/**
	 * The word identified by an identifying integer.
	 * 
	 * @param id the identifier of the word.
	 * @return the identified word
	 */
	public Word getWord(int id);

	/**
	 * The list of words starting at a given node.
	 * 
	 * @param start the start node
	 * @return the words starting at the given node
	 */
	public List<Word> getWords(int start);

	/**
	 * Removes the identified word from the chart.
	 * 
	 * @param id the identifier of the word 
	 */
	public void removeWord(int id);

	/**
	 * The length of the chart which coincides with the length of the wording
	 * 
	 * @return the length of the chart
	 */
	public int length();

}
