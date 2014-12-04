package org.logogenesis.wording.io.atag;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.logogenesis.wording.io.StorableDocument;
import org.logogenesis.wording.io.WordingChart;
import org.logogenesis.wording.io.WordingChartFactory;
import org.logogenesis.wording.io.hal.HalWordingChart;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AtagWordingChartFactory extends WordingChartFactory {

	@Override
	public final WordingChart newWordingChart() throws IOException {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			document.setXmlStandalone(true);
			Element chartElm = document.createElement("Text");
			document.appendChild(chartElm);
			return new AtagWordingChart(document, new StorableDocument(document, super.newFile(".atag")));
		} catch (ParserConfigurationException e) {
			throw new IOException();
		}
	}

	@Override
	public final WordingChart openWordingChart(String filePath) {
		try {
			// FIXME This code should open a file and not create a new document out of scratch
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			document.setXmlStandalone(true);
			return new AtagWordingChart(document, new StorableDocument(document, new File(filePath)));
		} catch (ParserConfigurationException e) {
			return null;
		}
	}

}
