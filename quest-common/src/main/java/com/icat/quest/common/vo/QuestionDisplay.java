package com.icat.quest.common.vo;

public enum QuestionDisplay {
	A("ONE_BY_ONE"),
	B("ALL"),
	C("RANDOM"),
	D("ABC");
	
	private String description;
	
	private QuestionDisplay(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
