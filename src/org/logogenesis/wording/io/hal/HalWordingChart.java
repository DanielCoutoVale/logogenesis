package org.logogenesis.wording.io.hal;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.logogenesis.wording.Word;
import org.logogenesis.wording.io.Storable;
import org.logogenesis.wording.io.WordingChart;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A wording segmentation in the format Human Adult Language (HAL).
 * 
 * @author Daniel Couto-Vale
 */
public final class HalWordingChart implements WordingChart, Storable {

	/**
	 * The document of the wording segmentation
	 */
	private final Document document;

	/**
	 * The storable delegate
	 */
	private final Storable storable;

	/**
	 * The character sequence 
	 */
	private String chars;

	/**
	 * Xpath
	 */
	private XPath xpath;

	/**
	 * Constructor
	 * 
	 * @param document the document of the wording segmentation
	 */
	HalWordingChart(Document document, Storable storable) {
		this.document = document;
		this.storable = storable;
		Element chartElm = document.getDocumentElement();
		this.chars = chartElm.getAttribute("chars");
		XPathFactory xpathFactory = XPathFactory.newInstance();
		this.xpath = xpathFactory.newXPath();
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
		while (start < chars.length()) {
			int size = getWordsStartingAt(start).size();
			if (size != 1) {
				return false;
			} else {
				start += getWordsStartingAt(start).get(0).getLength();
			}
		}
		return true;
	}

	@Override
	public final void setCharacterSequence(String chars) {
		this.chars = chars;
		Element chartElm = document.getDocumentElement();
		chartElm.setAttribute("chars", chars);
	}

	@Override
	public final String getCharacterSequence() {
		return chars;
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
		wordElm.setAttribute("end", Integer.toString(start + length));
		wordElm.setAttribute("form", this.chars.substring(start, start + length));
		Element chartElm = document.getDocumentElement();
		chartElm.appendChild(wordElm);
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
		Word word = new Word(chars, id, start, length, types);
		return word;
	}

	@Override
	public final Word getWord(int id) {
		try {
			String expression = "/WordingChart/Word[@id='" + Integer.toString(id) + "']";
			Element wordElm = (Element) xpath.compile(expression).evaluate(this.document, XPathConstants.NODE);
			Word word = makeWord(wordElm);
			return word;
		} catch (XPathExpressionException e) {
			return null;
		}
	}

	@Override
	public final List<Word> getWordsStartingAt(int start) {
		try {
			String expression = "/WordingChart/Word[@start='" + Integer.toString(start) + "']";
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
	public final List<Word> getWordsEndingAt(int end) {
		try {
			String expression = "/WordingChart/Word[@end='" + Integer.toString(end) + "']";
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
		try {
			String expression = "/WordingChart/Word[@id='" + Integer.toString(id) + "']";
			Element wordElm = (Element) xpath.compile(expression).evaluate(this.document, XPathConstants.NODE);
			wordElm.getParentNode().removeChild(wordElm);
		} catch (XPathExpressionException e) {
		}
	}

	@Override
	public final int length() {
		return chars.length();
	}

}
