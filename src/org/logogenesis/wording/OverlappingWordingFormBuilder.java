package org.logogenesis.wording;

import java.util.LinkedList;
import java.util.List;

import org.logogenesis.wording.io.WordingChart;

/**
 * A builder of overlapping wording forms
 * 
 * @author Adjan Hansen
 */
public class OverlappingWordingFormBuilder implements WordingFormBuilder {

	@Override
	public final List<String> buildWordingForms(WordingChart wordingChart) {
		return buildWordingForms(wordingChart, 0);
	}

	/**
	 * Builds wording forms for a chart and a start position
	 * 
	 * @param wordingChart the chart
	 * @param start the start of the wording forms
	 * @return the wording forms
	 */
	private final List<String> buildWordingForms(WordingChart wordingChart, int start) {
		List<String> wordingForms = new LinkedList<String>();
		collectWordingForms(wordingChart, start, wordingForms);
		return wordingForms;
	}

	/**
	 * Collects wording forms for a chart and a start position
	 * 
	 * @param wordingChart the chart
	 * @param start the start of the wordingforms
	 * @param wordingForms the wording forms
	 */
	private final void collectWordingForms(WordingChart wordingChart, int start,
			List<String> wordingForms) {
		List<Word> words = wordingChart.getWordsStartingAt(start);
		for (Word word : words) {
			if (word.getEnd() == wordingChart.length()) {
				wordingForms.add(word.getForm());
				continue;
			}
			int end = word.getEnd();
			collectWordingForms(wordingChart, wordingForms, word, end);
			if (word.getLength() == 1) {
				continue;
			}
			collectWordingForms(wordingChart, wordingForms, word, end - 1);
		}
	}

	/**
	 * Collects a list of wording forms for a chart and a word.
	 * 
	 * @param wordingChart the chart
	 * @param wordingForms the wording forms
	 * @param word the word
	 * @param start the start of the remainder
	 */
	private final void collectWordingForms(WordingChart wordingChart, List<String> wordingForms,
			Word word, int start) {
		List<String> remainders = buildWordingForms(wordingChart, start);
		for (String remainder : remainders) {
			String wordForm = word.getForm().replace(' ', '-');
			String wordingForm = wordForm + " " + remainder;
			wordingForms.add(wordingForm);
		}
	}

}
