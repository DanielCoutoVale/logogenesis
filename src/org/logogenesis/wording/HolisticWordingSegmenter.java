package org.logogenesis.wording;

import java.io.IOException;

import org.logogenesis.wording.io.WordingChart;
import org.logogenesis.wording.io.WordingChartFactory;

/**
 * A wording segmenter that implements a holistic approach to segmentation.
 * 
 * @author Daniel Couto Vale <danielvale@uni-bremen.de>
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
	public WordingChart segmentWording(String wording) {
		try {
			WordingChartFactory wordingChartFactory = new WordingChartFactory();
			WordingChart chart = wordingChartFactory.newWordingChart();
			chart.setWording(wording);
			for (int index = 0; index < wording.length(); index++) {
				for (Word word : wordRecognizer.recognizeWords(wording, index)) {
					chart.addWord(word);
				}
			}
			return chart;
		} catch (IOException e) {
			return null;
		}
	}

}
