package com.fdmgroup.currencyConverter;

import java.math.BigDecimal;
import java.util.TreeMap;

public interface iXmlDataParser {
	public String getExchangeRateDate();
	public String getExchangeRateBankName();
	public TreeMap<String, BigDecimal> getHashMap();
}
