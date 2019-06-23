package com.icat.quest.common.vo;

public class ExamSectionHasQuestionVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer examSectionHasQuestionId;
	private ExamSectionVo examSectionVo;
	private QuestionBankVo questionBankVo;
	private Integer questionNumber;
	private Float marks;
	private Float negativeMark;
	private Boolean active;
	
	
	 
	public ExamSectionHasQuestionVo() {
	}




	public ExamSectionHasQuestionVo(Integer examSectionHasQuestionId, ExamSectionVo examSectionVo,
			QuestionBankVo questionBankVo, Integer questionNumber, Float marks, Float negativeMark, Boolean active) {
		super();
		this.examSectionHasQuestionId = examSectionHasQuestionId;
		this.examSectionVo = examSectionVo;
		this.questionBankVo = questionBankVo;
		this.questionNumber = questionNumber;
		this.marks = marks;
		this.negativeMark = negativeMark;
		this.active = active;
	}




	public Float getNegativeMark() {
		return negativeMark;
	}




	public void setNegativeMark(Float negativeMark) {
		this.negativeMark = negativeMark;
	}





	public ExamSectionVo getExamSectionVo() {
		return examSectionVo;
	}




	public void setExamSectionVo(ExamSectionVo examSectionVo) {
		this.examSectionVo = examSectionVo;
	}




	public Integer getExamSectionHasQuestionId() {
		return examSectionHasQuestionId;
	}



	public void setExamSectionHasQuestionId(Integer examSectionHasQuestionId) {
		this.examSectionHasQuestionId = examSectionHasQuestionId;
	}




	public QuestionBankVo getQuestionBankVo() {
		return questionBankVo;
	}



	public void setQuestionBankVo(QuestionBankVo questionBankVo) {
		this.questionBankVo = questionBankVo;
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




	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	@Override
	public String toString() {
		return "ExamSectionHasQuestionVo [examSectionHasQuestionId=" + examSectionHasQuestionId + ", examSectionVo="
				+ examSectionVo + ", questionBankVo=" + questionBankVo + ", questionNumber=" + questionNumber
				+ ", marks=" + marks + ", negativeMark=" + negativeMark + ", active=" + active + "]";
	}

	

	
}
