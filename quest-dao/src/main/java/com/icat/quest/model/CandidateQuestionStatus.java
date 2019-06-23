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

import com.icat.quest.common.vo.AnswerType;



@Entity
@Table(name = "CandidateQuestionStatus")
public class CandidateQuestionStatus implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer testConductorHasTestCodeId;
	private Integer examSectionHasQuestionId;
	private Integer candidateId;
	private Boolean visited;
	private Boolean markedForReview;
	private Boolean answeredAndMarkedForReview;
	private Boolean answered;
	private AnswerType userAnswer;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	
	 
	public CandidateQuestionStatus() {
	}



	public CandidateQuestionStatus(Integer testConductorHasTestCodeId,Integer examSectionHasQuestionId, Integer candidateId, Boolean visited,
			Boolean markedForReview, Boolean answeredAndMarkedForReview, Boolean answered, AnswerType userAnswer,
			Date created, Long createdBy, Date lastModified, Long lastModifiedBy) {
		super();
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
		this.examSectionHasQuestionId = examSectionHasQuestionId;
		this.candidateId = candidateId;
		this.visited = visited;
		this.markedForReview = markedForReview;
		this.answeredAndMarkedForReview = answeredAndMarkedForReview;
		this.answered = answered;
		this.userAnswer = userAnswer;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "testConductorHasTestCodeId", nullable = false)
	public Integer getTestConductorHasTestCodeId() {
		return testConductorHasTestCodeId;
	}

	public void setTestConductorHasTestCodeId(Integer testConductorHasTestCodeId) {
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
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


	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "userAnswer", nullable = false)
	public AnswerType getUserAnswer() {
		return userAnswer;
	}



	public void setUserAnswer(AnswerType userAnswer) {
		this.userAnswer = userAnswer;
	}



	@Column(name = "visited")
	public Boolean getVisited() {
		return visited;
	}


	public void setVisited(Boolean visited) {
		this.visited = visited;
	}


	@Column(name = "markedForReview")
	public Boolean getMarkedForReview() {
		return markedForReview;
	}


	public void setMarkedForReview(Boolean markedForReview) {
		this.markedForReview = markedForReview;
	}


	@Column(name = "answeredAndMarkedForReview")
	public Boolean getAnsweredAndMarkedForReview() {
		return answeredAndMarkedForReview;
	}


	public void setAnsweredAndMarkedForReview(Boolean answeredAndMarkedForReview) {
		this.answeredAndMarkedForReview = answeredAndMarkedForReview;
	}


	@Column(name = "answered")
	public Boolean getAnswered() {
		return answered;
	}


	public void setAnswered(Boolean answered) {
		this.answered = answered;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
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
