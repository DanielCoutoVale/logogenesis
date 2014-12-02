package org.logogenesis.wording.io;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A factory of wording charts. It represents a wording chart as a storable document.
 * 
 * @author Daniel Couto Vale <danielvale@uni-bremen.de>
 */
public class WordingChartFactory {

	/**
	 * Creates a new wording chart document to a file path
	 * 
	 * @return the new wording chart document
	 * @throws IOException when a file or a document cannot be created
	 */
	public final WordingChart newWordingChart() throws IOException {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			document.setXmlStandalone(true);
			Element chartElm = document.createElement("WordingChart");
			document.appendChild(chartElm);
			return new WordingChartHal(document, new StorableDocument(document, newFile()));
		} catch (ParserConfigurationException e) {
			throw new IOException();
		}
	}

	/**
	 * Opens a file as a wording chart document
	 * 
	 * @param filePath the path of the file containing the document
	 * @return the wording chart
	 */
	public final WordingChart openWordingChart(String filePath) {
		try {
			// FIXME This code should open a file and not create a new document out of scratch
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			document.setXmlStandalone(true);
			return new WordingChartHal(document, new StorableDocument(document, new File(filePath)));
		} catch (ParserConfigurationException e) {
			return null;
		}
	}

	/**
	 * Creates a new file
	 * 
	 * @return a new file
	 * @throws IOException when a file cannot be created
	 */
	private final File newFile() throws IOException {
		// FIXME Use the user library of each platform (OSX, Linux, Windows) instead of user home
		// OSX      => {user.home}/Library + /HAL
		// Linux    => ??
		// Windows  => ??
		String userHome = System.getProperty("user.home");
		File file = new File(userHome, "Untitled.hal");
		int index = 1;
		while (file.exists()) {
			file = new File(userHome, "Untitled " + (++index) + ".hal");
		}
		String prefix = file.getName().split("[.]")[0];
		String suffix = file.getName().split("[.]")[1];
		return File.createTempFile(prefix, suffix, new File(userHome));
	}

}
