package com.fdmgroup.currencyConverter;

import java.math.BigDecimal;

public class ConverterController {
	
	private static ConverterController single_instance = null;
	XmlController xmlController;

	public ConverterController() {
		xmlController = XmlController.getInstance();
	}
	
	public static ConverterController getInstance() {
		if (single_instance == null)
			single_instance = new ConverterController();
		return single_instance;
	}
	
	public BigDecimal convertEuToOthers(String curr, Double amount) {
		if(amount == null || amount.isInfinite() || amount.isNaN() || amount < 0){
			return BigDecimal.ZERO;
	    }
	    try{
	    	BigDecimal bdAmount = BigDecimal.valueOf(amount);
	    	return (xmlController.getRate(curr).multiply(bdAmount));

	    } catch (Exception e) {
	    	return BigDecimal.ZERO;
	    }
	}
	
	public BigDecimal convertOthersToEu(String curr, Double amount) {
		if(amount == null || amount.isInfinite() || amount.isNaN() || amount < 0){
			return BigDecimal.ZERO;
	    }
	    try{
	    	BigDecimal bdAmount = BigDecimal.valueOf(amount);
	    	return bdAmount.divide(xmlController.getRate(curr), 6);

	    } catch (Exception e) {
	    	return BigDecimal.ZERO;
	    }
	}
	
	public BigDecimal convertOthersToOthers(String curr1, String curr2, Double amount) {
		if(amount == null || amount.isInfinite() || amount.isNaN() || amount < 0){
			return BigDecimal.ZERO;
	    }
	    try{
	    	BigDecimal bdAmount = BigDecimal.valueOf(amount);
	    	BigDecimal eurAmount = bdAmount.divide(xmlController.getRate(curr1), 6);
	    	return eurAmount.multiply(xmlController.getRate(curr2));
	    } catch (Exception e) {
	    	return BigDecimal.ZERO;
	    }
	}
}
	
