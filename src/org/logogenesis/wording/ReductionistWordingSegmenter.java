package org.logogenesis.wording;

import java.io.IOException;

import org.logogenesis.wording.io.WordingChart;
import org.logogenesis.wording.io.WordingChartFactory;
import org.logogenesis.wording.io.hal.HalWordingChartFactory;

import static java.lang.Character.*;

/**
 * A wording segmenter that implements a reductionist theory of wording parts.
 *  
 * @author Daniel Couto-Vale
 */
public class ReductionistWordingSegmenter implements WordingSegmenter {

	/**
	 * The wording chart factory
	 */
	private WordingChartFactory wordingChartFactory;

	/**
	 * Constructor
	 */
	public ReductionistWordingSegmenter() {
		this(new HalWordingChartFactory());
	}

	/**
	 * Constructor
	 * 
	 * @param wordingChartFactory the wording chart factory
	 */
	public ReductionistWordingSegmenter(WordingChartFactory wordingChartFactory) {
		this.wordingChartFactory = wordingChartFactory;
	}

	/* (non-Javadoc)
	 * @see org.hal.wording.WordingSegmenter#segmentWording(java.lang.String)
	 */
	@Override
	public final WordingChart segmentWording(String wording) {
		try {
			WordingChart chart = wordingChartFactory.newWordingChart();
			chart.setWording(wording);
			int nextId = 1;
			int start = 0;
			for (int index = 1; index < wording.length(); index++) {
				if (isLetter(wording.charAt(start)) && isLetter(wording.charAt(index))) {
					continue;
				}
				if (!isLetter(wording.charAt(start)) && !isLetter(wording.charAt(index))) {
					continue;
				}
				chart.addWord(nextId++, start, index - start, new int[0]);
				start = index;
			}
			chart.addWord(nextId, start, wording.length() - start, new int[0]);
			return chart;
		} catch(IOException e) {
			return null;
		}
	}

}
