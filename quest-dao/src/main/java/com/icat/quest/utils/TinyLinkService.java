package com.icat.quest.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.icat.quest.common.utils.SpringApplicationContext;
import com.icat.quest.dao.TestConductorHasTestCodeDao;
import com.icat.quest.model.TestConductorHasTestCode;


public class TinyLinkService {

	// storage for generated keys
	private HashMap<String, String> keyMap; // key-url map
												// whether an url is
	private char myChars[]; // This array is used for character to number
							// mapping
	private Random myRand; // Random object used to generate random integers
	private int keyLength; // the key length in URL defaults to 8

	// Default Constructor
	public TinyLinkService() {
		keyMap = new HashMap<String, String>();
		
		TestConductorHasTestCodeDao testConductorHasTestCodeDao = (TestConductorHasTestCodeDao) SpringApplicationContext.getBean("testConductorHasTestCodeDao");
		
		List<TestConductorHasTestCode> testConductorHasTestCodeList = testConductorHasTestCodeDao.listEntityByParameter(TestConductorHasTestCodeDao.findAll, null, null, null);
		if (testConductorHasTestCodeList != null && testConductorHasTestCodeList.size() > 0) {
			testConductorHasTestCodeList.forEach(tinyLink -> {
				String longKey = "U-"+tinyLink.getUser().getUserId()+"E-"+tinyLink.getExam().getExamId();
				if(tinyLink.getExam().getStartDate() != null) {
					longKey = longKey+"T-"+tinyLink.getExam().getStartDate().getTime();
				}
				keyMap.put(tinyLink.getTinyKey(),longKey);
			});
		}

		myRand = new Random();
		keyLength = 8;
		myChars = new char[62];
		for (int i = 0; i < 62; i++) {
			int j = 0;
			if (i < 10) {
				j = i + 48;
			} else if (i > 9 && i <= 35) {
				j = i + 55;
			} else {
				j = i + 61;
			}
			myChars[i] = (char) j;
		}
	}

	// shortenURL
	// the public method which can be called to shorten a given URL
	public String shortenURL(Integer testConductorHasTestCodeId, Integer userId, Long date) {
		String longKey = "U-"+userId+"E-"+testConductorHasTestCodeId;
		if(date != null) {
			longKey = longKey+"T-"+date;
		}
		String shortURL = "";
		if (validateURL(longKey)) {
			longKey = sanitizeURL(longKey);
				shortURL = getKey(longKey);
		}
		// add http part
		return shortURL;
	}

	// expandURL
	// public method which returns back the original URL given the shortened url
	public String expandURL(String shortURL) {
		String longURL = "";
		String key = shortURL;
		longURL = keyMap.get(key);
		return longURL;
	}

	// Validate URL
	// not implemented, but should be implemented to check whether the given URL
	// is valid or not
	boolean validateURL(String url) {
		return true;
	}

	// sanitizeURL
	// This method should take care various issues with a valid url
	// e.g. www.google.com,www.google.com/, http://www.google.com,
	// http://www.google.com/
	// all the above URL should point to same shortened URL
	// There could be several other cases like these.
	String sanitizeURL(String url) {
		if (url.substring(0, 7).equals("http://"))
			url = url.substring(7);

		if (url.substring(0, 8).equals("https://"))
			url = url.substring(8);

		if (url.charAt(url.length() - 1) == '/')
			url = url.substring(0, url.length() - 1);
		return url;
	}

	/*
	 * Get Key method
	 */
	private String getKey(String longURL) {
		String key;
		key = generateKey();
		keyMap.put(key, longURL);
		return key;
	}

	// generateKey
	private String generateKey() {
		String key = "";
		boolean flag = true;
		while (flag) {
			key = "";
			for (int i = 0; i <= keyLength; i++) {
				key += myChars[myRand.nextInt(62)];
			}
			// System.out.println("Iteration: "+ counter + "Key: "+ key);
			if (!keyMap.containsKey(key)) {
				flag = false;
			}
		}
		return key;
	}

}
