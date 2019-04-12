package com.fdmgroup.currencyConverter;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XmlParser {

	public Document getParsedDocument(File xmlFile) {
		if (!xmlFile.exists()) {
			XmlDownloader downloader = new XmlDownloader();
			downloader.downloadXML();
		}

		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = (Document) builder.build(xmlFile);
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		return document;
	}

	public Document getParsedDocument(String xmlUrl) {
		SAXBuilder builder = new SAXBuilder();
		Document document = null;
		try {
			document = (Document) builder.build(xmlUrl);
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		return document;
	}
}
