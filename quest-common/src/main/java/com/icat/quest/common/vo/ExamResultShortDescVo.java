package com.icat.quest.common.vo;

import java.io.Serializable;

public class ExamResultShortDescVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer examId;
	private String examName;
	private Long totalAttempt;
	public ExamResultShortDescVo() {
		super();
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
	public Long getTotalAttempt() {
		return totalAttempt;
	}
	public void setTotalAttempt(Long totalAttempt) {
		this.totalAttempt = totalAttempt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
