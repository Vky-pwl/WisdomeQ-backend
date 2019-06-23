package com.icat.quest.common.vo;

import java.io.Serializable;

public class CandidateQuestionStatusVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer examSectionHasQuestionId;
	private Integer testConductorHasTestCodeId;
	private Integer candidateId;
	private Boolean visited = false;
	private Boolean markedForReview = false;
	private Boolean answeredAndMarkedForReview = false;
	private Boolean answered= false;
	private String userAnswer;
	
	
	
	public CandidateQuestionStatusVo() {
		super();
	}



	public CandidateQuestionStatusVo(Integer examSectionHasQuestionId) {
		super();
		this.examSectionHasQuestionId = examSectionHasQuestionId;
	}





	public CandidateQuestionStatusVo( Integer testConductorHasTestCodeId,Integer examSectionHasQuestionId,
			Integer candidateId, Boolean visited, Boolean markedForReview, Boolean answeredAndMarkedForReview,
			Boolean answered, String userAnswer) {
		super();
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
		this.examSectionHasQuestionId = examSectionHasQuestionId;
		this.candidateId = candidateId;
		this.visited = visited;
		this.markedForReview = markedForReview;
		this.answeredAndMarkedForReview = answeredAndMarkedForReview;
		this.answered = answered;
		this.userAnswer = userAnswer;
	}



	public Integer getTestConductorHasTestCodeId() {
		return testConductorHasTestCodeId;
	}



	public void setTestConductorHasTestCodeId(Integer testConductorHasTestCodeId) {
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
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



	public Boolean getVisited() {
		return visited;
	}



	public void setVisited(Boolean visited) {
		this.visited = visited;
	}



	public Boolean getMarkedForReview() {
		return markedForReview;
	}



	public void setMarkedForReview(Boolean markedForReview) {
		this.markedForReview = markedForReview;
	}



	public Boolean getAnsweredAndMarkedForReview() {
		return answeredAndMarkedForReview;
	}



	public void setAnsweredAndMarkedForReview(Boolean answeredAndMarkedForReview) {
		this.answeredAndMarkedForReview = answeredAndMarkedForReview;
	}



	public Boolean getAnswered() {
		return answered;
	}



	public void setAnswered(Boolean answered) {
		this.answered = answered;
	}



	public String getUserAnswer() {
		return userAnswer;
	}



	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
