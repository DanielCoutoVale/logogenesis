package org.logogenesis.wording;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.logogenesis.wording.io.WordingChart;
import org.logogenesis.wording.io.WordingChartFactory;

/**
 * A wording segmenter that implements a holistic approach to segmentation.
 * 
 * @author Daniel Couto-Vale
 */
public class HolisticWordingSegmenter implements WordingSegmenter {

	/**
	 * The word recognizer
	 */
	private HolisticWordRecognizer wordRecognizer;

	/**
	 * Constructor
	 * 
	 * @param wordRecognizer the word recognizer
	 */
	public HolisticWordingSegmenter(HolisticWordRecognizer wordRecognizer) {
		this.wordRecognizer = wordRecognizer;
	}

	/* (non-Javadoc)
	 * @see org.hal.wording.WordingSegmenter#segmentWording(java.lang.String)
	 */
	@Override
	public final WordingChart segmentWording(String wording) {
		try {
			WordingChart chart = wordRecognizer.recognizeWords(wording);

			// Remove unreachable words from wording start
			boolean[] direct = new boolean[wording.length() + 1];
			direct[0] = true; 
			for (int start = 0; start < wording.length(); start++) {
				if (direct[start]) {
					for (Word word : chart.getWordsStartingAt(start)) {
						direct[word.getEnd()] = true;
					}
				} else {
					for (Word word : chart.getWordsStartingAt(start)) {
						chart.removeWord(word.getId());
					}
				}
			}

			// Remove unreachable words from wording end
			boolean[] reverse = new boolean[wording.length() + 1];
			reverse[wording.length()] = true; 
			for (int end = wording.length(); end > 0; end--) {
				if (reverse[end]) {
					for (Word word : chart.getWordsEndingAt(end)) {
						reverse[word.getStart()] = true;
					}
				} else {
					for (Word word : chart.getWordsEndingAt(end)) {
						chart.removeWord(word.getId());
					}
				}
			}
			return chart;
		} catch (IOException e) {
			return null;
		}
	}

}
