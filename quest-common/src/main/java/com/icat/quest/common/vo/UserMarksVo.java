package com.icat.quest.common.vo;

public class UserMarksVo {
	
	private Integer testConductorHasTestCodeId;
	private Float userTotalMark;
	private int rank;
	public UserMarksVo() {
		super();
	}
	
	public Integer getTestConductorHasTestCodeId() {
		return testConductorHasTestCodeId;
	}
	public void setTestConductorHasTestCodeId(Integer testConductorHasTestCodeId) {
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
	}
	public Float getUserTotalMark() {
		return userTotalMark;
	}
	public void setUserTotalMark(Float userTotalMark) {
		this.userTotalMark = userTotalMark;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	

}
