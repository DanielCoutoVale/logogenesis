package org.logogenesis.wording;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A word recognizer that implements a holistic approach to word recognition. It uses an automaton
 * for recognising words.
 * 
 * @author Daniel Couto Vale <danielvale@uni-bremen.de>
 */
public class HolisticWordRecognizer {

	/**
	 * The id of the next recognized word
	 */
	private static int nextId = 1;

	/**
	 * Map from character to word recognizers
	 */
	private final Map<Character, HolisticWordRecognizer> recognizerMap;
	private Map<Character, List<Integer>> typesMap;
	private int length;

	/**
	 * Constructor
	 */
	public HolisticWordRecognizer() {
		recognizerMap = new HashMap<Character, HolisticWordRecognizer>();
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
		if (length == 0) {
			length = index + 1;
		}
		if (index + 1 < pattern.length()) {
			char ch = pattern.charAt(index);
			HolisticWordRecognizer recognizer = recognizerMap.get(ch);
			if (recognizer == null) {
				recognizer = new HolisticWordRecognizer();
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
	 * Recognizes words.
	 * 
	 * @param wording the wording where to recognize words
	 * @param index the index of where to recognize words
	 * @return the types of the recognized words
	 */
	public final List<Word> recognizeWords(String wording, int index) {
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
			HolisticWordRecognizer recognizer = recognizerMap.get(ch);
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
			HolisticWordRecognizer recognizer = recognizerMap.get(ch);
			recognizer.appendString(buffer, indent + " ");
		}
	}
}

