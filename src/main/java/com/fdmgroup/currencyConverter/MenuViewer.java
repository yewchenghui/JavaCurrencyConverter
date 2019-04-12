package com.fdmgroup.currencyConverter;

import java.math.BigDecimal;
import java.util.Scanner;

public class MenuViewer {
	Scanner sc = new Scanner(System.in);
	ConverterController converterController;
	XmlController xmlController;
	String baseCurrency = "EUR";
	int menuSelection = -1;
	int currencySelected = -1;
	int currencySelected2 = -1;
	double amountToConvert = 0;

	public MenuViewer() {
		converterController = new ConverterController();
		xmlController = XmlController.getInstance();
	}

	public void getMenuLevel1() {
		System.out.println("Currency Converter - A service provided by "
				+ xmlController.returnDataParser().getExchangeRateBankName() + ".");
		System.out.println("");
		System.out.println("0. List all currencies available.");
		System.out.println("1. Convert from " + baseCurrency + " to other currencies.");
		System.out.println("2. Convert from other currencies to " + baseCurrency + ".");
		System.out.println("3. Convert from foreign currencies to other currencies.");
		System.out.println("");
		System.out.println("Enter your option. [0-3]");
		menuSelection = sc.nextInt();
		if (menuSelection <= 3 && menuSelection >= 0) {
			getMenuLevel2(menuSelection);
		} else {
			clearScreen();
			System.out.println("Invalid Option");
			getMenuLevel1();
		}
	}

	public void getMenuLevel2(int input) {
		if (input == 0) {
			clearScreen();
			displayListOfCurrencies();
		} else if (input == 1 || input == 2 || input == 3) {
			clearScreen();
			showCurrencySelection();
			getMenuLevel3(menuSelection, currencySelected, amountToConvert);
		} else {
			clearScreen();
			System.out.println("Invalid option. You should not see this.");
			getMenuLevel1();
		}
	}

	public void getMenuLevel3(int menuSelection, int currencySelected, double amountToConvert) {
		System.out.println("");
		String currencyName = xmlController.getCurrency(currencySelected);
		String currencyName2 = xmlController.getCurrency(currencySelected2);
		if (menuSelection == 1) {
			System.out.printf("You have chosen to convert %.2f " + baseCurrency + " to " + currencyName + ".\n", amountToConvert);
			BigDecimal amountConverted = converterController.convertEuToOthers(currencyName, amountToConvert);
			System.out.printf("The amount is %.2f " + currencyName + ".\n", amountConverted);
		} else if (menuSelection == 2) {
			System.out.printf("You have chosen to convert %.2f " + currencyName + " to " + baseCurrency + ".\n", amountToConvert);
			BigDecimal amountConverted = converterController.convertOthersToEu(currencyName, amountToConvert);
			System.out.printf("The amount is %.2f " + baseCurrency + ".\n", amountConverted);
		} else if (menuSelection == 3) {
			System.out.printf("You have chosen to convert %.2f " + currencyName + " to " + currencyName2 + ".\n", amountToConvert);
			BigDecimal amountConverted = converterController.convertOthersToOthers(currencyName, currencyName2, amountToConvert);
			System.out.printf("The amount is %.2f " + currencyName2 + ".\n", amountConverted);
		}
		sc.close();
	}

	public static void clearScreen() {
		for (int i = 0; i < 15; i++) {
			System.out.println("\n");
		}
	}

	public void displayListOfCurrencies() {
		System.out.println("Currency Converter - A service provided by "
				+ xmlController.returnDataParser().getExchangeRateBankName() + ".");
		System.out.println("");
		System.out.println("The list of currencies we provided are: ");

		int currencyListSize = xmlController.getCurrencyList().size();
		for (int i = 1; i < currencyListSize + 1; i++) {
			// if less than 10, add a blank in front
			String currencyName = xmlController.getCurrency(i);
			if (i < 10) {
				System.out.print(" " + i + ": " + currencyName);
			} else {
				System.out.print(i + ": " + currencyName);
			}
			// spacing between each values
			System.out.print("\t\t");
			// for every 5 values or last value, print new line
			if (i % 5 == 0 || i == currencyListSize) {
				System.out.print("\n");
			}
		}
		sc.close();
	}

	public double showCurrencySelection() {
		System.out.println("Currency Converter - A service provided by "
				+ xmlController.returnDataParser().getExchangeRateBankName() + ".");
		System.out.println("");
		System.out.println("To select a currency, enter the corresponding digits.");
		System.out.println(">> 8 -> DKK\n");

		int currencyListSize = xmlController.getCurrencyList().size();
		for (int i = 1; i < xmlController.getCurrencyList().size() + 1; i++) {
			// if less than 10, add a blank in front
			String currencyName = xmlController.getCurrency(i);
			BigDecimal rates = xmlController.getRate(currencyName);
			if (i < 10) {
				System.out.print(" " + i + ": " + currencyName + " (1 " + baseCurrency + " = " + rates + " "
						+ currencyName + ")");
			} else {
				System.out.print(
						i + ": " + currencyName + " (1 " + baseCurrency + " = " + rates + " " + currencyName + ")");
			}
			// spacing between each values
			System.out.print("\t");
			// for every 5 values or last value, print new line
			if (i % 5 == 0 || i == currencyListSize) {
				System.out.print("\n");
			}
		}
		System.out.println("Enter your option. [1-" + (currencyListSize) + "]");
		currencySelected = sc.nextInt();
		if (menuSelection == 3) {
			System.out.println("Enter your desired currency. [1-" + (currencyListSize) + "]");
			currencySelected2 = sc.nextInt();
			System.out.println("Enter your amount to convert. [" + xmlController.getCurrency(currencySelected) + " <-> " + xmlController.getCurrency(currencySelected2) + "]");
		} else {
			System.out.println("Enter your amount to convert. [" + baseCurrency + " <-> " + xmlController.getCurrency(currencySelected) + "]");
		}
		amountToConvert = sc.nextDouble();
		if (amountToConvert < 0) {
			amountToConvert = 0;
		}
		sc.close();
		return amountToConvert;
	}
}