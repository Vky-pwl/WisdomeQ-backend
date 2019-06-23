package com.icat.quest.common.vo;

public class CandidateExamSummaryCompositeVo {
	
	private Integer examId;
	private Integer examSectionId;
	private Integer questionNumber;
	private Float marks;
	private Float negativeMark;
	private Integer questionCategoryId;
	private  String correctAnswer;
	
	public CandidateExamSummaryCompositeVo() {
		super();
	}
	public CandidateExamSummaryCompositeVo(Integer examId, Integer examSectionId,
			Integer questionNumber, Float marks, Float negativeMark, Integer questionCategoryId,
			String correctAnswer) {
		super();
		this.examId = examId;
		this.examSectionId = examSectionId;
		this.questionNumber = questionNumber;
		this.marks = marks;
		this.negativeMark = negativeMark;
		this.questionCategoryId = questionCategoryId;
		this.correctAnswer = correctAnswer;
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
	
	public Integer getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}
	public Float getMarks() {
		return marks;
	}
	public void setMarks(Float marks) {
		this.marks = marks;
	}
	public Float getNegativeMark() {
		return negativeMark;
	}
	public void setNegativeMark(Float negativeMark) {
		this.negativeMark = negativeMark;
	}
	public Integer getQuestionCategoryId() {
		return questionCategoryId;
	}
	public void setQuestionCategoryId(Integer questionCategoryId) {
		this.questionCategoryId = questionCategoryId;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	

}
