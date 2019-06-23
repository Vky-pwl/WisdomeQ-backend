package com.icat.quest.common.vo;

import java.util.Date;

public class CandidateExamSummaryDescVo {
	
	private Integer candidateId;
	private String candidateFirstName;
	private String candidateLastName;
	private Integer examId;
	private String examName;
    private Integer totalQuestion;
    private Integer totalMarks;
    private Integer examSectionId;
    public String examSectionName;
    private Integer sectionTotalMarks;
    private Integer sectionTotalQuestion;
    private String examCategoryName;
    private Date startTime;
    private String testCode;
    private Integer questionCategoryId;
    private String questionCategoryName;
    private String contactEmail;
    private String contactNumber;
    private String sectionName;
    private Integer testConductorHasTestCodeId;
    
    public CandidateExamSummaryDescVo() {
		super();
	}
	

	public String getContactEmail() {
		return contactEmail;
	}


	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}


	public String getContactNumber() {
		return contactNumber;
	}


	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}


	public Integer getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}
	public String getCandidateFirstName() {
		return candidateFirstName;
	}
	public void setCandidateFirstName(String candidateFirstName) {
		this.candidateFirstName = candidateFirstName;
	}
	public String getCandidateLastName() {
		return candidateLastName;
	}
	public void setCandidateLastName(String candidateLastName) {
		this.candidateLastName = candidateLastName;
	}
	public Integer getExamId() {
		return examId;
	}
	public void setExamId(Integer examId) {
		this.examId = examId;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
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
	public Integer getExamSectionId() {
		return examSectionId;
	}
	public void setExamSectionId(Integer examSectionId) {
		this.examSectionId = examSectionId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public Integer getSectionTotalMarks() {
		return sectionTotalMarks;
	}
	public void setSectionTotalMarks(Integer sectionTotalMarks) {
		this.sectionTotalMarks = sectionTotalMarks;
	}
	public Integer getSectionTotalQuestion() {
		return sectionTotalQuestion;
	}
	public void setSectionTotalQuestion(Integer sectionTotalQuestion) {
		this.sectionTotalQuestion = sectionTotalQuestion;
	}

	public String getExamCategoryName() {
		return examCategoryName;
	}

	public void setExamCategoryName(String examCategoryName) {
		this.examCategoryName = examCategoryName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}



	public String getTestCode() {
		return testCode;
	}



	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}



	public Integer getQuestionCategoryId() {
		return questionCategoryId;
	}



	public void setQuestionCategoryId(Integer questionCategoryId) {
		this.questionCategoryId = questionCategoryId;
	}



	public String getQuestionCategoryName() {
		return questionCategoryName;
	}



	public void setQuestionCategoryName(String questionCategoryName) {
		this.questionCategoryName = questionCategoryName;
	}


	public String getExamSectionName() {
		return examSectionName;
	}


	public void setExamSectionName(String examSectionName) {
		this.examSectionName = examSectionName;
	}


	public Integer getTestConductorHasTestCodeId() {
		return testConductorHasTestCodeId;
	}


	public void setTestConductorHasTestCodeId(Integer testConductorHasTestCodeId) {
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
	}
    
    

}
