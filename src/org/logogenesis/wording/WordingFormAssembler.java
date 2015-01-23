package org.logogenesis.wording;

import java.io.IOException;
import java.util.List;

/**
 * A builder of wording forms from a chart of word forms.
 * 
 * @author Daniel Couto-Vale <daniel.couto-vale@ifaar.rwth-aachen.de>
 * @author Adjan
 */
public interface WordingFormAssembler {

	/**
	 * Builds wording forms from a character sequence.
	 * 
	 * @param wordingChart the character sequence
	 * @return the list of wording forms for the character sequence
	 * @throws IOException
	 */
	public List<String> buildWordingForms(String characterSequence) throws IOException;

}
