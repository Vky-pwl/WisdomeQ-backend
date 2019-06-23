package com.icat.quest.dao.user.service.impl;

public class Test {

	public static void main(String[] args) {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(6);

		for (int i = 0; i < 6; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}

		System.out.println(sb.toString());
	}

}
