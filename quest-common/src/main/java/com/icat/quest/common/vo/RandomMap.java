package com.icat.quest.common.vo;

import java.io.Serializable;

public class RandomMap implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer examSectionHasQuestionId;
	private Boolean attempt = false;
	public RandomMap() {
		super();
	}
	public RandomMap(Integer examSectionHasQuestionId, Boolean attempt) {
		super();
		this.examSectionHasQuestionId = examSectionHasQuestionId;
		this.attempt = attempt;
	}
	public Integer getExamSectionHasQuestionId() {
		return examSectionHasQuestionId;
	}
	public void setExamSectionHasQuestionId(Integer examSectionHasQuestionId) {
		this.examSectionHasQuestionId = examSectionHasQuestionId;
	}
	public Boolean getAttempt() {
		return attempt;
	}
	public void setAttempt(Boolean attempt) {
		this.attempt = attempt;
	}

	
}
