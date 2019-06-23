package com.icat.quest.common.vo;

import java.io.Serializable;

public class UserExamSummaryVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userExamSummaryId;
	private ExamVo examVo;
	private CandidateVo userVo;
	private Long startDate;
	private Long endDate;
	private Integer allowAttempts;
	private Integer attemptsRemaing;
	private Long attemptTime;
	private Integer totalAttempt;
	private Integer totalMarks;
	private Boolean active;
	public UserExamSummaryVo() {
		super();
	}

	

	

		public UserExamSummaryVo(Integer userExamSummaryId, ExamVo examVo, CandidateVo userVo, Long startDate, Long endDate,
			Integer allowAttempts, Integer attemptsRemaing, Long attemptTime, Integer totalAttempt, Integer totalMarks,
			Boolean active) {
		super();
		this.userExamSummaryId = userExamSummaryId;
		this.examVo = examVo;
		this.userVo = userVo;
		this.startDate = startDate;
		this.endDate = endDate;
		this.allowAttempts = allowAttempts;
		this.attemptsRemaing = attemptsRemaing;
		this.attemptTime = attemptTime;
		this.totalAttempt = totalAttempt;
		this.totalMarks = totalMarks;
		this.active = active;
	}





		public Integer getUserExamSummaryId() {
		return userExamSummaryId;
	}


	public void setUserExamSummaryId(Integer userExamSummaryId) {
		this.userExamSummaryId = userExamSummaryId;
	}



	public ExamVo getExamVo() {
		return examVo;
	}





	public void setExamVo(ExamVo examVo) {
		this.examVo = examVo;
	}





	public CandidateVo getUserVo() {
		return userVo;
	}


	public void setUserVo(CandidateVo userVo) {
		this.userVo = userVo;
	}



		public Long getStartDate() {
		return startDate;
	}



	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}



	public Long getEndDate() {
		return endDate;
	}



	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}



	public Integer getAllowAttempts() {
		return allowAttempts;
	}



	public void setAllowAttempts(Integer allowAttempts) {
		this.allowAttempts = allowAttempts;
	}



	public Integer getAttemptsRemaing() {
		return attemptsRemaing;
	}



	public void setAttemptsRemaing(Integer attemptsRemaing) {
		this.attemptsRemaing = attemptsRemaing;
	}



	public Long getAttemptTime() {
		return attemptTime;
	}



	public void setAttemptTime(Long attemptTime) {
		this.attemptTime = attemptTime;
	}



	public Integer getTotalAttempt() {
		return totalAttempt;
	}



	public void setTotalAttempt(Integer totalAttempt) {
		this.totalAttempt = totalAttempt;
	}



	public Integer getTotalMarks() {
		return totalMarks;
	}



	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
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
	
	
}
