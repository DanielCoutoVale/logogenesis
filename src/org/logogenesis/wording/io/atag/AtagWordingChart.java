package org.logogenesis.wording.io.atag;

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
import org.w3c.dom.Text;

/**
 * A wording segmentation in the format ATag.
 * 
 * @author Daniel Couto-Vale
 */
public class AtagWordingChart implements WordingChart, Storable {

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
	AtagWordingChart(Document document, Storable storable) {
		this.document = document;
		this.storable = storable;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		xpath = xpathFactory.newXPath();
		try {
			String expression = "/Text/W";
			NodeList wordElms = (NodeList) xpath.compile(expression).evaluate(document, XPathConstants.NODESET);
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < wordElms.getLength(); i++) {
				Element wordElm = (Element) wordElms.item(i);
				int start = Integer.parseInt(wordElm.getAttribute("cur"));
				while (buffer.length() < start) {
					buffer.append(" ");
				}
				Text formTxt = (Text)wordElm.getChildNodes();
				String form = formTxt.getTextContent();
				buffer.append(form);
			}
		} catch (XPathExpressionException e) {
		}
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
		int cur = 0;
		while (cur < wording.length()) {
			int size = getWordsStartingAt(cur).size();
			if (size != 1) {
				return false;
			} else {
				cur += getWordsStartingAt(cur).get(0).getLength();
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
		Element wordElm = document.createElement("W");
		wordElm.setAttribute("id", Integer.toString(id));
		wordElm.setAttribute("cur", Integer.toString(start));
		Text formTxt = document.createTextNode(this.wording.substring(start, start + length));
		wordElm.appendChild(formTxt);
		Element chartElm = document.getDocumentElement();
		chartElm.appendChild(wordElm);
	}

	private final Word makeWord(Element wordElm) {
		int id = Integer.parseInt(wordElm.getAttribute("id"));
		int start = Integer.parseInt(wordElm.getAttribute("cur"));
		Text formTxt = (Text) wordElm.getChildNodes().item(0);
		String form = formTxt.getTextContent();
		int length = form.length();
		int[] types = new int[0];
		Word word = new Word(wording, id, start, length, types);
		return word;
	}

	@Override
	public final Word getWord(int id) {
		try {
			String expression = "/Text/W[@id='" + Integer.toString(id) + "']";
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
			String expression = "/Text/W[@cur='" + Integer.toString(start) + "']";
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
			String expression = "/Text/W";
			NodeList wordElms = (NodeList) xpath.compile(expression).evaluate(this.document, XPathConstants.NODESET);
			List<Word> words = new LinkedList<Word>();
			for (int i = 0; i < wordElms.getLength(); i++) {
				Element wordElm = (Element) wordElms.item(i);
				Word word = makeWord(wordElm);
				if (end == word.getEnd()) {
					words.add(word);
				}
			}
			return words;
		} catch (XPathExpressionException e) {
			return new LinkedList<Word>();
		}
	}

	@Override
	public final void removeWord(int id) {
		try {
			String expression = "/Text/W[@id='" + Integer.toString(id) + "']";
			Element wordElm = (Element) xpath.compile(expression).evaluate(this.document, XPathConstants.NODE);
			wordElm.getParentNode().removeChild(wordElm);
		} catch (XPathExpressionException e) {
		}
	}

	@Override
	public final int length() {
		return wording.length();
	}

}
