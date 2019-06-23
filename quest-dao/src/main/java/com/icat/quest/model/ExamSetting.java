package com.icat.quest.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "ExamSetting")
public class ExamSetting implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer examSettingId;
	private MasterSetting masterSetting;
	private Integer examId;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	
	public ExamSetting() {
	}


	public ExamSetting( MasterSetting masterSetting, Integer examId, Boolean active, Date created,
			Long createdBy, Date lastModified, Long lastModifiedBy) {
		super();
		this.masterSetting = masterSetting;
		this.examId = examId;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "examSettingId", unique = true, nullable = false)
	public Integer getExamSettingId() {
		return examSettingId;
	}



	public void setExamSettingId(Integer examSettingId) {
		this.examSettingId = examSettingId;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "settingId", nullable = false)
	public MasterSetting getMasterSetting() {
		return masterSetting;
	}


	public void setMasterSetting(MasterSetting masterSetting) {
		this.masterSetting = masterSetting;
	}


	@Column(name = "exmaId", nullable = false)
	public Integer getExamId() {
		return examId;
	}



	public void setExamId(Integer examId) {
		this.examId = examId;
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
