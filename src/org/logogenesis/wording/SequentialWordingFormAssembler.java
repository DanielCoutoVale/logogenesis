package org.logogenesis.wording;

import java.io.IOException;
import java.util.List;

import org.logogenesis.wording.io.WordingChart;

/**
 * An assembler of sequential wording forms from character sequences.
 * 
 * @author Daniel Couto-Vale <daniel.couto-vale@ifaar.rwth-aachen.de>
 */
public class SequentialWordingFormAssembler implements WordingFormAssembler {

	/**
	 * A word form recognizer
	 */
	private final WordFormRecognizer wordFormRecognizer;

	/**
	 * A wording form builder
	 */
	private final WordingFormBuilder wordingFormBuilder;

	/**
	 * Constructor
	 * 
	 * @param wordFormRecognizer a wording form recognizer
	 */
	public SequentialWordingFormAssembler(WordFormRecognizer wordFormRecognizer) {
		this.wordFormRecognizer = wordFormRecognizer;
		this.wordingFormBuilder = new SequentialWordingFormBuilder();
	}

	@Override
	public List<String> buildWordingForms(String characterSequence) throws IOException {
		WordingChart wordingChart = wordFormRecognizer.recognizeWords(characterSequence);
		return wordingFormBuilder.buildWordingForms(wordingChart);
	}

}
