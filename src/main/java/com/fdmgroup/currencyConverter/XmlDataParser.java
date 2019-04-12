package com.fdmgroup.currencyConverter;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.jdom2.Document;
import org.jdom2.Element;

public class XmlDataParser implements iXmlDataParser {

	File xmlFile = new File("./eurofxref-daily.xml");
	String xmlFileUrl = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
	HashMap<String, BigDecimal> currencyRateHashMap = new HashMap<String, BigDecimal>();
	XmlParser xmlParser = new XmlParser(); 
	List<Element> featureList = null;
	Document document = null;
	String xmlExchangeRateBankName = null;
	String xmlExchangeDate = null;

	public XmlDataParser(boolean isLocal) {
		if (isLocal) {
			document = xmlParser.getParsedDocument(xmlFile);
		} else {
			document = xmlParser.getParsedDocument(xmlFileUrl);
		}
		Element envelopeNode = document.getRootElement();
		featureList = envelopeNode.getChildren();
	}
	
//	public List<Element> getChildren(boolean isLocal){
//		if (isLocal) {
//			document = xmlParser.getParsedDocument(xmlFile);
//		} else {
//			document = xmlParser.getParsedDocument(xmlFileUrl);
//		}
//		Element envelopeNode = document.getRootElement();
//		featureList = envelopeNode.getChildren();
//		return featureList;
//	}

	public String getExchangeRateDate() {
		for (int temp = 0; temp < featureList.size(); temp++) {
			Element section = featureList.get(temp);

			if (section.getName().equals("Cube")) {
				// Cube contains time + subsection Cube contains currency & rate
				List<Element> cubeList1 = section.getChildren();
				for (int cube1 = 0; cube1 < cubeList1.size(); cube1++) {
					Element name = cubeList1.get(cube1);
					xmlExchangeDate = name.getAttributeValue("time");
				}
			}
		}
		return xmlExchangeDate;
	}

	public String getExchangeRateBankName() {
		for (int temp = 0; temp < featureList.size(); temp++) {
			Element section = featureList.get(temp);

			if (section.getName().equals("Sender")) {
				// Sender contains bankName
				List<Element> senderList = section.getChildren();
				for (int sender = 0; sender < senderList.size(); sender++) {
					Element name = senderList.get(sender);
					xmlExchangeRateBankName = name.getValue();
				}
			}
		}
		return xmlExchangeRateBankName;
	}

	public TreeMap<String, BigDecimal> getHashMap() {
		for (int temp = 0; temp < featureList.size(); temp++) {
			Element section = featureList.get(temp);

			if (section.getName().equals("Cube")) {
				// Cube contains time + subsection Cube contains currency & rate
				List<Element> cubeList1 = section.getChildren();
				for (int cube1 = 0; cube1 < cubeList1.size(); cube1++) {
					Element name = cubeList1.get(cube1);

					List<Element> cubeList2 = name.getChildren();
					for (int cube2 = 0; cube2 < cubeList2.size(); cube2++) {
						Element cube2Element = cubeList2.get(cube2);
						String currency = cube2Element.getAttributeValue("currency");
						BigDecimal rate = new BigDecimal(cube2Element.getAttributeValue("rate"));
						currencyRateHashMap.put(currency, rate);
					}
				}
			}
		}
		TreeMap<String, BigDecimal> sortedCurrencyRateHashMap = new TreeMap<>(currencyRateHashMap);
		return sortedCurrencyRateHashMap;
	}
}