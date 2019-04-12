package com.fdmgroup.currencyConverter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XmlDownloader {

//	private final static Logger downloaderLog = LogManager.getLogger(XmlDownloader.class);
	
	public void downloadXML() {
		String filename = "eurofxref-daily.xml";
		URL oURL;
		URLConnection oConnection;
		BufferedReader oReader;
		BufferedWriter oWriter;
		String sLine;
		StringBuilder sbResponse;
		String sResponse = null;

		try {
			oURL = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
			oConnection = oURL.openConnection();
//			downloaderLog.info("Connection started.");
			oConnection.setRequestProperty("Accept", "application/xml");
//			downloaderLog.trace(oConnection);
			oReader = new BufferedReader(new InputStreamReader(oConnection.getInputStream()));
//			downloaderLog.trace("BufferedReader started.");
			sbResponse = new StringBuilder();
//			downloaderLog.trace("StringBuilder started.");
			while ((sLine = oReader.readLine()) != null) {
				sbResponse.append(sLine + "\n");
			}
			sResponse = sbResponse.toString();
//			downloaderLog.trace("XML String created.");
			// will replace each time
			oWriter = new BufferedWriter(new FileWriter(filename));
//			downloaderLog.trace("BufferedWriter started.");
			oWriter.write(sResponse);
			oWriter.close();
//			downloaderLog.trace("String written through BufferedWriter to File.");
//			downloaderLog.info("XML downloaded.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
