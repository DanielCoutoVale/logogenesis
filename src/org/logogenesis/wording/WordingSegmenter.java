package org.logogenesis.wording;

import java.io.IOException;

import org.logogenesis.wording.io.WordingChart;

/**
 * A wording segmenter.
 * 
 * @author Daniel Couto Vale <danielvale@uni-bremen.de>
 */
public interface WordingSegmenter {

	/**
	 * Segments a wording.
	 * 
	 * @param wording the wording to segment
	 * @return the segmentations of the wording
	 * @throws IOException when a file or a document cannot be created
	 */
	public abstract WordingChart segmentWording(String wording);

}