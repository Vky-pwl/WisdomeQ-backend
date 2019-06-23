package com.icat.quest.common.utils;

public class SpecialChracterTrim {

	public static String trim(String value) {

		value = value.replaceFirst("\n", "")
					 .replaceFirst("\"", "")
					 .replaceFirst("\t", "");
		value = new StringBuilder(value).reverse().toString();
		value = value.replaceFirst("\n", "")
				 	 .replaceFirst("\"", "")
				 	 .replaceFirst("\t", "");
		value = new StringBuilder(value).reverse().toString();

		return value;
	}

}
