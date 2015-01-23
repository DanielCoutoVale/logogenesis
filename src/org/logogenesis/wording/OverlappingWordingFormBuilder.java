package org.logogenesis.wording;

import java.util.LinkedList;
import java.util.List;

import org.logogenesis.wording.io.WordingChart;

/**
 * 
 * @author 
 */
public class OverlappingWordingFormBuilder implements WordingFormBuilder {

	@Override
	public List<String> buildWordingForms(WordingChart wordingChart) {
		// TODO Auto-generated method stub
		return buildWordingForms(wordingChart, 0);
	}

	private List<String> buildWordingForms(WordingChart wordingChart, int start) {
		List<String> wordingForms = new LinkedList<String>();

		List<Word> words = wordingChart.getWordsStartingAt(start);
		for (Word word : words) {
			if (word.getEnd() == wordingChart.length()) {
				wordingForms.add(word.getPattern());
				continue;
			}
			int end = word.getEnd();
			collectWordingForms(wordingChart, wordingForms,
					word, end);
			if(word.getLength()==1){
				continue;
			}
			collectWordingForms(wordingChart, wordingForms,
					word, end-1);
		}
		return wordingForms;
	}

	private List<String> collectWordingForms(WordingChart wordingChart,
			List<String> wordingForms, Word word, int end) {
		List<String> remainders = buildWordingForms(wordingChart,
				end);
		for (String remainder : remainders) {
			String wordingForm = word.getPattern().replace(' ', '-') + " "
					+ remainder;
			wordingForms.add(wordingForm);
		}
		return remainders;
	}

}
