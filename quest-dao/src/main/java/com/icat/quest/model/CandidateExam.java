package com.icat.quest.model;

import java.io.Serializable;
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

import com.icat.quest.common.vo.AnswerType;

@Entity
@Table(name = "CandidateExam")
public class CandidateExam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long candidateExamId;
	private Integer testConductorHasTestCodeId;
	private Integer examSectionHasQuestionId;
	private Integer candidateId;
	private AnswerType userAnswer;
	private Boolean active;
	private Date created;

	public CandidateExam() {
		super();
	}

	public CandidateExam(Integer testConductorHasTestCodeId, Integer examSectionHasQuestionId, Integer candidateId, AnswerType userAnswer, Boolean active,
			Date created) {
		super();
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
		this.examSectionHasQuestionId = examSectionHasQuestionId;
		this.candidateId = candidateId;
		this.userAnswer = userAnswer;
		this.active = active;
		this.created = created;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "candidateExamId", unique = true, nullable = false)
	public Long getCandidateExamId() {
		return candidateExamId;
	}

	public void setCandidateExamId(Long candidateExamId) {
		this.candidateExamId = candidateExamId;
	}

	
	@Column(name = "testConductorHasTestCodeId", nullable = false)
	public Integer getTestConductorHasTestCodeId() {
		return testConductorHasTestCodeId;
	}

	public void setTestConductorHasTestCodeId(Integer testConductorHasTestCodeId) {
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
	}

	@Column(name = "examSectionHasQuestionId", nullable = false)
	public Integer getExamSectionHasQuestionId() {
		return examSectionHasQuestionId;
	}

	public void setExamSectionHasQuestionId(Integer examSectionHasQuestionId) {
		this.examSectionHasQuestionId = examSectionHasQuestionId;
	}

	@Column(name = "candidateId", nullable = false)
	public Integer getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "userAnswer", nullable = false)
	public AnswerType getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(AnswerType userAnswer) {
		this.userAnswer = userAnswer;
	}

	@Column(name = "active", nullable = false)
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

}
