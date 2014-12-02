package org.logogenesis.wording.io;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.logogenesis.wording.Word;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A wording segmentation in the format Human Adult Language (HAL).
 * 
 * @author Daniel Couto Vale <danielvale@uni-bremen.de>
 */
public final class WordingChartHal implements WordingChart, Storable {

	/**
	 * The document of the wording segmentation
	 */
	private final Document document;

	/**
	 * The storable delegate
	 */
	private final Storable storable;

	/**
	 * The wording 
	 */
	private String wording;

	/**
	 * Xpath
	 */
	private XPath xpath;

	/**
	 * Constructor
	 * 
	 * @param document the document of the wording segmentation
	 */
	WordingChartHal(Document document, Storable storable) {
		this.document = document;
		this.storable = storable;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		xpath = xpathFactory.newXPath();
	}

	@Override
	public final void renameTo(String fileName) {
		// FIXME throw exception if changes were not saved
		this.storable.renameTo(fileName);
	}

	@Override
	public final void moveTo(String basePath) {
		// FIXME throw exception if changes were not saved
		this.storable.moveTo(basePath);
	}

	@Override
	public final void saveChanges() throws IOException {
		this.storable.saveChanges();
	}

	@Override
	public final String toString() {
		return this.storable.toString();
	}

	@Override
	public final boolean isListable() {
		int start = 0;
		while (start < wording.length()) {
			int size = getWords(start).size();
			if (size != 1) {
				return false;
			} else {
				start += getWords(start).get(0).getLength();
			}
		}
		return true;
	}

	@Override
	public final void setWording(String wording) {
		this.wording = wording;
	}

	@Override
	public final String getWording() {
		return wording;
	}

	@Override
	public final void addWord(Word word) {
		addWord(word.getId(), word.getStart(), word.getLength(), word.getTypes());
	}

	@Override
	public final void addWord(int id, int start, int length, int[] types) {
		Element wordElm = document.createElement("Word");
		wordElm.setAttribute("id", Integer.toString(id));
		wordElm.setAttribute("start", Integer.toString(start));
		wordElm.setAttribute("length", Integer.toString(length));
		wordElm.setAttribute("form", this.wording.substring(start, start + length));
		Element chart = document.getDocumentElement();
		chart.appendChild(wordElm);
		for (int i = 0; i < types.length; i++) {
			Element wordTypeElm = document.createElement("WordType");
			wordTypeElm.setAttribute("index", Integer.toString(types[i]));
			wordElm.appendChild(wordTypeElm);
		}
	}

	private final Word makeWord(Element wordElm) {
		int id = Integer.parseInt(wordElm.getAttribute("id"));
		int start = Integer.parseInt(wordElm.getAttribute("start"));
		int length = Integer.parseInt(wordElm.getAttribute("length"));
		NodeList typeElms = wordElm.getElementsByTagName("WordType");
		int[] types = new int[typeElms.getLength()];
		for (int i = 0; i < types.length; i++) {
			Element wordTypeElm = (Element)typeElms.item(i);
			types[i] = Integer.parseInt(wordTypeElm.getAttribute("index"));
		}
		Word word = new Word(wording, id, start, length, types);
		return word;
	}

	@Override
	public final Word getWord(int elmId) {
		Element wordElm = document.getElementById(Integer.toString(elmId));
		Word word = makeWord(wordElm);
		return word;
	}

	@Override
	public final List<Word> getWords(int start) {
		try {
			String expression = "/WordChart/Word[@start='" + Integer.toString(start) + "']";
			NodeList wordElms = (NodeList) xpath.compile(expression).evaluate(this.document, XPathConstants.NODESET);
			List<Word> words = new LinkedList<Word>();
			for (int i = 0; i < wordElms.getLength(); i++) {
				Element wordElm = (Element) wordElms.item(i);
				Word word = makeWord(wordElm);
				words.add(word);
			}
			return words;
		} catch (XPathExpressionException e) {
			return new LinkedList<Word>();
		}
	}

	@Override
	public final void removeWord(int id) {
		Element wordElm = document.getElementById(Integer.toString(id));
		document.removeChild(wordElm);
	}

	@Override
	public final int length() {
		return wording.length();
	}

}
