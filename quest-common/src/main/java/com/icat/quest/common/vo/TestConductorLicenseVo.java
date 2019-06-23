package com.icat.quest.common.vo;

import java.io.Serializable;

public class TestConductorLicenseVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer testConductorLicenseId;
	private ExamVo examVo;
	private TestConductorVo testConductorVo;
	private Integer licenseCount;
	private Integer remainingLicenseCount;
	private Boolean active;
	private Integer parentTestConductorId;
	public TestConductorLicenseVo() {
		super();
	}
	
	

		public TestConductorLicenseVo(Integer testConductorLicenseId, ExamVo examVo, TestConductorVo testConductorVo,
			Integer licenseCount, Integer remainingLicenseCount, Boolean active, Integer parentTestConductorId) {
		super();
		this.testConductorLicenseId = testConductorLicenseId;
		this.examVo = examVo;
		this.testConductorVo = testConductorVo;
		this.licenseCount = licenseCount;
		this.remainingLicenseCount = remainingLicenseCount;
		this.active = active;
		this.parentTestConductorId = parentTestConductorId;
	}



		public Integer getTestConductorLicenseId() {
			return testConductorLicenseId;
		}




		public void setTestConductorLicenseId(Integer testConductorLicenseId) {
			this.testConductorLicenseId = testConductorLicenseId;
		}




		public ExamVo getExamVo() {
			return examVo;
		}




		public void setExamVo(ExamVo examVo) {
			this.examVo = examVo;
		}




		public TestConductorVo getTestConductorVo() {
			return testConductorVo;
		}




		public void setTestConductorVo(TestConductorVo testConductorVo) {
			this.testConductorVo = testConductorVo;
		}



		public Integer getLicenseCount() {
			return licenseCount;
		}




		public void setLicenseCount(Integer licenseCount) {
			this.licenseCount = licenseCount;
		}




		public Integer getRemainingLicenseCount() {
			return remainingLicenseCount;
		}




		public void setRemainingLicenseCount(Integer remainingLicenseCount) {
			this.remainingLicenseCount = remainingLicenseCount;
		}




		public Boolean getActive() {
			return active;
		}




		public void setActive(Boolean active) {
			this.active = active;
		}




		public Integer getParentTestConductorId() {
			return parentTestConductorId;
		}



		public void setParentTestConductorId(Integer parentTestConductorId) {
			this.parentTestConductorId = parentTestConductorId;
		}



		public static long getSerialversionuid() {
		return serialVersionUID;
	}



		@Override
		public String toString() {
			return "TestConductorLicenseVo [testConductorLicenseId=" + testConductorLicenseId + ", examVo=" + examVo
					+ ", testConductorVo=" + testConductorVo + ", licenseCount=" + licenseCount
					+ ", remainingLicenseCount=" + remainingLicenseCount + ", active=" + active
					+ ", parentTestConductorId=" + parentTestConductorId + "]";
		}
	
	

}
