package com.icat.quest.common.vo;

import java.io.Serializable;

public class TestConductorHasTestCodeVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer testConductorHasTestCodeId;
	private Integer testConductorLicenseId;
	private String testCode;
	private CandidateVo userVo;
	private Long examStartDate;
	private Integer examId;
	private ExamVo examVo;
	private Integer totalQuestion;
	private Integer totalMarks;
	private String tinyKey;
	private Integer totalAttempts;
	private Float userTotalMarks;
	private Long submitDate;
	private Boolean attended;
	private Boolean active;
	private ExamStatus status;
	private Integer rank;
	private Integer totalCandidate;
		public TestConductorHasTestCodeVo() {
		super();
	}
	
	


	public TestConductorHasTestCodeVo(Integer testConductorHasTestCodeId, Integer testConductorLicenseId,
				String testCode, CandidateVo userVo, Long examStartDate, Integer examId, Integer totalQuestion,
				Integer totalMarks, String tinyKey, Integer totalAttempts, Float userTotalMarks, Long submitDate,
				Boolean attended, Boolean active) {
			super();
			this.testConductorHasTestCodeId = testConductorHasTestCodeId;
			this.testConductorLicenseId = testConductorLicenseId;
			this.testCode = testCode;
			this.userVo = userVo;
			this.examStartDate = examStartDate;
			this.examId = examId;
			this.totalQuestion = totalQuestion;
			this.totalMarks = totalMarks;
			this.tinyKey = tinyKey;
			this.totalAttempts = totalAttempts;
			this.userTotalMarks = userTotalMarks;
			this.submitDate = submitDate;
			this.attended = attended;
			this.active = active;
		}




	public Integer getTestConductorHasTestCodeId() {
		return testConductorHasTestCodeId;
	}




	public void setTestConductorHasTestCodeId(Integer testConductorHasTestCodeId) {
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
	}




	public Integer getTestConductorLicenseId() {
		return testConductorLicenseId;
	}




	public void setTestConductorLicenseId(Integer testConductorLicenseId) {
		this.testConductorLicenseId = testConductorLicenseId;
	}




	public String getTestCode() {
		return testCode;
	}




	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}




	public CandidateVo getUserVo() {
		return userVo;
	}




	public void setUserVo(CandidateVo userVo) {
		this.userVo = userVo;
	}




	public Long getExamStartDate() {
		return examStartDate;
	}




	public void setExamStartDate(Long examStartDate) {
		this.examStartDate = examStartDate;
	}




	public Integer getExamId() {
		return examId;
	}




	public void setExamId(Integer examId) {
		this.examId = examId;
	}




	public Integer getTotalQuestion() {
		return totalQuestion;
	}




	public void setTotalQuestion(Integer totalQuestion) {
		this.totalQuestion = totalQuestion;
	}




	public Integer getTotalMarks() {
		return totalMarks;
	}




	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}




	public String getTinyKey() {
		return tinyKey;
	}




	public void setTinyKey(String tinyKey) {
		this.tinyKey = tinyKey;
	}




	public Integer getTotalAttempts() {
		return totalAttempts;
	}




	public void setTotalAttempts(Integer totalAttempts) {
		this.totalAttempts = totalAttempts;
	}




	public Float getUserTotalMarks() {
		return userTotalMarks;
	}




	public void setUserTotalMarks(Float userTotalMarks) {
		this.userTotalMarks = userTotalMarks;
	}




	public Long getSubmitDate() {
		return submitDate;
	}




	public void setSubmitDate(Long submitDate) {
		this.submitDate = submitDate;
	}




	public Boolean getAttended() {
		return attended;
	}




	public void setAttended(Boolean attended) {
		this.attended = attended;
	}




	public Boolean getActive() {
		return active;
	}




	public void setActive(Boolean active) {
		this.active = active;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	public ExamStatus getStatus() {
		return status;
	}




	public void setStatus(ExamStatus status) {
		this.status = status;
	}




	public Integer getRank() {
		return rank;
	}




	public void setRank(Integer rank) {
		this.rank = rank;
	}




	public Integer getTotalCandidate() {
		return totalCandidate;
	}




	public void setTotalCandidate(Integer totalCandidate) {
		this.totalCandidate = totalCandidate;
	}




	public ExamVo getExamVo() {
		return examVo;
	}




	public void setExamVo(ExamVo examVo) {
		this.examVo = examVo;
	}




	@Override
	public String toString() {
		return "TestConductorHasTestCodeVo [testConductorHasTestCodeId=" + testConductorHasTestCodeId
				+ ", testConductorLicenseId=" + testConductorLicenseId + ", testCode=" + testCode + ", userVo=" + userVo
				+ ", examStartDate=" + examStartDate + ", examId=" + examId + ", examVo=" + examVo + ", totalQuestion="
				+ totalQuestion + ", totalMarks=" + totalMarks + ", tinyKey=" + tinyKey + ", totalAttempts="
				+ totalAttempts + ", userTotalMarks=" + userTotalMarks + ", submitDate=" + submitDate + ", attended="
				+ attended + ", active=" + active + ", status=" + status + ", rank=" + rank + ", totalCandidate="
				+ totalCandidate + "]";
	}





}
