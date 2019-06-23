package com.icat.quest.common.vo;

import java.io.Serializable;

public class QuestionCategoryVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer questionCategoryId;
	private String questionCategoryName;
	private Integer questionSubCategoryId;
	private String questionSubCategoryName;
	private Integer count;
	private Float totalMarks;
	private Boolean active;
	private Integer totalAttempt=0;
	private Float userTotalMarks=0f;
	private Integer totalCorrectAnswer=0;
	public QuestionCategoryVo() {
		super();
	}
	
	
	
	public QuestionCategoryVo(Integer questionSubCategoryId) {
		super();
		this.questionSubCategoryId = questionSubCategoryId;
	}




	public QuestionCategoryVo(Integer questionCategoryId, String questionCategoryName, Integer questionSubCategoryId,
			String questionSubCategoryName, Integer count, Float totalMarks, Boolean active, Integer totalAttempt,
			Float userTotalMarks, Integer totalCorrectAnswer) {
		super();
		this.questionCategoryId = questionCategoryId;
		this.questionCategoryName = questionCategoryName;
		this.questionSubCategoryId = questionSubCategoryId;
		this.questionSubCategoryName = questionSubCategoryName;
		this.count = count;
		this.totalMarks = totalMarks;
		this.active = active;
		this.totalAttempt = totalAttempt;
		this.userTotalMarks = userTotalMarks;
		this.totalCorrectAnswer = totalCorrectAnswer;
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



	public Integer getQuestionSubCategoryId() {
		return questionSubCategoryId;
	}



	public void setQuestionSubCategoryId(Integer questionSubCategoryId) {
		this.questionSubCategoryId = questionSubCategoryId;
	}



	public String getQuestionSubCategoryName() {
		return questionSubCategoryName;
	}



	public void setQuestionSubCategoryName(String questionSubCategoryName) {
		this.questionSubCategoryName = questionSubCategoryName;
	}



	public Integer getCount() {
		return count;
	}



	public void setCount(Integer count) {
		this.count = count;
	}



	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}



	public Integer getTotalAttempt() {
		return totalAttempt;
	}



	public void setTotalAttempt(Integer totalAttempt) {
		this.totalAttempt = totalAttempt;
	}

	public void incrTotalAttempt() {
		this.totalAttempt = this.totalAttempt+1;
	}

	public Float getUserTotalMarks() {
		return userTotalMarks;
	}



	public void setUserTotalMarks(Float userTotalMarks) {
		this.userTotalMarks = userTotalMarks;
	}
	
	public void addUserTotalMarks(Float questionMarks) {
		this.userTotalMarks = this.userTotalMarks+questionMarks;
	}

	public void subUserTotalMarks(Float negativeMarks) {
		this.userTotalMarks = this.userTotalMarks-negativeMarks;
	}

	public Integer getTotalCorrectAnswer() {
		return totalCorrectAnswer;
	}



	public void setTotalCorrectAnswer(Integer totalCorrectAnswer) {
		this.totalCorrectAnswer = totalCorrectAnswer;
	}

	public void incrTotalCorrectAnswer() {
		this.totalCorrectAnswer = this.totalCorrectAnswer+1;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public Float getTotalMarks() {
		return totalMarks;
	}



	public void setTotalMarks(Float totalMarks) {
		this.totalMarks = totalMarks;
	}



	@Override
	public String toString() {
		return "QuestionCategoryVo [questionCategoryId=" + questionCategoryId + ", questionCategoryName="
				+ questionCategoryName + ", questionSubCategoryId=" + questionSubCategoryId
				+ ", questionSubCategoryName=" + questionSubCategoryName + ", count=" + count + ", totalMarks="
				+ totalMarks + ", active=" + active + ", totalAttempt=" + totalAttempt + ", userTotalMarks="
				+ userTotalMarks + ", totalCorrectAnswer=" + totalCorrectAnswer + "]";
	}
	

			
}
