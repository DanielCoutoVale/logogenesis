package org.logogenesis.wording;

import java.io.IOException;

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
			boolean[] direct = new boolean[wording.length() + 1];
			boolean[] reverse = new boolean[wording.length() + 1];
			direct[0] = true;
			reverse[wording.length()] = true; 
			for (int i = 0; i < wording.length(); i++) {
			}
			return chart;
		} catch (IOException e) {
			return null;
		}
	}

}
