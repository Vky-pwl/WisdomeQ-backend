package com.icat.quest.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.icat.quest.common.vo.QuestionDisplay;



@Entity
@Table(name = "ExamHasSettings")
public class ExamHasSettings implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer examSettingId;
	private Boolean allowReattempts = false;
	private Integer reattemptCount = 0;
	private Boolean allowExamResume = false;
	private Integer allowExamResumeCount = 0;
	private QuestionDisplay displayQuestion;
	private Boolean allowBackButton = false;
	private Boolean privacy = false;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	
	public ExamHasSettings() {
	}


	public ExamHasSettings(Boolean allowReattempts, Integer reattemptCount, Boolean allowExamResume,
			Integer allowExamResumeCount, QuestionDisplay displayQuestion, Boolean allowBackButton, Boolean privacy,
			Boolean active, Date created, Long createdBy, Date lastModified, Long lastModifiedBy) {
		super();
		this.allowReattempts = allowReattempts;
		this.reattemptCount = reattemptCount;
		this.allowExamResume = allowExamResume;
		this.allowExamResumeCount = allowExamResumeCount;
		this.displayQuestion = displayQuestion;
		this.allowBackButton = allowBackButton;
		this.privacy = privacy;
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


	@Column(name = "allowReattempts")
	public Boolean getAllowReattempts() {
		return allowReattempts;
	}


	public void setAllowReattempts(Boolean allowReattempts) {
		this.allowReattempts = allowReattempts;
	}


	@Column(name = "reattemptCount")
	public Integer getReattemptCount() {
		return reattemptCount;
	}

	
	public void setReattemptCount(Integer reattemptCount) {
		this.reattemptCount = reattemptCount;
	}

	@Column(name = "allowExamResume")
	public Boolean getAllowExamResume() {
		return allowExamResume;
	}


	public void setAllowExamResume(Boolean allowExamResume) {
		this.allowExamResume = allowExamResume;
	}


	@Column(name = "allowExamResumeCount")
	public Integer getAllowExamResumeCount() {
		return allowExamResumeCount;
	}


	public void setAllowExamResumeCount(Integer allowExamResumeCount) {
		this.allowExamResumeCount = allowExamResumeCount;
	}

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "displayQuestion", nullable = false)
	public QuestionDisplay getDisplayQuestion() {
		return displayQuestion;
	}


	public void setDisplayQuestion(QuestionDisplay displayQuestion) {
		this.displayQuestion = displayQuestion;
	}

	@Column(name = "allowBackButton")
	public Boolean getAllowBackButton() {
		return allowBackButton;
	}


	public void setAllowBackButton(Boolean allowBackButton) {
		this.allowBackButton = allowBackButton;
	}

	@Column(name = "privacy")
	public Boolean getPrivacy() {
		return privacy;
	}


	public void setPrivacy(Boolean privacy) {
		this.privacy = privacy;
	}


	@Column(name = "active",nullable = false)
	public Boolean getActive() {
		return active;
	}




	public void setActive(Boolean active) {
		this.active = active;
	}



	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
	public Date getCreated() {
		return created;
	}




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
