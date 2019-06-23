package com.icat.quest.email.vo;

import java.util.Date;

public class TinyLinkVo {
	
	private Integer examId;
	private Integer candidateId;
	private Date examDate;
	public TinyLinkVo() {
		super();
	}
	
	public TinyLinkVo(Integer examId, Integer candidateId, Date examDate) {
		super();
		this.examId = examId;
		this.candidateId = candidateId;
		this.examDate = examDate;
	}

	public Integer getExamId() {
		return examId;
	}
	public void setExamId(Integer examId) {
		this.examId = examId;
	}
	public Integer getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	
	

}
