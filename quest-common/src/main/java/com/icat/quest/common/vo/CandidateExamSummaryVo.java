package com.icat.quest.common.vo;

public class CandidateExamSummaryVo {
	
	private Integer candidateExamSummaryId;
	private Integer examId;
	private Integer examSectionId;
	private Integer examSectionHasQuestionId;
	private Integer candidateId;
	private Integer questionCategoryId;
	private Boolean answerFlag;
	private Float questionMarks;
	private Float questionNegativeMarks;
	private Integer questionNumber;
	private Boolean sicoFlag;
	private Integer testConductorHasTestCodeId;
	public CandidateExamSummaryVo() {
		super();
	}
	
	
	public CandidateExamSummaryVo(Integer candidateExamSummaryId, Integer examId, Integer examSectionId,
			Integer examSectionHasQuestionId, Integer candidateId, Integer questionCategoryId, Boolean answerFlag,
			Float questionMarks, Float questionNegativeMarks, Integer questionNumber, Boolean sicoFlag, Integer testConductorHasTestCodeId) {
		super();
		this.candidateExamSummaryId = candidateExamSummaryId;
		this.examId = examId;
		this.examSectionId = examSectionId;
		this.examSectionHasQuestionId = examSectionHasQuestionId;
		this.candidateId = candidateId;
		this.questionCategoryId = questionCategoryId;
		this.answerFlag = answerFlag;
		this.questionMarks = questionMarks;
		this.questionNegativeMarks = questionNegativeMarks;
		this.questionNumber = questionNumber;
		this.sicoFlag = sicoFlag;
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
	}


	public Integer getCandidateExamSummaryId() {
		return candidateExamSummaryId;
	}
	public void setCandidateExamSummaryId(Integer candidateExamSummaryId) {
		this.candidateExamSummaryId = candidateExamSummaryId;
	}
	public Integer getExamId() {
		return examId;
	}
	public void setExamId(Integer examId) {
		this.examId = examId;
	}
	public Integer getExamSectionId() {
		return examSectionId;
	}
	public void setExamSectionId(Integer examSectionId) {
		this.examSectionId = examSectionId;
	}
	public Integer getExamSectionHasQuestionId() {
		return examSectionHasQuestionId;
	}
	public void setExamSectionHasQuestionId(Integer examSectionHasQuestionId) {
		this.examSectionHasQuestionId = examSectionHasQuestionId;
	}
	public Integer getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}
	public Integer getQuestionCategoryId() {
		return questionCategoryId;
	}
	public void setQuestionCategoryId(Integer questionCategoryId) {
		this.questionCategoryId = questionCategoryId;
	}
	public Boolean getAnswerFlag() {
		return answerFlag;
	}
	public void setAnswerFlag(Boolean answerFlag) {
		this.answerFlag = answerFlag;
	}
	public Float getQuestionMarks() {
		return questionMarks;
	}
	public void setQuestionMarks(Float questionMarks) {
		this.questionMarks = questionMarks;
	}
	public Float getQuestionNegativeMarks() {
		return questionNegativeMarks;
	}
	public void setQuestionNegativeMarks(Float questionNegativeMarks) {
		this.questionNegativeMarks = questionNegativeMarks;
	}
	public Integer getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}


	public Boolean getSicoFlag() {
		return sicoFlag;
	}


	public void setSicoFlag(Boolean sicoFlag) {
		this.sicoFlag = sicoFlag;
	}


	public Integer getTestConductorHasTestCodeId() {
		return testConductorHasTestCodeId;
	}


	public void setTestConductorHasTestCodeId(Integer testConductorHasTestCodeId) {
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
	}
	
	

}
