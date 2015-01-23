package org.logogenesis.wording;

import java.util.List;

import org.logogenesis.wording.io.WordingChart;

/**
 * A builder of wording forms from a chart of word forms.
 * 
 * @author Daniel Couto-Vale <daniel.couto-vale@ifaar.rwth-aachen.de>
 * @author Adjan
 */
public interface WordingFormBuilder {

	/**
	 * Builds wording forms from a chart of word forms.
	 * 
	 * @param wordingChart the chart of word forms
	 * @return the list of wording forms for the chart
	 */
	public List<String> buildWordingForms(WordingChart wordingChart);

}
