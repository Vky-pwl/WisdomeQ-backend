package com.icat.quest.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "ExamSettingHasCandidate")
public class ExamSettingHasCandidate implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer examSettingHasCandidateId;
	private Integer settingId;
	private Integer examId;
	private Integer candidateId;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	
	public ExamSettingHasCandidate() {
	}

		
	public ExamSettingHasCandidate(Integer settingId, Integer examId, Integer candidateId, Boolean active, Date created,
			Long createdBy, Date lastModified, Long lastModifiedBy) {
		super();
		this.settingId = settingId;
		this.examId = examId;
		this.candidateId = candidateId;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}





	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "examSettingHasCandidateId", unique = true, nullable = false)
	public Integer getExamSettingHasCandidateId() {
		return examSettingHasCandidateId;
	}



	public void setExamSettingHasCandidateId(Integer examSettingHasCandidateId) {
		this.examSettingHasCandidateId = examSettingHasCandidateId;
	}



	@Column(name = "settingId", nullable = false)
	public Integer getSettingId() {
		return settingId;
	}



	public void setSettingId(Integer settingId) {
		this.settingId = settingId;
	}



	@Column(name = "exmaId", nullable = false)
	public Integer getExamId() {
		return examId;
	}



	public void setExamId(Integer examId) {
		this.examId = examId;
	}





	@Column(name = "candidateId", nullable = false)
	public Integer getCandidateId() {
		return candidateId;
	}





	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}





	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Column(name = "active",nullable = false)
	public Boolean getActive() {
		return active;
	}




	public void setActive(Boolean active) {
		this.active = active;
	}




	public Date getCreated() {
		return created;
	}




	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
	public void setCreated(Date created) {
		this.created = created;
	}




	@Column(name = "createdBy", nullable = false, length = 20)
	public Long getCreatedBy() {
		return createdBy;
	}




	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}




	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastModified", nullable = false, length = 19)
	public Date getLastModified() {
		return lastModified;
	}




	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}




	@Column(name = "lastModifiedBy", nullable = false, length = 20)
	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}




	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}


	
		
}
