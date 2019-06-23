package com.icat.quest.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ExplanationDescription")
public class ExplanationDescription implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer explanationId;
	private Integer questionId;
	private byte[] explanation;
	private Boolean explanationTextType;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;

	public ExplanationDescription() {
	}

	public ExplanationDescription(Integer explanationId, Integer questionId, byte[] explanation,
			Boolean explanationTextType, Boolean active, Date created, Long createdBy, Date lastModified,
			Long lastModifiedBy) {
		super();
		this.explanationId = explanationId;
		this.questionId = questionId;
		this.explanation = explanation;
		this.explanationTextType = explanationTextType;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "explanationId", unique = true, nullable = false)
	public Integer getExplanationId() {
		return explanationId;
	}

	public void setExplanationId(Integer explanationId) {
		this.explanationId = explanationId;
	}

	@Column(name = "questionId")
	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	@Lob
	@Column(name = "explanation", columnDefinition = "BLOB")
	public byte[] getExplanation() {
		return explanation;
	}

	public void setExplanation(byte[] explanation) {
		this.explanation = explanation;
	}

	@Column(name = "explanationTextType")
	public Boolean getExplanationTextType() {
		return explanationTextType;
	}

	public void setExplanationTextType(Boolean explanationTextType) {
		this.explanationTextType = explanationTextType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
