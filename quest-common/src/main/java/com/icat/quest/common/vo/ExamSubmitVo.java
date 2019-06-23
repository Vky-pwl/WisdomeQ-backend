package com.icat.quest.common.vo;

public class ExamSubmitVo {
	
	private Integer questionMarks;
	private Boolean answerFlag;
	private Float questionNegativeMarks;
	private Integer totalAttempts;
	public ExamSubmitVo() {
		super();
	}
	public ExamSubmitVo(Integer questionMarks, Boolean answerFlag, Float questionNegativeMarks, Integer totalAttempts) {
		super();
		this.questionMarks = questionMarks;
		this.answerFlag = answerFlag;
		this.questionNegativeMarks = questionNegativeMarks;
		this.totalAttempts = totalAttempts;
	}
	public Integer getQuestionMarks() {
		return questionMarks;
	}
	public void setQuestionMarks(Integer questionMarks) {
		this.questionMarks = questionMarks;
	}
	public Boolean getAnswerFlag() {
		return answerFlag;
	}
	public void setAnswerFlag(Boolean answerFlag) {
		this.answerFlag = answerFlag;
	}
	public Float getQuestionNegativeMarks() {
		return questionNegativeMarks;
	}
	public void setQuestionNegativeMarks(Float questionNegativeMarks) {
		this.questionNegativeMarks = questionNegativeMarks;
	}
	public Integer getTotalAttempts() {
		return totalAttempts;
	}
	public void setTotalAttempts(Integer totalAttempts) {
		this.totalAttempts = totalAttempts;
	}
	
	
}
