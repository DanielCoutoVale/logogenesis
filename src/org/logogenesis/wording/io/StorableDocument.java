package org.logogenesis.wording.io;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * Implements the storable interface for other classes.
 * 
 * @author Daniel Couto Vale <danielvale@uni-bremen.de>
 */
public class StorableDocument implements Storable {

	/**
	 * The document to store
	 */
	private Document document;

	/**
	 * The file that contains the document
	 */
	private File file;

	/**
	 * Constructor
	 * 
	 * @param document the document to store
	 */
	public StorableDocument(Document document, File file) {
		this.document = document;
		this.file = file;
	}

	@Override
	public final void renameTo(String fileName) {
		File base = file.getParentFile();
		File newFile = new File(base, fileName);
		file.renameTo(newFile);
	}

	@Override
	public final void moveTo(String basePath) {
		String fileName = file.getName();
		File newFile = new File(basePath, fileName);
		file.renameTo(newFile);
	}

	@Override
	public final void saveChanges() throws IOException {
		try {
			Transformer transformer = makeTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result =  new StreamResult(file);
			transformer.transform(source, result);
		} catch (TransformerException e) {
			throw new IOException();
		}
	}

	@Override
	public final String toString() {
		try {
			Transformer transformer = makeTransformer();
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(document);
			transformer.transform(source, result);
			String xmlString = result.getWriter().toString();
			return xmlString;
		} catch (TransformerException e) {
			return super.toString();
		}
	}

	/**
	 * Makes a transformer
	 * 
	 * @return a transformer
	 * @throws TransformerConfigurationException when the transformer is wrongly configured
	 * @throws TransformerFactoryConfigurationError when the transformer factory is wrongly configured
	 */
	private static Transformer makeTransformer() throws TransformerConfigurationException,
			TransformerFactoryConfigurationError {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		return transformer;
	}

}
