package com.icat.quest.common.vo;

import java.io.Serializable;

public class TestConductorIdAndAttendeVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer testConductorHasTestCodeId;
	private Boolean attended;
	public TestConductorIdAndAttendeVo() {
		super();
	}
	public TestConductorIdAndAttendeVo(Integer testConductorHasTestCodeId, Boolean attended) {
		super();
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
		this.attended = attended;
	}
	public Integer getTestConductorHasTestCodeId() {
		return testConductorHasTestCodeId;
	}
	public void setTestConductorHasTestCodeId(Integer testConductorHasTestCodeId) {
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
	}
	public Boolean getAttended() {
		return attended;
	}
	public void setAttended(Boolean attended) {
		this.attended = attended;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	

}
