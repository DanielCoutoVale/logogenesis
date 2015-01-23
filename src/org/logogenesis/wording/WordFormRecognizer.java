package org.logogenesis.wording;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.logogenesis.wording.io.WordingChart;
import org.logogenesis.wording.io.WordingChartFactory;
import org.logogenesis.wording.io.hal.HalWordingChartFactory;

/**
 * A word recognizer that implements a holistic approach to word recognition. It uses an automaton
 * for recognising words.
 * 
 * @author Daniel Couto-Vale
 */
public class WordFormRecognizer {

	/**
	 * The id of the next recognized word
	 */
	private static int nextId = 1;

	/**
	 * Map from character to word recognizers
	 */
	private final Map<Character, WordFormRecognizer> recognizerMap;

	/**
	 * The wording selected wording chart factory 
	 */
	private final WordingChartFactory wordingChartFactory;

	/**
	 * The types map
	 */
	private Map<Character, List<Integer>> typesMap;

	/**
	 * The length of the forms stored in this map
	 */
	private int length;


	/**
	 * Constructor
	 */
	public WordFormRecognizer() {
		this(new HalWordingChartFactory(), 1);
	}

	/**
	 * Constructor
	 * 
	 * @param wordingChartFactory the wording chart factory
	 */
	public WordFormRecognizer(WordingChartFactory wordingChartFactory) {
		this(wordingChartFactory, 1);
	}

	/**
	 * Constructor
	 * 
	 * @param wordingChartFactory the wording chart factory
	 * @param length the length of the words stored at this level
	 */
	private WordFormRecognizer(WordingChartFactory wordingChartFactory, int length) {
		recognizerMap = new HashMap<Character, WordFormRecognizer>();
		this.wordingChartFactory = wordingChartFactory;
		this.length = length;
	}

	/**
	 * Adds a word type to the word recognizer
	 *  
	 * @param pattern the pattern to add
	 * @param sort the sort of the word (combinatory type)
	 * @param term the term of the word (semantical type)
	 * @param form the form of the word (inflectional type)
	 */
	public final void addWordType(String pattern, int type) {
		addWordType(pattern, type, 0);
	}

	/**
	 * Adds a word type to the word recognizer
	 *  
	 * @param pattern the pattern to add
	 * @param sort the sort of the word (combinatory type)
	 * @param term the term of the word (semantical type)
	 * @param form the form of the word (inflectional type)
	 * @param index the index of the pattern
	 */
	public final void addWordType(String pattern, int type, int index) {
		if (index + 1 < pattern.length()) {
			char ch = pattern.charAt(index);
			WordFormRecognizer recognizer = recognizerMap.get(ch);
			if (recognizer == null) {
				recognizer = new WordFormRecognizer(wordingChartFactory, length + 1);
				recognizerMap.put(ch, recognizer);
			}
			recognizer.addWordType(pattern, type, index + 1);
		} else {
			char ch = pattern.charAt(index);
			if (typesMap == null) {
				typesMap = new HashMap<Character, List<Integer>>();
			}
			List<Integer> types = typesMap.get(ch);
			if (types == null) {
				types = new LinkedList<Integer>();
				typesMap.put(ch, types);
			}
			types.add(type);
		}
	}

	/**
	 * Recognizes words in a wording
	 * 
	 * @param wording the wording
	 * @return a chart with recognized words
	 * @throws IOException when a chart document cannot be created
	 */
	public final WordingChart recognizeWords(String wording) throws IOException {
		WordingChart chart = wordingChartFactory.newWordingChart();
		chart.setWording(wording);
		for (int index = 0; index < wording.length(); index++) {
			for (Word word : recognizeWords(wording, index)) {
				chart.addWord(word);
			}
		}
		return chart;
	}

	/**
	 * Recognizes words.
	 * 
	 * @param wording the wording where to recognize words
	 * @param index the index of where to recognize words
	 * @return the types of the recognized words
	 */
	private final List<Word> recognizeWords(String wording, int index) {
		List<Word> words = new LinkedList<Word>();
		recognizeWords(wording, index, words);
		return words;
	}

	/**
	 * Recognizes words.
	 * 
	 * @param wording the wording where to recognize words
	 * @param index the index of where to recognize words
	 * @param typeList the types of the recognized words
	 */
	private final void recognizeWords(String wording, int index, List<Word> words) {
		char ch = wording.charAt(index);
		if (typesMap != null) {
			List<Integer> foundTypes = typesMap.get(ch);
			if (foundTypes != null) {
				int types[] = new int[foundTypes.size()];
				for (int i = 0; i < foundTypes.size(); i++) {
					types[i] = foundTypes.get(i);
				}
				Word word = new Word(wording, nextId++, index + 1 - length, length, types);
				words.add(word);
			}
		}
		if (index + 1 < wording.length()) {
			WordFormRecognizer recognizer = recognizerMap.get(ch);
			if (recognizer != null) {
				recognizer.recognizeWords(wording, index + 1, words);
			}
		}
	}

	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		appendString(buffer, "");
		return buffer.toString();
	}

	/**
	 * Appends a string representing this subtree.
	 * 
	 * @param buffer the string buffer
	 * @param indent the indentation
	 */
	public final void appendString(StringBuffer buffer, String indent) {
		if (typesMap != null) {
			for (Character ch : typesMap.keySet()) {
				buffer.append(indent + "'" + ch + "' " + length + ":");
				List<Integer> types = typesMap.get(ch);
				buffer.append(types.size());
				buffer.append("\n");
			}
		}
		for (Character ch : recognizerMap.keySet()) {
			buffer.append(indent + "'" + ch + "' " + length + "\n");
			WordFormRecognizer recognizer = recognizerMap.get(ch);
			recognizer.appendString(buffer, indent + " ");
		}
	}

}
