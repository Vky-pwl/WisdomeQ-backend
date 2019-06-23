package com.icat.quest.common.vo;

public class ExamSettingHasCandidateVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer examSettingHasCandidateId;
	private Integer settingId;
	private Integer examId;
	private Integer candidateId;
	private Boolean active;
	
	public ExamSettingHasCandidateVo() {
	}

	public ExamSettingHasCandidateVo(Integer examSettingHasCandidateId, Integer settingId, Integer examId,
			Integer candidateId, Boolean active) {
		super();
		this.examSettingHasCandidateId = examSettingHasCandidateId;
		this.settingId = settingId;
		this.examId = examId;
		this.candidateId = candidateId;
		this.active = active;
	}

	public Integer getExamSettingHasCandidateId() {
		return examSettingHasCandidateId;
	}

	public void setExamSettingHasCandidateId(Integer examSettingHasCandidateId) {
		this.examSettingHasCandidateId = examSettingHasCandidateId;
	}

	public Integer getSettingId() {
		return settingId;
	}

	public void setSettingId(Integer settingId) {
		this.settingId = settingId;
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
