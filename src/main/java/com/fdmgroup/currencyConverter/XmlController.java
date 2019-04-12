package com.fdmgroup.currencyConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XmlController {
	
//	private final static Logger XmlControllerLog = LogManager.getLogger(XmlController.class);
	
	private static XmlController single_instance = null;
	XmlDataParser dParserOnline = new XmlDataParser(false);
	XmlDataParser dParserLocal = new XmlDataParser(true);

	public static XmlController getInstance() {
		if (single_instance == null)
			single_instance = new XmlController();
//			XmlControllerLog.trace("New XmlController created.");
		return single_instance;
	}

	public int compareDate() {
		LocalDate fileDate = LocalDate.parse(dParserLocal.getExchangeRateDate());
		LocalDate xmlDate = LocalDate.parse(dParserOnline.getExchangeRateDate());
		return fileDate.compareTo(xmlDate);
	}

	public XmlDataParser returnDataParser() {
		if (compareDate() != 0) {
//			XmlControllerLog.info("Newer XML found. Running XmlDownloader.");
			XmlDownloader downloader = new XmlDownloader();
			downloader.downloadXML();
			return dParserLocal;
		} else {
//			XmlControllerLog.info("No newer XML found.");
			return dParserLocal;
		}
	}

	public List<String> getCurrencyList() {
		Set<String> keySet = returnDataParser().getHashMap().keySet();
		List<String> keys = new ArrayList<String>(keySet);
		return keys;
	}

	public String getCurrency(int input) {
		Set<String> keySet = returnDataParser().getHashMap().keySet();
		List<String> keys = new ArrayList<String>(keySet);
		HashMap<Integer, String> newMap = new HashMap<Integer, String>();

		int listOfCurrencies = keySet.size();
		for (int i = 1; i < listOfCurrencies + 1; i++) {
			newMap.put(i, (String) keys.get(i - 1));
		}
		return newMap.get(input);
	}

	public BigDecimal getRate(String input) {
		return returnDataParser().getHashMap().get(input);
	}
}
