package com.icat.quest.common.vo;

import java.io.Serializable;

public class ExamMetadatDashboardVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer totalExam;
	private Integer takenExam;
	private Integer remainingExam;
	public ExamMetadatDashboardVo(Integer totalExam, Integer takenExam, Integer remainingExam) {
		super();
		this.totalExam = totalExam;
		this.takenExam = takenExam;
		this.remainingExam = remainingExam;
	}
	public Integer getTotalExam() {
		return totalExam;
	}
	public void setTotalExam(Integer totalExam) {
		this.totalExam = totalExam;
	}
	public Integer getTakenExam() {
		return takenExam;
	}
	public void setTakenExam(Integer takenExam) {
		this.takenExam = takenExam;
	}
	public Integer getRemainingExam() {
		return remainingExam;
	}
	public void setRemainingExam(Integer remainingExam) {
		this.remainingExam = remainingExam;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
