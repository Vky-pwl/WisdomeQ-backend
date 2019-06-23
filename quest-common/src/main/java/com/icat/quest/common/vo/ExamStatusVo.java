package com.icat.quest.common.vo;

import java.io.Serializable;

public class ExamStatusVo implements Serializable{

	/**
	 * 
	 */

	private static final long serialVersionUID = -5744032367921718782L;
	private Integer currentExamId;
    private ExamStatus currentExamStatus;
    private Integer currentSectionId;
    private ExamStatus currentSectionStatus;
    private Integer currentQuestionId;
    private ExamStatus currentQuestionStatus;
    private Long currentSectionRemaingTime;
    private Long examRemaingTime;
    private Boolean updateFlag=false;
    private Integer testConductorHasTestCodeId;
	
    public ExamStatusVo() {
		super();
	}

	public Integer getCurrentExamId() {
		return currentExamId;
	}

	public void setCurrentExamId(Integer currentExamId) {
		this.currentExamId = currentExamId;
	}

	public ExamStatus getCurrentExamStatus() {
		return currentExamStatus;
	}

	public void setCurrentExamStatus(ExamStatus currentExamStatus) {
		this.currentExamStatus = currentExamStatus;
	}

	public Integer getCurrentSectionId() {
		return currentSectionId;
	}

	public void setCurrentSectionId(Integer currentSectionId) {
		this.currentSectionId = currentSectionId;
	}

	public ExamStatus getCurrentSectionStatus() {
		return currentSectionStatus;
	}

	public void setCurrentSectionStatus(ExamStatus currentSectionStatus) {
		this.currentSectionStatus = currentSectionStatus;
	}

	public Integer getCurrentQuestionId() {
		return currentQuestionId;
	}

	public void setCurrentQuestionId(Integer currentQuestionId) {
		this.currentQuestionId = currentQuestionId;
	}

	public ExamStatus getCurrentQuestionStatus() {
		return currentQuestionStatus;
	}

	public void setCurrentQuestionStatus(ExamStatus currentQuestionStatus) {
		this.currentQuestionStatus = currentQuestionStatus;
	}

	public Long getCurrentSectionRemaingTime() {
		return currentSectionRemaingTime;
	}

	public void setCurrentSectionRemaingTime(Long currentSectionRemaingTime) {
		this.currentSectionRemaingTime = currentSectionRemaingTime;
	}

	public Long getExamRemaingTime() {
		return examRemaingTime;
	}

	public void setExamRemaingTime(Long examRemaingTime) {
		this.examRemaingTime = examRemaingTime;
	}

	public Boolean getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(Boolean updateFlag) {
		this.updateFlag = updateFlag;
	}

	public Integer getTestConductorHasTestCodeId() {
		return testConductorHasTestCodeId;
	}

	public void setTestConductorHasTestCodeId(Integer testConductorHasTestCodeId) {
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
	}

	@Override
	public String toString() {
		return "ExamStatusVo [currentExamId=" + currentExamId + ", currentExamStatus=" + currentExamStatus
				+ ", currentSectionId=" + currentSectionId + ", currentSectionStatus=" + currentSectionStatus
				+ ", currentQuestionId=" + currentQuestionId + ", currentQuestionStatus=" + currentQuestionStatus
				+ ", currentSectionRemaingTime=" + currentSectionRemaingTime + ", examRemaingTime=" + examRemaingTime
				+ ", updateFlag=" + updateFlag + ", testConductorHasTestCodeId=" + testConductorHasTestCodeId + "]";
	}
	
	
	
}
