package com.icat.quest.model;

import java.io.Serializable;
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
@Table(name = "CandidateExamSummary")
public class CandidateExamSummary implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long candidateExamSummaryId;
	private Integer testConductorHasTestCodeId;
	private Integer examId;
	private Integer examSectionId;
	private Integer examSectionHasQuestionId;
	private Integer candidateId;
	private Integer questionCategoryId;
	private Boolean answerFlag;
	private Float questionMarks;
	private Float questionNegativeMarks;
	private Integer questionNumber;
	private Boolean sicoFlag;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	public CandidateExamSummary() {
		super();
	}
	
	


	public CandidateExamSummary(Integer testConductorHasTestCodeId ,Integer examId, Integer examSectionId, Integer examSectionHasQuestionId,
			Integer candidateId, Integer questionCategoryId, Boolean answerFlag, Float questionMarks,
			Float questionNegativeMarks, Integer questionNumber, Boolean sicoFlag, Boolean active, Date created,
			Long createdBy, Date lastModified, Long lastModifiedBy) {
		super();
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
		this.examId = examId;
		this.examSectionId = examSectionId;
		this.examSectionHasQuestionId = examSectionHasQuestionId;
		this.candidateId = candidateId;
		this.questionCategoryId = questionCategoryId;
		this.answerFlag = answerFlag;
		this.questionMarks = questionMarks;
		this.questionNegativeMarks = questionNegativeMarks;
		this.questionNumber = questionNumber;
		this.sicoFlag = sicoFlag;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}




	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "candidateExamSummaryId", unique = true, nullable = false)
	public Long getCandidateExamSummaryId() {
		return candidateExamSummaryId;
	}




	public void setCandidateExamSummaryId(Long candidateExamSummaryId) {
		this.candidateExamSummaryId = candidateExamSummaryId;
	}

	@Column(name = "testConductorHasTestCodeId", nullable = false)
	public Integer getTestConductorHasTestCodeId() {
		return testConductorHasTestCodeId;
	}

	public void setTestConductorHasTestCodeId(Integer testConductorHasTestCodeId) {
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
	}


	@Column(name = "examId")
	public Integer getExamId() {
		return examId;
	}




	public void setExamId(Integer examId) {
		this.examId = examId;
	}




	@Column(name = "examSectionId")
	public Integer getExamSectionId() {
		return examSectionId;
	}




	public void setExamSectionId(Integer examSectionId) {
		this.examSectionId = examSectionId;
	}




	@Column(name = "examSectionHasQuestionId")
	public Integer getExamSectionHasQuestionId() {
		return examSectionHasQuestionId;
	}




	public void setExamSectionHasQuestionId(Integer examSectionHasQuestionId) {
		this.examSectionHasQuestionId = examSectionHasQuestionId;
	}




	@Column(name = "candidateId")
	public Integer getCandidateId() {
		return candidateId;
	}




	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}



	@Column(name = "questionCategoryId")
	public Integer getQuestionCategoryId() {
		return questionCategoryId;
	}




	public void setQuestionCategoryId(Integer questionCategoryId) {
		this.questionCategoryId = questionCategoryId;
	}




	@Column(name = "answerFlag")
	public Boolean getAnswerFlag() {
		return answerFlag;
	}




	public void setAnswerFlag(Boolean answerFlag) {
		this.answerFlag = answerFlag;
	}




	@Column(name = "questionMarks")
	public Float getQuestionMarks() {
		return questionMarks;
	}




	public void setQuestionMarks(Float questionMarks) {
		this.questionMarks = questionMarks;
	}



	@Column(name = "sicoFlag")
	public Boolean getSicoFlag() {
		return sicoFlag;
	}




	public void setSicoFlag(Boolean sicoFlag) {
		this.sicoFlag = sicoFlag;
	}




	@Column(name = "questionNegativeMarks")
	public Float getQuestionNegativeMarks() {
		return questionNegativeMarks;
	}




	public void setQuestionNegativeMarks(Float questionNegativeMarks) {
		this.questionNegativeMarks = questionNegativeMarks;
	}




	@Column(name = "questionNumber")
	public Integer getQuestionNumber() {
		return questionNumber;
	}




	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
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

	@Column(name = "createdBy", nullable = false)
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

	@Column(name = "lastModifiedBy", nullable = false)
	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
