package com.icat.quest.common.vo;

import java.io.Serializable;

public class CandidateExamVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer examSectionHasQuestionId;
	private Integer testConductorHasTestCodeId;
	private Integer candidateId;
	private String userAnswer;
	private Boolean active = true;
	private Long remainingTime;
	private Long sectionRemainingTime;
	private Boolean sicoFlag;
	private Integer examId;
	public CandidateExamVo() {
		super();
	}
	



	public CandidateExamVo(Integer examSectionHasQuestionId, Integer testConductorHasTestCodeId, Integer candidateId,
			String userAnswer, Boolean active, Long remainingTime, Long sectionRemainingTime, Boolean sicoFlag,
			Integer examId) {
		super();
		this.examSectionHasQuestionId = examSectionHasQuestionId;
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
		this.candidateId = candidateId;
		this.userAnswer = userAnswer;
		this.active = active;
		this.remainingTime = remainingTime;
		this.sectionRemainingTime = sectionRemainingTime;
		this.sicoFlag = sicoFlag;
		this.examId = examId;
	}




	public Integer getExamSectionHasQuestionId() {
		return examSectionHasQuestionId;
	}




	public void setExamSectionHasQuestionId(Integer examSectionHasQuestionId) {
		this.examSectionHasQuestionId = examSectionHasQuestionId;
	}




	public Integer getTestConductorHasTestCodeId() {
		return testConductorHasTestCodeId;
	}




	public void setTestConductorHasTestCodeId(Integer testConductorHasTestCodeId) {
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
	}




	public Integer getCandidateId() {
		return candidateId;
	}




	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}




	public String getUserAnswer() {
		return userAnswer;
	}




	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}




	public Boolean getActive() {
		return active;
	}




	public void setActive(Boolean active) {
		this.active = active;
	}




	public Long getRemainingTime() {
		return remainingTime;
	}




	public void setRemainingTime(Long remainingTime) {
		this.remainingTime = remainingTime;
	}




	public Long getSectionRemainingTime() {
		return sectionRemainingTime;
	}




	public void setSectionRemainingTime(Long sectionRemainingTime) {
		this.sectionRemainingTime = sectionRemainingTime;
	}




	public Boolean getSicoFlag() {
		return sicoFlag;
	}




	public void setSicoFlag(Boolean sicoFlag) {
		this.sicoFlag = sicoFlag;
	}




	public Integer getExamId() {
		return examId;
	}




	public void setExamId(Integer examId) {
		this.examId = examId;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	@Override
	public String toString() {
		return "CandidateExamVo [examSectionHasQuestionId=" + examSectionHasQuestionId + ", testConductorHasTestCodeId="
				+ testConductorHasTestCodeId + ", candidateId=" + candidateId + ", userAnswer=" + userAnswer
				+ ", active=" + active + ", remainingTime=" + remainingTime + ", sectionRemainingTime="
				+ sectionRemainingTime + ", sicoFlag=" + sicoFlag + ", examId=" + examId + "]";
	}




	
}
