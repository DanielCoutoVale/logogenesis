package org.logogenesis.wording.io;

import java.io.File;
import java.io.IOException;

/**
 * A factory of wording charts. It contains a factory method for representing a wording chart as a
 * storable document.
 * 
 * @author Daniel Couto-Vale
 */
public abstract class WordingChartFactory {

	/**
	 * Creates a new temporary file with the given suffix
	 * 
	 * @param suffix the suffix of the file
	 * @return a new file
	 * @throws IOException when a file cannot be created
	 */
	protected final File newFile(String suffix) throws IOException {
		// FIXME Use the user library of each platform (OSX, Linux, Windows) instead of user home
		// OSX      => {user.home}/Library + /Logogenesis
		// Linux    => ??
		// Windows  => ??
		String userHome = System.getProperty("user.home");
		String prefix = "Untitled";
		File file = File.createTempFile(prefix, suffix, new File(userHome));
		file.deleteOnExit();
		return file;
	}

	/**
	 * Creates a new wording chart document to a file path
	 * 
	 * @return the new wording chart document
	 * @throws IOException when a file or a document cannot be created
	 */
	public abstract WordingChart newWordingChart() throws IOException;

	/**
	 * Opens a file as a wording chart document
	 * 
	 * @param filePath the path of the file containing the document
	 * @return the wording chart
	 */
	public abstract WordingChart openWordingChart(String filePath);

}
